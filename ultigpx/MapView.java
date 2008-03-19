
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
    public static final double     MIN_SCALE       = 0.2;
    
    public static final double     ZOOM_IN         = 1.11;
    public static final double     ZOOM_OUT        = 0.99;


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
}