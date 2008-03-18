
package ultigpx;

import javax.swing.*;

class GridMapView extends BasicMapView
{
    UltiGPX main;
    
    public GridMapView(UltiGPX main)
    {
        super(main);
        
    }
    
    
    protected void render()
    {
        renderGrid();
        super.render();
    }
    
    protected void renderGrid()
    {
        
        return;
        
    }
    
    
}