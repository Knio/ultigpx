
package ultigpx;

import java.util.*;

public class TrackSegment extends ArrayList<Waypoint>
{
    static final long serialVersionUID = 0;
    public Track parent;
    
    /**
     * Constructor for empty track segment
     */
    public TrackSegment() {
        super();
    }
    
    /**
     * Set parent Track of TrackSegment
     * @param Track x
     */
    public void setParent(Track x) {
        parent = x;
    }
    
    /**
     * return parent Track of TrackSegment
     * @return Track
     */
    public Track getParent() {
        return parent;
    }
    
    /**
     * Get the total distance of the TrackSegment
     * @return Double value for the total distance of the TrackSegment in meters
     */
    public double getDistance()
    {
        double distance = 0.0;
        
        for (int i=0;i<size()-1; i++)
            distance += get(i).distanceTo(get(i+1));
        return distance;
	}
}