
package ultigpx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

import ultigpx.BasicMapView.State;

/**
 * Similar to the other Elevation view, this one projects based on the
 * "dist" field of the Waypoints, which is their distance along
 * their respective tracks.
 * 
 * 
 * @author Nathan
 *
 */
		
class ElevationViewDist extends BasicMapView
{
	//this is the track that the view is rendering.
	Track focustrack = null;
	
	double scroll_elev = 0.0;
	double scale_elev = 0.0001;
	
	//this is the x coord scale, distance from start of track.
	double scroll_dist = 0.0;
	double scale_dist = 1.0; 
	
    public ElevationViewDist(UltiGPX main)
    {
        super(main);
    }
    
    
    /**
     * Override the standard projection in favour of a side-view
     * projection. Also override the use of longitude in favour of
     * distance from start of track.
     */
    protected Point2D project(double lon, double lat)
    {
        double x = lon - this.scroll_dist;
        double y = lat - this.scroll_elev;        
        
        //the window scale
        x *= scale_dist;
        y *= scale_elev; 
        
        //our projection should be relative to the centre of the window,
        // not the origin. Note the 
        x += getWidth() /2.0;
        y -= getHeight()/2.0;
        
        return new Point2D.Double(x, -y);
    } 
    protected Point2D project(Waypoint wp)
    {
        return project(wp.dist, wp.getEle());
    }
    protected Point2D project(Point2D p)
    {
        return project(p.getX(), p.getY());
    }
    
    protected Point2D inverseproject(double x, double y)
    {
        double lon = x;
        double lat =-y;
        
        lon -= getWidth() /2.0;
        lat += getHeight()/2.0;
        
        lon /= scale_dist;
        lat /= scale_elev;
        
        lon = lon + this.scroll_dist;
        lat = lat + this.scroll_elev;
        
        return new Point2D.Double(lon, lat);
    }
    
    /*
     * we don't want clamping on our vertical scrolling.
     * hold our own up-down variable for verticle scrolling
     */
    protected void scroll(double lon, double lat)
    {
        //lon = Math.max(-180, lon);
        //lon = Math.min( 180, lon);        
        
        this.scroll_dist = lon;
        this.scroll_elev = lat;
        
        main.view.refreshmap();
        //repaint();
    }
    
    /*
     * we also hold our own scaling parameters.
     */
    protected void scale(double s)
    {
        s = Math.min(s, 100);	// how near we can go
        s = Math.max(s, 0.002); // how far out we can go
        scale_elev = s;
        scale_dist = s;
        
        main.view.refreshmap();
        //repaint();
        
    }
    /*
     * we're scaling only our vertical elevation value, not the 
     * global scale values of the other maps.
     */
    protected void scaleBy(double s)
    {
    	scale(scale_elev*s);
    }
    
    /*
     *  We change the elevation of the point, not the latitude.
     * @param e
     */
    protected void movePoint(double x, double y) {
		Point2D click = new Point2D.Double(x, y);
		Point2D world = inverseproject(click);
		
		Waypoint wp = (Waypoint)main.selected.get();

		wp.setEle(world.getY());
		
		//this keeps the views synced up. by Nate
		main.view.refreshmap();
		//repaint();
	}
    
    /**
     * zooms the map so that all of the loaded objects
     * fit in the screen.
     */
     public void fill()
     {
    	 if(focustrack == null)
    		 return;    
    	 
    	 focustrack.refreshDist();
         
         double max_lon = Double.NEGATIVE_INFINITY;
         double max_ele = Double.NEGATIVE_INFINITY;
         double min_lon = Double.POSITIVE_INFINITY;
         double min_ele = Double.POSITIVE_INFINITY;

         for(TrackSegment ts : focustrack.getArray())
        	 for (Waypoint i:ts)
        	 {
        		 max_lon = Math.max(max_lon, i.dist);
        		 max_ele = Math.max(max_ele, i.getEle());
        		 min_lon = Math.min(min_lon, i.dist);
        		 min_ele = Math.min(min_ele, i.getEle());
        	 }

         double lon = (max_lon + min_lon) / 2;
         double ele = (max_ele + min_ele) / 2;
         
         scroll(lon, ele);

         //double s1 = 0.005 * getHeight() / Math.abs(max_ele - min_ele);
         double s2 = 0.9 * getWidth() / Math.abs(max_lon - min_lon);

         scale(s2);
     }
    
    /*
     * 	Again, we don't want a legend.
     */
    protected void renderLegend()
    {    	
    }
    
    public void selectionChanged()
    {
    	Track old = focustrack;
    	
    	// we can only focus on one track, so just pick the last one to
    	// be selected.
    	for (Track i : main.selected.tracks())
        {
            focustrack = i;
        }
    	
    	//only re-fit if our track has actually changed
    	if(old != focustrack) {
    		fill();        	
    	}
    	repaint();
    }
    
