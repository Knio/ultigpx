
package ultigpx;

import java.util.*;

public class UltiGPX
{
    UGPXFile file;
    MainView view;
    public static void main(String args[])
    {
        new UltiGPX();
    }
    
    public UltiGPX()
    {
        System.out.println("Hello, World!");
        view = new MainView(this);
        view.run();
    }
    
}