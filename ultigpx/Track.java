
package ultigpx;

import java.util.*;
import java.awt.Color;

public class Track implements UGPXData
{
    //static final long serialVersionUID = 0;
    String name;
    String desc;
    public boolean enabled;
    public Color color;
    private ArrayList<TrackSegment> trackSegments = new ArrayList<TrackSegment>();
    
    /**
     * Constructor for collection of track segments, making empty list
     * 
     */
    public Track()
    {
        enabled = true;
        color = null;
    }
    
    /**
     * Constructor for collection of track segments, making an empty list but specifying name
     * @param name of track
     */
    public Track(String name)
    {
        this.name = name;
        enabled = true;
        color = null;
    }
    
    /**
     * Constructor for collection of track segments, populated by c with specified name
     * @param name of track
     * @param Collection of track segments
     */
    public Track(String name, List<TrackSegment> c)
    {
        for (int x = 0; x < c.size(); x++) {
            add(c.get(x));
        }
        this.name = name;
        enabled = true;
        color = null;
    }
    
    /**
     * Add a track segment t to the Track's list
     * @param TrackSegment t
     */
    public void add(TrackSegment t) {
        this.trackSegments.add(t);
    
    }
    
    /**
     * Remove a track segment t from the Track's list
     * @param TrackSegment t
     */
    public void remove(TrackSegment t) {
        this.trackSegments.remove(t);
    }
    
    /**
     * Check if a TrackSegment is in this Track
     * @param TrackSegment t
     * @return true is t is in this Track, false otherwise
     */
    public boolean contains(TrackSegment t) {
        return this.trackSegments.contains(t);
    }
    
    /**
     * Get Track name
     * @return String name
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
     * @return String description
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
     * @return boolean enabled status
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
        return String.format("<TRACK name=%s n=%d>", name, this.trackSegments.size());
    }
    
    /**
     * Get the total distance of the Track
     * @return Double value for the total distance of the Track in meters
     */
    public double getDistance() {
        double distance = 0.0;
        double ndist;
        for (TrackSegment s:this.trackSegments) {
            distance = distance + s.getDistance();
        }
        return distance;
    }
    
    /**
     * Return an ArrayList of track segments
     * @return ArrayList<TrackSegment>
     */
    public ArrayList<TrackSegment> getArray() {
        return this.trackSegments;
    }
    
    /**
     * Return the size of the track segment array
     * @return int
     */
    public int size() {
        return this.trackSegments.size();
    }
    
    /**
     * Refreshes the internal waypoints "dist" parameter,
     * which denotes the distance from the start of the track.
     * this is used for the dstance x height projection.
     * (added by Nathan)
     */
    public void refreshDist() {
        double dist = 0.0;
        Waypoint last = null;
        for(TrackSegment ts : this.trackSegments)
            for(Waypoint wp : ts) {
                if(last != null) {
                    dist += last.distanceTo(wp);
                }
                wp.dist = dist;
                last = wp;
            }
    }
}
