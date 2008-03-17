
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Route extends ArrayList<Waypoint>
{
    public boolean enabled;
    public Color color;
    
    //Constructor for collection of waypoints, described as a route
    public Route()
    {
        super();
        enabled = true;
        color = null;
    }
}