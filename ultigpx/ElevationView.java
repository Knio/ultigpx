
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
	private double lon_low;
	private double lon_high;

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
		if(wpt.getEle() > elev_high)
			elev_high = wpt.getEle();
		if(wpt.getEle() < elev_low)
			elev_low = wpt.getEle();
		if(wpt.getLon() > lon_high)
			lon_high = wpt.getLon();
		if(wpt.getLon() < lon_low)
			lon_low = wpt.getLon();
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
		lon_low = 999999999;
		elev_high = -999999999;
		lon_high = -999999999;
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
		Double ls = getWidth()/(lon_high-lon_low);
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
		if((elev_high-elev_low)/gscale < 5) {
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
				if(last != null) {					
					g2d.drawLine((int)(last.getLon()*ls), (int)(getHeight() - (last.getEle()*le)), 
							(int)(wpt.getLon()*ls), (int)(getHeight() - (wpt.getEle()*le)));
				}
				last = wpt;
			}
		}
		last = null;
		for(Track trk : main.file.tracks()) {       
			for(TrackSegment ts : trk) {
				for(Waypoint wpt : ts ) {
					if(last != null) {
						System.out.println(lon_low);
						System.out.println(lon_high);
						g2d.drawLine((int)((last.getLon() - lon_low)*ls), (int)(getHeight() - ((last.getEle() - elev_low)*le)), 
								(int)((wpt.getLon() - lon_low)*ls), (int)(getHeight() - ((wpt.getEle() - elev_low)*le)));
					}
					last = wpt;
				}
			}
		}

		for(Waypoint wpt : main.file.waypoints() ) {
			g2d.drawOval((int)(wpt.getLon()*ls), (int)(getHeight() - (wpt.getEle()*le)), 
					2,2);
		}				
	}
}
































