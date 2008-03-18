
package ultigpx;

import java.io.*;
import java.util.*;
import org.jdom.*;
//import org.jdesktop.application.SingleFrameApplication;

public class UltiGPX
{
    UGPXFile file;
    //MainView view;
    GuiGTXApp view;
    public static void main(String args[])
    {
        
        new UltiGPX();
        //System.out.println(args[0]);
    }
    
    public UltiGPX()
    {
        
        System.out.println("Hello, World!");
        
        file = sample(); // stub until importer works
        //importGPX("example1.gpx");
        
        //view = new MainView(this);
        view = GuiGTXApp.getApplication();
        view.startup();
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
    
    
}