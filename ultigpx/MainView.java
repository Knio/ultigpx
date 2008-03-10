// stub to test MapView. Please implement me

package ultigpx;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame
{
    UltiGPX main;
    MapView map;
    
    public MainView(UltiGPX _main)
    {
        super("UltiGPX");
        main = _main;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new GridLayout(1, 1));
        
        map = new BasicMapView(main);
        add(map);
        
        setSize(300, 200);
        setVisible(true);
    }
    
}