
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Route extends ArrayList<Waypoint>  implements UGPXDataList
{
	static final long serialVersionUID = 0;
    public boolean enabled;
    public Color color;
    public String name;
    public String desc;
    
    //Constructor for collection of waypoints, described as a route
    public Route()
    {
        super();
        enabled = true;
        color = null;
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
     * Returns color parameter
     * @return color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets color parameter
     * @param Color x
     */
    public void setColor(Color x) {
    	color = x;
    }
    
    /**
     * Get name parameter
     * @return String name
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Sets name parameter
     * @param String x
     */
    public void setName(String x) {
    	name = x;
    }
    
    /**
     * Get description parameter
     * @return desc
     */
    public String getDesc() {
    	return desc;
    }
    
    /**
     * Set description parameter
     * @param String x
     */
    public void setDesc(String x) {
    	desc = x;
    }
    
    /**
     * Turn Route to string
     * @return String representation of Route
     */
    public String toString()
    {
        return String.format("<ROUTE name=%s n=%d>", name, size());
    }
	
	/**
     * Get the total distance of the Route
     * @return Double value for the total distance of the Route in meters
     */
    public double getDistance()
    {
        double distance = 0.0;
        
        for (int i=0;i<size()-1; i++)
            distance += get(i).distanceTo(get(i+1));
        return distance;
	}
}