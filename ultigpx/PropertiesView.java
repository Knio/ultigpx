
package ultigpx;

// needed imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JColorChooser;

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
	// TextArea that holds the info
	TextArea mllabel;

	// set color button
	Button setcolor;
	MainView parent;
	
	JColorChooser colordialog;
	
	PropertiesView me = this;
	
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
	public PropertiesView(MainView view) {
		super();
		parent = view;
		setLayout(new GridBagLayout());
		// creates a text box
    	mllabel = new TextArea("",5,5,TextArea.SCROLLBARS_VERTICAL_ONLY);
    	mllabel.setEditable(false);
    	// sets up the gridbag for the text box
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 20.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,10,0,9);
        // adds text box to panel
    	add(mllabel,c);
    	// creates button
    	setcolor = new Button("Choose Color");
    	c.weighty = 0.5;
    	// add button
    	c.insets = new Insets(0,10,9,9);
    	add(setcolor, c);
    	// set up button actions
    	setcolor.setActionCommand("SetColor");
    	setcolor.addActionListener(new submitactionlistener());
    	// set selected element to nothing
		selected = 0;
		this.setPreferredSize(new Dimension(100, 100));
		repaint();
	}
	
	// draws the panel/frame/pane on the screen
    public void paintComponent(Graphics g) {
    	// paints the background
        super.paintComponent(g);
        
        // converts the graphic into a 2D graphic
        Graphics2D g2d = (Graphics2D)g;
        
        // draws the outer frame
        g2d.draw(new Rectangle2D.Double(5, 5, getWidth()-10, getHeight()-10));
        // draws the inner frame
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        
        // writes the info on the screen
        paintinfo(g2d);
    }
    
    protected void paintinfo(Graphics2D g2d) {
    	// prints a changing test string to the frame
    	/*painttest(g2d);
    	return;//*/
    	
    	// the code below prints different info
    	// based on what type of data is selected
    	switch (selected)
        {
            case (0):
                paintnull(g2d);
            	break;
            case (1):
            	paintwp(g2d);
            	break;
            case (2):
            	painttrk(g2d);
            	break;
            case (3):
            	paintrt(g2d);
            	break;
        }//*/
    }
    
    protected void paintnull(Graphics2D g2d) {
    	// disables button
    	setcolor.setEnabled(false);
    }
    
    // alternates writing a test string of each type in the text box
    protected void painttest(Graphics2D g2d) {
    	// prints a waypoint and then on repaint prints
    	// a track and then on repaint prints a route
    	// and then repeats cycle.
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
    	// disclaimer cause people might think my part works without
    	// all of the other parts done. It does not!
    	mllabel.append("\n\nThis data is for testing only!");
    }
    
    // writes a waypoint on the frame
    protected void paintwp(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Waypoint: " + selwp.getName() + "\n\n");
    	// displays status
    	if (selwp.getEnabled())
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
    	// displays time
    	Date x = new Date((long)selwp.getTime());
    	mllabel.append("Time: " + x.toString() + "\n\n");
    	// displays description
    	mllabel.append("Description: " + selwp.getDesc() + "\n\n");
    	// displays latitude
    	mllabel.append("Latitude: " + selwp.getLat() + "\n\n");
    	// displays longitude
    	mllabel.append("Longitude: " + selwp.getLon() + "\n\n");
    	// displays elevation
    	mllabel.append("Elevation: " + selwp.getEle() + "\n\n");
    	// displays color
    	if (selwp.getColor() != null)
    		mllabel.append("Color:\n  Red: " + selwp.getColor().getRed() + "\n  Green: " + selwp.getColor().getGreen() + "\n  Blue: " + selwp.getColor().getBlue() + "\n\n");
    	else
    		mllabel.append("Color:\n  Default\n\n");
    }
    // writes a track on the screen
    protected void painttrk(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Track: " + seltrk.getName() + "\n\n");
    	// displays status
    	if (seltrk.enabled)
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
    	// displays description
    	mllabel.append("Number of Segments: " + seltrk.size() + "\n\n");
    	// displays color
    	if (seltrk.color != null)
    		mllabel.append("Color:\n  Red: " + seltrk.getColor().getRed() + "\n  Green: " + seltrk.getColor().getGreen() + "\n  Blue: " + seltrk.getColor().getBlue() + "\n\n");
    	else
    		mllabel.append("Color:\n  Default\n\n");
    }
    // writes a route on the screen
    protected void paintrt(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Route: " + selrt.getName() + "\n\n");
    	// displays status
    	if (selrt.enabled)
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
    	// displays description
    	mllabel.append("Description: " + selrt.getDesc() + "\n\n");
    	// displays number of waypoints in the route
    	mllabel.append("Number of Waypoints: " + selrt.size() + "\n\n");
    	// displays color
    	if (selrt.color != null)
    		mllabel.append("Color:\n  Red: " + selrt.getColor().getRed() + "\n  Green: " + selrt.getColor().getGreen() + "\n  Blue: " + selrt.getColor().getBlue() + "\n\n");
    	else
    		mllabel.append("Color:\n  Default\n\n");
    }
    
    // action listener for the set color button
    class submitactionlistener implements ActionListener {
    	public submitactionlistener() {
    		super();
    	}
    	public void actionPerformed(ActionEvent e) {
    		if (selected == 1)
    		{
    			colordialog = new JColorChooser();
    			Color clr = colordialog.showDialog(me, "Color Chooser", selwp.getColor());
    			selwp.setColor(clr);
    		}
    		if (selected == 2)
    		{
    			colordialog = new JColorChooser();
    			Color clr = colordialog.showDialog(me, "Color Chooser", seltrk.getColor());
    			seltrk.setColor(clr);
    		}
    		if (selected == 3)
    		{
    			colordialog = new JColorChooser();
    			Color clr = colordialog.showDialog(me, "Color Chooser", selrt.getColor());
    			selrt.setColor(clr);
    		}
    	}
    }
    
}
