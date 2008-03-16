
package ultigpx;

import java.util.*;

public class Track extends ArrayList<TrackSegment>
{
    String name;
    public boolean enabled;
    
    //Constructor for collection of track segments, making empty list
    public Track()
    {
        super();
        enabled = true;
    }
    
    //Constructor for collection of track segments, making an empty list but
    //specifying name
    public Track(String name)
    {
        super();
        this.name = name;
        enabled = true;
    }
    
    //Constrcutor for collection of track segments, populated by c with specified name
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
