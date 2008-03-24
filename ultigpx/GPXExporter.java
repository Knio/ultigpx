package ultigpx;

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

//Author Jill Ainsworth
public class GPXExporter {
    
    public static void exportGPX(UGPXFile outputData, String filename) throws JDOMException, IOException {
        
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output KML header
        outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        outputFile.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
        
        outputFile.write("<Document>\n");
        
        //Waypoints
        List<Waypoint> waypointsToExport = outputData.waypoints();
        if (waypointsToExport != null) {
            Iterator<Waypoint> waypointIterator = waypointsToExport.iterator();
            
            while (waypointIterator.hasNext()) {
                //Export each enabled track
                Waypoint currentWaypoint = waypointIterator.next();
                
                //if (currentWaypoint.getEnabled()) {
                    
                    //Print out header stuff
                    //outputFile.write("<Document>\n");
                    outputFile.write("\t<Placemark>\n");
                    outputFile.write("\t\t<name>" + currentWaypoint.getName() + "</name>\n");
                    outputFile.write("\t\t<description></description>");
                    outputFile.write("\t\t<Point>\n");
                    outputFile.write("\t\t\t<coordinates>");
                    outputFile.write(currentWaypoint.getLon() + "," + currentWaypoint.getLat() + "," + currentWaypoint.getEle());
                    outputFile.write("\t\t\t</coordinates>\n");
                    outputFile.write("\t\t</Point>\n");
                    outputFile.write("\t</Placemark>\n");
                    //outputFile.write("</Document>\n");
                //} //end if getEnabled
            } //end while
        } //end if
        
        //Tracks
        List<Track> tracksToExport = outputData.tracks();
        if (tracksToExport != null) {
            Iterator<Track> tracksIterator = tracksToExport.iterator();
            
            while (tracksIterator.hasNext()) {
                //Export each enabled track
                Track currentTrack = tracksIterator.next();
                
                //if (currentTrack.getEnabled()){
                    
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
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write("\t\t\t\t\t" + currentWaypoint.getLon() + "," + currentWaypoint.getLat() + "," + currentWaypoint.getEle());
                            
                        } //end for
                        
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("\t\t\t\t</coordinates>\n");
                    outputFile.write("\t\t\t</LineString>\n");
                    outputFile.write("\t\t</Placemark>\n");
                    outputFile.write("\t</Document>\n");
                    
                //} //end if getEnabled
                
            } //end while
            
        } //end if
        
        
        //Routes
        List<Route> routesToExport = outputData.routes();
        if (routesToExport != null) {
            Iterator<Route> routesIterator = routesToExport.iterator();
            
            while (routesIterator.hasNext()) {
                //Export each enabled track
                Route currentRoute = routesIterator.next();
                
                //if (currentRoute.getEnabled()) {
                    
                    //Print out header stuff
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
                    
                    //Print out footer stuff
                    outputFile.write("\t\t\t\t</coordinates>\n");
                    outputFile.write("\t\t\t</LineString>\n");
                    outputFile.write("\t\t</Placemark>\n");
                    outputFile.write("\t</Document>\n");
                    
                //} //end if getEnabled
                
            } //end while
            
        } //end if
        
        //Write footer
        outputFile.write("</Document>\n");
        outputFile.write("</kml>");
        
        outputFile.close();
        
    } //end exportGPX
    
} //end GPXExporter