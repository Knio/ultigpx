
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;

/**
 * No point selection yet, but we have a self-adjusting grid now.
 * @author Nathan
 *
 */
public class ElevationView extends JPanel
{
	UltiGPX         main;
	UGPXFile        file;
	Waypoint        selected_wp;
	Track        	selected_tr;
	Route       	 selected_rt;

	java.util.List<Waypoint> entities;

	private double elev_low;
	private double elev_high;
	private double time_low;
	private double time_high;
	private double ls;
	private double le;
	// this gives a bit of margin around our edge waypoints on the display
	private int MARGIN = 3;
	private int WAYPOINT_SIZE = 5;

	public ElevationView(UltiGPX main)
	{
		super();
		this.main = main;
	}

	/**
	 * identifies a point to be displayed as the "Selected" point
	 * @param wp the waypoint that is selected
	 */
	public void select(Waypoint wp)
	{
		selected_wp = wp;
		selected_tr = null;
		selected_rt = null;
		repaint();
	}
	/**
	 * identifies a track to be displayed as the "Selected" track
	 * @param tr the track that is selected
	 */
	public void select(Track tr)
	{
		selected_tr = tr;
		selected_wp = null;
		selected_rt = null;
		repaint();
	}
	/**
	 * identifies a route to be displayed as the "Selected" route
	 * @param rt the route that is selected
	 */
	public void select(Route rt)
	{
		selected_rt = rt;
		selected_tr = null;
		selected_wp = null;
		repaint();
	}

	/**
	 * used when looping through waypoints to find bounds for display
	 * @param wpt
	 */
	private void updateBounds(Waypoint wpt) {
		//if(wpt.getLon() > 0) {
			if(wpt.getEle() > elev_high)
				elev_high = wpt.getEle();
			if(wpt.getEle() < elev_low)
				elev_low = wpt.getEle();
			if(wpt.getLon() > time_high)
				time_high = wpt.getLon();
			if(wpt.getLon() < time_low)
				time_low = wpt.getLon();
		//}
	}
	/**
	 * Draw a line b'twixt these two waypoints.
	 * @param wp1
	 * @param wp2 
	 */
	private void draw(Graphics2D g2d, Waypoint wp1, Waypoint wp2)
	{
		if(wp1 == null || wp2 == null)
			return;
		//if(wp1.getLon() < 0 || wp2.getLon() < 0)
		//	return;
		
		double x1 = ((wp1.getLon() - time_low)*ls) + (double)MARGIN;
		int y1 = (int)(getHeight() - ((wp1.getEle() - elev_low)*le)) + MARGIN;
		double x2 = ((wp2.getLon() - time_low)*ls) + (double)MARGIN;
		int y2 = (int)(getHeight() - ((wp2.getEle() - elev_low)*le)) + MARGIN;
		
		//System.out.println("x2:" + x2 + ", y2:" + y2);
		g2d.drawLine((int)x1,y1,(int)x2,y2);		
	}
	/**
	 * Draws a little circle on a single waypoint
	 * @param wp1
	 */
	private void draw(Graphics2D g2d, Waypoint wpt, int size)
	{
		if(wpt == null)
			return;
		//if(wpt.getLon() < 0)
		//	return;
				
		
		int x1 = (int)((wpt.getLon() - time_low)*ls) + MARGIN;
		int y1 = (int)(getHeight() - ((wpt.getEle() - elev_low)*le)) + MARGIN;
		g2d.drawOval(	x1, y1, size, size);		
	}
	
