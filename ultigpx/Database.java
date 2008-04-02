
package ultigpx;

import java.util.ArrayList;
import java.util.List;


public class Database {
	private List<Waypoint>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
    private List<Group>		group;
    
    /**
    * Constructor to send in list with list of data
    * @param List<Waypoint> wp
    * @param List<Track> tk
    * @param List<Route> rt
    */ 
   public Database(List<Waypoint> wp, List<Track> tk, List<Route> rt, List<Group> g)
   {
       waypoint = new ArrayList<Waypoint>(wp);
       track    = new ArrayList<Track>(tk);
       route    = new ArrayList<Route>(rt);
       group    = new ArrayList<Group>(g);
   }
   
   /**
    *Constructor to create null Database, to be populated by add methods
    */
   public Database() {
   	waypoint = new ArrayList<Waypoint>();
   	track = new ArrayList<Track>();
   	route = new ArrayList<Route>();
   	group = new ArrayList<Group>();
   }
   
   /**
    * Get groups
    * @return List<Group>
    */
   public List<Group> groups()
   {
       return group;
   }

   /**
    * Get group
    * @param integer n
    * @return Group
    * @throws IndexOutOfBoundsException
    */
   public Group getGroup(int n) throws IndexOutOfBoundsException
   {
       return group.get(n);
   }
   
   /**
    * Add group to file
    * @param Group t
    */
   public void addGroup(Group g) {
   	group.add(g);
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