    /**
     * This view only renders a single selected track, so we
     * need a bit of customized drawing.
     */
    protected void render()
    {
    	renderGrid();
    	renderHorizontalGrid();
    	
    	if(focustrack != null) {
    		//update the distances between points.
    		// this theoretically wouldn't have to be done every frame,
    		// but we have no notification event if the point's positions
    		// have been changed (like a "dirty" flag) so we'll just keep
    		// it robust and refresh every time we render.
    		focustrack.refreshDist();

    		labelhints.clear();
    		labelhints2 = new RectQuadTree(getVisibleRect());    	        

    		g.setFont(new Font("Arial", 0, FONT_SIZE));
    		
    		// render selected wps so they have label priority
            forcecolor  = true;
            
            linecolor   = SELECTED_COLOR;
            pointcolor  = SELECTED_COLOR;
            
            linesize    = 5.0f;
            pointsize   = 2*WAYPOINT_SIZE;
            
        	g.setStroke(new BasicStroke(5.0f));
    		for(Waypoint wp : main.selected.waypoint)
    		{    		    		
    			render(wp);
    		}


    		// render the focus track
    		forcecolor  = false;
            
            linecolor   = TRACK_COLOR;
            pointcolor  = POINT_COLOR;
            
            linesize    = LINE_SIZE;
            pointsize   = POINT_SIZE;
            
            
            g.setStroke(new BasicStroke(2.0f));
    		
    		render(focustrack);
    	}
    }
    
	/**
	 * Draws the scaled grid for height
	 * @param g2d
	 */
	private void renderGrid()
	{		
		//This part is for drawing the grid
		// This all might be put into private functions later to
		// keep things nice and clean.
		//scale is the "distance" between elevation grid lines.
		// we want to shoot for about 10 lines in the view, any more is too cluttery.
		double elev_high = inverseproject(0,0).getY();
		double elev_low = inverseproject(0,getHeight()).getY();
		double le = (getHeight()) / (elev_high - elev_low);
		double gscale = 1;		
		
		while((elev_high-elev_low)/gscale > 10) {
			gscale *= 10;
		}
		// double the density of lines if we can "afford" it, eg: from 100m lines to 50m lines.
		if(((elev_high-elev_low)/gscale < 5) && (gscale > 1)) {
			gscale /= 2;
		}
		//actually draw the lines
		//ppl = pixels per line (integer distance between two lines)
		int ppl = (int)(gscale*le);

		//startline = altitude of the first line 
		// starts at the nearest appropriate elevation (aligned to 10's, 50's or what have you)
		double startline = gscale*Math.round(elev_low/gscale);
		//now convert this to a pixel coordinates of our current line-to-draw
		int cur = getHeight() - (int)((startline - elev_low)*le);

		//draw the lines from bottom to the top
		g.setPaint(Color.GRAY);
		while(cur > 0) {
			g.drawLine(0, cur, getWidth(), cur);
			g.drawString((int)startline + "m", 0, cur - 5);
			startline += gscale;
			cur -= ppl;
		}
	}
	
	private void renderHorizontalGrid()
	{		
		//This part is for drawing the grid
		// This all might be put into private functions later to
		// keep things nice and clean.
		//scale is the "distance" between elevation grid lines.
		// we want to shoot for about 10 lines in the view, any more is too cluttery.
		double dist_low = inverseproject(0,0).getX();
		double dist_high = inverseproject(getWidth(),0).getX();
		double le = (getWidth()) / (dist_high - dist_low);
		double gscale = 1;	
		
		while((dist_high-dist_low)/gscale > 10) {
			gscale *= 10;
		}
		// double the density of lines if we can "afford" it, eg: from 100m lines to 50m lines.
		if(((dist_high-dist_low)/gscale < 5) && (gscale > 1)) {
			gscale /= 2;
		}
		//actually draw the lines
		//ppl = pixels per line (integer distance between two lines)
		int ppl = (int)(gscale*le);

		//startline = altitude of the first line 
		// starts at the nearest appropriate elevation (aligned to 10's, 50's or what have you)
		double startline = gscale*Math.round(dist_low/gscale);
		//now convert this to a pixel coordinates of our current line-to-draw
		int cur = getWidth() - (int)((startline - dist_low)*le);

		//draw the lines from left to the right
		g.setPaint(Color.GRAY);
		while(cur > 0) {
			g.drawLine(getWidth() - cur, 0, getWidth() - cur, getHeight());
			g.drawString((int)startline + "m", getWidth() - cur + 2, getHeight() - 10);
			startline += gscale;
			cur -= ppl;
		}
	}
	
	/**
	 * Unlike BasicMapView, we have to ensure we're only selecting waypoints on
	 * the focused track--not from every point in existence. 
	 */
	public void select(int x, int y) {

		if (file == null || focustrack == null)
			return;

		//this is so we play nice with the existing functions
		// in the MapView eventhandler. It uses "data" as the
		// current selected track... I think.
		data = focustrack;
		Point2D click = new Point2D.Double(x,y);

		Waypoint min_w = null;

		min_w = getTrackPoint(focustrack, click);

		if (min_w != null)
		{
			//main.view.select(min_w);
			main.selected.select(min_w);
			state = State.TK_RT_WP;
		}
	}
}