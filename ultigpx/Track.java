
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Track extends ArrayList<TrackSegment>
{
	static final long serialVersionUID = 0;
    String name;
    public boolean enabled;
    public Color color;
    
    //Constructor for collection of track segments, making empty list
    public Track()
    {
        super();
        enabled = true;
        color = null;
    }
    
    //Constructor for collection of track segments, making an empty list but
    //specifying name
    public Track(String name)
    {
        super();
        this.name = name;
        enabled = true;
        color = null;
    }
    
    //Constructor for collection of track segments, populated by c with specified name
    public Track(String name, Collection<TrackSegment> c)
    {
        super(c);
        this.name = name;
        enabled = true;
        color = null;
    }
    
    String getName() {
        return name;
    }
    
    public void setName(String x) {
    	name = x;
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
}
