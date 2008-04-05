package ultigpx;

public class Selection extends Group
{
    UltiGPX main;
    
    public Selection(UltiGPX main)
    {
        super();
        this.main = main;
    }
    
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
        selectionChanged();
    }
    
    
    // returns the first selected object. 
    public UGPXData get()
    {
        if (waypoint.size()!=0) return getWaypoint(0);
        if (track.size()!=0)    return getTrack(0);
        if (route.size()!=0)    return getRoute(0);
        return null;
    }
    
    public void selectionChanged()
    {
        main.view.selectionChanged();
    }
    
    
    
}

