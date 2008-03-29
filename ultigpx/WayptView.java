package ultigpx;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.event.*;


public class WayptView extends JComponent
{
    
    UltiGPX         main;
    //UGPXFile        file;
    //Color white;

    
    protected JList  waypt_list;
    private JScrollPane wtrPanel;
    private JTree tree;
    private DefaultMutableTreeNode top;
    
    MainView parent;
    TextArea tArealabel;
    
    public WayptView(UltiGPX main) {
        super();
        this.main = main;
        this.setPreferredSize(new Dimension(100, 300));
    }
    
    void fill()
    {
        removeAll();
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        
        
        top = new DefaultMutableTreeNode(main.file == null ? "GPX File" : main.file.name);
        createNodes();
        
        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        
        tree = new JTree(treeModel)
        {
            public String convertValueToText(Object value,
                                 boolean selected,
                                 boolean expanded,
                                 boolean leaf,
                                 int row,
                                 boolean hasFocus)
            {
                Object o  = ((DefaultMutableTreeNode)value).getUserObject();
                //System.out.println(o);
                //System.out.println(o.getClass().getCanonicalName());
                if (o instanceof Track)
                    return ((Track)o).getName();
                if (o instanceof Route)
                    return ((Route)o).getName();
                if (o instanceof Waypoint)
                    return ((Waypoint)o).getName();
                if (o instanceof String)
                    return (String)o;
                return "XX"+o.toString();
            }
        };
        
        tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        
        TreeSelectionListener sl = new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                selectEvent(e.getPath());
            }
        };
        
        tree.addTreeSelectionListener(sl);
        
        
        wtrPanel = new JScrollPane(tree);
        
        add(wtrPanel,c);
        
        
        
        MouseListener ml = new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if((e.getClickCount() == 2) && (e.getButton() == e.BUTTON1))
                    clickEvent(tree.getPathForLocation(e.getX(), e.getY()));
                if((e.getClickCount() == 1) && (e.getButton() == e.BUTTON3))
                    rightclickEvent(tree.getPathForLocation(e.getX(), e.getY()));
            }
        };
        
        tree.addMouseListener(ml);
        
    }
    /*
    public void fill()
    {
        System.out.println("hi");
        top.removeAllChildren();
        createNodes();
        tree.setRootVisible(true);
    }
    //*/
    
    
    public void clickEvent(TreePath e)
    {
        System.out.println("CLICK: "+ e);
        if (e==null) return;
        
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
        // these things should be refactored
        if (o instanceof Track)
            ((Track)o).enabled = !((Track)o).enabled;
        if (o instanceof Route)
            ((Route)o).enabled = !((Route)o).enabled;
        if (o instanceof Waypoint)
            ((Waypoint)o).enabled = !((Waypoint)o).enabled;
        
        main.view.refreshmap();
    }
    
    public void rightclickEvent(TreePath e)
    {
        System.out.println("RIGHT CLICK: "+ e);
        if (e==null) return;
        
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
        if (o instanceof Track)
        {
        	if (((Track)o).enabled)
        		main.view.map1.fill((Track)o);
        }

        main.view.refreshmap();
    }
    
    public void selectEvent(TreePath e)
    {
        System.out.println("SELECT: "+ e);
        
        if (e==null)
        {
            main.view.select((Object)null);
            return;
        }
        
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
        main.view.select(o);
        
    }
    
    
    private void createNodes() {
            DefaultMutableTreeNode category = null;
            DefaultMutableTreeNode info = null;
            
            
            List<Waypoint>  waypoint;
            List<Track>     track;
            List<Route>     route;
           
            if(main.file == null){
                category = new DefaultMutableTreeNode("Tracks");
                top.add(category);
                category = new DefaultMutableTreeNode("Routes");
                top.add(category);
                category = new DefaultMutableTreeNode("Waypoints");
                top.add(category);
            
            }else{
                
                track = main.file.tracks();
                category = new DefaultMutableTreeNode("Tracks");
                top.add(category);
                int count = track.size();
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" track = " + track.get(i)+ " count " + i);
                    info = new DefaultMutableTreeNode(track.get(i));
                    category.add(info);
                }
                
                route = main.file.routes();
                category = new DefaultMutableTreeNode("Routes");
                top.add(category);
                count = route.size();
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" route = " + route.get(i)+ " count " + i);
                    info = new DefaultMutableTreeNode(route.get(i));
                    category.add(info);
                    
                }
                
                waypoint = main.file.waypoints();
                count = waypoint.size();
                category = new DefaultMutableTreeNode("Waypoints");
                top.add(category);
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" waypt = " + waypoint.get(i).getName()+ "count " + i);
                    info = new DefaultMutableTreeNode(waypoint.get(i));
                    category.add(info);
                }
                
            }
            
            
        
    }

}



