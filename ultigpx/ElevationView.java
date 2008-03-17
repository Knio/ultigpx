
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;

/**
 * Quick and dirty to see if I can get something up and running.
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
        Double le = getWidth()/(lon_high-lon_low);
        Double ls = getHeight() / (elev_high - elev_low);
        Waypoint last = null;
        
        //Now we can draw the waypoints
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
            			g2d.drawLine((int)(last.getLon()*ls), (int)(getHeight() - (last.getEle()*le)), 
            					(int)(wpt.getLon()*ls), (int)(getHeight() - (wpt.getEle()*le)));
            		}
            		last = wpt;
        		}
        	}
        }
        
        for(Waypoint wpt : main.file.waypoints() ) {
    			g2d.drawOval((int)(wpt.getLon()*ls), (int)(getHeight() - (wpt.getEle()*le)), 
    					2,2);
        }
        
        g2d.setPaint(Color.GREEN);
        
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
    }
}
































