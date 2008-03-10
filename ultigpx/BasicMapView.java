
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 

public class BasicMapView extends MapView
{
    public BasicMapView(UltiGPX _main)
    {
        super(_main);
        
    }
    
    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        
        System.out.println("Painting");
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.BLACK);
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        Rectangle r = getBounds();
        r.grow(-20,-20);
        g2d.draw(r);
    }
    
}