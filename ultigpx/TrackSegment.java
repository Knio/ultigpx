
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
	public double getDistance() {
		double distance = 0.0;
		Iterator iter = this.iterator();
		Waypoint lastPoint = null;
		Waypoint tempWP;
		double dto;
		
		if (iter.hasNext()) lastPoint = (Waypoint)iter.next();
		for(;iter.hasNext();) {
			tempWP = (Waypoint)iter.next();
			dto = lastPoint.distanceTo(tempWP);
			distance = distance + dto;
			if ("NaN".equals("" + dto)) System.out.println("Distance to is NaN ASDASDASDASDASDASDAS");
		}
		
		return distance;
	}
}