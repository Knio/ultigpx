package ultigpx;

import java.awt.geom.*;

public class DragOperation extends Operation
{
    Waypoint pt;
    double slon;
    double slat;
    double elon;
    double elat;
    
    
    public DragOperation(Waypoint pt)
    {
        this.pt = pt;
        
    }
    
    public void setStart(Point2D p)
    {
        slon = p.getX();
        slat = p.getY();
    }
    
    
    public void setEnd(Point2D p)
    {
        elon = p.getX();
        elat = p.getY();
    }
    
    
    public void redo()
    {
        pt.lat = elat;
        pt.lon = elon;
    }
    
    public void undo()
    {
        pt.lat = slat;
        pt.lon = slon;
    }
    
    
    
    
    
}