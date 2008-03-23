
package ultigpx;

import java.io.*;
import java.util.*;
import org.jdom.*;
import java.awt.geom.*;		// For calculating distance between points

public class UltiGPX
{
    UGPXFile file;
    MainView view;
    //guigtx.GuiGTXApp view;
    public static void main(String args[])
    {
        try
        {
            Installer.install();
            new UltiGPX();
        }
        catch (IOException e)
        {
            System.out.println("Failed to install UltiGPX");
            System.out.println(e);
        }
        //System.out.println(args[0]);
    }
    
    public UltiGPX()
    {
        
        System.out.println("Hello, World!");
        
        //importGPX("example2.gpx");
        //exportGPX("example2.kml");
        
        
        view = new MainView(this);
        
        
        
        
        
    }
    
    public void importGPX(String filename)
    {
        try
        {
            file = GPXImporter.importGPX(filename);
            reduceFile(file);
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
    
    public void exportGPX(String filename)
    {
        try
        {
            GPXExporter.exportGPX(file, filename);
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