
package ultigpx;

import javax.swing.*;
import java.awt.geom.*; 
import java.awt.*; 

public class GridMapView extends BasicMapView
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
        
        Point2D topleft     = inverseproject(left,  top);
        Point2D botright    = inverseproject(right, bot);
        
        double east = botright.getX();
        double west = topleft .getX();
        double north= topleft .getY();
        double south= botright.getY();
        
        double g1 = east - west;
        double g2 = g1;
        
        double w1 = g1;
        double w2 = g2;
        
        g1 /= 10;
        g2 /= 100;
        
        g1 = Math.pow(10, (int)Math.log10(g1));
        g2 = Math.pow(10, (int)Math.log10(g2));
        
        double x,y;
        
        // minor gridlines
        //*
        float c = (float)(1-10*g2/w2);
        g.setPaint(new Color(c,c,c));
        
        for (x=((int)(west/g2))*g2; x<east+g2; x+=g2)
        {
            int sx = (int)project(x, 0).getX();
            
            g.drawLine(sx, top, sx, bot); 
        }
        
        
        for (y=((int)(south/g2))*g2; y<north; y+=g2)
        {
            int sy = (int)project(0, y).getY();
            
            g.drawLine(left, sy, right, sy); 
        }
        
        g.setFont(new Font("Arial", 0, FONT_SIZE));
        
        // major gridlines
        g.setPaint(Color.BLACK);
        
        for (x=((int)(west/g1))*g1; x<east+g1; x+=g1)
        {
            int sx = (int)project(x, 0).getX();
            g.drawLine(sx, top, sx, bot); 
            g.drawString(String.format("%.3f%c",Math.abs(x), x>0?'E':'W'), sx, bot);
        }
        
        
        for (y=((int)(south/g1))*g1; y<north; y+=g1)
        {
            int sy = (int)project(0, y).getY();
            g.drawLine(left, sy, right, sy); 
            g.drawString(String.format("%.3f%c",Math.abs(y), y>0?'N':'S'), left, sy);
        }
        
        
        
        return;
        
    }
    
    
}