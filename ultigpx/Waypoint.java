
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
    
    public String getName() {
        return name; 
    }
    
    public void setName(String x) {
    	name = x;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String x) {
    	desc = x;
    }
    
    public double getLat() {
        return lat;
    }
    
    public void setLat(double x) {
    	lat = x;
    }
    
    public double getLon() {
        return lon;
    }
    
    public void setLon(double x) {
    	lon = x;
    }
    
    public double getEle() {
        return ele;
    }
    
    public void setEle(double x) {
    	ele = x;
    }
    
    public double getTime() {
        return time;
    }
    
    public void setTime(double x) {
    	time = x;
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