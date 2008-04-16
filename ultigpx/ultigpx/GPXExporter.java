package ultigpx;


/**
 * Class GPX Exporter is used for Exporting the UltiGPXFile to a GPX file, a KML file or an ultiGPX file
 * The KML file can then be view using Google Earth
 * For more information about Google Earth, see http://earth.google.com/
 * This class was written for the project for CPSC 301, Winter 2008
 * @author Jill Ainsworth
 */

import java.util.*;
import java.util.Date.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;
import java.text.*;

public class GPXExporter implements GPXImporterExporterConstants, KMLConstants {
    
    /**
     * exportToKML exports a Group to KML.  The filename is given as a string as well as the Group.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a Group, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToKML(Group outputData, String filename) throws JDOMException, IOException {
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output KML header
        outputFile.write(KMLConstants.KML_HEADER);
        
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
        outputFile.write(KMLConstants.KML_FOOTER);
        
        //Close input file
        outputFile.close();
    } //end exportToKML (group)
    
    /**
     * exportToKML exports a database to KML.  The filename is given as a string as well as the database.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a Database, containing the tracks, routes, and waypoints
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToKML(Database outputData, String filename) throws JDOMException, IOException {
        //Create a new group with the data that we want to export
        Group groupToExport = new Group();
        List<Route> routesToExport = outputData.routes();
        List<Track> tracksToExport = outputData.tracks();
        List<Waypoint> waypointsToExport = outputData.waypoints();
        
        //Put all of the tracks, routes, and waypoints from the database into the new group
        for (int counter = 0; counter < routesToExport.size(); counter++)
            groupToExport.addRoute(routesToExport.get(counter));
        for (int counter = 0; counter < tracksToExport.size(); counter++)
            groupToExport.addTrack(tracksToExport.get(counter));
        for (int counter = 0; counter < waypointsToExport.size(); counter++)
            groupToExport.addWaypoint(waypointsToExport.get(counter));
        
        //Output the new group
        exportToKML(groupToExport, filename);
    } //end exportToKML (Database)
    
    /**
     * exportToGPX exports a group to GPX.  The filename is given as a string and the group is given.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a group, containing the tracks, routes, and waypoints that you want to output
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
                    outputFile.write(GPXImporterExporterConstants.TAB + "<"+ WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write(TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write(TAB + TAB + "<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write(TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write(TAB + "</" + WAYPOINT + ">\n\n");
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
                    
                    //Print out header
                    outputFile.write(TAB + "<" + TRACK + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write(TAB + TAB + "<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write(TAB + TAB + TAB + "<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write(TAB + TAB + TAB + "</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write(TAB + TAB + "</" + TRACK_SEGMENT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</" + TRACK + ">\n\n");
                    
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
                    outputFile.write(TAB + "<" + ROUTE + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write(TAB + TAB + "<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write(TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + TIME + ">" + convertTime(currentWaypoint.getTime()) + "</" + TIME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write(TAB + TAB + "</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</" + ROUTE + ">\n\n");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + GPX + ">");
        
        //Close input file
        outputFile.close();
    } //end exportToGPX
    
    /**
     * exportToGPX exports a database to GPX.  The filename is given as a string a the database to output is given.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a Database, containing the tracks, routes, and waypoints to be exported
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToGPX(Database outputData, String filename) throws JDOMException, IOException {
        //Create a new group and lists of the routes, tracks, and waypoints
        Group groupToExport = new Group();
        List<Route> routesToExport = outputData.routes();
        List<Track> tracksToExport = outputData.tracks();
        List<Waypoint> waypointsToExport = outputData.waypoints();
        
        //Add the tracks, routes, and waypoints to the new group
        for (int counter = 0; counter < routesToExport.size(); counter++)
            groupToExport.addRoute(routesToExport.get(counter));
        for (int counter = 0; counter < tracksToExport.size(); counter++)
            groupToExport.addTrack(tracksToExport.get(counter));
        for (int counter = 0; counter < waypointsToExport.size(); counter++)
            groupToExport.addWaypoint(waypointsToExport.get(counter));
        
        //Export the new group
        exportToGPX(groupToExport, filename);
    } //end exportToGPX
    
    /**
     * exportToUltiGPX exports a group to UltiGPX.  The filename is given as a string as well as the UGPXFile.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a group, containing the tracks, routes, and waypoints that are to be exported
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToUltiGPX(Group outputData, String filename) throws JDOMException, IOException {
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output Ulti GPX header
        outputFile.write(ULTI_GPX_HEADER);
        outputFile.write("<" + ULTI_GPX + ">\n");
        
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
                    outputFile.write(TAB + "<" + WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write(TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write(TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write(TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write(TAB + "</" + WAYPOINT + ">\n\n");
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
                    
                    //Print out header
                    outputFile.write(TAB + "<" + TRACK + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write(TAB + TAB + "<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write(TAB + TAB + TAB + "<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write(TAB + TAB + TAB + "</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write(TAB + TAB + "</" + TRACK_SEGMENT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</" + TRACK + ">\n\n");
                    
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
                    outputFile.write(TAB + "<" + ROUTE + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write(TAB + TAB + "<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write(TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</"+ ELEVATION + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write(TAB + TAB + "</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</"+ ROUTE + ">\n\n");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + ULTI_GPX + ">");
        
        //Close input file
        outputFile.close();
        
    } //end exportToUltiGPX (group)
    
    /**
     * exportToUltiGPX exports a database to UltiGPX.  The filename is given as a string as well as the database.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to export
     * @param outputData a Database, contains the tracks, routes, and waypoints to be exported
     * @throws JDOMException if a problem occurs when exporting the file
     * @throws IOException if a problem occurs when exporting the file
     */
    public static void exportToUltiGPX(Database outputData, String filename) throws JDOMException, IOException {
        //Attributes
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(filename));
        
