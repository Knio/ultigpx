// driver to test MapView classes

package ultigpx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainView extends JFrame 
{
    UltiGPX main;
    MapView map1;
    MapView map2;
    MapView map3;
    PropertiesView prop;
    ElevationView ele;
    JTabbedPane pane;
    
    public MainView(UltiGPX _main)
    {
        super("UltiGPX");
        main = _main;
        
        
        
        
        
        
        
        JMenuBar menuBar;
        menuBar = new javax.swing.JMenuBar();
        
        
        JMenu fileMenu = new javax.swing.JMenu();
        fileMenu.setText("File");
        
        JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        exitMenuItem.setActionCommand("Exit");
        exitMenuItem.setText("Exit");
        
        menuBar.add(fileMenu);
        
        JMenuItem importMenuItem = new JMenuItem();
        importMenuItem.setText("Import GPX");
        importMenuItem.setVisible(true);
        fileMenu.add(importMenuItem);
        importMenuItem.setActionCommand("Import");
        importal fml = new importal();
        importMenuItem.addActionListener(fml);
        
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(fml);
        
        
        setJMenuBar(menuBar);
        
        
        
        
        
        // so that the program doesn't get too small
        // PropertiesView needs at least 55 pixels width
        this.setMinimumSize(new Dimension(250, 250));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // makes a gridbag
        GridBagLayout gridbag = new GridBagLayout();
        
        // initializes the constraints
        GridBagConstraints c = new GridBagConstraints();
        
        // makes the components in the layout stretch
        // in both directions to fill the window
        c.fill = GridBagConstraints.BOTH;
        
        // sets vertical weight
        c.weighty = 1.5;
        setLayout(gridbag);
        
        
        // creates a properties view and adds it as a pane
        
        // sets horiz weight of the map
        c.weightx = 5.0;
        // since this is the last element we want in the
        // first row we set gridwidth to remainder
        c.gridwidth = GridBagConstraints.REMAINDER;
        
        // creates a tabbed pane to switch between the maps
        pane = new JTabbedPane();
        add(pane,c);
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        // creates a map view and adds it as a pane
        map1 = new PlainMapView(main);
        map2 = new GridMapView(main);
        map3 = new GoogleMapView(main);
        pane.add("Basic Map",   map1);
        pane.add("Grid Map",    map2);
        pane.add("Google Map",  map3);
        pane.setSelectedComponent(map2); 
        
        
        c.gridwidth = 1;
        
        c.weighty = .5;
        // sets horiz weight of PropertiesView
        c.weightx = 1.0;
        
        prop = new PropertiesView(this);
        add(prop, c);
        
        c.weightx = 5.0;
        ele = new ElevationView(this.main);
        add(ele, c);
        
        setSize(600, 600);
        setVisible(true);
        
        // zoom maps to fill screen
        map1.fill();
        map3.load();
        //map2.fill();
        //map3.fill();
	//((GoogleMapView)map2).outputHTML();
    }
    
    public void select(Waypoint x) {
    	prop.select(x);
    	ele.select(x);
    }
    
    public void select(Track x) {
    	prop.select(x);
    	ele.select(x);
    }
    
    public void select(Route x) {
    	prop.select(x);
    	ele.select(x);
    }
    
    public void refreshmap() {
    	map1.refresh();
    	map2.refresh();
    	map3.refresh();
    	ele.repaint();
    }
    
    class importal implements ActionListener {
    	public importal() {
    		super();
    	}
    	public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Import"))
    	{
    		Frame parent = new Frame();
    		FileDialog fd = new FileDialog(parent, "Choose a GPX file:",
    		           FileDialog.LOAD);
    		fd.setVisible(true);
    		fd.setFilenameFilter(new GPXFilter());
    		String GPXfile = fd.getFile();
    		if (GPXfile == null)
    		{}
    		else
    		{
    			main.importGPX(GPXfile);
    			//map2 = new GoogleMapView(main);
    			//((GoogleMapView)map2).outputHTML();
    			//map1 = new PlainMapView(main);
    			//map1.fill();
    			//System.out.println("GoogleMap is broken.");
    			prop.select();
    			refreshmap();
                map1.fill();
    		}
    	}
    	else if (e.getActionCommand().equals("Exit"))
    		System.exit(1);
    	}
    }
    class GPXFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".gpx"));
        }
    }
}
