
package ultigpx;

import java.io.*;
import java.util.*;
import org.jdom.*;
import java.awt.geom.*;		// For calculating distance between points

public class UltiGPX
{
    UGPXFile file;
    MainView view;
    //GuiGTXApp view;
    public static void main(String args[])
    {
        
        new UltiGPX();
        //System.out.println(args[0]);
    }
    
    public UltiGPX()
    {
        
        System.out.println("Hello, World!");
        
		importGPX("example2.gpx");
		reduceFile(file);
        
        view = new MainView(this);
        //view = GuiGTXApp.getApplication();
        //view.startup(this);
        
        /* Debugging
        
        System.out.println("ROUTES\n\n");
        for (Route rt : file.routes())
        {
            System.out.println(rt);
        }
        
        System.out.println("TRACKS\n\n");
        for (Track tk : file.tracks())
        {
            System.out.println(tk);
        }
        
        System.out.println("WAYPOINTS\n\n");
        for (Waypoint wp : file.waypoints())
        {
            System.out.println(wp);
        }
        //*/
        
        
        
        
        
    }
    
    public void importGPX(String filename)
    {
        try
        {
            file = new GPXImporter().importGPX(filename);
        }
        catch (JDOMException e)
        {
            System.out.println("Error parsing file:");
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println("Error reading file:");
            System.out.println(e);
        }
    }
    
    //stub until importer works. delete me later
    public UGPXFile sample()
    {
        ArrayList<Waypoint> wp = new ArrayList<Waypoint>();
        ArrayList<Track>    tk = new ArrayList<Track>();
        ArrayList<Route>    rt = new ArrayList<Route>();
        
        
        wp.add(new Waypoint("WP001", "Description", 50, 50, 0, 0));
        wp.add(new Waypoint("WP002", "Description", 50-1, 50-1, 0, 0));
        wp.add(new Waypoint("WP003", "Description", 51, 51, 0, 0));
        
        wp.add(new Waypoint("WP001", "Description", 0, 0, 0, 0));
        wp.add(new Waypoint("WP002", "Description", -1, -1, 0, 0));
        wp.add(new Waypoint("WP003", "Description", 1, 1, 0, 0));
        
        wp.add(new Waypoint("NORTH", "", 80,   0, 0, 0));
        wp.add(new Waypoint("SOUTH", "",-80,   0, 0, 0));
        wp.add(new Waypoint("EAST ", "",  0, 170, 0, 0));
        wp.add(new Waypoint("WEST ", "",  0,-170, 0, 0));
        
        TrackSegment ts = new TrackSegment();
        ts.add(new Waypoint("", "",  0,  0, 0, 0));
        ts.add(new Waypoint("", "",  0,  1, 0, 0));
        ts.add(new Waypoint("", "",  1,  1, 0, 0));
        ts.add(new Waypoint("", "",  1,  2, 0, 0));
        ts.add(new Waypoint("", "",  2,  3, 0, 0));
        ts.add(new Waypoint("", "",  3,  4, 0, 0));
        ts.add(new Waypoint("", "",  4,  5, 0, 0));
        ts.add(new Waypoint("", "",  4,  6, 0, 0));
        
        Track t = new Track();
        t.add(ts);
        
        tk.add(t);
        
        return new UGPXFile(wp, tk, rt);
    }
	
	// Reduce the number of waypoints in a file
	public void reduceFile (UGPXFile file) {
		if (file == null) return;
		
		// If two points are closer than this, the later will not be drawn
		double cutoff = 0.01;
		
		Point2D oldPT;
		
		for (Track tk : file.tracks()) {
			for (TrackSegment ts : tk) {
				if (ts.size() >= 10) {
					oldPT = null;
					for (Waypoint wp : ts) {
						if ((oldPT != null) && (oldPT.distance(new Point2D.Double(wp.lat,wp.lon)) < cutoff)) wp.enabled = false;
						else { oldPT = new Point2D.Double(wp.lat,wp.lon); }
					}
				}
			}
			
		}
		
		for (Route rt : file.routes()) {
			if (rt.size() >= 10) {
				oldPT = null;
				for (Waypoint wp : rt) {
					if ((oldPT != null) && (oldPT.distance(new Point2D.Double(wp.lat,wp.lon)) < cutoff)) wp.enabled = false;
					else { oldPT = new Point2D.Double(wp.lat,wp.lon); }
				}
			}
		}
		
		oldPT = null;
		for (Waypoint wp : file.waypoints()) {
			if ((oldPT != null) && (oldPT.distance(new Point2D.Double(wp.lat,wp.lon)) < cutoff) && (wp.getName() == null)) wp.enabled = false;
			else { oldPT = new Point2D.Double(wp.lat,wp.lon); }
		}
		
		return;
	}
    
    
}