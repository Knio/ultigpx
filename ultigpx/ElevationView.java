
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
		Double ls = getWidth() / (time_high-time_low);
		Double le = getHeight() / (elev_high - elev_low);
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
		g2d.setPaint(Color.BLACK);
		for(Route rt : main.file.routes()) {       
			for(Waypoint wpt : rt) {
				if(wpt.getTime() > 0) {
					if(last != null) {					
						g2d.drawLine((int)(last.getTime()*ls), (int)(getHeight() - (last.getEle()*le)), 
								(int)(wpt.getTime()*ls), (int)(getHeight() - (wpt.getEle()*le)));
					}
					last = wpt;
				}
			}
		}
		last = null;
		for(Track trk : main.file.tracks()) {       
			for(TrackSegment ts : trk) {
				for(Waypoint wpt : ts ) {
					if(last != null) {
						if(wpt.getTime() > 0) {
							g2d.drawLine((int)((last.getTime() - time_low)*ls), (int)(getHeight() - ((last.getEle() - elev_low)*le)), 
									(int)((wpt.getTime() - time_low)*ls), (int)(getHeight() - ((wpt.getEle() - elev_low)*le)));
						}
					}
					last = wpt;
				}
			}
		}

		for(Waypoint wpt : main.file.waypoints() ) {
			if(wpt.getTime() > 0) {
				g2d.drawOval((int)(wpt.getTime()*ls), (int)(getHeight() - (wpt.getEle()*le)), 
						2,2);
			}
		}				
	}
}
