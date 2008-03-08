
package ultigpx;

import java.util.*;

public class Waypoint
{
    private String name;
    private String desc;
    
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
    }
    
    public String getName() { return name; }
    public String getDesc() { return desc; }
}