        //Output GPX header
        outputFile.write(ULTI_GPX_HEADER);
        outputFile.write("<" + ULTI_GPX + ">\n");
        
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
                    outputFile.write(TAB + "<" + WAYPOINT + " ");
                    outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                    outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                    outputFile.write(TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                    outputFile.write(TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                    outputFile.write(TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                    outputFile.write("</" + WAYPOINT + ">\n\n");
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
                    
                    //Print out header
                    outputFile.write(TAB + "<" + TRACK + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentTrack.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentTrack.size(); counter++) {
                        TrackSegment currentTrackSegment = currentTrack.getArray().get(counter);
                        outputFile.write(TAB + TAB + "<" + TRACK_SEGMENT + ">\n");
                        for(int counter2 = 0; counter2 < currentTrackSegment.size(); counter2++) {
                            Waypoint currentWaypoint = currentTrackSegment.get(counter2);
                            outputFile.write(TAB + TAB + TAB + "<" + TRACK_POINT + " ");
                            outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                            outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                            outputFile.write(TAB + TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                            outputFile.write(TAB + TAB + TAB + "</" + TRACK_POINT + ">\n");
                        } //end for
                        outputFile.write(TAB + TAB + "</" + TRACK_SEGMENT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</" + TRACK + ">\n\n");
                    
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
                    outputFile.write(TAB + "<" + ROUTE + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentRoute.getName() + "</" + NAME + ">\n");
                    
                    //Print out all coordinates
                    for (int counter = 0; counter < currentRoute.size(); counter++) {
                        Waypoint currentWaypoint = currentRoute.get(counter);
                        outputFile.write(TAB + TAB + "<" + ROUTE_POINT + " ");
                        outputFile.write(LATITUDE + "=\"" + currentWaypoint.getLat() + "\" ");
                        outputFile.write(LONGITUDE + "=\"" + currentWaypoint.getLon() + "\" >\n");
                        outputFile.write(TAB + TAB + TAB + "<" + ELEVATION + ">" + currentWaypoint.getEle() + "</" + ELEVATION + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + TIME + ">" + currentWaypoint.getTime() + "</" + TIME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + NAME + ">" + currentWaypoint.getName() + "</" + NAME + ">\n");
                        outputFile.write(TAB + TAB + TAB + "<" + DESCRIPTION + ">" + currentWaypoint.getDesc() + "</" + DESCRIPTION + ">\n");
                        outputFile.write(TAB + TAB + "</" + ROUTE_POINT + ">\n");
                    } //end for
                    
                    //Print out footer stuff
                    outputFile.write(TAB + "</" + ROUTE + ">\n\n");
                    
                } //end if getEnabled
            } //end while
        } //end if
        
        //Put groups in file
        //When we export a group, for each route, track, the index of the object in the default list is output
        List<Group> groupsToExport = outputData.groups();
        if (routesToExport != null) {
            //For each group
            Iterator<Group> groupsIterator = groupsToExport.iterator();
            while (groupsIterator.hasNext()) {
                //Export each enabled group
                Group currentGroup = groupsIterator.next();
                if (currentGroup.getEnabled()) {
                    //Print out name
                    outputFile.write(TAB + "<" + GROUP + ">\n");
                    outputFile.write(TAB + TAB + "<" + NAME + ">" + currentGroup.name + "</" + NAME + ">\n");
                    //print out waypoints
                    List<Waypoint> waypointsToExportFromGroup = currentGroup.waypoints();
                    for (int counter = 0; counter < waypointsToExport.size(); counter++)
                        outputFile.write(TAB + TAB + "<" + WAYPOINT + ">" + outputData.waypoints().indexOf(waypointsToExportFromGroup.get(counter)) + "</" + WAYPOINT + ">\n");
                    
                    //Print out tracks
                    List<Track> tracksToExportFromGroup = currentGroup.tracks();
                    for (int counter = 0; counter < tracksToExport.size(); counter++)
                        outputFile.write(TAB + TAB + "<" + TRACK + ">" + outputData.tracks().indexOf(tracksToExportFromGroup.get(counter)) + "</" + TRACK + ">\n");
                    
                    //Print out routes
                    List<Route> routesToExportFromGroup = currentGroup.routes();
                    for (int counter = 0; counter < routesToExport.size(); counter++)
                        outputFile.write(TAB + TAB + "<" + ROUTE + ">" + outputData.routes().indexOf(routesToExportFromGroup.get(counter)) + "</" + ROUTE + ">\n");
                    
                    outputFile.write(TAB + "</" + GROUP + ">\n\n");
                } //end if getEnabled
            } //end while
        } //end if
        
        //Write footer
        outputFile.write("</" + ULTI_GPX + ">");
        
        //Close input file
        outputFile.close();
        
    } //end exportToUltiGPX
    
    /**
     * Convert Time Old extracks the time and converts it to a string to be exported in the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Gregorian calendar to convert time in milis into the string.
     * This method is private because it is only used when exporting GPX files
     * Do not use this method, it has been replaced by convertTime
     * @param toConvert a double, the time in milis since 1970
     * @returns String a string representing time in the GPX file
     * @deprecated
     */
    private static String convertTimeOld(double toConvert) {
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
    
    /**
     * Convert Time extracks the time and converts it to a string to be exported in the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Simple Date Format to convert time into the string.
     * This method is private because it is only used when exporting GPX files
     * This method replaces convertTimeOld
     * @param toConvert a long, the time in milis since 1970
     * @returns String a string representing time in the GPX file
     */
    private static String convertTime(long toConvert) {
        //Return the converted time
        return new SimpleDateFormat("yyy-mm-dd'T'HH:mm:ss'z'").format(toConvert);
        
    } //end getTime
    
} //end GPXExporter      