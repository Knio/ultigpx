
package ultigpx;

import java.io.*;
import java.util.*;
import org.jdom.*;
import java.awt.geom.*;		// For calculating distance between points
import javax.swing.*;

public class UltiGPX
{
    UGPXFile file;
    MainView view;
    
    List<Operation> undo;
    List<Operation> redo;
    
    Selection selected;
    
    public static void main(String args[])
    {
        try
        {
            Installer.install();
        }
        catch (IOException e)
        {
            System.out.println("Failed to install UltiGPX");
            System.out.println(e);
        }
        
        UltiGPX u = new UltiGPX();
        
        
        if (args.length == 1)
        {
            System.out.println(args[0]);
            u.importGPX(args[0]);
        }
    }
    
    public UltiGPX()
    {
        
        System.out.println("Hello, World!");
        
        //importGPX("example2.gpx");
        //exportGPX("example2.kml");
        
        
        view = new MainView(this);
        
        undo = new ArrayList<Operation>();
        redo = new ArrayList<Operation>();
        
        selected = new Selection();
        
        
    }
    
    
    public void undo()
    {
        if (undo.size() == 0) return;
        System.out.println("Undo");
        Operation o = undo.remove(undo.size()-1);
        o.undo();
        redo.add(o);
        
        view.refresh();
        
    } 
    
    public void redo()
    {
        if (redo.size() == 0) return;
        System.out.println("Redo");
        Operation o = redo.remove(redo.size()-1);
        o.redo();
        undo.add(o);
        
        view.refresh();
    } 
    
    public void addOperation(Operation o)
    {
        undo.add(o);
    }
    
    
    public void importGPX(String filename)
    {
        try
        {
			if ((new File(filename).length()) >= 102400) {
				JOptionPane pane = new JOptionPane("WARNING:\nYou are about to open a file which is over 100kb in size. This may take a while, do you wish to continue opening the file?");
				Object[] options = new String[] { "Yes", "No" };
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(), "Large File");
				dialog.setVisible(true);
				Object obj = pane.getValue(); 
				int result = -1;
				for (int k = 0; k < options.length; k++)
					if (options[k].equals(obj)) result = k;
				if (result == 0) file = GPXImporter.importGPX(filename);
			}
			else {
            	file = GPXImporter.importGPX(filename);
			}
            //file = GPXImporter.importGPX(filename);
            //reduceFile(file);
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
        view.refresh();
    }
    
    public void exportGPX(String filename)
    {
        try
        {
            GPXExporter.exportToGPX(file, filename);
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