package ultigpx;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;


public class WayptView extends JComponent
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	UltiGPX         main;
        
    protected List  select_list;
    protected JButton group_button;
    private JScrollPane wtrPanel;
    private JTree tree;
    private DefaultMutableTreeNode top;
    
    
    MainView parent;
    TextArea tArealabel;
    
    public WayptView(UltiGPX main) {
        super();
        this.main = main;
        this.setPreferredSize(new Dimension(100, 300));
        select_list = new ArrayList();
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
            (TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        
        
        TreeSelectionListener sl = new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
                selectEvent(e.getPath());
                
            }
        };
        
        tree.addTreeSelectionListener(sl);
        
        
        wtrPanel = new JScrollPane(tree);
        
        c.gridx = 0;
        add(wtrPanel,c);
        
        group_button = new JButton(" Create Group ");
        c.weighty=0.1;
        group_button.setToolTipText("select waypoints/Tracks/Routes to form a Group");
        add(group_button,c);
//      set up button actions
    	group_button.setActionCommand("createGroup");
    	
    	if(main.file == null)
    		group_button.setEnabled(false);
    	else 
    		group_button.setEnabled(true);
    	group_button.addActionListener(new ActionHandler());
    	
        
        
        
        MouseListener ml = new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                    clickEvent(tree.getPathForLocation(e.getX(), e.getY()));
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
    
    
    public void selectEvent(TreePath e)
    {
        System.out.println("SELECT: "+ e);
        
        if (e==null)
        {
            main.view.select((Object)null);
            return;
        }
        
              
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
        select_list.add(o);
        main.view.select(o);
        
    }
    
    
    private void createNodes() {
            DefaultMutableTreeNode category = null;
            DefaultMutableTreeNode info = null;
            
            
            List<Waypoint>  waypoint;
            List<Track>     track;
            List<Route>     route;
           // List<Group>     group;
           
            if(main.file == null){
                category = new DefaultMutableTreeNode("Tracks");
                top.add(category);
                category = new DefaultMutableTreeNode("Routes");
                top.add(category);
                category = new DefaultMutableTreeNode("Waypoints");
                top.add(category);
                category = new DefaultMutableTreeNode("Groups");
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
                
               /* group = main.file.groups();
                   count = group.size();
                for(int i = 0;i<count;i++)
                {
                    System.out.println(" Group = " + group.get(i).getName()+ "count " + i);
                    info = new DefaultMutableTreeNode(group.get(i));
                    category.add(info);
                }*/
                
                
            }
            
            
        
    }
    
 class ActionHandler implements ActionListener{
	 /**
 	 * Creates the ActionListener
 	 */
 	public ActionHandler() {
 		super();
 	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("createGroup")){
			//System.out.println(" display a text box");
			if(select_list.size() == 0){
				ShowSelectMessageDialogBox();
				return;
			}
			String input = "";
			while(input.equals("")){
				 input = ShowDialogBox();
				 
				 if(input.equals("")){
					 // prompt user to enter file name
					 ShowMessageDialogBox();
				 }
				 //System.out.println("input from user =" +input);
			}// end while
			if(input != null){
				System.out.println("selection list size " + select_list.size());
			}
			
		}
		
	}
	 @SuppressWarnings("static-access")
	private void ShowSelectMessageDialogBox() {
		 JOptionPane pane = new JOptionPane();
		  pane.showMessageDialog(null,"Please Select the Tracks, Waypoints, or Routes to form a group",
				    "Message Dialog",JOptionPane.PLAIN_MESSAGE);
		
	}

	private void ShowMessageDialogBox() {
		
		 JOptionPane pane = new JOptionPane();
		 pane.showMessageDialog(null,"Please enter the name of the file",
				    "Message Dialog",JOptionPane.PLAIN_MESSAGE);
		
	}

	@SuppressWarnings("deprecation")
	protected String ShowDialogBox()

	    {
		 JOptionPane pane = new JOptionPane();
		 String input = JOptionPane.showInputDialog(null,"Enter name of the group");
		 pane.show();
		 if(input == null){
			 pane.hide();
			 input = " ";
		 }
		 return input;
		 
		 

	    }
	 
 }
 
 class CustomDialog extends JDialog
 implements ActionListener,
            PropertyChangeListener {
  protected JDialog grp_dialog;
  
  

public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}

public void propertyChange(PropertyChangeEvent arg0) {
	// TODO Auto-generated method stub
	
}
  






 }
 

}
