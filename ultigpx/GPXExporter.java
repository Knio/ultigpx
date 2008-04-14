package ultigpx;

/**
 * Class GPX Exporter is used for Exporting the UltiGPXFile to a GPX file, a KML file or an ultiGPX file
 * The KML file can then be view using Google Earth
 * For more information about Google Earth, see http://earth.google.com/
 * This class was written for Part 1 of the project for CPSC 301, Winter 2008
 * @author Jill Ainsworth
 */

import java.util.*;
import java.util.Date.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;


public class GPXExporter implements GPXImporterExporterConstants {
    
    /**
     * exportToKML exports a Group to KML.  The filename is given as a string as well as the Group.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param outputData, a Group, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToKML(Group outputData, String filename) throws JDOMException, IOException {
        
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output KML header
        outputFile.write(KML_HEADER);
        
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
                    outputFile.write("\t\t\t\t<color>7" + currentTrack.getColor() + "</color>\n");
                    outputFile.write("\t\t\t\t<width>4</width>\n");
                    outputFile.write("\t\t\t</LineStyle>\n");
                    outputFile.write("\t\t\t<PolyStyle>\n");
                    outputFile.write("\t\t\t\t<color>" + currentTrack.getColor() + "</color>\n");
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
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
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
        outputFile.write(KML_FOOTER);
        
        //Close input file
        outputFile.close();
        
    } //end exportGPX
    
    /**
     * exportToGPX exports a UGPXFile to GPX.  The filename is given as a string as well as the UGPXFile.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param UGPXFile, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToKML(Database outputData, String filename) throws JDOMException, IOException {
        Group groupToExport = new Group();
        List<Route> routesToExport = outputData.routes();
        List<Track> tracksToExport = outputData.tracks();
        List<Waypoint> waypointsToExport = outputData.waypoints();
        for (int counter = 0; counter < routesToExport.size(); counter++)
            groupToExport.addRoute(routesToExport.get(counter));
        for (int counter = 0; counter < tracksToExport.size(); counter++)
            groupToExport.addTrack(tracksToExport.get(counter));
        for (int counter = 0; counter < waypointsToExport.size(); counter++)
            groupToExport.addWaypoint(waypointsToExport.get(counter));
        exportToKML(groupToExport, filename);
    } //end exportToKML
    
    /**
     * exportToGPX exports a UGPXFile to GPX.  The filename is given as a string as well as the UGPXFile.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param UGPXFile, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToGPX(Group outputData, String filename) throws JDOMException, IOException {
        
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output GPX header
        outputFile.write(GPX_HEADER);
        
        //Put waypoints in the file
        List<Waypoint> waypointsToExport = outputData.waypoints();
        if (waypointsToExport != null) {
            //For each waypoint
            Iterator<Waypoint> waypointIterator = waypointsToExport.iterator();
            while (waypointIterator.hasNext()) {
                //Export each enabled waypoint
                Waypoint currentWaypoint = waypointIterator.next();
                if (currentWaypoint.getEnabled()) {
                    
                    //Print out waypoint
                    outputFile.write("<"+ WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write("<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                    outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write("</" + WAYPOINT + ">\n");
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
                    
                    outputFile.write("<" + TRACK + ">\n");
                    outputFile.write("<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write("<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write("<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write("<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                            outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write("</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write("</" + TRACK_SEGMENT + ">");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</" + TRACK + ">");
                    
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
                    
                    //Print out header stuff
                    
                    outputFile.write("<" + ROUTE + ">\n");
                    outputFile.write("<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write("<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                        outputFile.write("<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                        outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write("</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</" + ROUTE + ">");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + GPX + ">");
        
        //Close input file
        outputFile.close();
        
    } //end exportToGPX
    
    /**
     * exportToGPX exports a UGPXFile to GPX.  The filename is given as a string as well as the UGPXFile.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param UGPXFile, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToGPX(Database outputData, String filename) throws JDOMException, IOException {
        Group groupToExport = new Group();
        List<Route> routesToExport = outputData.routes();
        List<Track> tracksToExport = outputData.tracks();
        List<Waypoint> waypointsToExport = outputData.waypoints();
        for (int counter = 0; counter < routesToExport.size(); counter++)
            groupToExport.addRoute(routesToExport.get(counter));
        for (int counter = 0; counter < tracksToExport.size(); counter++)
            groupToExport.addTrack(tracksToExport.get(counter));
        for (int counter = 0; counter < waypointsToExport.size(); counter++)
            groupToExport.addWaypoint(waypointsToExport.get(counter));
        exportToGPX(groupToExport, filename);
    } //end exportToGPX
    
    /**
     * exportToUltiGPX exports a UGPXFile to UltiGPX.  The filename is given as a string as well as the UGPXFile.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param UGPXFile, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToUltiGPX(Group outputData, String filename) throws JDOMException, IOException {
        
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output GPX header
        outputFile.write(ULTI_GPX_HEADER);
        
        //Put waypoints in the file
        List<Waypoint> waypointsToExport = outputData.waypoints();
        if (waypointsToExport != null) {
            //For each waypoint
            Iterator<Waypoint> waypointIterator = waypointsToExport.iterator();
            while (waypointIterator.hasNext()) {
                //Export each enabled waypoint
                Waypoint currentWaypoint = waypointIterator.next();
                if (currentWaypoint.getEnabled()) {
                    
                    //Print out waypoint
                    outputFile.write("<" + WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                    outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write("</" + WAYPOINT + ">\n");
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
                    
                    outputFile.write("<" + TRACK + ">\n");
                    outputFile.write("<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write("<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write("<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                            outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write("</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write("</" + TRACK_SEGMENT + ">");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</" + TRACK + ">");
                    
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
                    
                    //Print out header stuff
                    
                    outputFile.write("<" + ROUTE + ">\n");
                    outputFile.write("<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write("<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</"+ ELEVATION + ">\n");
                        outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                        outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write("</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</"+ ROUTE + ">");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + ULTI_GPX + ">");
        
        //Close input file
        outputFile.close();
        
    } //end exportToUltiGPX
    
    
    /**
     * exportToUltiGPX exports a UGPXFile to UltiGPX.  The filename is given as a string as well as the UGPXFile.
     * Use indexOf in arraylist
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to export
     * @param UGPXFile, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToUltiGPX(Database outputData, String filename) throws JDOMException, IOException {
        
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output GPX header
        outputFile.write(ULTI_GPX_HEADER);
        
        //Put waypoints in the file
        List<Waypoint> waypointsToExport = outputData.waypoints();
        if (waypointsToExport != null) {
            //For each waypoint
            Iterator<Waypoint> waypointIterator = waypointsToExport.iterator();
            while (waypointIterator.hasNext()) {
                //Export each enabled waypoint
                Waypoint currentWaypoint = waypointIterator.next();
                if (currentWaypoint.getEnabled()) {
                    
                    //Print out waypoint
                    outputFile.write("<" + WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                    outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write("</" + WAYPOINT + ">\n");
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
                    
                    outputFile.write("<" + TRACK + ">\n");
                    outputFile.write("<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write("<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write("<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                            outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write("</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write("</" + TRACK_SEGMENT + ">");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</" + TRACK + ">");
                    
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
                    
                    //Print out header stuff
                    
                    outputFile.write("<" + ROUTE + ">\n");
                    outputFile.write("<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write("<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write("<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                        outputFile.write("<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                        outputFile.write("<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write("<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write("</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write("</" + ROUTE + ">");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Put routes in file
        List<Group> groupsToExport = outputData.groups();
        if (routesToExport != null) {
            //For each route
            Iterator<Group> groupsIterator = groupsToExport.iterator();
            while (groupsIterator.hasNext()) {
                //Export each enabled route
                Group currentGroup = groupsIterator.next();
                if (currentGroup.getEnabled()) {
                    //print out name
                    outputFile.write("<" + NAME + ">" + currentGroup.name + "</" + NAME + ">\n");
                    //print out waypoints
                    List<Waypoint> waypointsToExportFromGroup = currentGroup.waypoints();
                    for (int counter = 0; counter < waypointsToExport.size(); counter++)
                        outputFile.write("<" + WAYPOINT + ">" + outputData.waypoints().indexOf(waypointsToExportFromGroup.get(counter)) + "</" + WAYPOINT + ">\n");
                    //print out tracks
                    List<Track> tracksToExportFromGroup = currentGroup.tracks();
                    for (int counter = 0; counter < tracksToExport.size(); counter++)
                        outputFile.write("<" + TRACK + ">" + outputData.tracks().indexOf(tracksToExportFromGroup.get(counter)) + "</" + TRACK + ">\n");
                    
                    //print out routes
                    List<Route> routesToExportFromGroup = currentGroup.routes();
                    for (int counter = 0; counter < routesToExport.size(); counter++)
                        outputFile.write("<" + ROUTE + ">" + outputData.routes().indexOf(routesToExportFromGroup.get(counter)) + "</" + ROUTE + ">\n");
                    
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + ULTI_GPX + ">");
        
        //Close input file
        outputFile.close();
        
    } //end exportToUltiGPX
    
    /**
     * Convert Time extracks the time and converts it to a string to be exported in the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Gregorian calendar to convert time in milis into the string.
     * This method is private because it is only used when exporting GPX files
     * @param toConvert, a double, the time in milis since 1970
     * @returns String, a string representing time in the GPX file
     */
    private static String convertTime(double toConvert) {
        
        //Create a calender for converting times
        GregorianCalendar timeConverter = new GregorianCalendar();
        timeConverter.setGregorianChange(new Date((long) toConvert));
        
        //Other attributes
        Integer year = timeConverter.get(Calendar.YEAR);
        Integer month = timeConverter.get(Calendar.MONTH);
        Integer day = timeConverter.get(Calendar.DATE);
        Integer hour = timeConverter.get(Calendar.HOUR);
        Integer minute = timeConverter.get(Calendar.MINUTE);
        Integer second = timeConverter.get(Calendar.SECOND);
        
        //Return the date string
        return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + "Z";
    } //end getTime
    
    
} //end GPXExporter