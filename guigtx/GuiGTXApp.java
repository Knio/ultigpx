/*
 * GuiGTXApp.java
 */

package guigtx;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class GuiGTXApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //show(new GuiGTXView(this));
    }
        
    public void startup(ultigpx.UltiGPX main) {
        
        show(new GuiGTXView(this,  main));
    }
    
    
    public void select(ultigpx.Waypoint wp)
    {
        
    }
    
    public void select(ultigpx.Track wp)
    {
        
    }
    
    public void select(ultigpx.Route wp)
    {
        
    }
    
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of GuiGTXApp
     */
    public static GuiGTXApp getApplication() {
        return Application.getInstance(GuiGTXApp.class);
    }

 /*
    public static void guiLaunch(String[] args){
    	//String args = "[Ljava.lang.String;@1e4457d";
    	launch(GuiGTXApp.class,args);
    }
    */
    
}