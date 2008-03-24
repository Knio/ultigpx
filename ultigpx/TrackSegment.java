
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
}