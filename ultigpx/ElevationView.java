
package ultigpx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.*;

/**
 * I know this is HIDEOUSLY UGLY but I just wanted to see if technically
 *  it would work to sync up the elevation view and the other map views 
 *  in a straightforward way. I pretty much re-implemented random functions
 *  from the Map-View and BasicMapView as necessary, to allow for this
 *  "side view." Which amounts to basically re-wiring "elevation" into
 *  everywhere there is a reference to "latitude." 
 * @author Nathan
 *
 */
		
class ElevationView extends BasicMapView
{
	double scroll_elev;
	double scale_elev = 0.0001;
	
    public ElevationView(UltiGPX main)
    {
        super(main);
    }
    
    /**
     * Override the standard projection in favour of a side-view
     * projection.
     */
    protected Point2D project(double lon, double lat)
    {
        // http://en.wikipedia.org/wiki/Mercator_projection
        double x = lon - this.lon;
        double y = lat - this.scroll_elev;        
        
        x *= scale;
        y *= scale * scale_elev; 
        
        x += getWidth() /2.0;
        y -= getHeight()/2.0;
        
        return new Point2D.Double(x, -y);
    }
    protected Point2D project(Waypoint wp)
    {
        return project(wp.lon, wp.getEle());
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
        
        lon /= scale;
        lat /= scale * scale_elev;
        
        lon = lon + this.lon;
        lat = lat + this.scroll_elev;
        
        return new Point2D.Double(lon, lat);
    }
    
    /*
     * we don't want clamping on our vertical scrolling.
     * hold our own up-down variable for verticle scrolling
     */
    protected void scroll(double lon, double lat)
    {
        lon = Math.max(-180, lon);
        lon = Math.min( 180, lon);        
        
        this.lon = lon;
        this.scroll_elev = lat;
        //this.lat = lat;
        
        main.view.refreshmap();
        //repaint();
    }
    
    /*
     * we also hold our own scaling parameter. This is because
     * we want scaling here just to affect our vertical scale.
     *  Regular scaling can still be done via the main map.
     */
    protected void scale(double s)
    {
        s = Math.min(s, 1);
        s = Math.max(s, 0.000001);
        scale_elev = s;
        
        main.view.refreshmap();
        //repaint();
        
    }
    /*
     * we're scaling only our verticle elevation value, not the 
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
		wp.lon = world.getX();
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
         if (entities.size()==0)
         {
             lon     = 0;
             scroll_elev = 0;
             scale_elev = 0.0001;
             return;
         }
         
         double max_ele = entities.get(0).getEle();
         double min_ele = max_ele;
         
         for (Waypoint i:entities)
         {
             max_ele = Math.max(max_ele, i.getEle());
             min_ele = Math.min(min_ele, i.getEle());
         }
         
         double ele = (max_ele + min_ele) / 2;
         
         //we let the main map views adjust the global
         // longitudinal fit.
         scroll(this.lon, ele);

         double s1 = 0.001 * getHeight() / Math.abs(max_ele - min_ele);
         //double s2 = 0.6 * getWidth() / Math.abs(max_lon - min_lon);

         scale(s1);
     }
    
    /*
     * 	Again, we don't want a legend.
     */
    protected void renderLegend()
    { 
    }
    
    
    protected void render()
    {
        renderGrid();
        super.render();
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
}