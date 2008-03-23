package ultigpx;


import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.event.*;


public class WayptView extends JComponent implements TreeSelectionListener
{
    
    UltiGPX         main;
    //UGPXFile        file;
    Color white;

    private List<Waypoint>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
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
        
        top = new DefaultMutableTreeNode("GPX FILE");
        createNodes();
        
        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        
        tree = new JTree(treeModel);
        tree.setEditable(true);
        
        tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        tree.addTreeSelectionListener(this);
        
        
        wtrPanel = new JScrollPane(tree);
        
        add(wtrPanel,c);
        
        
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
    
    public void valueChanged(TreeSelectionEvent e)
    {
        System.out.println(e.getPath());
        // select code here
        
    }
    
    
    private void createNodes() {
            DefaultMutableTreeNode category = null;
            DefaultMutableTreeNode info = null;
            
           
            if(main.file == null){
                category = new DefaultMutableTreeNode("Waypoints");
                top.add(category);
                category = new DefaultMutableTreeNode("Tracks");
                top.add(category);
                category = new DefaultMutableTreeNode("Routes");
                top.add(category);
            
            }else{
                
                waypoint = main.file.waypoints();
                int count = waypoint.size();
                category = new DefaultMutableTreeNode("Waypoints");
                top.add(category);
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" waypt = " + waypoint.get(i).getName()+ "count " + i);
                    info = new DefaultMutableTreeNode(waypoint.get(i));
                    category.add(info);
                }
                
                category = new DefaultMutableTreeNode("Tracks");
                top.add(category);
                track = main.file.tracks();
                count = track.size();
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" track = " + track.get(i)+ " count " + i);
                    info = new DefaultMutableTreeNode("track"+i+1);
                    category.add(info);
                }
                
                category = new DefaultMutableTreeNode("Routes");
                top.add(category);
                route = main.file.routes();
                count = route.size();
                
                for(int i = 0;i<count;i++)
                {
                    //System.out.println(" route = " + route.get(i)+ " count " + i);
                    info = new DefaultMutableTreeNode("route"+i+1);
                    category.add(info);
                    
                }
                
            }
            
            
        
    }

}



