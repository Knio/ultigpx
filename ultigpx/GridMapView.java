
package ultigpx;

import javax.swing.*;
import java.awt.geom.*; 

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
        
        
        int top  = 0;
        int left = 0;
        int bot  = getHeight();
        int right= getWidth();
        
        Point2D topleft     = inverseproject(top, left);
        Point2D botright    = inverseproject(bot, right);
        
        double west = topleft .getX();
        double east = botright.getX();
        double north= topleft .getY();
        double south= botright.getY();
        
        double g1 = east - west;
        
        g1 /= 10;
        g1 = Math.pow(10, (int)Math.log10(g1));
        
        
        
        double x,y;
        
        for (x=((int)(west/g1))*g1; x<east; x+=g1)
        {
            int sx = (int)project(x, 0).getX();
            
            g.drawLine(sx, top, sx, bot); 
        }
        
        
        for (y=((int)(south/g1))*g1; y<north; y+=g1)
        {
            int sy = (int)project(0, y).getY();
            
            g.drawLine(left, sy, right, sy); 
        }
        //*/
        
        
        
        return;
        
    }
    
    
}