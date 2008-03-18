// driver to test MapView classes

package ultigpx;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame
{
    UltiGPX main;
    MapView map;
    PropertiesView prop;
    
    public MainView(UltiGPX _main)
    {
        super("UltiGPX");
        main = _main;
        
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
        
        // sets horiz weight of PropertiesView
        c.weightx = 1.0;
        
        // creates a properties view and adds it as a pane
        prop = new PropertiesView();
        add(prop, c);
        
        // sets horiz weight of the map
        c.weightx = 5.0;
        // since this is the last element we want in the
        // first row we set gridwidth to remainder
        c.gridwidth = GridBagConstraints.REMAINDER;
        
        // creates a map view and adds it as a pane
        map = new MapView(main);
        //map = new GoogleMapView(main);
	//map = new BasicMapView(main);
        add(map, c);
        
        // resets the gridwidth for next row
        c.gridwidth = 1;
        
        setSize(600, 600);
        setVisible(true);
    }
    
}
