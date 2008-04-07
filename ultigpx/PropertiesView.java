
package ultigpx;

// needed imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JColorChooser;

/**
 * Displays the properties of a selected point. This class also allows you to change
 * the color of the selected point.
 * 
 * @author Steven
 */
public class PropertiesView extends JPanel {
	static final long serialVersionUID = 0;
	// selected waypoint
	Waypoint selwp;
	// selected track
	Track seltrk;
	// selected route
	Route selrt;
	// selected group
	Group selgp;
	// integer that tells which type of data is selected
	// 0 = none, 1 = wp, 2 = trk, 3 = rt, 4 = group, else = crashed program
	int selected;
	// TextArea that holds the info
	TextArea mllabel;

	// set color button
	Button setcolor;
	Button setAtt;
	MainView parent;
	
	JColorChooser colordialog;
	
	PropertiesView me = this;
	
	/**
	 * Displays the properties of a Waypoint.
	 * 
	 * @param wp	a {@link Waypoint}
	 * @see		Waypoint
	 */
	public void select(Waypoint wp) {
		selwp = wp;
		selected = 1;
        if (wp == null)
            selected = 0;
		repaint();
	}
	
	/**
	 * Displays the properties of a Track.
	 * 
	 * @param trk	a {@link Track}
	 * @see		Track
	 */
	public void select(Track trk) {
		seltrk = trk;
		selected = 2;
		repaint();
	}
	
	/**
	 * Displays the properties of a Route
	 * 
	 * @param rt	a {@link Route}
	 * @see		Route
	 */
	public void select(Route rt) {
		selrt = rt;
		selected = 3;
		repaint();
	}
	
	public void select(Group g) {
		//TODO
		if (g.route.size() == 1 && g.track.size() == 0 && g.waypoint.size() == 0)
			select(g.getRoute(0));
		else if (g.route.size() == 0 && g.track.size() == 1 && g.waypoint.size() == 0)
			select(g.getTrack(0));
		else if (g.route.size() == 0 && g.track.size() == 0 && g.waypoint.size() == 1)
			select(g.getWaypoint(0));
		else if (g.route.size() == 0 && g.track.size() == 0 && g.waypoint.size() == 0)
			select();
		else
		{
			selgp = g;
			selected = 4;
			repaint();
		}
	}
	
	/**
	 * Displays nothing. Currently unused since you can not unselect a point
	 * once you have selected it as the program is now.
	 */
	public void select() {
		selected = 0;
		repaint();
	}
	
