
package ultigpx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * The GUI that displays and manages the components.
 * 
 * @author Steven, Tom
 */
public class MainView extends JFrame 
{
    UltiGPX main;
    
    JTabbedPane pane;
    
    MapView map1;
    MapView map2;
    MapView map3;
    
    WayptView wpview;
    PropertiesView prop;
    MapView ele;
    
    SearchResult searchresult;
    
    JMenuBar menuBar;
    
    JMenu fileMenu;
    JMenu editMenu;
    JMenu waypMenu;
    
    JMenuItem importMenuItem;
    JMenuItem exportMenuItem;
    JMenuItem exportGPXMenuItem;
    JMenuItem exportHTMLMenuItem;
    JMenuItem exitMenuItem;
    
    JMenuItem undoMenuItem;
    JMenuItem redoMenuItem;
    
    JMenuItem addwp;
    JMenuItem delwp;
    
    // sets up the GUI with all the components
    public MainView(UltiGPX main)
    {
        super("UltiGPX");
        
        
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            
        }
        
        
        this.main = main;
        
        addWindowListener(new WinListener());
        
        
        setupMenu();
        setupPanes();
        
        // zoom maps to fill screen
        map1.fill();
        map3.load();
        
        setVisible(true);
        
    }
    
    // sets up the menu bar
    void setupMenu()
    {
        
        menuBar = new javax.swing.JMenuBar();
        
        
        fileMenu = new javax.swing.JMenu();
        fileMenu.setText("File");
        
        editMenu = new javax.swing.JMenu();
        editMenu.setText("Edit");
        
        waypMenu = new javax.swing.JMenu();
        waypMenu.setText("Waypoint");
        
        exitMenuItem = new javax.swing.JMenuItem();
        exitMenuItem.setActionCommand("Exit");
        exitMenuItem.setText("Exit");
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(waypMenu);
        
        addwp = new JMenuItem();
        addwp.setText("Add");
        addwp.setVisible(true);
        waypMenu.add(addwp);
        addwp.addActionListener(new ActionListener()
        	{public void actionPerformed(ActionEvent e)
        		{ // TODO do something
        		}});
        addwp.setEnabled(false);
        
        delwp = new JMenuItem();
        delwp.setText("Delete");
        delwp.setVisible(true);
        waypMenu.add(delwp);
        delwp.addActionListener(new ActionListener()
        	{public void actionPerformed(ActionEvent e)
        		{ // TODO do something
        		}});
        delwp.setEnabled(false);
        
        undoMenuItem = new JMenuItem();
        undoMenuItem.setText("Undo");
        undoMenuItem.setVisible(true);
        editMenu.add(undoMenuItem);
        undoMenuItem.setActionCommand("Undo");
        undoMenuItem.addActionListener(new ActionListener()
            {public void actionPerformed(ActionEvent e)
             { main.undo(); }});
        
        
        redoMenuItem = new JMenuItem();
        redoMenuItem.setText("Redo");
        redoMenuItem.setVisible(true);
        editMenu.add(redoMenuItem);
        redoMenuItem.setActionCommand("Redo");
        redoMenuItem.addActionListener(new ActionListener()
            {public void actionPerformed(ActionEvent e)
             { main.redo(); }});
        
        redoMenuItem.setEnabled(false);
        undoMenuItem.setEnabled(false);
        
        
        importMenuItem = new JMenuItem();
        importMenuItem.setText("Import GPX");
        importMenuItem.setVisible(true);
        fileMenu.add(importMenuItem);
        importMenuItem.setActionCommand("Import");
        
        exportMenuItem = new JMenuItem();
        exportMenuItem.setText("Export KML");
        exportMenuItem.setVisible(true);
        fileMenu.add(exportMenuItem);
        exportMenuItem.setActionCommand("Export");
        exportMenuItem.setEnabled(false);
        
        exportGPXMenuItem = new JMenuItem();
        exportGPXMenuItem.setText("Export GPX");
        exportGPXMenuItem.setVisible(true);
        fileMenu.add(exportGPXMenuItem);
        exportGPXMenuItem.setActionCommand("ExportGPX");
        exportGPXMenuItem.setEnabled(false);

        exportHTMLMenuItem = new JMenuItem();
        exportHTMLMenuItem.setText("Export HTML");
        exportHTMLMenuItem.setVisible(true);
        fileMenu.add(exportHTMLMenuItem);
        exportHTMLMenuItem.setActionCommand("ExportHTML");
        exportHTMLMenuItem.setEnabled(false);
        
        MenuListener ml = new MenuListener();
        importMenuItem.addActionListener(ml);
        exportMenuItem.addActionListener(ml);
        exportGPXMenuItem.addActionListener(ml);
		exportHTMLMenuItem.addActionListener(ml);
        
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(ml);
        
        
        setJMenuBar(menuBar);
    }
    
    // sets up the components to be displayed
    void setupPanes()
    {
    
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
        
        c.weightx = 1;
        wpview = new WayptView(main);
        add(wpview, c);
        wpview.fill();
        
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
        map1 = new PlainMapView (main);
        map2 = new GridMapView  (main);
        map3 = new GoogleMapView(main);
        
        searchresult = new SearchResult(this);
        
        pane.add("Basic Map",   map1);
        pane.add("Grid Map",    map2);
        pane.add("Google Map",  map3);
        pane.setSelectedComponent(map2); 
        
        
        c.gridwidth = 1;
        
        c.weighty = 1;
        // sets horiz weight of PropertiesView
        c.weightx = 1.0;
        
        prop = new PropertiesView(this);
        add(prop, c);
        
        c.weightx = 5.0;
        ele = new ElevationView(this.main);
        add(ele, c);
        
        setSize(600, 600);

	map3.load();
        
        
    }
    
    // if what is selected is changed this should be called
    public void selectionChanged()
    {
        map1.selectionChanged();
        map2.selectionChanged();
        //map3.selectionChanged(x); // google maps doesn't work with selecting
        prop.select(main.selected);
    	ele.selectionChanged();
    }
    
    /**
     * @deprecated
     * @param x
     */
    public void select(Object x) {
        
        select((Waypoint)null);
        if (x instanceof Waypoint) select((Waypoint)x);
        if (x instanceof Track) select((Track)x);
        if (x instanceof Route) select((Route)x);
    }
    /**
     * @deprecated
     * @param x
     */
    public void select(Waypoint x) {
        map1.select(x);
        map2.select(x);
        map3.select(x);
    	prop.select(x);
    	ele.select(x);
    }
    /**
     * @deprecated
     * @param x
     */
    public void select(Track x) {
        map1.select(x);
        map2.select(x);
        map3.select(x);
    	prop.select(x);
    	ele.select(x);
    }
    /**
     * @deprecated
     * @param x
     */
    public void select(Route x) {
        map1.select(x);
        map2.select(x);
        map3.select(x);
    	prop.select(x);
    	ele.select(x);
    }
    
    // refreshes the maps and most of the other components
    public void refreshmap() {
        map1.refresh();
        map2.refresh();
        map3.refresh();
        ele.repaint();
        //prop.paintThis();
        
        setVisible(true);
    }
    
    // refreshes the waypoint viewer as well as everything else
    public void refresh()
    {
        
        wpview.updateUI();
        refreshmap();
        setVisible(true);
    }
    
    // makes closing the window "faster" since
    // it hides the gui as it is closing
    class WinListener implements WindowListener
    {
        
        public void windowActivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
        public void windowClosing(WindowEvent e) { setVisible(false); }
        public void windowDeactivated(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowOpened(WindowEvent e) {}
        
    }
    
    // listens for action commands from the menu items
    class MenuListener implements ActionListener {
    	public MenuListener() {
    		super();
    	}
    	public void actionPerformed(ActionEvent e)
        {
            String filenam = null;
            if (e.getActionCommand().equals("Import"))
            {
                Frame parent = new Frame();
                FileDialog fd = new FileDialog(parent, "Choose a GPX file:",
                           FileDialog.LOAD);
                fd.setVisible(true);
                fd.setFilenameFilter(new GPXFilter());
                String GPXfile = fd.getDirectory() + fd.getFile();
                wpview.mainfile = fd.getFile();
                System.out.println("File: "+GPXfile);
                if (GPXfile == null)
                {}
                else
                {
                	boolean exflag=false;
                	try{
                    main.importGPX(GPXfile);
                	} catch (Exception ex) {exflag = true;}
                    if (!exflag)
                    {
                    	prop.select();
                    	map1.fill();
                    	wpview.fill();
                    }
                    else
                    	JOptionPane.showMessageDialog(main.view,"Could not parse the selected file.","Parsing Error",JOptionPane.ERROR_MESSAGE);
                    
                    //by Nate. This is just getting annoying.
                    //searchresult.checkForConflicts();
                }
            }
            
            if (e.getActionCommand().equals("Exit"))
            {
                setVisible(false);
                System.exit(1);
            }
            
            if (e.getActionCommand().equals("Export"))
            {
                Frame parent = new Frame();
                FileDialog fd = new FileDialog(parent, "Choose a KML file:",
                           FileDialog.SAVE);
                fd.setFile(filenam.replaceFirst(".gpx", ".kml"));
                fd.setVisible(true);
                fd.setFilenameFilter(new KMLFilter());
                String GPXfile = fd.getFile();
                if (GPXfile == null)
                {}
                else if (GPXfile.substring(Math.max(GPXfile.length()-4,0), GPXfile.length()).equalsIgnoreCase(".kml"))
                	main.exportKML(GPXfile);
                else
                    main.exportKML(GPXfile+".kml");
            }

            if (e.getActionCommand().equals("ExportHTML"))
            {
                Frame parent = new Frame();
                FileDialog fd = new FileDialog(parent, "Choose an HTML file:",
                           FileDialog.SAVE);
                fd.setVisible(true);
                fd.setFilenameFilter(new HTMLFilter());
                String HTMLfile = fd.getFile();
                if (HTMLfile == null)
                {}
                else if (HTMLfile.substring(Math.max(HTMLfile.length()-5,0), HTMLfile.length()).equalsIgnoreCase(".html"))
                	((GoogleMapView)map3).outputHTML(HTMLfile);
                else
                    ((GoogleMapView)map3).outputHTML(HTMLfile+".html");
            }
            
            if (e.getActionCommand().equals("ExportGPX"))
            {
                Frame parent = new Frame();
                FileDialog fd = new FileDialog(parent, "Choose a GPX file:",
                           FileDialog.SAVE);
                fd.setVisible(true);
                fd.setFilenameFilter(new GPXFilter());
                String GPXfile = fd.getFile();
                if (GPXfile == null)
                {}
                else if (GPXfile.substring(Math.max(GPXfile.length()-4,0), GPXfile.length()).equalsIgnoreCase(".gpx"))
                	main.exportKML(GPXfile);
                else
                    main.exportKML(GPXfile+".gpx");
            }
            
    	}
    }
    
    // filter for importing/exporting a GPX file
    class GPXFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".gpx"));
        }
    }

    // filter for importing/exporting an HTML file
    class HTMLFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".html"));
        }
    }
    
    // filter for exporting a KML file
    class KMLFilter implements FilenameFilter {
    	public boolean accept(File dir, String name) {
    		return (name.endsWith(".kml"));
    	}
    }
}
