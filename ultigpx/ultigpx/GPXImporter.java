package ultigpx;

/**
 * Class GPX Importer is used for importing a file into the UltiGPXFile structure.  It uses JDOM for processing XML.
 * For more information about JDOM visit http://www.jdom.org/
 * This class was written for the project for CPSC 301, Winter 2008
 * @author Jill Ainsworth
 */

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;
import java.text.*;
import org.jdom.output.XMLOutputter;

public class GPXImporter implements GPXImporterExporterConstants {
    
    /**
     * importGPX imports a GPX file to a group.  The filename is given as a string, and the group is given.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to import
     * @param inputGroup a Group, contains the tracks, routes, and waypoints that were in the file after this method is run
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    public static void importGPX(Group inputGroup, String filename) throws JDOMException, IOException {
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not GPX, throw an execption
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
                    String name = DEFAULT_NAME;
                    String desc = DEFAULT_DESCRIPTION;
                    double ele = DEFAULT_ELEVATION;
                    long time = DEFAULT_TIME;
                    
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
                    
                    //Add the waypoint to the group
                    inputGroup.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute(LATITUDE).getDoubleValue(), currentElement.getAttribute(LONGITUDE).getDoubleValue(), ele, time));
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
                            String name = DEFAULT_NAME;
                            String desc = DEFAULT_DESCRIPTION;
                            double ele = DEFAULT_ELEVATION;
                            long time = DEFAULT_TIME;
                            
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
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute(LATITUDE).getDoubleValue(), currentRouteChild.getAttribute(LONGITUDE).getDoubleValue(), ele, time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                    } //end while
                    
                    //Add route to group
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
                                    String name = DEFAULT_NAME;
                                    String desc = DEFAULT_DESCRIPTION;
                                    double ele = DEFAULT_ELEVATION;
                                    long time = DEFAULT_TIME;
                                    
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
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute(LATITUDE).getDoubleValue(), currentTrackSegment.getAttribute(LONGITUDE).getDoubleValue(), ele, time);
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
    } //end importGPX (group)
    
    /**
     * importGPX imports a GPX file.  The filename is given as a string, and a database is given.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * @param filename a String, the name of the file you want to import
     * @param inputDatabase a Database, contains the tracks, routes, and waypoints that were in the file after this method is run
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    public static void importGPX(Database inputDatabase, String filename) throws JDOMException, IOException {
        //Create a new group, and input all of the data into that group
        Group inputGroup = new Group();
        importGPX(inputGroup, filename);
        
        //Move all the routes from the group to the database
        List<Route> routesToInput = inputGroup.routes();
        for (int counter = 0; counter < routesToInput.size(); counter++)
            inputDatabase.addRoute(routesToInput.get(counter));
        
        //Move all the tracks from the group to the database
        List<Track> tracksToInput = inputGroup.tracks();
        for (int counter = 0; counter < tracksToInput.size(); counter++)
            inputDatabase.addTrack(tracksToInput.get(counter));
        
        //Move all the waypoints from the group to the database
        List<Waypoint> waypointsToInput = inputGroup.waypoints();
        for (int counter = 0; counter < waypointsToInput.size(); counter++)
            inputDatabase.addWaypoint(waypointsToInput.get(counter));
    } //end importGPX (database)
    
    /**
     * importGPX imports an UltiGPX file to a database.  The filename is given as a string, a Database is given.
     * If there are any problems with the file, either a JDOMException or an IO Exception is thrown
     * Because importUltiGPX imports groups from the file and groups can not be nested, you can not import an UltiGPX file to a group
     * @param filename a String, the name of the file you want to import
     * @param inputDatabase a Database, contains the tracks, routes, and waypoints that were in the file after this method is run
     * @throws JDOMException if a problem occurs when importing the file
     * @throws IOException if a problem occurs when importing the file
     */
    
