
package ultigpx;

import java.util.*;

public class Track extends ArrayList<TrackSegment>
{
    String name;
    
    public Track()
    {
        super();
    }
    public Track(String name)
    {
        this.name = name;
    }
    
    public Track(String name, Collection<TrackSegment> c)
    {
        super(c);
        this.name = name;
    }
    
    String getName() {
    	return name;
    }
}
