
package ultigpx;

import java.util.*;

public class TrackSegment extends ArrayList<Waypoint>
{
	static final long serialVersionUID = 0;
	public Track parent;
	
    public TrackSegment() {
    	super();
    }
    
    public void setParent(Track x) {
    	parent = x;
    }
    
    public Track getParent() {
    	return parent;
    }
}