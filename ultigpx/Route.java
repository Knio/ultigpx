
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Route extends ArrayList<Waypoint>
{
	static final long serialVersionUID = 0;
    public boolean enabled;
    public Color color;
    public String name;
    public String description;
    
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
    
    public String getName() {
    	return name;
    }
    
    public void setName(String x) {
    	name = x;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String x) {
    	description = x;
    }
}