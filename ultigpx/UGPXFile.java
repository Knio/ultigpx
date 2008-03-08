
package ultigpx;

import java.util.*;
//import ultigpx.*;


public class UGPXFile
{
    private List<Waypoint>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
    
    public UGPXFile(List<Waypoint> wp, List<Track> tk, List<Route> rt)
    {
        waypoint = new ArrayList<Waypoint>(wp);
        track    = new ArrayList<Track>(tk);
        route    = new ArrayList<Route>(rt);
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
}
