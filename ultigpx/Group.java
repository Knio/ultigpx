
package ultigpx;

import java.util.*;


public class Group
{
    private List<Waypoint>  waypoint;
    private List<Track>     track;
    private List<Route>     route;

    public String name;
    public boolean enabled;
    
    /**
     * Constructor to send in list with list of data
     * @param List<Waypoint> wp
     * @param List<Track> tk
     * @param List<Route> rt
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
	 * @param boolean x value
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
     * @param integer n
     * @return Track
     * @throws IndexOutOfBoundsException
     */
    public Track getTrack(int n) throws IndexOutOfBoundsException
    {
        return track.get(n);
    }
    
    /**
     * Get route
     * @param integer n
     * @return Route
     * @throws IndexOutOfBoundsException
     */
    public Route getRoute(int n) throws IndexOutOfBoundsException
    {
        return route.get(n);
    }
    
    /**
     * Get waypoint
     * @param integer n
     * @return Waypoint
     * @throws IndexOutOfBoundsException
     */
    public Waypoint getWaypoint(int n) throws IndexOutOfBoundsException
    {
        return waypoint.get(n);
    }
    
    /**
     * Add track to file
     * @param Track t
     */
    public void addTrack(Track t) {
    	track.add(t);
        if (t.getName() == null)
            t.name = "Track " + track.size();
    }
    
    /**
     * Add route to file
     * @param Route r
     */
    public void addRoute(Route r) {
    	route.add(r);
        if (r.getName() == null)
            r.name = "Route " + route.size();
    }
    
    /**
     * Add waypoint to file
     * @param Waypoint w
     */
    public void addWaypoint(Waypoint w) {
    	waypoint.add(w);
    }
}
