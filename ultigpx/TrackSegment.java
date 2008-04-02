
package ultigpx;

import java.util.*;

public class TrackSegment extends ArrayList<Waypoint>
{
    static final long serialVersionUID = 0;
    public Track parent;
    
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
     * @return
     */
    public Track getParent() {
        return parent;
    }
    
    /**
     * Get the total distance of the TrackSegment
     * @return Double value for the total distance of the TrackSegment in meters
     */
	public Double getDistance() {
		double distance = 0.0;
		Iterator iter = this.iterator();
		Waypoint lastPoint = null;
		Waypoint tempWP;
		
		if (iter.hasNext()) lastPoint = (Waypoint)iter.next();
		for(;iter.hasNext();) {
			tempWP = (Waypoint)iter.next();
			distance = distance + lastPoint.distanceTo(tempWP);
		}
		
		return distance;
	}
}