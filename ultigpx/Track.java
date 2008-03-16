
package ultigpx;

import java.util.*;

public class Track extends ArrayList<TrackSegment>
{
    String name;
    public boolean enabled;
    
    public Track()
    {
        super();
        enabled = true;
    }
    public Track(String name)
    {
        super();
        this.name = name;
        enabled = true;
    }
    
    public Track(String name, Collection<TrackSegment> c)
    {
        super(c);
        this.name = name;
        enabled = true;
    }
    
    String getName() {
        return name;
    }
}
