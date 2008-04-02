
package ultigpx;

import java.awt.Color;

public class Waypoint
{
    private String name;
    private String desc;
    
    public boolean enabled;
    public Color  color;
    public double lat;
    public double lon;
    public double ele;
    public double time;
    
    public Waypoint(String _name, 
                    String _desc, 
                    double _lat,
                    double _lon,
                    double _ele,
                    double _time)
    {
        name    = _name;
        desc    = _desc;
        lat     = _lat;
        lon     = _lon;
        ele     = _ele;
        time    = _time;
        enabled = true;
        color = null;
    }
    
    
    /**
     * Returns name parameter
     * @return name
     */
    public String getName() {
        return name; 
    }
    
    /**
     * Set Waypoint name
     * @param String x
     */
    public void setName(String x) {
    	name = x;
    }
    
    /**
     * Returns description
     * @return String description
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Set description
     * @param String x
     */
    public void setDesc(String x) {
    	desc = x;
    }
    
    /**
     * Returns Latitude parameter
     * @return Latitude
     */
    public double getLat() {
        return lat;
    }
    
    /**
     * Set Latitude
     * @param double x
     */
    public void setLat(double x) {
    	lat = x;
    }
    
    /**
     * Returns Longitude parameter
     * @return longitude
     */
    public double getLon() {
        return lon;
    }
    
    /**
     * Set longitude
     * @param double x
     */
    public void setLon(double x) {
    	lon = x;
    }
    
    /**
     * Returns elevation parameter
     * @return elevation
     */
    public double getEle() {
        return ele;
    }
    
    /**
     * Set elevation
     * @param double x
     */
    public void setEle(double x) {
    	ele = x;
    }
    
    /**
     * Returns time parameter
     * @return time
     */
    public double getTime() {
        return time;
    }
    
    /**
     * Set time
     * @param double x
     */
    public void setTime(double x) {
    	time = x;
    }
    
    /**
     * Returns enabled status
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
     * Returns color parameter
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
     * Turn Route to string
     * @return String representation of Route
     */
    public String toString()
    {
        return String.format("<WP name=%s lat=%.3f lon=%.3f>", name, lat, lon);
    }
	
	/**
	 * Get the distance between this point and the given point
	 * @param The other waypoint
	 * @return Double value of the distance in meters
	 */
	public double distanceTo(Waypoint wpt)
	{
		if (wpt == null) return 0.0;
		double lat1 = Math.toRadians(this.getLat());
		double lon1 = Math.toRadians(this.getLon());
		double lat2 = Math.toRadians(wpt.getLat());
		double lon2 = Math.toRadians(wpt.getLon());
		double radius = 6371000;			// Approx radius of the earth in meters
		
		// Spherical law of cosines
		double distance = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2-lon1)) * radius;
		
		return distance;
	}
}