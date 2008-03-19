
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
    
    public double lon;
    public double lat;
    public double scale;
    
    public static Color            WAYPOINT_COLOR  = Color.BLACK;
    public static Color            TRACK_COLOR     = Color.BLUE;
    public static Color            ROUTE_COLOR     = Color.GREEN;
    public static Color            SELECTED_COLOR  = Color.RED;
    
    public static final int        WAYPOINT_SIZE   = 5;
    public static final int        FONT_SIZE       = 9;
    
    public static final double     MAX_SCALE       = 100000.0;
    public static final double     MIN_SCALE       = 20.0;
    
    public static final double     ZOOM_IN         = 1.1;
    public static final double     ZOOM_OUT        = 0.9;
    
    
    public MapView(UltiGPX main)
    {
        super();
        this.main = main;
    }
    
    abstract public void fill();
}