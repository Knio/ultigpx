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

	Database	file;
	WebBrowser	webBrowser;

	static final Double     CUTOFF		= 50.0;
	static final Boolean	DEBUG_MODE	= true;

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
            for (TrackSegment s:r.getArray())
                for (Waypoint i:s)
                    entities.add(i);
        
		fill();
        outputHTML(null);
    }

	
	/**
	 * Take a java Color and convert it to a hex color (NOT prefixed with #)
	 * @param Java Color object to convert to a hex value
	 * @return String value of the hex conversion of the color
	 */
	public String getHex (Color color) {
		// Didn't pass a Color, don't get one back!
		if (color == null) return null;
		
		// Get the hex values
		String hexRed = Integer.toHexString(color.getRed());
		String hexGreen = Integer.toHexString(color.getGreen());
		String hexBlue = Integer.toHexString(color.getBlue());
		
		// If they are only 1 character, we need to add a leading zero
		if (hexRed.length() == 1) hexRed = "0" + hexRed;
		if (hexGreen.length() == 1) hexGreen = "0" + hexGreen;
		if (hexBlue.length() == 1) hexBlue = "0" + hexBlue;
		
		return hexRed + hexGreen + hexBlue;
	}

	
	/**
	 * Get the javascript string to output a Route or TrackSegment
	 *
	 */
	private String getPolyString (Color color, ArrayList<Waypoint> elements) {
		if (elements.size() == 0) return "";
		
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
		Waypoint lastPoint = null;
		for(;iter.hasNext();) {
			tempWP = (Waypoint) iter.next();

			if ((tempWP.enabled) && ((lastPoint == null) || (tempWP.distanceTo(lastPoint) >= CUTOFF) || (elements.size() <= 10))) {
				lastPoint = tempWP;

				// Add the Waypoint to the javascript array of waypoints
				retString = retString + "new GLatLng("+Double.toString(tempWP.lat)+","+Double.toString(tempWP.lon)+"),";
				// Add the Waypoint to the beginning to have a dot drawn on it
				//retString = "		map.addOverlay(new GMarker(new GLatLng("+Double.toString(tempWP.lat)+","+Double.toString(tempWP.lon)+"),{clickable:false, icon:icon1}));\n" + retString;
			}
			
		}
		// Remove the last comma we wrote, it isn't needed
		retString = retString.substring(0, retString.length() - 1);
		retString = retString + "];\n";
		retString = retString + "		map.addOverlay(new GPolyline(points,'#" + drawcolor + "',2,1));\n\n";
		//retString = retString + "		map.addOverlay(polylineEncoder.dpEncodeToGPolyline(points));\n\n";
		
		return retString;
		
	}
	
	private String getPointString (Waypoint wp) {
		
		// Add a comment with the Waypoint info
		/*String retString = "		//Waypoint: ";
		if (wp.getName() != null) retString = retString + wp.getName();
		else retString = retString + "Null";
		retString = retString + " - ";
		if (wp.getDesc() != null) retString = retString + wp.getDesc();
		else retString = retString + "Null";
		retString = retString + "\n";*/
		
		String retString = "		map.addOverlay(new GMarker(new GLatLng("+Double.toString(wp.lat)+","+Double.toString(wp.lon)+"),{clickable:false, icon:icon1}));\n";
		return retString;
	}

	/**
	 * Output the HTML to the given filename. Update the GoogleMapView tab if the filename is null.
	 * @param The output filename, null to update the GoogleMapView tab.
	 */
	public void outputHTML (String filename) {
		
		file = main.file;
		if (file == null) return;
        
		if (DEBUG_MODE) System.out.println("GoogleMapView: outputting HTML.");
		
		// Construct a platform-independent file name
		File infile;
		if (filename == null) infile = new File(HTML_OUT_FILE);
		else infile = new File(filename);
		
		try {
			// If the file does not exist, create it
			if (!infile.exists()) infile.createNewFile();
			
			StringBuilder wtext = new StringBuilder();
			StringBuilder drawcode = new StringBuilder();
			
			// For each TrackSegment in each Track, append the string to draw it
			for (Track tk : file.tracks())
				if (tk.enabled)
					for (TrackSegment ts : tk.getArray())
						drawcode.append(getPolyString(tk.color, ts));
			
			// For each Route, append the string to draw it
			for (Route rt : file.routes())
				if (rt.enabled)
					drawcode.append(getPolyString(rt.color, rt));
			
			// For each Waypoint, append the string to draw it
			for (Waypoint wp : file.waypoints()) drawcode.append(getPointString(wp));
					
			wtext.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n");
			wtext.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
			wtext.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n");
  			wtext.append("<head>\n");
			wtext.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n");
			wtext.append("<title>Google Maps JavaScript API Example</title>\n");
			wtext.append("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAv8I7SSVba2lHsj8Pc-r5SBTTdj5zBXD9jRFEjQGoMBeg8N65dBQ1Q8m1Xi4E-Q4o6l_EaKjx6--APw\" type=\"text/javascript\"></script>\n");
			//wtext.append("<script src=\"PolylineEncoder.js\" type=\"text/javascript\"></script>");
			wtext.append("<script type=\"text/javascript\">\n");
			wtext.append("//<![CDATA[\n");
			wtext.append("function load() {\n");
			wtext.append("	if (GBrowserIsCompatible()) {\n");
			wtext.append("		var map = new GMap2(document.getElementById(\"map\"));\n");
			wtext.append("		map.addControl(new GLargeMapControl());\n");
			wtext.append("		map.addControl(new GMapTypeControl());\n");
			wtext.append("		map.addControl(new GScaleControl());\n");
			wtext.append("		map.setCenter(new GLatLng(" + lat + "," + lon + ")," + calcZoom() + ");\n\n");
			
			//wtext.append("		var polylineEncoder = new PolylineEncoder();");
			
			// Full-size pins
			wtext.append("		var icon1 = new GIcon();\n");
			wtext.append("		icon1.image = G_DEFAULT_ICON.image;\n");
			wtext.append("		icon1.iconSize = G_DEFAULT_ICON.iconSize;\n");
			wtext.append("		icon1.iconAnchor = G_DEFAULT_ICON.iconAnchor;\n");
			
			// Make them baby pins
			wtext.append("		icon1.iconSize.width = icon1.iconSize.width / 2;\n");
			wtext.append("		icon1.iconSize.height = icon1.iconSize.height / 2;\n");
			wtext.append("		icon1.iconAnchor.x = icon1.iconAnchor.x / 2;\n");
			wtext.append("		icon1.iconAnchor.y = icon1.iconAnchor.y / 2;\n");
			
			/* Small circle pins
			wtext.append("		var icon2 = new GIcon();\n");
   			wtext.append("		icon2.image = 'point_b.png';\n");
			wtext.append("		icon2.iconSize = new GSize(8,8);\n");
			wtext.append("		icon2.iconAnchor = new GPoint(4,4);\n");
			*/
			
			wtext.append("		var points;\n\n");
			
			wtext.append(drawcode.toString());
			
			wtext.append("	}\n");
			wtext.append("}\n");
			
			wtext.append("//]]>\n");
  			wtext.append("</script>\n");
			wtext.append("</head>\n");
  			wtext.append("<body onload=\"load()\" scroll=no style='width: 100%; height: 100%'>\n");
			wtext.append("<div id=\"map\" style=\"position:absolute; top: 0px; left: 0px; right:0px; bottom:0px; width: 100%; height: 100%\"></div>\n");
			wtext.append("</body>\n");
			wtext.append("</html>\n");
			
			//Open the file, write the text, close the file
			FileWriter writer = new FileWriter(infile);	
			writer.write(wtext.toString());
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error writing file.");
		}
		
		if (filename != null) return;
		
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
		
		double prevDiff = 100000000;
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
