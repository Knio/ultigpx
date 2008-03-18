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
            
            while (childListIterator.hasNext())
            {
                Element currentElement = (Element) childListIterator.next();
                
                //Determine the type of this element
                
                //If it is a waypoint
                if (currentElement.getName().equals("wpt"))
                {
                    List waypointChildList = currentElement.getChildren();
                    Iterator waypointChildIterator = waypointChildList.iterator();
                    String name = "";
                    String desc = "";
                    double ele = 0;
                    double time = 0;
                    
                    while(waypointChildIterator.hasNext())
                    {
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
                else if (currentElement.getName().equals("rte"))
                {
                    ;
                    //Get <name> xsd:string </name> [0..1] ?
                    //Get <cmt> xsd:string </cmt> [0..1] ?
                    //Get <desc> xsd:string </desc> [0..1] ?
                    //Get <src> xsd:string </src> [0..1] ?
                    //Get <link> linkType </link> [0..*] ?
                    //Get <number> xsd:nonNegativeInteger </number> [0..1] ?
                    //Get <type> xsd:string </type> [0..1] ?
                    //Get <extensions> extensionsType </extensions> [0..1] ?
                    //Get <rtept> wptType </rtept> [0..*] ?
                        //Copy this from above
                    //returnValue.addRoute();
                } //end if route
                
                //If it is a track
                else if (currentElement.getName().equals("trk"))
                {
                    ;
                    //Get <name> xsd:string </name> [0..1] ?
                    //Get <cmt> xsd:string </cmt> [0..1] ?
                    //Get <desc> xsd:string </desc> [0..1] ?
                    //Get <src> xsd:string </src> [0..1] ?
                    //Get <link> linkType </link> [0..*] ?
                    //Get <number> xsd:nonNegativeInteger </number> [0..1] ?
                    //Get <type> xsd:string </type> [0..1] ?
                    //Get <extensions> extensionsType </extensions> [0..1] ?
                    //Get <trkseg> trksegType </trkseg> [0..*] ?
    
                    //Track segment stuff
                        //Get <trkpt> wptType </trkpt> [0..*] ?
                        //Get <extensions> extensionsType </extensions> [0..1] ?
    
                    //returnValue.addTrack(name);
                } //end if track
                
            } //end while
            
        } //end if has root
        
        else
            throw new JDOMException();
        
        return returnValue;
    }
    
}