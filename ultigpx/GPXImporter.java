package ultigpx;


import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

//Author Jill Ainsworth
public class GPXImporter {
    
    public static UGPXFile importGPX(String filename) throws JDOMException, IOException {
        // Create a new UGPXFile to put data in
        UGPXFile returnValue = new UGPXFile();
        
        // Create the document from the input stream using JAXPDOM adapter
        Document inputFile = new SAXBuilder().build(new File(filename));
        
        // Get the root Element document.getRootElement
        if (inputFile.hasRootElement()) {
            Element root = inputFile.getRootElement();
            
            // If the root is not GPX, throw an execption (HELP!)
            
            // Get each of the children
            List childList = root.getChildren();
            
            //For every child, read it in
            Iterator childListIterator = childList.iterator();
            
            while (childListIterator.hasNext()) {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                
                //If it is a waypoint
                if (currentElement.getName().equals("wpt")) {
                    List waypointChildList = currentElement.getChildren();
                    Iterator waypointChildIterator = waypointChildList.iterator();
                    String name = "";
                    String desc = "";
                    double ele = 0;
                    double time = 0;
                    
                    while(waypointChildIterator.hasNext()) {
                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                        if (currentWaypointChild.getName().equals("name"))
                            name = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("desc"))
                            desc = currentWaypointChild.getText();
                        else if (currentWaypointChild.getName().equals("ele"))
                            ele = Double.parseDouble(currentWaypointChild.getText());
                        else if (currentWaypointChild.getName().equals("time"))
                            time = Double.parseDouble(currentWaypointChild.getText());
                    } //end while
                    returnValue.addWaypoint( new Waypoint(name, desc, currentElement.getAttribute("lat").getDoubleValue(), currentElement.getAttribute("lon").getDoubleValue(), ele, time));
                } //end if waypoint
                
                //If it is a route
                else if (currentElement.getName().equals("rte")) {
                    List routeChildList = currentElement.getChildren();
                    Iterator routeChildIterator = routeChildList.iterator();
                    Route newRoute = new Route();
                    
                    while(routeChildIterator.hasNext()) {
                        Element currentRouteChild = (Element) routeChildIterator.next();
                        if (currentElement.getName().equals("rtept")) {
                            List waypointChildList = currentElement.getChildren();
                            Iterator waypointChildIterator = waypointChildList.iterator();
                            String name = "";
                            String desc = "";
                            double ele = 0;
                            double time = 0;
                            
                            while(waypointChildIterator.hasNext()) {
                                Element currentWaypointChild = (Element) waypointChildIterator.next();
                                if (currentWaypointChild.getName().equals("name"))
                                    name = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("desc"))
                                    desc = currentWaypointChild.getText();
                                else if (currentWaypointChild.getName().equals("ele"))
                                    ele = Double.parseDouble(currentWaypointChild.getText());
                                else if (currentWaypointChild.getName().equals("time"))
                                    time = Double.parseDouble(currentWaypointChild.getText());
                            } //end while
                            
                            Waypoint newWaypoint = new Waypoint(name, desc, currentElement.getAttribute("lat").getDoubleValue(), currentElement.getAttribute("lon").getDoubleValue(), ele, time);
                            newRoute.add(newWaypoint);
                            
                        } //end if waypoint
                    } //end while
                    
                    returnValue.addRoute(newRoute);
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals("trk")) {
                    List trackChildList = currentElement.getChildren();
                    Iterator trackChildIterator = trackChildList.iterator();
                    Track newTrack = new Track();
                    
                    //Get track segments
                    while(trackChildIterator.hasNext()) {
                        Element currentTrackChild = (Element) trackChildIterator.next();
                        if (currentElement.getName().equals("trkseg")) {
                            List trackSegmentList = currentElement.getChildren();
                            Iterator trackSegmentIterator = trackSegmentList.iterator();
                            TrackSegment newTrackSegment = new TrackSegment();
                            
                            //Get trackpoints
                            while (trackSegmentIterator.hasNext()){
                                Element currentTrackSegment = (Element) trackSegmentIterator.next();
                                if (currentElement.getName().equals("trkpt")) {
                                    List waypointChildList = currentElement.getChildren();
                                    Iterator waypointChildIterator = waypointChildList.iterator();
                                    String name = "";
                                    String desc = "";
                                    double ele = 0;
                                    double time = 0;
                                    
                                    while(waypointChildIterator.hasNext()) {
                                        Element currentWaypointChild = (Element) waypointChildIterator.next();
                                        if (currentWaypointChild.getName().equals("name"))
                                            name = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("desc"))
                                            desc = currentWaypointChild.getText();
                                        else if (currentWaypointChild.getName().equals("ele"))
                                            ele = Double.parseDouble(currentWaypointChild.getText());
                                        else if (currentWaypointChild.getName().equals("time"))
                                            time = Double.parseDouble(currentWaypointChild.getText());
                                    } //end while
                                    
                                    //Add waypoints to track segments
                                    Waypoint newWaypoint = new Waypoint(name, desc, currentElement.getAttribute("lat").getDoubleValue(), currentElement.getAttribute("lon").getDoubleValue(), ele, time);
                                    newTrackSegment.add(newWaypoint);
                                    
                                } //end if track point
                            } //end while get trackpoints
                            
                            //Add track segments to track
                            newTrack.add(newTrackSegment);
                            
                        } //end if track segment
                    } //end while for getting track segments
                    returnValue.addTrack(newTrack);
                } //end if track
                
            } //end while
            
        } //end if has root
        
        else
            throw new JDOMException();
        
        return returnValue;
    }
    
}