
package ultigpx;

import java.util.*;

/*
 * Group class that contains a listing of waypoints, tracks and routes, as well as name and
 * enabled status
 */
public class Group
{
    protected List<Waypoint>  waypoint;
    protected List<Track>     track;
    protected List<Route>     route;

    public String name;
    public boolean enabled;
    
    /**
     * Constructor to send in list with list of data
     * @param wp List<Waypoint> 
     * @param tk List<Track> 
     * @param rt List<Route> 
     */ 
    public Group(List<Waypoint> wp, List<Track> tk, List<Route> rt)
    {
        waypoint = new ArrayList<Waypoint>(wp);
        track    = new ArrayList<Track>(tk);
        route    = new ArrayList<Route>(rt);
        name = "";
    }
    
    /**
     *Constructor to create null Group, to be populated by add methods
     */
    public Group() {
    	waypoint = new ArrayList<Waypoint>();
    	track = new ArrayList<Track>();
    	route = new ArrayList<Route>();
        name = "";
    }
    
    /**
	 * Returns enabled status
	 * @return enabled
	 */
    public boolean getEnabled() {
        return enabled;
    }
    
    /**
	 * Sets enabled status
	 * @param x : boolean
	 */
    public void setEnabled(boolean x) {
    	enabled = x;
    }
    
    /**
     * Get tracks
     * @return List<Track>
     */
    public List<Track> tracks()
    {
        return track;
    }
    
    /**
     * Get routes
     * @return List<Route>
     */
    public List<Route> routes()
    {
        return route;
    }
    
    /**
     * Get waypoints
     * @return List<Waypoint>
     */
    public List<Waypoint> waypoints()
    {
        return waypoint;
    }
    
    /**
     * Get track
     * @param n integer
     * @return Track
     * @throws IndexOutOfBoundsException
     */
    public Track getTrack(int n) throws IndexOutOfBoundsException
    {
        return track.get(n);
    }
    
    /**
     * Get route
     * @param n integer
     * @return Route
     * @throws IndexOutOfBoundsException
     */
    public Route getRoute(int n) throws IndexOutOfBoundsException
    {
        return route.get(n);
    }
    
    /**
     * Get waypoint
     * @param n integer
     * @return Waypoint
     * @throws IndexOutOfBoundsException
     */
    public Waypoint getWaypoint(int n) throws IndexOutOfBoundsException
    {
        return waypoint.get(n);
    }
    
    public int size()
    {
        return track.size() + route.size() + waypoint.size();
    }
    
    /**
     * Add track to file
     * @param t Track
     */
    public void addTrack(Track t) {
    	track.add(t);
        if (t.getName() == null)
            t.name = "Track " + track.size();
    }
    
    /**
     * Add route to file
     * @param r Route
     */
    public void addRoute(Route r) {
    	route.add(r);
        if (r.getName() == null)
            r.name = "Route " + route.size();
    }
    
    /**
     * Add waypoint to file
     * @param w Waypoint
     */
    public void addWaypoint(Waypoint w) {
    	waypoint.add(w);
    }
    
    /**
     * Add a UGPXData object to the current group
     * @param x UGPXData
     */
    public void add(UGPXData x)
    {
        if (x instanceof Track) addTrack((Track)x);
        if (x instanceof Route) addRoute((Route)x);
        if (x instanceof Waypoint) addWaypoint((Waypoint)x);
    }
    
    /**
     * Remove an instance of UGPXData object from current group
     * @param x
     * @return boolean value of  removal
     */
    public boolean remove(UGPXData x)
    {
        if (x instanceof Track)     return track.remove((Track)x);
        if (x instanceof Route)     return route.remove((Route)x);
        if (x instanceof Waypoint)  return waypoint.remove((Waypoint)x);
        return false;
    }
    
    
    /**
     * Check if current group has an instance of a route
     * @param x Route
     * @return boolean value of search
     */
    public boolean containsRoute(Route x)
    {
        return route.contains(x);
    }   
    
    /**
     * Check if current group has an instance of a track
     * @param x track
     * @return boolean value of search
     */
    public boolean containsTrack(Track x)
    {
        return track.contains(x);
    }   
    
    /**
     * Check if current group has an instance of a waypoint
     * @param x waypoint
     * @return boolean value of search
     */
    public boolean containsWaypoint(Waypoint x)
    {
        return waypoint.contains(x);
    }   
    
    /**
     * Check if current group has an instance of an UGPXData ojbect
     * @param x UGPXData
     * @return boolean value of search
     */
    public boolean contains(UGPXData x)
    {
        if (x instanceof Track)     return track.contains((Track)x);
        if (x instanceof Route)     return route.contains((Route)x);
        if (x instanceof Waypoint)  return waypoint.contains((Waypoint)x);
        return false;
    }   
    /*
    public Iterator<UGPXData> iterator()
    {
        return new GroupIterator();
    }
    
    /* work in progress
    class GroupIterator implements iterator<UGPXData>
    {
        iterator i;
        public GroupIterator()
        {
        
        }
        public UGPXData next() throws NoSuchElementException
        {
            try
            {
                return i.next();
            }
            catch NoSuchElementException
            {
                if
            }
            
        }
    }
    //*/
}
