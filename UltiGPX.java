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
        GuiGTXApp.getApplication();
        
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
    
    
}/*
package ultigpx;

import java.io.*;
import org.jdom.*;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Application;


public class UltiGPX extends SingleFrameApplication
{
	@Override protected void startup() {
        show(new GuiGTXView(this));
    }
	   /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
/*
    @Override protected void configureWindow(java.awt.Window root) {
    }
    /**
     * A convenient static getter for the application instance.
     * @return the instance of GuiGTXApp
     */
/*
    public static UltiGPX getApplication() {
        return Application.getInstance(UltiGPX.class);
    }

    UGPXFile file;
    MainView view;
    //GuiGTXApp view;
    public static void main(String args[])
    {
        
        new UltiGPX();
       
       // launch(UltiGPX.class, args);
        
        //System.out.println(args[0]);
    }
    
    public UltiGPX()
    {
        //System.out.println("Hello, World!");
        
        //view = new MainView(this);
    	        
        //SingleFrameApplication.launch(GuiGTXApp.class, new String[0]);
   /*     
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
    
    
}*/