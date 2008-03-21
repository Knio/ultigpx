package ultigpx;

import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import org.jdesktop.jdic.browser.*;
import java.awt.*;


public class GoogleMapView extends MapView {

	UGPXFile	file;
	WebBrowser	webBrowser;
	
	static final Boolean	DEBUG_MODE		= true;

	// HTML file to output
	static final String 	HTML_OUT_FILE 	= "maps.html";	

	public GoogleMapView (UltiGPX main) {
		super(main);
		
		if (DEBUG_MODE) System.out.println("GoogleMapView initialization started.");
		this.main = main;
		
		webBrowser = new WebBrowser();
		
		webBrowser.addWebBrowserListener(
			new WebBrowserListener() {
				boolean isFirstPage = true;
				public void downloadStarted(WebBrowserEvent event) {;}
				public void downloadCompleted(WebBrowserEvent event) {;}
				public void downloadProgress(WebBrowserEvent event) {;}
				public void downloadError(WebBrowserEvent event) {;}
				public void documentCompleted(WebBrowserEvent event) {;}
				public void titleChange(WebBrowserEvent event) {;}
				public void statusTextChange(WebBrowserEvent event) {;}
				public void windowClose(WebBrowserEvent event) {
					if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(
						webBrowser,
						"The webpage you are viewing is trying to close the window.\n Do you want to close this window?",
						"Warning",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE))
						{
							System.exit(0);
						}
			}
		});
		
		
		webBrowser.setVisible(true);
		this.setLayout(new BorderLayout());
		webBrowser.setPreferredSize(new Dimension(getWidth(), getHeight()));
		add(webBrowser, BorderLayout.CENTER);
		
		return;
	}
	
	protected void load() {
        if (file == main.file)
            return;
        
        file = main.file;
        
        entities.clear();
        
        if (file == null)
            return;
        
        for (Waypoint i:file.waypoints())
            entities.add(i);
            
        for (Route r:file.routes())
            for (Waypoint i:r)
                entities.add(i);
            
        for (Track r:file.tracks())
            for (TrackSegment s:r)
                for (Waypoint i:s)
                    entities.add(i);
        
        outputHTML();
    }

	
	// Take a java Color and convert it to a hex string prefixed with #
	public String getHex (Color color) {
		// Didn't pass a Color, don't get one back!
		if (color == null) return null;
		
		// Het the hex values
		String hexRed = Integer.toHexString(color.getRed());
		String hexGreen = Integer.toHexString(color.getGreen());
		String hexBlue = Integer.toHexString(color.getBlue());
		
		// If they are only 1 character, we need to add a leading zero
		if (hexRed.length() == 1) hexRed = "0" + hexRed;
		if (hexGreen.length() == 1) hexGreen = "0" + hexGreen;
		if (hexBlue.length() == 1) hexBlue = "0" + hexBlue;
		
		return hexRed + hexGreen + hexBlue;
	}

	
	private String getPolyString (Color color, ArrayList<Waypoint> elements) {
		
		// The string we will return
		String retString = "";
		
		// Get the color of the Route or the Track
		String drawcolor = getHex(color);
		String tmpName;
		String tmpDesc;
		
		// Add a comment with the Route/Track info
		if (elements instanceof Route) {
			tmpName = ((Route)elements).getName();
			tmpDesc = ((Route)elements).getDesc();
			retString = retString + "		//Route: ";
			if (tmpName != null) retString = retString + tmpName;
			else retString = retString + "Null";
			retString = retString + " - ";
			if (tmpDesc != null) retString = retString + tmpDesc;
			else retString = retString + "Null";
			
			if (drawcolor == null) drawcolor = getHex(ROUTE_COLOR);
		}
		else if (elements instanceof TrackSegment) {
			if (((TrackSegment)elements).parent != null) {
				tmpName = ((TrackSegment)elements).parent.getName();
				tmpDesc = ((TrackSegment)elements).parent.getDesc();
			}
			else {
				tmpName = null;
				tmpDesc = null;
			}
			retString = retString + "		//TrackSegment: ";
			if (tmpName != null) retString = retString + tmpName;
			else retString = retString + "Null";
			retString = retString + " - ";
			if (tmpDesc != null) retString = retString + tmpDesc;
			else retString = retString + "Null";
			
			if (drawcolor == null) drawcolor = getHex(TRACK_COLOR);
		}
		retString = retString + "\n";
		
		// Start a javascrript array of Waypoints
		retString = retString + "		points = [";
		
		
		Waypoint tempWP;
		
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
		retString = retString + "		map.addOverlay(new GPolyline(points,'#" + drawcolor + "',2,1));\n\n";
		
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

	public void outputHTML () {
		
		file = main.file;
		if (file == null) return;
        
		if (DEBUG_MODE) System.out.println("GoogleMapView: outputting HTML.");
		
		// Construct a platform-independent file name
		File infile = new File(HTML_OUT_FILE);	
		
		try {
			// If the file does not exist, create it
			if (!infile.exists()) infile.createNewFile();
			
			String wtext;
			StringBuilder drawcode = new StringBuilder();
			
			// For each TrackSegment in each Track, append the string to draw it
			for (Track tk : file.tracks())
				if (tk.enabled)
					for (TrackSegment ts : tk)
						drawcode.append(getPolyString(tk.color, ts));
			
			// For each Route, append the string to draw it
			for (Route rt : file.routes())
				if (rt.enabled)
					drawcode.append(getPolyString(rt.color, rt));
			
			// For each Waypoint, append the string to draw it
			for (Waypoint wp : file.waypoints())
				if (wp.enabled)
					drawcode.append(getPointString(wp.color, wp));
				
				
				
				
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
			wtext = wtext + "		map.setCenter(new GLatLng(" + lat + "," + lon + ")," + calcZoom() + ");\n\n";
			
			wtext = wtext + "		var icon1 = new GIcon();\n";
   			wtext = wtext + "		icon1.image = 'point_b.png';\n";
			wtext = wtext + "		icon1.iconSize = new GSize(8, 8);\n";
			wtext = wtext + "		icon1.iconAnchor = new GPoint(4,4);\n";
			wtext = wtext + "		var points;\n\n";
			
			wtext = wtext + drawcode.toString();
			
			wtext = wtext + "	}\n";
			wtext = wtext + "}\n";
			
			wtext = wtext + "//]]>\n";
  			wtext = wtext + "</script>\n";
			wtext = wtext + "</head>\n";
  			wtext = wtext + "<body onload=\"load()\" onunload=\"GUnload()\" scroll=no style='width: 100%; height: 100%'>\n";
			wtext = wtext + "<div id=\"map\" style=\"position:absolute; top: 0px; left: 0px; right:0px; bottom:0px; width: 100%; height: 100%\"></div>\n";
			wtext = wtext + "</body>\n";
			wtext = wtext + "</html>\n";
			
			//Open the file, write the text, close the file
			FileWriter writer = new FileWriter(infile);	
			writer.write(wtext);
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error writing file.");
		}
		
		try {
			webBrowser.setURL(new URL("file://" + new File(HTML_OUT_FILE).getCanonicalPath()));
			webBrowser.setVisible(true);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		return;
	}
	
	public int calcZoom () {
		
		double prevDiff = 100000;
		double curDiff;
		
		int i;
		for (i = 0; i <= 17; i++) {
			curDiff = scale - zoomLevels[i];
			if (curDiff < 0) {
				if (curDiff > prevDiff * -0.5) i++;
				break;
			}
			if (curDiff < prevDiff) prevDiff = curDiff;
			
			else break;
		}
		
		return i - 1;
	}
	
}
