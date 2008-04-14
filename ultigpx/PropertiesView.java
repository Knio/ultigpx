
package ultigpx;

// needed imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JColorChooser;
import com.lavantech.gui.comp.DateTimePicker;

/**
 * Displays the properties of a selected waypoint/track/route or group. This class also allows you to change
 * the color and attributes of the selected object.
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
	Group selgrp;
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
	
	EditOperation editop;
	
	PropertiesView me = this;
	
	// needed for the attribute changer
	TextField name;
	JCheckBox enabled;
	TextArea desc;
	DateTimePicker time;
	TextField ele;
	TextField lon;
	TextField lat;
	Label stat;
	Button submit;
	JDialog x;
	
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
		paintThis();
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
		paintThis();
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
		paintThis();
	}
	
	/**
	 * Displays the properties of a Group
	 * 
	 * @param g	a {@link Group}
	 * @see		Group
	 */
	public void select(Group g) {
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
			selgrp = g;
			selected = 4;
			paintThis();
		}
	}
	
	/**
	 * Displays nothing.
	 */
	public void select() {
		selected = 0;
		paintThis();
	}
	
	/**
	 * The constructor for the PropertiesView class. Initially sets up the panel
	 * to display nothing in the TextArea and disable the Buttons.
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
		paintThis();
	}
	
	/**
	 * Repaints the panel. Draws the components of the panel on the screen.
	 */
    public void paintThis() {
        // writes the info on the screen
        paintinfo();
    }
    
    /**
     * Changes the information to be displayed on the panel depending on the
     * type of the selected point.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void paintinfo() {
    	// prints a changing test string to the frame
    	/*painttest(g2d);
    	return;//*/
    	
    	// the code below prints different info
    	// based on what type of data is selected
    	switch (selected)
        {
            case (0):
                paintnull();
            	break;
            case (1):
            	paintwp();
            	break;
            case (2):
            	painttrk();
            	break;
            case (3):
            	paintrt();
            	break;
            case (4):
            	paintgrp();
            	break;
        }//*/
    }
    
    /**
     * Disables both buttons and displays nothing in the TextArea.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void paintnull() {
    	// disables button
    	setcolor.setEnabled(false);
    	setAtt.setEnabled(false);
    	// sets the text to ""
    	mllabel.setText("");
    }
    
    /**
     * Displays some information about the group.
     * 
     * @param g2d	the Graphics2D element to draw onto
     * @see		Group
     */
    protected void paintgrp() {
    	// disables button
    	setcolor.setEnabled(false);
    	setAtt.setEnabled(true);
    	// sets the text to ""
    	mllabel.setText("Name: " + selgrp.name + "\n");
    	if (selgrp.getEnabled())
    		mllabel.append("Status: Enabled\n");
    	else
    		mllabel.append("Status: Disabled\n");
    	mllabel.append("Number of Waypoints: " + selgrp.waypoint.size() + "\n");
    	mllabel.append("Number of Tracks: " + selgrp.track.size() + "\n");
    	mllabel.append("Number of Routes: " + selgrp.route.size() + "\n");
    }
    
    /**
     * A testing method that displays a Waypoint, a Track, and a Route. These three
     * are displayed in a predefined order such that on every repaint what is displayed
     * will change.
     * 
     * @param g2d	the Graphics2D element to draw onto
     */
    protected void painttest() {
    	// prints a waypoint and then on repaint prints
    	// a track and then on repaint prints a route
    	// and then repeats cycle.
    	if (selected == 1)
    	{
    	selwp = new Waypoint("Name", "This is a super long description used to test how well my line splitter works. I really have nothing to say in the description so I'm just typing a lot of words because it will help me test.", 10.0, 5.21483726152, 72.7162535412365213, 11);
    	paintwp();
    	selected = 2;
    	}
    	else if (selected == 2)
    	{
    	seltrk = new Track("Name");
    	painttrk();
    	selected = 3;
    	}
    	else
    	{
    	selrt = new Route();
    	paintrt();
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
    protected void paintwp(){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Waypoint: " + selwp.getName() + "\n");
    	// displays status
    	if (selwp.getEnabled())
    		mllabel.append("Status: Enabled\n");
    	else
    		mllabel.append("Status: Disabled\n");
    	// displays time
    	Date x = new Date((long)selwp.getTime());
    	mllabel.append("Time: " + x.toString() + "\n");
    	// displays description
    	mllabel.append("Description: " + selwp.getDesc() + "\n");
    	// displays latitude
    	mllabel.append("Latitude: " + selwp.getLat() + "\n");
    	// displays longitude
    	mllabel.append("Longitude: " + selwp.getLon() + "\n");
    	// displays elevation
    	mllabel.append("Elevation: " + selwp.getEle() + "\n");
    	// displays color
    	if (selwp.getColor() != null)
    		mllabel.append("Color:\n  Red: " + selwp.getColor().getRed() + "\n  Green: " + selwp.getColor().getGreen() + "\n  Blue: " + selwp.getColor().getBlue());
    	else
    		mllabel.append("Color:\n  Default");
    }
    
    /**
     * Displays the information contained within a Track in an easy to
     * understand manner.
     * 
     * @param g2d	the Graphics2D element to draw onto
     * @see		Track
     */
    protected void painttrk(){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Track: " + seltrk.getName() + "\n");
    	// displays status
    	if (seltrk.enabled)
    		mllabel.append("Status: Enabled\n");
    	else
    		mllabel.append("Status: Disabled\n");
    	// displays number of track segments in the Track
    	mllabel.append("Number of Segments: " + seltrk.size() + "\n");
		mllabel.append("Total Distance: " + formatDistance(seltrk.getDistance()) + "\n");
    	// displays color
    	if (seltrk.color != null)
    		mllabel.append("Color:\n  Red: " + seltrk.getColor().getRed() + "\n  Green: " + seltrk.getColor().getGreen() + "\n  Blue: " + seltrk.getColor().getBlue());
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
    protected void paintrt(){
    	// enables button
    	setcolor.setEnabled(true);
    	setAtt.setEnabled(true);
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Route: " + selrt.getName() + "\n");
    	// displays status
    	if (selrt.enabled)
    		mllabel.append("Status: Enabled\n");
    	else
    		mllabel.append("Status: Disabled\n");
    	// displays description
    	mllabel.append("Description: " + selrt.getDesc() + "\n");
    	// displays number of waypoints in the route
    	mllabel.append("Number of Waypoints: " + selrt.size() + "\n");
		mllabel.append("Total Distance: " + formatDistance(selrt.getDistance()) + "\n");
    	// displays color
    	if (selrt.color != null)
    		mllabel.append("Color:\n  Red: " + selrt.getColor().getRed() + "\n  Green: " + selrt.getColor().getGreen() + "\n  Blue: " + selrt.getColor().getBlue());
    	else
    		mllabel.append("Color:\n  Default");
    }
	
	/**
     * Formats a distance for display to the user.
     * 
     * @param distance The distance to format
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
     * A simple ActionListener that detects if a button is pressed and shows
     * the correct dialog to the user.
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
    	 * Displays a dialog when a button is pressed. If set color is pressed it displays
    	 * a color dialog and if the edit button is pressed it displays a custom change
    	 * attributes dialog.
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
    			parent.refreshmap();
    		}
    		else if (e.getActionCommand().equals("SetAtt"))
    		{
    			dispattdialog();
    		}
    	}
    }
    
    /**
     * Displays the information that can be edited for a waypoint.
     * 
     * @param x	a JDialog to write on
     * @param c the GridGagConstraints
     * @param w the waypoint
     */
    public void dispattdialog(JDialog x,GridBagConstraints c,Waypoint w) {
    	name = new TextField(w.getName(), 20);
    	x.add(new Label("Name:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(name,c);
		
		enabled = new JCheckBox("Enabled:                   ",w.getEnabled());
		enabled.setHorizontalTextPosition(SwingConstants.LEADING);
		x.add(enabled,c);
        c.gridwidth = 1;
        
        desc = new TextArea(w.getDesc(),5,5,TextArea.SCROLLBARS_VERTICAL_ONLY);
        x.add(new Label("Description:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(desc,c);
		c.gridwidth = 1;
        
		ele = new TextField(""+w.getEle(), 20);
        x.add(new Label("Elevation:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(ele,c);
		c.gridwidth = 1;
        
		lat = new TextField(""+w.getLat(), 20);
        x.add(new Label("Latitude:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(lat,c);
		c.gridwidth = 1;
        
		lon = new TextField(""+w.getLon(), 20);
        x.add(new Label("Longitude:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(lon,c);
        
		c.gridwidth = GridBagConstraints.REMAINDER;
		time = new DateTimePicker();
		x.add(time,c);
		time.setDate(new Date((long)selwp.getTime()));
		
		/**
		 * Apply button applies the changes to a waypoint
		 * and adds the action to the undo list.
		 * It also displays an error if one of the inputs are
		 * not correctly formatted or out of the required range.
		 */
		submit = new Button("Apply");
		x.add(submit,c);
		submit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
		    	editop = new EditOperation(selwp);
				selwp.setName(name.getText());
				selwp.setDesc(desc.getText());
				stat.setText("Attributes have been set");
				try{
				selwp.setEle(Double.valueOf(ele.getText()));
				} catch (Exception ex) {stat.setText("Error: Could not parse Elevation");System.out.println(ex);};
				selwp.setEnabled(enabled.isSelected());
				selwp.setTime(time.getDate().getTime());
				try {
				if (Double.valueOf(lat.getText()) >= -90 && Double.valueOf(lat.getText()) <= 90)
					selwp.setLat(Double.valueOf(lat.getText()));
				else
					stat.setText("Error: Latitude is not valid");
				} catch (Exception ex) {stat.setText("Error: Could not parse Latitude");System.out.println(ex);};
				
				try {
				if (Double.valueOf(lon.getText()) >= -180 && Double.valueOf(lon.getText()) <= 180)
					selwp.setLon(Double.valueOf(lon.getText()));
				else
					stat.setText("Error: Longitude is not valid");
				} catch (Exception ex) {stat.setText("Error: Could not parse Longitude");System.out.println(ex);};
				
				parent.refreshmap();
				editop.setnew(selwp);
				parent.main.addOperation(editop);
			}
		});
		
		stat = new Label();
		x.add(stat,c);
    }
    
    /**
     * Displays the information that can be edited for a track.
     * 
     * @param x	a JDialog to write on
     * @param c the GridGagConstraints
     * @param t the track
     */
    public void dispattdialog(JDialog x,GridBagConstraints c,Track t) {
    	name = new TextField(t.getName(), 20);
    	x.add(new Label("Name:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(name,c);
		
		enabled = new JCheckBox("Enabled:                   ",t.getEnabled());
		enabled.setHorizontalTextPosition(SwingConstants.LEADING);
		x.add(enabled,c);
        c.gridwidth = 1;
        
        desc = new TextArea(t.getDesc(),10,10,TextArea.SCROLLBARS_VERTICAL_ONLY);
        x.add(new Label("Description:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(desc,c);
		
		/**
		 * Apply button applies the changes to a track
		 * and sets adds the action to the undo list.
		 */
		submit = new Button("Apply");
		x.add(submit,c);
		submit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
		    	editop = new EditOperation(seltrk);
				seltrk.setName(name.getText());
				seltrk.setDesc(desc.getText());
				seltrk.setEnabled(enabled.isSelected());
				parent.refreshmap();
				editop.setnew(seltrk);
				parent.main.addOperation(editop);
			}
		});
    }
    
    /**
     * Displays the information that can be edited for a route.
     * 
     * @param x	a JDialog to write on
     * @param c the GridGagConstraints
     * @param r the route
     */
    public void dispattdialog(JDialog x,GridBagConstraints c,Route r) {
    	name = new TextField(r.getName(), 20);
    	x.add(new Label("Name:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(name,c);
		
		enabled = new JCheckBox("Enabled:                   ",r.getEnabled());
		enabled.setHorizontalTextPosition(SwingConstants.LEADING);
		x.add(enabled,c);
        c.gridwidth = 1;
        
        desc = new TextArea(r.getDesc(),10,10,TextArea.SCROLLBARS_VERTICAL_ONLY);
        x.add(new Label("Description:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(desc,c);
		
		/**
		 * Apply button applies the changes to a route
		 * and sets adds the action to the undo list.
		 */
		submit = new Button("Apply");
		x.add(submit,c);
		submit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
		    	editop = new EditOperation(selrt);
				selrt.setName(name.getText());
				selrt.setDesc(desc.getText());
				selrt.setEnabled(enabled.isSelected());
				parent.refreshmap();
				editop.setnew(selrt);
				parent.main.addOperation(editop);
			}
		});
    }
    
    /**
     * Displays the information that can be edited for a group.
     * 
     * @param x	a JDialog to write on
     * @param c the GridGagConstraints
     * @param g the group
     */
    public void dispattdialog(JDialog x,GridBagConstraints c,Group g) {
    	name = new TextField(g.name, 20);
    	x.add(new Label("Name:"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
		x.add(name,c);
		
		enabled = new JCheckBox("Enabled:                   ",g.getEnabled());
		enabled.setHorizontalTextPosition(SwingConstants.LEADING);
		x.add(enabled,c);
        c.gridwidth = 1;
		
        /**
		 * Apply button applies the changes to a group
		 * and sets adds the action to the undo list.
		 */
        c.gridwidth = GridBagConstraints.REMAINDER;
		submit = new Button("Apply");
		x.add(submit,c);
		submit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				editop = new EditOperation(selgrp);
				selgrp.name = name.getText();
				selgrp.setEnabled(enabled.isSelected());
				parent.refreshmap();
				editop.setnew(selgrp);
				parent.main.addOperation(editop);
			}
		});
    }
    
    /**
     * Displays the set attributes dialog and calls the
     * corresponding function to fill the dialog with information.
     */
    public void dispattdialog() {
    	x = new JDialog(parent, "Edit Attributes", true);
		x.setAlwaysOnTop(true);
		x.setResizable(true);
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
        else if (selected == 4)
        	dispattdialog(x,c,selgrp);
        //*/
        x.setVisible(true);
    }
    
}
