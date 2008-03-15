
package ultigpx;

// needed imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class PropertiesView extends JPanel {
	static final long serialVersionUID = 0;
	// selected waypoint
	Waypoint selwp;
	// selected track
	Track seltrk;
	// selected route
	Route selrt;
	// integer that tells which type of data is selected
	// 0 = none, 1 = wp, 2 = trk, 3 = rt, else = crashed program
	int selected;
	
	// displays a waypoint
	public void select(Waypoint wp) {
		selwp = wp;
		selected = 1;
		repaint();
	}
	// displays a track
	public void select(Track trk) {
		seltrk = trk;
		selected = 2;
		repaint();
	}
	// displays a route 
	public void select(Route rt) {
		selrt = rt;
		selected = 3;
		repaint();
	}
	// displays nothing
	public void select() {
		selected = 0;
		repaint();
	}
	
	// constructor that will initially display nothing
	public PropertiesView() {
		super();
		selected = 0;
		repaint();
	}
	
	// draws the panel/frame/pane on the screen
    public void paintComponent(Graphics g) {
    	// paints the background
        super.paintComponent(g);
        
        // converts the graphic into a 2D graphic
        Graphics2D g2d = (Graphics2D)g;
        
        // writes the info on the screen
        paintinfo(g2d);
        
        // draws the outer frame
        g2d.draw(new Rectangle2D.Double(5, 5, getWidth()-10, getHeight()-10));
        // draws the inner frame
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));

        
    }
    
    protected void paintinfo(Graphics2D g2d) {
    	// prints a test string to the frame
    	painttest(g2d);
    	return;
    	// the code below will later print different info
    	// based on what type of data is selected
    	/*
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
        }*/
    }
    
    // writes a test string on the frame
    protected void painttest(Graphics2D g2d) {
    	g2d.drawString("Test", 20, 30);
    }
    
    // writes a waypoint on the frame
    protected void paintwp(Graphics2D g2d){
    	
    }
    // writes a track on the screen
    protected void painttrk(Graphics2D g2d){
    	
    }
    // writes a route on the screen
    protected void paintrt(Graphics2D g2d){
    	
    }
    
}
