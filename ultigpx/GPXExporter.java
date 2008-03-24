package ultigpx;

	/** For more information about Google Earth, see http://earth.google.com/
	 * This class was written for Part 1 of the project for CPSC 301, Winter 2008
	 * @author Jill Ainsworth
	 */

	import java.util.*;
	import java.io.*;
	import org.jdom.*;
	import org.jdom.input.*;


	public class GPXExporter {
	    
	        /**
	     * exportGPX exports a UGPXFile.  The filename is given as a string as well as the UGPXFile.
	     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
	     * @param filename, a String, the name of the file you want to export
	     * @param UGPXFile, containing the tracks, routes, and waypoints
	     * @throws JDOMException if a problem occurs when importing the file
	     * @throws IOException if a problem occurs when importing the file
	     */
	    public static void exportGPX(UGPXFile outputData, String filename) throws JDOMException, IOException {
	        
	        //Attributes
	        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
	        
	        //Output KML header
	        outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	        outputFile.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
	        
	        //Put waypoints in the file
	        List<Waypoint> waypointsToExport = outputData.waypoints();
	        if (waypointsToExport != null) {
	            //For each waypoint
	            Iterator<Waypoint> waypointIterator = waypointsToExport.iterator();
	            while (waypointIterator.hasNext()) {
	                //Export each enabled waypoint
	                Waypoint currentWaypoint = waypointIterator.next();
	                if (currentWaypoint.getEnabled()) {
	                    
	                    //Print out header
	                    outputFile.write("\t<Placemark>\n");
	                    outputFile.write("\t\t<name>" + currentWaypoint.getName() + "</name>\n");
	                    outputFile.write("\t\t<description></description>");
	                    outputFile.write("\t\t<Point>");
	                    outputFile.write("\t\t\t<coordinates>");
	                    
	                    //Print out waypoint
	                    outputFile.write(currentWaypoint.getLon() + "," + currentWaypoint.getLat() + "," + currentWaypoint.getEle());
	                    
	                    //Print out footer
	                    outputFile.write("</coordinates>\n");
	                    outputFile.write("\t\t</Point>\n");
	                    outputFile.write("\t\t</Placemark>\n");
	                    outputFile.write("\t</Document>\n");
	                } //end if getEnabled
	            } //end while
	        } //end if
	        
	        //Put tracks in the file
	        List<Track> tracksToExport = outputData.tracks();
	        if (tracksToExport != null) {
	            //For each track
	            Iterator<Track> tracksIterator = tracksToExport.iterator();
	            while (tracksIterator.hasNext()) {
	                //Export each enabled track
	                Track currentTrack = tracksIterator.next();
	                if (currentTrack.getEnabled()){
	                    
	                    //Print out header stuff
	                    outputFile.write("\t<Document>\n");
	                    outputFile.write("\t\t<name>" + currentTrack.getName() + "</name>\n");
	                    outputFile.write("\t\t<description></description>");
	                    outputFile.write("\t\t<Style id=\"yellowLineGreenPoly\">\n");
	                    outputFile.write("\t\t\t<LineStyle>\n");
	                    outputFile.write("\t\t\t\t<color>7f00ffff</color>\n");
	                    outputFile.write("\t\t\t\t<width>4</width>\n");
	                    outputFile.write("\t\t\t</LineStyle>\n");
	                    outputFile.write("\t\t\t<PolyStyle>\n");
	                    outputFile.write("\t\t\t\t<color>7f00ff00</color>\n");
	                    outputFile.write("\t\t\t</PolyStyle>\n");
	                    outputFile.write("\t\t</Style>\n");
	                    outputFile.write("\t\t<Placemark>\n");
	                    outputFile.write("\t\t\t<name> </name>\n");
	                    outputFile.write("\t\t\t<description></description>\n");
	                    outputFile.write("\t\t\t<styleUrl>#yellowLineGreenPoly</styleUrl>\n");
	                    outputFile.write("\t\t\t<LineString>\n");
	                    outputFile.write("\t\t\t\t<extrude>1</extrude>\n");
	                    outputFile.write("\t\t\t\t<tessellate>1</tessellate>\n");
	                    outputFile.write("\t\t\t\t<altitudeMode>absolute</altitudeMode>\n");
	                    outputFile.write("\t\t\t\t<coordinates>");
	                    
	                    //Print out all coordinates
	                    for (int counter = 0; counter < currentTrack.size(); counter++) {
	                        TrackSegment currentTrackSegment = currentTrack.get(counter);
	                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter++) {
	                            Waypoint currentWaypoint = currentTrackSegment.get(counter);
	                            outputFile.write("\t\t\t\t\t" + currentWaypoint.getLon() + "," + currentWaypoint.getLat() + "," + currentWaypoint.getEle());
	                            
	                        } //end for
	                        
	                    } //end for
	                    
	                    //Print out footer stuff
	                    outputFile.write("\t\t\t\t</coordinates>\n");
	                    outputFile.write("\t\t\t</LineString>\n");
	                    outputFile.write("\t\t</Placemark>\n");
	                    outputFile.write("\t</Document>\n");
	                    
	                } //end if getEnabled  
	            } //end while 
	        } //end if
	        
	        
	        //Put routes in file
	        List<Route> routesToExport = outputData.routes();
	        if (routesToExport != null) {
	            //For each route
	            Iterator<Route> routesIterator = routesToExport.iterator();
	            while (routesIterator.hasNext()) {
	                //Export each enabled route
	                Route currentRoute = routesIterator.next();
	                if (currentRoute.getEnabled()) {
	                    
	                    //Print out header
	                    outputFile.write("\t<Document>\n");
	                    outputFile.write("\t\t<name> </name>\n");
	                    outputFile.write("\t\t<description></description>");
	                    outputFile.write("\t\t<Style id=\"yellowLineGreenPoly\">\n");
	                    outputFile.write("\t\t\t<LineStyle>\n");
	                    outputFile.write("\t\t\t\t<color>7f00ffff</color>\n");
	                    outputFile.write("\t\t\t\t<width>4</width>\n");
	                    outputFile.write("\t\t\t</LineStyle>\n");
	                    outputFile.write("\t\t\t<PolyStyle>\n");
	                    outputFile.write("\t\t\t\t<color>7f00ff00</color>\n");
	                    outputFile.write("\t\t\t</PolyStyle>\n");
	                    outputFile.write("\t\t</Style>\n");
	                    outputFile.write("\t\t<Placemark>\n");
	                    outputFile.write("\t\t\t<name> </name>\n");
	                    outputFile.write("\t\t\t<description></description>\n");
	                    outputFile.write("\t\t\t<styleUrl>#yellowLineGreenPoly</styleUrl>\n");
	                    outputFile.write("\t\t\t<LineString>\n");
	                    outputFile.write("\t\t\t\t<extrude>1</extrude>\n");
	                    outputFile.write("\t\t\t\t<tessellate>1</tessellate>\n");
	                    outputFile.write("\t\t\t\t<altitudeMode>absolute</altitudeMode>\n");
	                    outputFile.write("\t\t\t\t<coordinates>");
	                    
	                    //Print out all coordinates
	                    for (int counter = 0; counter < currentRoute.size(); counter++) {
	                        Waypoint currentWaypoint = currentRoute.get(counter);
	                        outputFile.write("\t\t\t\t\t" + currentWaypoint.getLon() + "," + currentWaypoint.getLat() + "," + currentWaypoint.getEle());
	                        
	                    } //end for
	                    
	                    //Print out footer
	                    outputFile.write("\t\t\t\t</coordinates>\n");
	                    outputFile.write("\t\t\t</LineString>\n");
	                    outputFile.write("\t\t</Placemark>\n");
	                    outputFile.write("\t</Document>\n");
	                    
	                } //end if getEnabled
	            } //end while
	        } //end if
	        
	        //Write footer
	        outputFile.write("</kml>");
	        
	        //Close input file
	        outputFile.close();
	        
	    } //end exportGPX
	} //end GPXExporter