
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Route extends ArrayList<Waypoint>
{
	static final long serialVersionUID = 0;
    public boolean enabled;
    public Color color;
    
    //Constructor for collection of waypoints, described as a route
    public Route()
    {
        super();
        enabled = true;
        color = null;
    }
    
    public boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean x) {
    	enabled = x;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color x) {
    	color = x;
    }
}