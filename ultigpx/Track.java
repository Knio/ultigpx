
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Track extends ArrayList<TrackSegment>
{
	//static final long serialVersionUID = 0;
    String name;
	String desc;
    public boolean enabled;
    public Color color;
    
    /**
     * Constructor for collection of track segments, making empty list
     * 
     */
    public Track()
    {
        super();
        enabled = true;
        color = null;
    }
    
    /**
     * Constructor for collection of track segments, making an empty list but specifying name
     * @param name of track
     */
    public Track(String name)
    {
        super();
        this.name = name;
        enabled = true;
        color = null;
    }
    
    /**
     * Constructor for collection of track segments, populated by c with specified name
     * @param name of track
     * @ param Collection of track segments
     */
    public Track(String name, Collection<TrackSegment> c)
    {
        super(c);
        this.name = name;
        enabled = true;
        color = null;
    }
    
    /**
     * Get Track name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set Track name
     * @param String x
     */
    public void setName(String x) {
    	name = x;
    }
	
    /**
     * Get Track description
     * @return description
     */
	public String getDesc() {
        return desc;
    }
	
	/**
	 * Set Track description
	 * @param String x
	 */
    public void setDesc(String x) {
    	desc = x;
    }
    
    /**
     * Get enabled status
     * @return enabled
     */
    public boolean getEnabled() {
        return enabled;
    }
    
    /**
     * Set enabled status
     * @param boolean x
     */
    public void setEnabled(boolean x) {
    	enabled = x;
    }
    
    /**
     * Get color parameter
     * @return color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Set color parameter
     * @param Color x
     */
    public void setColor(Color x) {
    	color = x;
    }
    
    /**
     * Get string of Track
     * @return String representation of Track
     */
    public String toString()
    {
        return String.format("<TRACK name=%s n=%d>", name, size());
    }
}
