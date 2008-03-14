
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 

import javax.swing.*;
import java.util.*;

public class PropertiesView extends JPanel {
	Waypoint selwp;
	Track seltrk;
	Route selrt;
	int selected;
	
	public void select(Waypoint wp) {
		selwp = wp;
		selected = 1;
		repaint();
	}
	public void select(Track trk) {
		seltrk = trk;
		selected = 2;
		repaint();
	}
	public void select(Route rt) {
		selrt = rt;
		selected = 3;
		repaint();
	}
	public void select() {
		selected = 0;
		repaint();
	}
	
	public PropertiesView() {
		super();
		selected = 0;
		repaint();
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        paintinfo(g2d);
        
        g2d.setPaint(Color.BLACK);
        
        g2d.draw(new Rectangle2D.Double(5, 5, getWidth()-10, getHeight()-10));
        
        Rectangle r = getBounds();
        r.grow(-10,-10);
        g2d.draw(r);
    }
    
    protected void paintinfo(Graphics2D g2d) {
    	painttest(g2d);
    	return;
    	switch (selected)
        {
            case (0):
                return;
            case (1):
            	paintwp(g2d);
            case (2):
            	painttrk(g2d);
            case (3):
            	paintrt(g2d);
        }
    }
    
    protected void painttest(Graphics2D g2d) {
    	Graphics2D.drawString("Test", 20, 20);
    }
    
    protected void paintwp(Graphics2D g2d);
    protected void painttrk(Graphics2D g2d);
    protected void paintrt(Graphics2D g2d);
    
}
