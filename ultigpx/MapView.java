
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;


abstract public class MapView extends JPanel
{
    UltiGPX         main;
    Object          selected;
    
    static public double lon;
    static public double lat;
    static public double scale;
    
    public static Color            WAYPOINT_COLOR  = Color.BLACK;
    public static Color            TRACK_COLOR     = Color.BLUE;
    public static Color            ROUTE_COLOR     = Color.GREEN;
    public static Color            SELECTED_COLOR  = Color.RED;
    
    public static final int        WAYPOINT_SIZE   = 5;
    public static final int        FONT_SIZE       = 9;
    
    public static final double     MAX_SCALE       = 100000.0;
    public static final double     MIN_SCALE       = 20.0;
    
    public static final double     ZOOM_IN         = 1.10;
    public static final double     ZOOM_OUT        = 0.90909090909;


	public double[] zoomLevels = new double[18];
    
    
    public MapView(UltiGPX main)
    {
        super();
        this.main = main;
		zoomLevels[0] = 0.711111;
		zoomLevels[1] = 1.422222;
		zoomLevels[2] = 2.844444;
		zoomLevels[3] = 5.688888;
		zoomLevels[4] = 11.377777;
		zoomLevels[5] = 22.755555;
		zoomLevels[6] = 45.511111;
		zoomLevels[7] = 91.022222;
		zoomLevels[8] = 182.044444;
		zoomLevels[9] = 364.088888;
		zoomLevels[10] = 728.177777;
		zoomLevels[11] = 1456.355555;
		zoomLevels[12] = 2912.711111;
		zoomLevels[13] = 5825.422222;
		zoomLevels[14] = 11650.844444;
		zoomLevels[15] = 23301.688888;
		zoomLevels[16] = 46603.377777;
		zoomLevels[17] = 93206.755555;
    }

    
    abstract public void fill();
    
        
    protected void scroll(double lon, double lat)
    {
        lon = Math.max(-180, lon);
        lon = Math.min( 180, lon);
        
        lat = Math.max(-80, lat);
        lat = Math.min( 80, lat);
        
        this.lon = lon;
        this.lat = lat;
        
        repaint();
    }
    
    protected void scrollBy(double lon, double lat)
    {
        scroll(lon+this.lon, lat+this.lat);
    }
    
    protected void scrollByScreen(double x, double y)
    {
        x = getWidth()  / 2.0 - x;
        y = getHeight() / 2.0 - y;
        
        Point2D p = new Point2D.Double(x, y);
        p = inverseproject(p);
        scroll(p.getX(), p.getY());
        
    }
    
    protected void scale(double s)
    {
        s = Math.min(s, MAX_SCALE);
        s = Math.max(s, MIN_SCALE);
        scale = s;
        repaint();
        
    }
    
    protected void scaleBy(double s)
    {
        scale(scale*s);
    }
    
    
    // returns a screen coordinate from a world coordinate,
    // by applying the Mercator map projection, scale, and scroll
    protected Point2D project(double lon, double lat)
    {
        // http://en.wikipedia.org/wiki/Mercator_projection
        double x = lon - this.lon;
        double y = Math.log(Math.tan(Math.PI*(0.25 +    lat/360))) -
                   Math.log(Math.tan(Math.PI*(0.25 +this.lat/360)));
        
        y  = Math.toDegrees(y);
        
        x *= scale;
        y *= scale; 
        
        x += getWidth() /2.0;
        y -= getHeight()/2.0;
        
        return new Point2D.Double(x, -y);
    }
    protected Point2D project(Waypoint wp)
    {
        return project(wp.lon, wp.lat);
    }
    protected Point2D project(Point2D p)
    {
        return project(p.getX(), p.getY());
    }
    
    
    // returns a world coordinate from a screen coordinate
    protected Point2D inverseproject(double x, double y)
    {
        double lon = x;
        double lat =-y;
        
        lon -= getWidth() /2.0;
        lat += getHeight()/2.0;
        
        lon /= scale;
        lat /= scale;
        
        lon = lon + this.lon;
        
        lat = Math.toRadians(lat);
        lat+= Math.log(Math.tan(Math.PI*(0.25 + this.lat/360)));
        lat = Math.atan(Math.sinh(lat));
        
        lat = Math.toDegrees(lat);
        
        return new Point2D.Double(lon, lat);
    }
    protected Point2D inverseproject(Point2D p)
    {
        return inverseproject(p.getX(), p.getY());
    }
    
    
}