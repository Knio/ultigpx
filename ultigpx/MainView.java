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
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new GridLayout(1, 1));
        
        // creates a properties view and adds it as a pane
        prop = new PropertiesView();
        add(prop);
        // creates a map view and adds it as a pane
        map = new MapView(main);
        add(map);

        
        setSize(600, 600);
        setVisible(true);
    }
    
}