    public static void importUltiGPX(Database inputDatabase, String filename) throws JDOMException, IOException {
        // Create a new UGPXFile to put data in
        Group returnValue = new Group();
        
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not Ulti_gpx, throw an execption
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
                    String name = DEFAULT_NAME;
                    String desc = DEFAULT_DESCRIPTION;
                    double ele = DEFAULT_ELEVATION;
                    long time = DEFAULT_TIME;
                    
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
                    
                    //Add the waypoint to the database
                    returnValue.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute(LATITUDE).getDoubleValue(), currentElement.getAttribute(LONGITUDE).getDoubleValue(), ele, time));
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
                            String name = DEFAULT_NAME;
                            String desc = DEFAULT_DESCRIPTION;
                            double ele = DEFAULT_ELEVATION;
                            long time = DEFAULT_TIME;
                            
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
                            Waypoint newWaypoint = new Waypoint(name, desc, currentRouteChild.getAttribute(LATITUDE).getDoubleValue(), currentRouteChild.getAttribute(LONGITUDE).getDoubleValue(), ele, time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                        
                        else if (currentRouteChild.getName().equals(NAME))
                            newRoute.setName(currentRouteChild.getText());
                        
                    } //end while
                    
                    //Add route to database
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
                                    String name = DEFAULT_NAME;
                                    String desc = DEFAULT_DESCRIPTION;
                                    double ele = DEFAULT_ELEVATION;
                                    long time = DEFAULT_TIME;
                                    
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
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentTrackSegment.getAttribute(LATITUDE).getDoubleValue(), currentTrackSegment.getAttribute(LONGITUDE).getDoubleValue(), ele, time);
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
                //When we import a group, for each route, track, the index of the object in the default list is given, so add it to the group
                else if(currentElement.getName().equals(GROUP)) {
                    //Make variables to keep track of points in the route
                    List groupChildList = currentElement.getChildren();
                    Iterator groupChildIterator = groupChildList.iterator();
                    Group newGroup = new Group();
                    
                    //For each object in the group
                    while(groupChildIterator.hasNext()) {
                        Element currentGroupChild = (Element) groupChildIterator.next();
                        //Add name
                        if (currentGroupChild.getName().equals(NAME))
                            newGroup.name = currentGroupChild.getText();
                        
                        //Add waypoint
                        else if (currentGroupChild.getName().equals(WAYPOINT))
                            newGroup.addWaypoint(returnValue.getWaypoint(Integer.parseInt(currentGroupChild.getText())));
                        
                        //Add track
                        else if (currentGroupChild.getName().equals(TRACK))
                            newGroup.addTrack(returnValue.getTrack(Integer.parseInt(currentGroupChild.getText())));
                        
                        //Add route
                        else if (currentGroupChild.getName().equals(ROUTE))
                            newGroup.addRoute(returnValue.getRoute(Integer.parseInt(currentGroupChild.getText())));
                        
                    } //end for each element in the group
                } //end if group
                
            } //end while
        } //end if has root
        
        else //!inputFile.hasRootElement()
            throw new JDOMException();
    } //end importUltiGPX (database)
    
    /**
     * Get Time Old extracks the time from the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Gregorian calendar to then convert this into time in milis.
     * This method is private because it is only used when importing GPX files
     * If there is a formating problem, the defalut time is returned
     * This method is old, and has been replaced by getTime, do not use this method
     * @param toParse a String, the string representing time in the GPX file
     * @return double the time in milis since 1970
     * @deprecated
     */
    private static double getTimeOld(String toParse) {
        
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
            return DEFAULT_TIME;
        } //end catch
        
        //Otherwise, convert the time to Millis using the gregorian calendar
        timeConverter.set(year, month, day, hour, minute, second);
        return timeConverter.getTimeInMillis();
    } //end getTimeOld
    
    /**
     * Get Time extracks the time from the GPXFile.
     * Time in a GPX file is stored as YYYY_MM_DDTHH:MM:SSZ
     * This method uses a Simple Date Format to parse the date into a long.
     * This method is private because it is only used when importing GPX files
     * This method replaces getTimeOld
     * If there is a formating problem, the default time is returned
     * @param toParse a String, the string representing time in the GPX file
     * @return long the time
     */
    private static long getTime(String toParse) {
        //Try to format the date and return it
        try{
            Date newDate = new SimpleDateFormat("yyy-mm-dd'T'HH:mm:ss'z'").parse(toParse);
            return newDate.getTime();
            
            //If there is a problem, return a time of -1
        } catch (ParseException e) {
            return DEFAULT_TIME;
        }
    } //end getTime
    
} //end GPXImporter