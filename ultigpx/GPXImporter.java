package ultigpx;


/**
 * Class GPX Importer is used for importing a GPX file into the UltiGPXFile structure.  It uses JDOM for processing XML.
 * For more information about JDOM visit http://www.jdom.org/
 * This class was written for Part 1 of the project for CPSC 301, Winter 2008
 * @author Jill Ainsworth
 *
 * Jill needs to fix time conversion, she is going to bring this up on Monday in lab
 */

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

public class GPXImporter implements GPXImporterExporterConstants {
    
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
            if (!root.getName().equals(GPX))
                throw new IOException();
            
            // Get each of the children
            List childList = root.getChildren();
            
            //For every child, read it in
            Iterator childListIterator = childList.iterator();
            while (childListIterator.hasNext()) {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                //If it is a waypoint
                if (currentElement.getName().equals(WAYPOINT)) {
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
                        if (currentWaypointChild.getName().equals(NAME))
                            name = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals(DESCRIPTION))
                            desc = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals(ELEVATION))
                            ele = Double.parseDouble(currentWaypointChild.getText());
                        else if (currentWaypointChild.getName().equals(TIME))
                            time = getTime(currentWaypointChild.getText());
                    } //end while
                    
                    //Add the waypoint to the GPXFile
                    inputGroup.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute(LATITUDE).getDoubleValue(), currentElement.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time));
                } //end if waypoint
                
                //If it is a route
                else if (currentElement.getName().equals(ROUTE)) {
                    //Make variables to keep track of points in the route
                    List routeChildList = currentElement.getChildren();
                    Iterator routeChildIterator = routeChildList.iterator();
                    Route newRoute = new Route();
                    
                    //For each point in the route
                    while(routeChildIterator.hasNext()) {
                        Element currentRouteChild = (Element) routeChildIterator.next();
                        if (currentRouteChild.getName().equals(ROUTE_POINT)) {
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
                                if (currentWaypointChild.getName().equals(NAME))
                                    name = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals(DESCRIPTION))
                                    desc = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals(ELEVATION))
                                    ele = Double.parseDouble(currentWaypointChild.getText());
                                else if (currentWaypointChild.getName().equals(TIME))
                                    time = getTime(currentWaypointChild.getText());
                            } //end while
                            
                            //Add point to the route
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute(LATITUDE).getDoubleValue(), currentRouteChild.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                    } //end while
                    
                    //Add route to GPXFILE
                    inputGroup.addRoute(newRoute);
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals(TRACK)) {
                    //Make variables to keep track of points in the route
                    List trackChildList = currentElement.getChildren();
                    Iterator trackChildIterator = trackChildList.iterator();
                    Track newTrack = new Track();
                    
                    //Get track segments
                    while(trackChildIterator.hasNext()) {
                        //For each track segment
                        Element currentTrackChild = (Element) trackChildIterator.next();
                        if (currentTrackChild.getName().equals(TRACK_SEGMENT)) {
                            //Extract Information about that track segment
                            List trackSegmentList = currentTrackChild.getChildren();
                            Iterator trackSegmentIterator = trackSegmentList.iterator();
                            TrackSegment newTrackSegment = new TrackSegment();
                            
                            //Get trackpoints
                            while (trackSegmentIterator.hasNext()){
                                //Make variable to represent name, description, elevation and time for that point
                                Element currentTrackSegment = (Element) trackSegmentIterator.next();
                                if (currentTrackSegment.getName().equals(TRACK_POINT)) {
                                    List waypointChildList = currentTrackSegment.getChildren();
                                    Iterator waypointChildIterator = waypointChildList.iterator();
                                    String name = "";
                                    String desc = "";
                                    double ele = 0;
                                    double time = -1;
                                    
                                    //Extract name, description, elevation, and time
                                    while(waypointChildIterator.hasNext()) {
                                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                                        if (currentWaypointChild.getName().equals(NAME))
                                            name = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals(DESCRIPTION))
                                            desc = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals(ELEVATION))
                                            ele = Double.parseDouble(currentWaypointChild.getText());
                                        else if (currentWaypointChild.getName().equals(TIME))
                                            time = getTime(currentWaypointChild.getText());
                                    } //end while
                                    
                                    //Add trackpoints to track segments
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute(LATITUDE).getDoubleValue(), currentTrackSegment.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time);
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
    public static void importUltiGPX(Database inputDatabase, String filename) throws JDOMException, IOException {
        // Create a new UGPXFile to put data in
        Group returnValue = new Group();
        
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not GPX, throw an execption (HELP!)
            if (!root.getName().equals(ULTI_GPX))
                throw new IOException();
            
            // Get each of the children
            List childList = root.getChildren();
            
            //For every child, read it in
            Iterator childListIterator = childList.iterator();
            while (childListIterator.hasNext()) {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                //If it is a waypoint
                if (currentElement.getName().equals(WAYPOINT)) {
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
                        if (currentWaypointChild.getName().equals(NAME))
                            name = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals(DESCRIPTION))
                            desc = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals(ELEVATION))
                            ele = Double.parseDouble(currentWaypointChild.getText());
                        else if (currentWaypointChild.getName().equals(TIME))
                            time = getTime(currentWaypointChild.getText());
                    } //end while
                    
                    //Add the waypoint to the GPXFile
                    returnValue.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute(LATITUDE).getDoubleValue(), currentElement.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time));
                } //end if waypoint
                
                //If it is a route
                else if (currentElement.getName().equals(ROUTE)) {
                    //Make variables to keep track of points in the route
                    List routeChildList = currentElement.getChildren();
                    Iterator routeChildIterator = routeChildList.iterator();
                    Route newRoute = new Route();
                    
                    //For each point in the route
                    while(routeChildIterator.hasNext()) {
                        Element currentRouteChild = (Element) routeChildIterator.next();
                        if (currentRouteChild.getName().equals(ROUTE_POINT)) {
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
                                if (currentWaypointChild.getName().equals(NAME))
                                    name = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals(DESCRIPTION))
                                    desc = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals(ELEVATION))
                                    ele = Double.parseDouble(currentWaypointChild.getText());
                                else if (currentWaypointChild.getName().equals(TIME))
                                    time = getTime(currentWaypointChild.getText());
                            } //end while
                            
                            //Add point to the route
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute(LATITUDE).getDoubleValue(), currentRouteChild.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                        
                        else if (currentRouteChild.getName().equals(NAME))
                            newRoute.setName(currentRouteChild.getText());
                        
                    } //end while
                    
                    //Add route to GPXFILE
                    returnValue.addRoute(newRoute);
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals(TRACK)) {
                    //Make variables to keep track of points in the route
                    List trackChildList = currentElement.getChildren();
                    Iterator trackChildIterator = trackChildList.iterator();
                    Track newTrack = new Track();
                    
                    //Get track segments
                    while(trackChildIterator.hasNext()) {
                        //For each track segment
                        Element currentTrackChild = (Element) trackChildIterator.next();
                        if (currentTrackChild.getName().equals(TRACK_SEGMENT)) {
                            //Extract Information about that track segment
                            List trackSegmentList = currentTrackChild.getChildren();
                            Iterator trackSegmentIterator = trackSegmentList.iterator();
                            TrackSegment newTrackSegment = new TrackSegment();
                            
                            //Get trackpoints
                            while (trackSegmentIterator.hasNext()){
                                //Make variable to represent name, description, elevation and time for that point
                                Element currentTrackSegment = (Element) trackSegmentIterator.next();
                                if (currentTrackSegment.getName().equals(TRACK_POINT)) {
                                    List waypointChildList = currentTrackSegment.getChildren();
                                    Iterator waypointChildIterator = waypointChildList.iterator();
                                    String name = "";
                                    String desc = "";
                                    double ele = 0;
                                    double time = -1;
                                    
                                    //Extract name, description, elevation, and time
                                    while(waypointChildIterator.hasNext()) {
                                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                                        if (currentWaypointChild.getName().equals(NAME))
                                            name = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals(DESCRIPTION))
                                            desc = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals(ELEVATION))
                                            ele = Double.parseDouble(currentWaypointChild.getText());
                                        else if (currentWaypointChild.getName().equals(TIME))
                                            time = getTime(currentWaypointChild.getText());
                                    } //end while
                                    
                                    //Add trackpoints to track segments
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute(LATITUDE).getDoubleValue(), currentTrackSegment.getAttribute(LONGITUDE).getDoubleValue(), ele, (long)time);
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
                
                //If it is a group
                else if(currentElement.getName().equals(GROUP)) {
                    //Make variables to keep track of points in the route
                    List groupChildList = currentElement.getChildren();
                    Iterator groupChildIterator = groupChildList.iterator();
                    Group newGroup = new Group();
                    
                    //For each thing in the group
                    while(groupChildIterator.hasNext()) {
                        Element currentGroupChild = (Element) groupChildIterator.next();
                        if (currentGroupChild.getName().equals(NAME))
                            newGroup.name = currentGroupChild.getText();
                        else if (currentGroupChild.getName().equals(WAYPOINT))
                            newGroup.addWaypoint(returnValue.getWaypoint(Integer.parseInt(currentGroupChild.getText())));
                        else if (currentGroupChild.getName().equals(TRACK))
                            newGroup.addTrack(returnValue.getTrack(Integer.parseInt(currentGroupChild.getText())));
                        else if (currentGroupChild.getName().equals(ROUTE))
                            newGroup.addRoute(returnValue.getRoute(Integer.parseInt(currentGroupChild.getText())));
                        
                    } //end for each element in the group
                } //end if group
                
            } //end while
        } //end if has root
        
        else //!inputFile.hasRootElement()
            throw new JDOMException();
        
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
            year = year.parseInt(toParse.substring(0, 4));
            month = month.parseInt(toParse.substring(5, 7));
            day = day.parseInt(toParse.substring(8, 10));
            hour = hour.parseInt(toParse.substring(11, 13));
            minute = minute.parseInt(toParse.substring(14, 16));
            second = second.parseInt(toParse.substring(17, 19));
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