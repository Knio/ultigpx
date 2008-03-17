
package ultigpx;

// needed imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

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
	// It works a lot better than my code but doesn't
	// necessarily stay resized. Anyone got an idea to fix it?
	TextArea mllabel;
	
	// RGB sliders
	JSlider sliderr;
	JSlider sliderg;
	JSlider sliderb;
	
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
		setLayout(new GridBagLayout());
    	mllabel = new TextArea("",5,5,TextArea.SCROLLBARS_VERTICAL_ONLY);
    	mllabel.setEditable(false);
    	GridBagConstraints c = new GridBagConstraints();
    	GridBagConstraints labels = new GridBagConstraints();
    	c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 2.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,10,0,9);
    	add(mllabel,c);
    	Button y = new Button("Set Color");
    	Label r = new Label("Red");
    	Label g = new Label("Green");
    	Label b = new Label("Blue");
    	sliderr = new JSlider(0, 255, 0);
    	sliderg = new JSlider(0, 255, 0);
    	sliderb = new JSlider(0, 255, 0);
    	c.insets = new Insets(0,10,0,9);
    	labels.insets = new Insets(0,10,0,9);
    	labels.fill = GridBagConstraints.BOTH;
    	labels.weighty = 0.05;
    	labels.weightx = 1;
    	labels.gridwidth = GridBagConstraints.REMAINDER;
    	c.weighty = 0.05
    	;
    	add(r, labels);
    	add(sliderr, c);
    	add(g, labels);
    	add(sliderg, c);
    	add(b, labels);
    	add(sliderb, c);
    	c.insets = new Insets(0,10,9,9);
    	add(y, c);
    	y.setActionCommand("SetColor");
    	y.addActionListener(new submitactionlistener());
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
    	painttest(g2d);
    	return;//*/
    	
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
        }//*/
    }
    
    // writes a test string on the frame
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
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Waypoint: " + selwp.getName() + "\n\n");
    	// displays status
    	if (selwp.getEnabled())
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
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
    		mllabel.append("Color: " + selwp.getColor() + "\n\n");
    	else
    		mllabel.append("Color: Default\n\n");
    }
    // writes a track on the screen
    protected void painttrk(Graphics2D g2d){
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
    		mllabel.append("Color: " + seltrk.color + "\n\n");
    	else
    		mllabel.append("Color: Default\n\n");
    }
    // writes a route on the screen
    protected void paintrt(Graphics2D g2d){
    	// Clear the text
    	mllabel.setText("");
    	// displays name
    	mllabel.append("Route: " + "\n\n");
    	// displays status
    	if (selrt.enabled)
    		mllabel.append("Status: Enabled\n\n");
    	else
    		mllabel.append("Status: Disabled\n\n");
    	// displays description
    	mllabel.append("Number of Waypoints: " + selrt.size() + "\n\n");
    	// displays color
    	if (selrt.color != null)
    		mllabel.append("Color: " + selrt.color + "\n\n");
    	else
    		mllabel.append("Color: Default\n\n");
    }
    
    // action listener for the set color button
    class submitactionlistener implements ActionListener {
    	public submitactionlistener() {
    		super();
    	}
    	public void actionPerformed(ActionEvent e) {
    		if (e.getActionCommand().equals("SetColor") && (selected == 1))
    		{
    			selwp.color = (new Color(sliderr.getValue(),sliderg.getValue(),sliderb.getValue()));
    			System.out.println("wpcolor: " + selwp.color);
    		}
    		else if (e.getActionCommand().equals("SetColor") && (selected == 2))
    		{
    			seltrk.color = (new Color(sliderr.getValue(),sliderg.getValue(),sliderb.getValue()));
    			System.out.println("trkcolor: " + seltrk.color);
    		}
    		else if (e.getActionCommand().equals("SetColor") && (selected == 3))
    		{
    			selrt.color = (new Color(sliderr.getValue(),sliderg.getValue(),sliderb.getValue()));
    			System.out.println("rtcolor: " + selrt.color);
    		}
    	}
    }
    
}
