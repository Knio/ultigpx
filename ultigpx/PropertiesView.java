
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
    	// prints a changing test string to the frame
    	//painttest(g2d);
    	//return;
    	
    	// the code below prints different info
    	// based on what type of data is selected
    	/*switch (selected)
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
    	if (selected == 0)
    	{
    	selwp = new Waypoint("Name", "This is a super long description used to test how well my line splitter works. I really have nothing to say in the description so I'm just typing a lot of words because it will help me test.", 10.0, 5.21483726152, 72.7162535412365213, 11.2);
    	paintwp(g2d);
    	selected = 1;
    	}
    	else if (selected == 1)
    	{
    	seltrk = new Track("Name");
    	painttrk(g2d);
    	selected = 2;
    	}
    	else
    	{
    	selrt = new Route();
    	paintrt(g2d);
    	selected = 0;
    	}
    }
    
    // writes a waypoint on the frame
    protected void paintwp(Graphics2D g2d){
    	int workingwidth = this.getWidth() - 50;
    	int workingheight = 30;
    	if (selwp.enabled == true)
    		g2d.drawString("Waypoint: enabled", 20, workingheight);
    	else
    		g2d.drawString("Waypoint: disabled", 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Name: " + selwp.getName(), 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Description:", 20, workingheight);
    	workingheight += 11;
    	for (int i = 0; i < selwp.getDesc().length(); i += (workingwidth / 5))
    	{
    		int endofstring;
    		if (i + (workingwidth / 5) >= selwp.getDesc().length())
    			endofstring = selwp.getDesc().length();
    		else
    			endofstring = i + (workingwidth / 5);
    		g2d.drawString("  " + selwp.getDesc().substring(i, endofstring), 20, workingheight);
    		workingheight += 11;
    	}
    	g2d.drawString("Latitude: " + selwp.getLat(), 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Longitude: " + selwp.getLon(), 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Elevation: " + selwp.getEle(), 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Colour: " + selwp.getColour(), 20, workingheight);
    }
    // writes a track on the screen
    protected void painttrk(Graphics2D g2d){
    	int workingheight = 30;
    	if (seltrk.enabled == true)
    		g2d.drawString("Track: enabled", 20, workingheight);
    	else
    		g2d.drawString("Track: disabled", 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Name: " + seltrk.getName(), 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Number of Segments: " + seltrk.size(), 20, workingheight);
    }
    // writes a route on the screen
    protected void paintrt(Graphics2D g2d){
    	int workingheight = 30;
    	if (selrt.enabled == true)
    		g2d.drawString("Route: enabled", 20, workingheight);
    	else
    		g2d.drawString("Route: disabled", 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Name: " + "", 20, workingheight);
    	workingheight += 11;
    	g2d.drawString("Number of Segments: " + selrt.size(), 20, workingheight);
    }
    
}
