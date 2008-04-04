package ultigpx;

public class Selection extends Group
{
    
    
    public void clear()
    {
        track.clear();
        route.clear();
        waypoint.clear();
        
    }
    
    public void select(UGPXData d)
    {
        clear();
        add(d);
    }
    
    
    
    
    
}

