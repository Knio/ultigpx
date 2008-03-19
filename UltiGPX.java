package ultigpx;

import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdesktop.application.SingleFrameApplication;

public class UltiGPX
{
    UGPXFile file;
    MainView view;
    
    public static void main(String[] args)
    {
        
        new UltiGPX();
       GuiGTXApp app = GuiGTXApp.getApplication();
       app.startup(); 
    }
    
    public UltiGPX()
    {
        System.out.println("Hello, World!");
        
        //view = new MainView(this);
        
        
    }
    
    public void importGPX(String filename)
    {
        try
        {
            file = new GPXImporter().importGPX(filename);
        }
        catch (JDOMException e)
        {
            System.out.println("Error parsing file:");
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println("Error reading file:");
            System.out.println(e);
        }
    }
    
    
}