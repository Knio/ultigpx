
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
	Waypoint        selected;   

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
		selected = wp;
		repaint();
	}

	/**
	 * used when looping through waypoints to find bounds for display
	 * @param wpt
	 */
	private void updateBounds(Waypoint wpt) {
		if(wpt.getTime() > 0) {
			if(wpt.getEle() > elev_high)
				elev_high = wpt.getEle();
			if(wpt.getEle() < elev_low)
				elev_low = wpt.getEle();
			if(wpt.getTime() > time_high)
				time_high = wpt.getTime();
			if(wpt.getTime() < time_low)
				time_low = wpt.getTime();
		}
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
		if(wp1.getTime() < 0 || wp2.getTime() < 0)
			return;

		System.out.println();
		
		int x1 = (int)((wp1.getTime() - time_low)*ls) + MARGIN;
		int y1 = (int)(getHeight() - ((wp1.getEle() - elev_low)*le)) + MARGIN;
		int x2 = (int)((wp2.getTime() - time_low)*ls) + MARGIN;
		int y2 = (int)(getHeight() - ((wp2.getEle()- - elev_low)*le)) + MARGIN;
		
		System.out.println("x2:" + x2 + ", y2:" + y2);
		g2d.drawLine(x1,y1,x2,y2);
	}
	/**
	 * Draws a little circle on a single waypoint
	 * @param wp1
	 */
	private void draw(Graphics2D g2d, Waypoint wpt)
	{
		if(wpt == null)
			return;
		if(wpt.getTime() < 0)
			return;
		
		int x1 = (int)((wpt.getTime() - time_low)*ls) + MARGIN;
		int y1 = (int)(getHeight() - ((wpt.getEle() - elev_low)*le)) + MARGIN;
		g2d.drawOval(	x1, y1, WAYPOINT_SIZE, WAYPOINT_SIZE); 
	}
	
	/**
	 * Refreshes the elevation view
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//TODO set this to use default colours
		g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());		


		//leave this window empty if there is no file loaded yet.
		if (main.file == null)
			return;

		// We'll iterate over the waypoints looking for the highest and lowest
		// to properly scale the graph.        
		elev_low = 999999999;
		time_low = 999999999;
		elev_high = -999999999;
		time_high = -999999999;
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
		
		// This is another "out." Basically we have no points
		// that have a time value, (we haven't changed the time bounds)
		// or else all points have the same time.
		// so we shouldn't display anything
		if(time_high <= time_low)
			return;

		//The scaling factors for longitude and elevation
		// The fudge-factor is so I "zoom out" just a little bit so
		// points near the edges get drawn.
		ls = (getWidth() - 2*MARGIN) / (time_high-time_low);
		le = (getHeight() - 2*MARGIN) / (elev_high - elev_low);
		Waypoint last = null;
		
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

		//Now we can draw the waypoints
		System.out.println("W:" + getWidth() + "H:" + getHeight());
		g2d.setPaint(Color.BLACK);
		for(Route rt : main.file.routes()) {       
			for(Waypoint wpt : rt) {
				//draw(g2d, last, wpt);						
				last = wpt;
			}
			last = null;
		}
		g2d.setPaint(Color.RED);
		for(Track trk : main.file.tracks()) {       
			for(TrackSegment ts : trk) {
				for(Waypoint wpt : ts ) {
					draw(g2d,last,wpt);					
					last = wpt;
				}
				last = null;
			}
		}

		for(Waypoint wpt : main.file.waypoints() ) {
			draw(g2d, wpt);
		}		
	}
}
