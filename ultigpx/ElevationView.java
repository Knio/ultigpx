
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;

/**
 * Quick and dirty to see if I can get something up and running.
 * @author Nathan
 *
 */
public class ElevationView extends JPanel
{
    UltiGPX         main;
    UGPXFile        file;
    Waypoint        selected;
    
    java.util.List<Waypoint> entities;
    
    
    public ElevationView(UltiGPX main)
    {
        super();
        this.main = main;
    }
    
    protected void select(Waypoint wp)
    {
        selected = wp;
        repaint();
    }
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setPaint(Color.RED);
        
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        
        Rectangle r = getBounds();
        r.grow(-20,-20);
        g2d.draw(r);
    }
}
