	/**
	 * Calculates the distance between two points.
	 * @param wp1
	 * @param wp2
	 * @return the distance
	 */
	private double dist(Waypoint wp1, Waypoint wp2)
	{
		double dx = wp2.getLon() - wp1.getLon();
		double dy = wp2.getLat() - wp1.getLat();
		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	/**
	 * Draws a route, "unwrapped" based on distance, scaled
	 * to fit the window.
	 * @param g2d Graphics Context
	 * @param rt route
	 */
	private void draw(Graphics2D g2d, Route rt)
	{
		elev_low = Double.POSITIVE_INFINITY;
		elev_high = Double.NEGATIVE_INFINITY;
		double total_x = 0;
		Waypoint last = null;
		//need to find total distance first, so scaling can happen
		for(Waypoint wp : rt)
		{
			updateBounds(wp);
			if(last != null) {
				total_x += dist(wp, last);
			}
			last = wp;
		}
		le = (getHeight() - 2*MARGIN) / (elev_high - elev_low);
		draw_grid(g2d);
		
		
		//no distance between points on this route for some reason
		// simply return.
		if(total_x <= 0)
			return;
		
		//Now we can draw the route
		double old_x = 0.0;
		double cur_x = 0.0;
		last = null;

		for(Waypoint wp : rt) {
			if(last != null) {
				old_x = cur_x;
				cur_x += (dist(wp, last) * getWidth()) / total_x; 

				double x1 = old_x;
				int y1 = (int)(getHeight() - ((last.getEle() - elev_low)*le)) + MARGIN;
				double x2 = cur_x;
				int y2 = (int)(getHeight() - ((wp.getEle() - elev_low)*le)) + MARGIN;

				g2d.drawLine((int)x1,y1,(int)x2,y2);
				
				System.out.println(x1 + "," + y1 + "elev: " + last.getEle());
			}
			last = wp;
		}		
	}	
	
	/**
	 * Draws a selected Track
	 * @param g2d
	 * @param rt
	 */
	private void draw(Graphics2D g2d, Track tr)
	{
		elev_low = Double.POSITIVE_INFINITY;
		elev_high = Double.NEGATIVE_INFINITY;
		double total_x = 0;
		Waypoint last = null;
		//need to find total distance first, so scaling can happen
		for(TrackSegment trs : tr) {
			for(Waypoint wp : trs)
			{
				updateBounds(wp);
				if(last != null) {
					total_x += dist(wp, last);
				}
				last = wp;
			}
		}
		
		le = (getHeight() - 2*MARGIN) / (elev_high - elev_low);
		draw_grid(g2d);
		
		//no distance between points on this route for some reason
		// simply return.
		if(total_x <= 0)
			return;
		
		//Now we can draw the route
		double old_x = 0.0;
		double cur_x = 0.0;
		last = null;

		for(TrackSegment trs : tr) {
			for(Waypoint wp : trs) {
				if(last != null) {
					old_x = cur_x;
					cur_x += (dist(wp, last) * getWidth()) / total_x; 

					double x1 = old_x;
					int y1 = (int)(getHeight() - ((last.getEle() - elev_low)*le)) + MARGIN;
					double x2 = cur_x;
					int y2 = (int)(getHeight() - ((wp.getEle() - elev_low)*le)) + MARGIN;

					g2d.drawLine((int)x1,y1,(int)x2,y2);

					System.out.println(x1 + "," + y1 + "elev: " + last.getEle());
				}
				last = wp;
			}
		}
	}
	
	/**
	 * Draws the whole scene (and highlights any selected waypoints)
	 * @param g2d
	 */
	private void draw(Graphics2D g2d)
	{
		// We'll iterate over the waypoints looking for the highest and lowest
		// to properly scale the graph.        
		elev_low = Double.POSITIVE_INFINITY;
		time_low = Double.POSITIVE_INFINITY;
		elev_high = Double.NEGATIVE_INFINITY;
		time_high = Double.NEGATIVE_INFINITY;
		for(Route rt : main.file.routes()) {
			for(Waypoint wpt : rt) {
				updateBounds(wpt);
			}
		}
		for(Track trk : main.file.tracks()) {       
			for(TrackSegment ts : trk) {
				for(Waypoint wpt : ts ) {
					updateBounds(wpt);
				}
			}
		}
		for(Waypoint wpt : main.file.waypoints() ) {
			updateBounds(wpt);
		}

		//The scaling factors for longitude and elevation
		// The fudge-factor is so I "zoom out" just a little bit so
		// points near the edges get drawn.
		ls = (getWidth() - 2*MARGIN) / (time_high-time_low);
		le = (getHeight() - 2*MARGIN) / (elev_high - elev_low);
		
		draw_grid(g2d);

		Waypoint last = null;
		for(Route rt : main.file.routes()) {
			g2d.setPaint(Color.BLUE);
			g2d.setStroke(new BasicStroke(1.0f));
			for(Waypoint wpt : rt) {
				//draw(g2d, last, wpt);						
				last = wpt;
			}
			last = null;
		}

		for(Track trk : main.file.tracks()) {
			g2d.setPaint(Color.BLUE);
			g2d.setStroke(new BasicStroke(1.0f));

			for(TrackSegment ts : trk) {
				for(Waypoint wpt : ts ) {
					draw(g2d,last,wpt);					
					last = wpt;
				}
				last = null;
			}
		}

		for(Waypoint wpt : main.file.waypoints() ) {
			// the selected point should be big and fat.
			if(wpt == selected_wp) {
				g2d.setPaint(Color.RED);
				g2d.setStroke(new BasicStroke(3.0f));
			} else {
				g2d.setPaint(Color.BLACK);
				g2d.setStroke(new BasicStroke(1.0f));
			}
			draw(g2d, wpt, WAYPOINT_SIZE);
		}
		
	}
	
	/**
	 * Draws the scaled grid for height
	 * @param g2d
	 */
	private void draw_grid(Graphics2D g2d)
	{
		
		
		//This part is for drawing the grid
		// This all might be put into private functions later to
		// keep things nice and clean.
		//scale is the "distance" between elevation grid lines.
		// we want to shoot for about 10 lines in the view, any more is too cluttery.
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
		g2d.setPaint(Color.GRAY);
		while(cur > 0) {
			g2d.drawLine(0, cur, getWidth(), cur);
			g2d.drawString((int)startline + "m", 0, cur - 5);
			startline += gscale;
			cur -= ppl;
		}
	}
	
	/**
	 * Refreshes the elevation view
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//Antialias all nice like Tom does.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		//TODO set this to use default colours
		g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());		


		//leave this window empty if there is no file loaded yet.
		if (main.file == null)
			return;

		

		//Now we can draw the waypoints
		//System.out.println("W:" + getWidth() + "H:" + getHeight());
		g2d.setPaint(Color.BLACK);
		
		//draw the selected route, if you can.
		if(selected_rt != null) {
			draw(g2d, selected_rt);
		}		
		//otherwise draw the selected track
		else if(selected_tr != null) {
			draw(g2d, selected_tr);
		}
		//otherwise just draw the whole mess
		else {
			draw(g2d);
		}
	}
}
