package ultigpx;

import java.awt.geom.*;
/**
 * Allows a drag to be done / undone.
 * @author Tom
 */
public class DragOperation extends Operation
{
    Waypoint pt;
    double slon;
    double slat;
    double elon;
    double elat;
    double sele;
    double eele;
    
    
    public DragOperation(Waypoint pt)
    {
        this.pt = pt;
    }
    
    public void setStart(Waypoint p)
    {
        slon = p.getLon();
        slat = p.getLat();
        sele = p.getEle();
    }
    
    
    public void setEnd(Double lon, Double lat, Double ele)
    {
    	if (lon != null)
    		elon = lon;
    	else
    		elon = slon;
    	if (lat != null)
    		elat = lat;
    	else
    		elat = slat;
    	if (ele != null)
    		eele = ele;
    	else
    		eele = sele;
    }
    
    
    public void redo()
    {
        pt.lat = elat;
        pt.lon = elon;
        pt.ele = eele;
    }
    
    public void undo()
    {
        pt.lat = slat;
        pt.lon = slon;
        pt.ele = sele;
    }
    
    
    
    
    
}