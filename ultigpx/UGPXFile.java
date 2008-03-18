
package ultigpx;

import java.util.*;


public class UGPXFile
{
    private List<Waypoint>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
    
    // Constructor to send in list with list of data
    public UGPXFile(List<Waypoint> wp, List<Track> tk, List<Route> rt)
    {
        waypoint = new ArrayList<Waypoint>(wp);
        track    = new ArrayList<Track>(tk);
        route    = new ArrayList<Route>(rt);
    }
    
    //Constructor to create null UGPXFile, to be populated by add methods
    public UGPXFile() {
    	waypoint = new ArrayList<Waypoint>();
    	track = new ArrayList<Track>();
    	route = new ArrayList<Route>();
    }
    
    public List<Track> tracks()
    {
        return track;
    }
    
    public List<Route> routes()
    {
        return route;
    }
    
    public List<Waypoint> waypoints()
    {
        return waypoint;
    }
    
    public Track getTrack(int n) throws IndexOutOfBoundsException
    {
        return track.get(n);
    }
    
    public Route getRoute(int n) throws IndexOutOfBoundsException
    {
        return route.get(n);
    }
    
    public Waypoint getWaypoint(int n) throws IndexOutOfBoundsException
    {
        return waypoint.get(n);
    }
    
    public void addTrack(Track t) {
    	track.add(t);
    }
    
    public void addRoute(Route r) {
    	route.add(r);
        
    }
    
    public void addWaypoint(Waypoint w) {
    	waypoint.add(w);
    }
}
