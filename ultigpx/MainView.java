// driver to test MapView classes

package ultigpx;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame 
{
    UltiGPX main;
    MapView map1;
    MapView map2;
    MapView map3;
    PropertiesView prop;
    ElevationView ele;
    
    public MainView(UltiGPX _main)
    {
        super("UltiGPX");
        main = _main;
        
        
        
        
        
        
        
        JMenuBar menuBar;
        menuBar = new javax.swing.JMenuBar();
        
        
        JMenu fileMenu = new javax.swing.JMenu();
        fileMenu.setText("File");
        
        JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        //exitMenuItem.setAction();
        exitMenuItem.setName("exitMenuItem");
        
        fileMenu.add(exitMenuItem);
        
        
        menuBar.add(fileMenu);
        
        
        
        
        
        
        
        
        
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
        c.weighty = 1.0;
        setLayout(gridbag);
        
        
        // creates a properties view and adds it as a pane
        
        // sets horiz weight of the map
        c.weightx = 5.0;
        // since this is the last element we want in the
        // first row we set gridwidth to remainder
        c.gridwidth = GridBagConstraints.REMAINDER;
        
        // creates a tabbed pane to switch between the maps
        JTabbedPane x = new JTabbedPane();
        add(x,c);
        x.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        // creates a map view and adds it as a pane
        map1 = new PlainMapView(main);
        map2 = new GoogleMapView(main);
        map3 = new GridMapView(main);
        x.add("Basic Map",map1);
        x.add("Grid Map", map3);
        x.add("Google Map", map2);
        
        
        c.gridwidth = 1;
        
        // sets horiz weight of PropertiesView
        c.weightx = 1.0;
        
        prop = new PropertiesView(this);
        add(prop, c);
        
        c.weightx = 5.0;
        
        c.weighty = .5;
        ele = new ElevationView(this.main);
        add(ele, c);
        
        setSize(600, 600);
        setVisible(true);
        
        // zoom maps to fill screen
        map1.fill();
        //map2.fill();
        //map3.fill();
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
    	map1.repaint();
    	map2.repaint();
    	map3.repaint();
    }
    
}
