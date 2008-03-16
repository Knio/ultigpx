
package ultigpx;

import java.util.*;

public class Route extends ArrayList<Waypoint>
{
    public boolean enabled;
    
    //Constructor for collection of waypoints, described as a route
    public Route()
    {
        super();
        enabled = true;
    }
}