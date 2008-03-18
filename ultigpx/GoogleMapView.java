package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.io.*;
import java.util.*;


public class GoogleMapView extends MapView {

	UGPXFile	file;
	
	static final Boolean	DEBUG_MODE		= true;

	static final String 	HTML_OUT_FILE 	= "maps.html";	// File to output the HTML to

	public GoogleMapView (UltiGPX main) {
		super(main);
		this.main = main;
		//file = main.file;
		file = conTestFile();
		outputHTML();
		
		//while (true) {
		repaint();
		//}
		
		return;
	}
    
    public void fill() {}

	private UGPXFile conTestFile () {
		// Construct a new UGPXFile with nothing in it
		file = new UGPXFile();
		
		Waypoint		tempWP;
		Track	 		tempTK;
		TrackSegment 	tempTS;
		//Route	 		tempRT;
		
		// Add a Waypoint to the UGPXFile
		tempWP = new Waypoint("name","desc",(double)(43.90),(double)(-80.07),(double)(0),0);
		file.addWaypoint(tempWP);
		
		// Create a new TrackSegment with 5 points
		tempTS = new TrackSegment();
		tempTS.add(new Waypoint("name","desc",(double)(43.28),(double)(-80.07),(double)(0),0));
		tempTS.add(new Waypoint("name","desc",(double)(43.51),(double)(-79.95),(double)(0),0));
		tempTS.add(new Waypoint("name","desc",(double)(43.69),(double)(-79.80),(double)(0),0));
		tempTS.add(new Waypoint("name","desc",(double)(43.76),(double)(-79.59),(double)(0),0));
		tempTS.add(new Waypoint("name","desc",(double)(43.83),(double)(-79.17),(double)(0),0));
		
		// Create a new Track and add the TrackSegment to it
		tempTK = new Track();
		tempTK.add(tempTS);
		
		// Add the Track to the UGPXFile
		file.addTrack(tempTK);
		
		// Return the constructed UGPXFile
		return file;
	}

    
/*	public void refresh () {

		return;
		
	}*/
	
	private String getPolyString (Color color, ArrayList<Waypoint> elements) {
		
		String retString = "		points = [";		// The string we will return
		Waypoint tempWP;								// Store the waypoint while we are working with it
		
		// Add a comment with the Route/Track info
		// The Route and Track types currently do not have the name and desc set up, uncomment when they do
		/*
		String retString = ""
		if (elements instanceof Route) retString = retString + "		//Route: ";
        if (elements instanceof TrackSegment) retString = retString + "		//Track: ";
		if (elements.getName() != null) retString = retString + elements.getName();
		else retString = retString + "Null";
		retString = " - ";
		if (elements.getDesc() != null) retString = retString + elements.getDesc();
		retString = retString + "\n";
		*/
		
		// Create an Iterator to loop over the elements
		Iterator iter = elements.iterator();
		for(;iter.hasNext();) {
			tempWP = (Waypoint) iter.next();
			
			if (tempWP.enabled) {
				// Add the Waypoint to the javascript array of waypoints
				retString = retString + "new GLatLng("+Double.toString(tempWP.lat)+","+Double.toString(tempWP.lon)+"),";
				// Add the Waypoint to the beginning to have a dot drawn on it
				retString = "		map.addOverlay(new GMarker(new GLatLng("+Double.toString(tempWP.lat)+","+Double.toString(tempWP.lon)+"),{clickable:false, icon:icon1}));\n" + retString;
			}
			
		}
		// Remove the last comma we wrote, it isn't needed
		retString = retString.substring(0, retString.length() - 1);
		retString = retString + "];\n";
		retString = retString + "		map.addOverlay(new GPolyline(points,'#000000',3,1));\n\n";
		
		return retString;
		
	}
	
	private String getPointString (Color color, Waypoint wp) {
		
		// Add a comment with the Waypoint info
		String retString = "		//Waypoint: ";
		if (wp.getName() != null) retString = retString + wp.getName();
		else retString = retString + "Null";
		retString = retString + " - ";
		if (wp.getDesc() != null) retString = retString + wp.getDesc();
		else retString = retString + "Null";
		retString = retString + "\n";
		
		retString = retString + "		map.addOverlay(new GMarker(new GLatLng("+Double.toString(wp.lat)+","+Double.toString(wp.lon)+"),{clickable:false, icon:icon1}));\n";
		return retString;
	}

	private void outputHTML () {
		
		// Construct a platform-independent file name
		File infile = new File(HTML_OUT_FILE);			
		
		try {
			// If the file does not exist, create it
			if (!infile.exists()) infile.createNewFile();
			
			// Open the file
			FileWriter writer = new FileWriter(infile);	
			String wtext;
			String drawcode = "";
			
			// For each TrackSegment in each Track, append the string to draw it
			for (Track tk : file.tracks())
				if (tk.enabled)
					for (TrackSegment ts : tk)
                		drawcode = drawcode + getPolyString(tk.color, ts);
			
			// For each Route, append the string to draw it
			for (Route rt : file.routes())
				if (rt.enabled)
					drawcode = drawcode + getPolyString(rt.color, rt);
			
			// For each Waypoint, append the string to draw it
			for (Waypoint wp : file.waypoints())
				if (wp.enabled)
					drawcode = drawcode + getPointString(wp.color, wp);
				
				
				
				
			// Write the HTML file
			//char dc = '"';
			
			wtext = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n";
			wtext = wtext + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";
			wtext = wtext + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n";
  			wtext = wtext + "<head>\n";
    		wtext = wtext + "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n";
    		wtext = wtext + "<title>Google Maps JavaScript API Example</title>\n";
    		wtext = wtext + "<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAv8I7SSVba2lHsj8Pc-r5SBTTdj5zBXD9jRFEjQGoMBeg8N65dBQ1Q8m1Xi4E-Q4o6l_EaKjx6--APw\" type=\"text/javascript\"></script>\n";
    		wtext = wtext + "<script type=\"text/javascript\">\n";
			wtext = wtext + "//<![CDATA[\n";
			wtext = wtext + "function load() {\n";
			wtext = wtext + "	if (GBrowserIsCompatible()) {\n";
			wtext = wtext + "		var map = new GMap2(document.getElementById(\"map\"));\n";
			wtext = wtext + "		map.addControl(new GLargeMapControl());\n";
			wtext = wtext + "		map.addControl(new GMapTypeControl());\n";
			wtext = wtext + "		map.setCenter(new GLatLng(43.51,-79.95), 8);\n\n";
			
			wtext = wtext + "		var icon1 = new GIcon();\n";
   			wtext = wtext + "		icon1.image = \"point_b.png\";\n";
			wtext = wtext + "		icon1.iconSize = new GSize(20, 20);\n";
    		wtext = wtext + "		icon1.iconAnchor = new GPoint(10,10);\n";
			wtext = wtext + "		var points;\n\n";
			
			wtext = wtext + drawcode;
			
			wtext = wtext + "	}\n";
			wtext = wtext + "}\n";
			
    		wtext = wtext + "//]]>\n";
  			wtext = wtext + "</script>\n";
			wtext = wtext + "</head>\n";
  			wtext = wtext + "<body onload=\"load()\" onunload=\"GUnload()\">\n";
			wtext = wtext + "<div id=\"map\" style=\"position:absolute; top: 0px; left: 0px; width: 1000px; height: 500px\"></div>\n";
			wtext = wtext + "</body>\n";
			wtext = wtext + "</html>\n";
			
			
			writer.write(wtext);
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error creating FileWriter.");
		}
		
		return;
	}
    
}

































