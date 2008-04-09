package ultigpx;

/**
 * Class GPX Importer is used for importing a GPX file into the UltiGPXFile structure.  It uses JDOM for processing XML.
 * For more information about JDOM visit http://www.jdom.org/
 * This class was written for Part 1 of the project for CPSC 301, Winter 2008
 * @author Jill Ainsworth
 */

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

public class GPXImporter {
    
    /**
     * importGPX imports a GPX file.  The filename is given as a string, and a UGPXFile is returned.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to import
     * @return UGPXFile, containing the tracks, routes, and waypoints that were in the file
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    public static void importGPX(Group inputGroup, String filename) throws JDOMException, IOException {
    //public static Group importGPX(String filename) throws JDOMException, IOException {
        // Create a new UGPXFile to put data in
        //Group returnValue = new Group();
        
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not GPX, throw an execption (HELP!)
            if (!root.getName().equals("gpx"))
                throw new IOException();
            
            // Get each of the children
            List childList = root.getChildren();
            
            //For every child, read it in
            Iterator childListIterator = childList.iterator();
            while (childListIterator.hasNext()) {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                //If it is a waypoint
                if (currentElement.getName().equals("wpt")) {
                    //Make variables to represent name, description, elevation and time for the waypoint
                    List waypointChildList = currentElement.getChildren();
                    Iterator waypointChildIterator = waypointChildList.iterator();
                    String name = "";
                    String desc = "";
                    double ele = 0;
                    double time = -1;
                    
                    //Extrack name, description, elevation, and time
                    while(waypointChildIterator.hasNext()) {
                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                        if (currentWaypointChild.getName().equals("name"))
                            name = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("desc"))
                            desc = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("ele"))
                            ele = Double.parseDouble(currentWaypointChild.getText());
                        else if (currentWaypointChild.getName().equals("time"))
                            time = getTime(currentWaypointChild.getText());
                    } //end while
                    
                    //Add the waypoint to the GPXFile
                    inputGroup.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute("lat").getDoubleValue(), currentElement.getAttribute("lon").getDoubleValue(), ele, time));
                } //end if waypoint
                
                //If it is a route
                else if (currentElement.getName().equals("rte")) {
                    //Make variables to keep track of points in the route
                    List routeChildList = currentElement.getChildren();
                    Iterator routeChildIterator = routeChildList.iterator();
                    Route newRoute = new Route();
                    
                    //For each point in the route
                    while(routeChildIterator.hasNext()) {
                        Element currentRouteChild = (Element) routeChildIterator.next();
                        if (currentRouteChild.getName().equals("rtept")) {
                            //Make variable to represent name, description, elevation and time for that point
                            List waypointChildList = currentElement.getChildren();
                            Iterator waypointChildIterator = waypointChildList.iterator();
                            String name = "";
                            String desc = "";
                            double ele = 0;
                            double time = -1;
                            
                            //Extract name, description, elevation, and time
                            while(waypointChildIterator.hasNext()) {
                                Element currentWaypointChild = (Element) waypointChildIterator.next();
                                if (currentWaypointChild.getName().equals("name"))
                                    name = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("desc"))
                                    desc = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("ele"))
                                    ele = Double.parseDouble(currentWaypointChild.getText());
                                else if (currentWaypointChild.getName().equals("time"))
                                    time = getTime(currentWaypointChild.getText());
                            } //end while
                            
                            //Add point to the route
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute("lat").getDoubleValue(), currentRouteChild.getAttribute("lon").getDoubleValue(), ele, time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                    } //end while
                    
                    //Add route to GPXFILE
                    inputGroup.addRoute(newRoute);
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals("trk")) {
                    //Make variables to keep track of points in the route
                    List trackChildList = currentElement.getChildren();
                    Iterator trackChildIterator = trackChildList.iterator();
                    Track newTrack = new Track();
                    
                    //Get track segments
                    while(trackChildIterator.hasNext()) {
                        //For each track segment
                        Element currentTrackChild = (Element) trackChildIterator.next();
                        if (currentTrackChild.getName().equals("trkseg")) {
                            //Extract Information about that track segment
                            List trackSegmentList = currentTrackChild.getChildren();
                            Iterator trackSegmentIterator = trackSegmentList.iterator();
                            TrackSegment newTrackSegment = new TrackSegment();
                            
                            //Get trackpoints
                            while (trackSegmentIterator.hasNext()){
                                //Make variable to represent name, description, elevation and time for that point
                                Element currentTrackSegment = (Element) trackSegmentIterator.next();
                                if (currentTrackSegment.getName().equals("trkpt")) {
                                    List waypointChildList = currentTrackSegment.getChildren();
                                    Iterator waypointChildIterator = waypointChildList.iterator();
                                    String name = "";
                                    String desc = "";
                                    double ele = 0;
                                    double time = -1;
                                    
                                    //Extract name, description, elevation, and time
                                    while(waypointChildIterator.hasNext()) {
                                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                                        if (currentWaypointChild.getName().equals("name"))
                                            name = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("desc"))
                                            desc = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("ele"))
                                            ele = Double.parseDouble(currentWaypointChild.getText());
                                        else if (currentWaypointChild.getName().equals("time"))
                                            time = getTime(currentWaypointChild.getText());
                                    } //end while
                                    
                                    //Add trackpoints to track segments
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute("lat").getDoubleValue(), currentTrackSegment.getAttribute("lon").getDoubleValue(), ele, time);
                                    newTrackSegment.add(newWaypoint);
                                    
                                    
                                } //end if track point
                            } //end while get trackpoints
                            
                            //Add track segments to track
                            newTrackSegment.setParent(newTrack);
                            newTrack.add(newTrackSegment);
                            
                        } //end if track segment
                    } //end while for getting track segments
                    inputGroup.addTrack(newTrack);
                } //end if track
                
            } //end while
        } //end if has root
        
        else //!inputFile.hasRootElement()
            throw new JDOMException();
        
        //return returnValue;
    } //end importGPX
    
    /**
     * importGPX imports a GPX file.  The filename is given as a string, and a UGPXFile is returned.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to import
     * @return UGPXFile, containing the tracks, routes, and waypoints that were in the file
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    public static void importGPX(Database inputDatabase, String filename) throws JDOMException, IOException {
        Group inputGroup = new Group();
        importGPX(inputGroup, filename);
        List<Route> routesToInput = inputGroup.routes();
        for (int counter = 0; counter < routesToInput.size(); counter++)
            inputDatabase.addRoute(routesToInput.get(counter));
        List<Track> tracksToInput = inputGroup.tracks();
        for (int counter = 0; counter < tracksToInput.size(); counter++)
            inputDatabase.addTrack(tracksToInput.get(counter));
        List<Waypoint> waypointsToInput = inputGroup.waypoints();
        for (int counter = 0; counter < waypointsToInput.size(); counter++)
            inputDatabase.addWaypoint(waypointsToInput.get(counter));
    } //end importGPX
    
    /**
     * importGPX imports a GPX file.  The filename is given as a string, and a UGPXFile is returned.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename, a String, the name of the file you want to import
     * @return UGPXFile, containing the tracks, routes, and waypoints that were in the file
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    //public static void importUltiGPX(Group inputGroup, String filename) throws JDOMException, IOException {
    public static Group importUltiGPX(String filename) throws JDOMException, IOException {
        // Create a new UGPXFile to put data in
        Group returnValue = new Group();
        
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not GPX, throw an execption (HELP!)
            if (!root.getName().equals("UltiGPX"))
                throw new IOException();
            
            // Get each of the children
            List childList = root.getChildren();
            
            //For every child, read it in
            Iterator childListIterator = childList.iterator();
            while (childListIterator.hasNext()) {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                //If it is a waypoint
                if (currentElement.getName().equals("waypoint")) {
                    //Make variables to represent name, description, elevation and time for the waypoint
                    List waypointChildList = currentElement.getChildren();
                    Iterator waypointChildIterator = waypointChildList.iterator();
                    String name = "";
                    String desc = "";
                    double ele = 0;
                    double time = -1;
                    
                    //Extrack name, description, elevation, and time
                    while(waypointChildIterator.hasNext()) {
                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                        if (currentWaypointChild.getName().equals("name"))
                            name = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("desc"))
                            desc = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("ele"))
                            ele = Double.parseDouble(currentWaypointChild.getText());
                        else if (currentWaypointChild.getName().equals("time"))
                            time = getTime(currentWaypointChild.getText());
                    } //end while
                    
                    //Add the waypoint to the GPXFile
                    returnValue.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute("lat").getDoubleValue(), currentElement.getAttribute("lon").getDoubleValue(), ele, time));
                } //end if waypoint
                
                //If it is a route
                else if (currentElement.getName().equals("route")) {
                    //Make variables to keep track of points in the route
                    List routeChildList = currentElement.getChildren();
                    Iterator routeChildIterator = routeChildList.iterator();
                    Route newRoute = new Route();
                    
                    //For each point in the route
                    while(routeChildIterator.hasNext()) {
                        Element currentRouteChild = (Element) routeChildIterator.next();
                        if (currentRouteChild.getName().equals("routePoint")) {
                            //Make variable to represent name, description, elevation and time for that point
                            List waypointChildList = currentElement.getChildren();
                            Iterator waypointChildIterator = waypointChildList.iterator();
                            String name = "";
                            String desc = "";
                            double ele = 0;
                            double time = -1;
                            
                            //Extract name, description, elevation, and time
                            while(waypointChildIterator.hasNext()) {
                                Element currentWaypointChild = (Element) waypointChildIterator.next();
                                if (currentWaypointChild.getName().equals("name"))
                                    name = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("desc"))
                                    desc = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("ele"))
                                    ele = Double.parseDouble(currentWaypointChild.getText());
                                else if (currentWaypointChild.getName().equals("time"))
                                    time = getTime(currentWaypointChild.getText());
                            } //end while
                            
                            //Add point to the route
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute("lat").getDoubleValue(), currentRouteChild.getAttribute("lon").getDoubleValue(), ele, time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                        
                        else if (currentRouteChild.getName().equals("name"))
                            newRoute.setName(currentRouteChild.getText());
                        
                    } //end while
                    
                    //Add route to GPXFILE
                    returnValue.addRoute(newRoute);
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals("track")) {
                    //Make variables to keep track of points in the route
                    List trackChildList = currentElement.getChildren();
                    Iterator trackChildIterator = trackChildList.iterator();
                    Track newTrack = new Track();
                    
                    //Get track segments
                    while(trackChildIterator.hasNext()) {
                        //For each track segment
                        Element currentTrackChild = (Element) trackChildIterator.next();
                        if (currentTrackChild.getName().equals("trackSegment")) {
                            //Extract Information about that track segment
                            List trackSegmentList = currentTrackChild.getChildren();
                            Iterator trackSegmentIterator = trackSegmentList.iterator();
                            TrackSegment newTrackSegment = new TrackSegment();
                            
                            //Get trackpoints
                            while (trackSegmentIterator.hasNext()){
                                //Make variable to represent name, description, elevation and time for that point
                                Element currentTrackSegment = (Element) trackSegmentIterator.next();
                                if (currentTrackSegment.getName().equals("trackPoint")) {
                                    List waypointChildList = currentTrackSegment.getChildren();
                                    Iterator waypointChildIterator = waypointChildList.iterator();
                                    String name = "";
                                    String desc = "";
                                    double ele = 0;
                                    double time = -1;
                                    
                                    //Extract name, description, elevation, and time
                                    while(waypointChildIterator.hasNext()) {
                                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                                        if (currentWaypointChild.getName().equals("name"))
                                            name = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("desc"))
                                            desc = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("ele"))
                                            ele = Double.parseDouble(currentWaypointChild.getText());
                                        else if (currentWaypointChild.getName().equals("time"))
                                            time = getTime(currentWaypointChild.getText());
                                    } //end while
                                    
                                    //Add trackpoints to track segments
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute("lat").getDoubleValue(), currentTrackSegment.getAttribute("lon").getDoubleValue(), ele, time);
                                    newTrackSegment.add(newWaypoint);
                                    
                                    
                                } //end if track point
                            } //end while get trackpoints
                            
                            //Add track segments to track
                            newTrackSegment.setParent(newTrack);
                            newTrack.add(newTrackSegment);
                            
                        } //end if track segment
                    } //end while for getting track segments
                    returnValue.addTrack(newTrack);
                } //end if track
                
            } //end while
        } //end if has root
        
        else //!inputFile.hasRootElement()
            throw new JDOMException();
        
        return returnValue;
    } //end importUltiGPX
    
    
    /**
     * Get Time extracks the time from the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Gregorian calendar to then convert this into time in milis.
     * This method is private because it is only used when importing GPX files
     * If there is a formating problem, the time of -1 is returned
     * @param toParse, a String, the string representing time in the GPX file
     * @returns double, the time in milis since 1970
     */
    private static double getTime(String toParse) {
        
        //Create a calender for converting times
        Calendar timeConverter = new GregorianCalendar();
        
        //Other attributes
        Integer year = 0;
        Integer month = 0;
        Integer day = 0;
        Integer hour = 0;
        Integer minute = 0;
        Integer second = 0;
        
        //Parse the time out of the string
        try {
            year = year.parseInt(toParse.substring(0, 3));
            month = month.parseInt(toParse.substring(5, 6));
            day = day.parseInt(toParse.substring(8, 9));
            hour = hour.parseInt(toParse.substring(11, 12));
            minute = year.parseInt(toParse.substring(14, 15));
            second = year.parseInt(toParse.substring(17, 18));
        } //end try
        
        //If there was a problem, return -1
        catch (NumberFormatException e) {
            return -1;
        } //end catch
        
        //Otherwise, convert the time to Millis using the gregorian calendar
        timeConverter.set(year, month, day, hour, minute, second);
        return timeConverter.getTimeInMillis();
    } //end getTime
    
} //end GPXImporter