	/**
	 * The constructor for the PropertiesView class. Initially sets up the panel
	 * to display nothing in the TextArea and disable the Button.
	 * 
	 * @param view	the MainView that PropertiesView is contained within.
	 * @see		MainView
	 */
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
        c.insets = new Insets(0,10,0,9);
    	setcolor = new Button("Choose Color");
    	setAtt = new Button("Edit");
    	c.weighty = 0.5;
    	add(setAtt, c);
    	// add button
    	c.insets = new Insets(0,10,9,9);
    	add(setcolor, c);
    	// set up button actions
    	setcolor.setActionCommand("SetColor");
    	setcolor.addActionListener(new submitactionlistener());
    	setAtt.setActionCommand("SetAtt");
    	setAtt.addActionListener(new submitactionlistener());
    	// set selected element to nothing
		selected = 0;
		this.setPreferredSize(new Dimension(100, 100));
		repaint();
	}
	
	/**
	 * Repaints the panel. Draws the components of the panel on the screen.
	 * 
	 * @param g	A Graphics to render the panel with.
	 */
    public void paintComponent(Graphics g) {
    	// paints the background
        super.paintComponent(g);
        
        // converts the graphic into a 2D graphic
        Graphics2D g2d = (Graphics2D)g;
        
        // draws the outer frame
        g2d.draw(new Rectangle2D.Double(5, 5, getWidth()-10, getHeight()-10));
        
        // writes the info on the screen
        paintinfo(g2d);
    }
    
    /**
     * Changes the information to be displayed on the panel depending on the
     * type of the selected point.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
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
            case (4):
            	paintgrp(g2d);
            	break;
        }//*/
    }
    
    /**
     * Disables the color change Button and displays nothing in the TextArea.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void paintnull(Graphics2D g2d) {
    	// disables button
    	setcolor.setEnabled(false);
    	setAtt.setEnabled(false);
    	// sets the text to ""
    	mllabel.setText("");
    }
    
    /**
     * Disables the color change Button and displays nothing in the TextArea.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void paintgrp(Graphics2D g2d) {
    	// disables button
    	setcolor.setEnabled(false);
    	setAtt.setEnabled(false);
    	// sets the text to ""
    	mllabel.setText("");
    }
    
    /**
     * A testing method that displays a Waypoint, a Track, and a Route. These three
     * are displayed in a predefined order such that on every repaint what is displayed
     * will change.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void painttest(Graphics2D g2d) {
    	// prints a waypoint and then on repaint prints
    	// a track and then on repaint prints a route
    	// and then repeats cycle.
    	if (selected == 1)
    	{
    	selwp = new Waypoint("Name", "This is a super long description used to test how well my line splitter works. I really have nothing to say in the description so I'm just typing a lot of words because it will help me test.", 10.0, 5.21483726152, 72.7162535412365213, 11.2);
    	paintwp(g2d);
    	selected = 2;
    	}
    	else if (selected == 2)
    	{
    	seltrk = new Track("Name");
    	painttrk(g2d);
    	selected = 3;
    	}
    	else
    	{
    	selrt = new Route();
    	paintrt(g2d);
    	selected = 1;
    	}
    	// disclaimer cause people might think my part works without
    	// all of the other parts done. It does not!
    	mllabel.append("\n\nThis data is for testing only!");
    }
    
    /**
     * Displays the information contained within a Waypoint in an easy to
     * understand manner.
     * 
     * @param g2d	the Graphics2D element to draw onto
     * @see		Waypoint
     */
    protected void paintwp(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
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
    
    /**
     * Displays the information contained within a Track in an easy to
     * understand manner.
     * 
     * @param g2d	the Graphics2D element to draw onto
     * @see		Track
     */
    protected void painttrk(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Track: " + seltrk.getName() + "\n\n");
    	// displays status
    	if (seltrk.enabled)
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
    	// displays number of track segments in the Track
    	mllabel.append("Number of Segments: " + seltrk.size() + "\n");
		mllabel.append("Total Distance: " + formatDistance(seltrk.getDistance()) + "\n");
    	// displays color
    	if (seltrk.color != null)
    		mllabel.append("Color:\n  Red: " + seltrk.getColor().getRed() + "\n  Green: " + seltrk.getColor().getGreen() + "\n  Blue: " + seltrk.getColor().getBlue() + "\n\n");
    	else
    		mllabel.append("Color:\n  Default");
    }
    
    /**
     * Displays the information contained within a Route in an easy to
     * understand manner.
     * 
     * @param g2d	the Graphics2D element to draw onto
     * @see		Route
     */
    protected void paintrt(Graphics2D g2d){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
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
    	mllabel.append("Number of Waypoints: " + selrt.size() + "\n");
		mllabel.append("Total Distance: " + formatDistance(selrt.getDistance()) + "\n");
    	// displays color
    	if (selrt.color != null)
    		mllabel.append("Color:\n  Red: " + selrt.getColor().getRed() + "\n  Green: " + selrt.getColor().getGreen() + "\n  Blue: " + selrt.getColor().getBlue() + "\n\n");
    	else
    		mllabel.append("Color:\n  Default");
    }
	
	/**
     * Formats a distance for display to the user.
     * 
     * @param The distance to format
     * @return The string value of the distance rounded to 1 decimal place, with km or m appended
     */
	protected String formatDistance(double distance){
		// Kilometers
		if (distance >= 1000) {
			distance = Math.rint(distance / 100) / 10;		// Divide by 1000, round to 1 decimal place
			return "" + distance + " km";
		}
		// Meters
		else {
			distance = Math.rint(distance * 10) / 10;		// Round to 1 decimal place
			return "" + distance + " m";
		}
	}
    
    /**
     * A simple ActionListener that detects if the button is pressed and shows
     * a color selection dialog to the user.
     * 
     * @author Steven
     * @see	ActionListener
     */
    class submitactionlistener implements ActionListener {
    	/**
    	 * Creates the ActionListener
    	 */
    	public submitactionlistener() {
    		super();
    	}
    	
    	/**
    	 * Changes the color of a point based on what type of point is selected.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		if (e.getActionCommand().equals("SetColor"))
    		{
    			if (selected == 1)
    			{
    				Color clr = JColorChooser.showDialog(parent, "Color Chooser", selwp.getColor());
    				selwp.setColor(clr);
    			}
    			else if (selected == 2)
    			{
    				Color clr = JColorChooser.showDialog(parent, "Color Chooser", seltrk.getColor());
    				seltrk.setColor(clr);
    			}
    			else if (selected == 3)
    			{
    				Color clr = JColorChooser.showDialog(parent, "Color Chooser", selrt.getColor());
    				selrt.setColor(clr);
    			}
    		}
    		else if (e.getActionCommand().equals("SetAtt"))
    		{
    			dispattdialog();
    		}
    	}
    }
    
    public void dispattdialog(JDialog x,GridBagConstraints c,Waypoint w) {
    	// TODO
    	x.add(new Label("Name:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(new TextField(w.getName(), 20),c);
		
		JCheckBox v = new JCheckBox("Enabled:                    ",w.getEnabled());
		v.setHorizontalTextPosition(SwingConstants.LEADING);
		x.add(v,c);
        c.gridwidth = 1;
        
        x.add(new Label("Description:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(new TextArea(w.getDesc(),10,10,TextArea.SCROLLBARS_VERTICAL_ONLY),c);
		c.gridwidth = 1;
        
        x.add(new Label("Elevation:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(new TextField(""+w.getEle(), 20),c);
		c.gridwidth = 1;
        
        x.add(new Label("Time:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(new TextField(""+new Date((long)w.getTime()), 20),c);
		
    }
    
    public void dispattdialog(JDialog x,GridBagConstraints c,Track t) {
    	// TODO
    }
    
    public void dispattdialog(JDialog x,GridBagConstraints c,Route r) {
    	// TODO
    }
    
    public void dispattdialog(JDialog x,GridBagConstraints c,Group g) {
    	// TODO
    	// do we even need this?
    }
    
    /**
     * Displays the set attributes dialog for a 
     */
    public void dispattdialog() {
    	JDialog x = new JDialog(parent, "", true);
		x.setAlwaysOnTop(true);
		x.setResizable(false);
		x.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.BOTH;
    	c.weightx = 1.0;
        c.weighty = 1.0;
		x.setBounds(parent.getX()+parent.getWidth()/2-150, parent.getY()+parent.getHeight()/2-150, 300, 300);

        if (selected == 1)
        	dispattdialog(x,c,selwp);
        else if (selected == 2)
        	dispattdialog(x,c,seltrk);
        else if (selected == 3)
        	dispattdialog(x,c,selrt);
        //*/
        x.setVisible(true);
    }
    
}
