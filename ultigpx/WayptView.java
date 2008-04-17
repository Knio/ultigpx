package ultigpx;

//needed imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/*
 * Displays the waypoints, Tracks, Routes and Groups info on the left panel
 * creates checkboxes for the JTree and also creates Group
 * 
 * @author Bindu
 */
public class WayptView extends JComponent{
	
	UltiGPX   main;
    
    protected JButton group_button;
    private JScrollPane wtrPanel;
    private JTree tree;
    private DefaultMutableTreeNode top;
    private String filename;
    private CheckTreeManager checkTreeManager;
    
    
   // MainView parent;
  //  TextArea tArealabel;
    String mainfile = null;
    
      /*
       * @param main UltiGPX file
       */ 
    public WayptView(UltiGPX main) {
        super();
        this.main = main;
        this.setPreferredSize(new Dimension(100, 300));
    }
   /*
    * JTree has two selections: one is normal selection and another is checkSelection (checkbox selected)
    * Used TreeSelectionModel to maintain checkSelection and modified DefaultTreeSelectionModel
    *
    */
    public class CheckTreeSelectionModel extends DefaultTreeSelectionModel{ 
        private TreeModel model; 
     
        public CheckTreeSelectionModel(TreeModel model){ 
            this.model = model; 
            setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION); 
        } 
     /*
      *  @param TreePath path that needs to be tested for any unselected node in the 
      *                  subtree of given path
      *  @return boolean true if  there is a unselected node
      *                  otherwise false
      */
         
        public boolean isPartiallySelected(TreePath path){ 
            if(isPathSelected(path, true)) 
                return false; 
            TreePath[] selectionPaths = getSelectionPaths(); 
            if(selectionPaths==null) 
                return false; 
            for(int j = 0; j<selectionPaths.length; j++){ 
                if(isDescendant(selectionPaths[j], path)) 
                    return true; 
            } 
            return false; 
        } 
        /* tells whether given path is selected.
         * if dig is true, then a path is assumed to be selected, if 
         * one of its ancestor is selected.
         *  @param1 TreePath path - path to be tested
         *  @param2 boolean dig
         *  @return boolean true if path is selected
         *          otherwise false
         */
        
        public boolean isPathSelected(TreePath path, boolean dig){ 
            if(!dig) 
                return super.isPathSelected(path); 
            while(path!=null && !super.isPathSelected(path)) 
                path = path.getParentPath(); 
            return path!=null; 
        } 
     
        /* 
         * is path1 descendant of path2
         * @param1 TreePath path1
         * @param2 TreePath path2
         * @return boolean true if path is descendant of path2
         */ 
        private boolean isDescendant(TreePath path1, TreePath path2){ 
            Object obj1[] = path1.getPath(); 
            Object obj2[] = path2.getPath(); 
            for(int i = 0; i<obj2.length; i++){ 
                if(obj1[i]!=obj2[i]) 
                    return false; 
            } 
            return true; 
        } 
     /*
      * (non-Javadoc)
      * @see javax.swing.tree.DefaultTreeSelectionModel#setSelectionPaths(javax.swing.tree.TreePath[])
      */
        //TODO need to implement inorder to set the paths
        public void setSelectionPaths(TreePath[] pPaths){ 
            throw new UnsupportedOperationException("not implemented yet!!!"); 
        } 
     /*
      *  adds selectionPaths
      *  @param TreePath[] array of TreePaths
      * 
      */
        public void addSelectionPaths(TreePath[] paths){ 
            // unselect all descendants of paths[] 
            for(int i = 0; i<paths.length; i++){ 
                TreePath path = paths[i]; 
                TreePath[] selectionPaths = getSelectionPaths(); 
                if(selectionPaths==null) 
                    break; 
                ArrayList<Object> toBeRemoved = new ArrayList<Object>(); 
                for(int j = 0; j<selectionPaths.length; j++){ 
                    if(isDescendant(selectionPaths[j], path)) 
                        toBeRemoved.add(selectionPaths[j]); 
                } 
                super.removeSelectionPaths((TreePath[])toBeRemoved.toArray(new TreePath[0])); 
            } 
     
            // if all siblings are selected then unselect them and select parent recursively 
            // otherwize just select that path. 
            for(int i = 0; i<paths.length; i++){ 
                TreePath path = paths[i]; 
                TreePath temp = null; 
                while(areSiblingsSelected(path)){ 
                    temp = path; 
                    if(path.getParentPath()==null) 
                        break; 
                    path = path.getParentPath(); 
                } 
                if(temp!=null){ 
                    if(temp.getParentPath()!=null) 
                        addSelectionPath(temp.getParentPath()); 
                    else{ 
                        if(!isSelectionEmpty()) 
                            removeSelectionPaths(getSelectionPaths()); 
                        super.addSelectionPaths(new TreePath[]{temp}); 
                    } 
                }else 
                    super.addSelectionPaths(new TreePath[]{ path}); 
            } 
        } 
     
        /* 
         * tells whether all siblings of given path are selected. 
         * @param TreePath path 
         * @return boolean true if all siblings are selected
         *         otherwise false
         * */
        
        private boolean areSiblingsSelected(TreePath path){ 
            TreePath parent = path.getParentPath(); 
            if(parent==null) 
                return true; 
            Object node = path.getLastPathComponent(); 
            Object parentNode = parent.getLastPathComponent(); 
     
            int childCount = model.getChildCount(parentNode); 
            for(int i = 0; i<childCount; i++){ 
                Object childNode = model.getChild(parentNode, i); 
                if(childNode==node) 
                    continue; 
                if(!isPathSelected(parent.pathByAddingChild(childNode))) 
                    return false; 
            } 
            return true; 
        } 
     /*
      * removes given selection paths
      * @param TreePath[]  array of TreePaths
      * 
      */
        public void removeSelectionPaths(TreePath[] paths){ 
            for(int i = 0; i<paths.length; i++){ 
                TreePath path = paths[i]; 
                if(path.getPathCount()==1) 
                    super.removeSelectionPaths(new TreePath[]{ path}); 
                else 
                    toggleRemoveSelection(path); 
            } 
        } 
     
        /*  if any ancestor node of given path is selected then unselect it 
         *  and selection all its descendants except given path and descendants. 
         *   otherwise just unselect the given path
         *   @param TreePath 
         */
        
        private void toggleRemoveSelection(TreePath path){ 
            Stack<Object> stack = new Stack<Object>(); 
            TreePath parent = path.getParentPath(); 
            while(parent!=null && !isPathSelected(parent)){ 
                stack.push(parent); 
                parent = parent.getParentPath(); 
            } 
            if(parent!=null) 
                stack.push(parent); 
            else{ 
                super.removeSelectionPaths(new TreePath[]{path});
                 return; 
            } 
     
            while(!stack.isEmpty()){ 
                TreePath temp = (TreePath)stack.pop(); 
                TreePath peekPath = stack.isEmpty() ? path : (TreePath)stack.peek(); 
                Object node = temp.getLastPathComponent(); 
                Object peekNode = peekPath.getLastPathComponent(); 
                int childCount = model.getChildCount(node); 
                for(int i = 0; i<childCount; i++){ 
                    Object childNode = model.getChild(node, i); 
                    if(childNode!=peekNode) 
                        super.addSelectionPaths(new TreePath[]{temp.pathByAddingChild(childNode)}); 
                } 
            } 
            super.removeSelectionPaths(new TreePath[]{parent}); 
        } 
    }
    
    /*
     * adds functionality to the checkboxes created
     */
    public static class TristateCheckBox extends JCheckBox {
    	  /** This is a type-safe enumerated type */
    	  public static class State { private State() { } }
    	  public static final State NOT_SELECTED = new State();
    	  public static final State SELECTED = new State();
    	  public static final State DONT_CARE = new State();

    	  private final TristateDecorator model;

    	  public TristateCheckBox(String text, Icon icon, State initial){
    	    super(text, icon);
    	    // Add a listener for when the mouse is pressed
    	    super.addMouseListener(new MouseAdapter() {
    	      public void mousePressed(MouseEvent e) {
    	        grabFocus();
    	        model.nextState();
    	      }
    	    });
    	    // Reset the keyboard action map
    	    ActionMap map = new ActionMapUIResource();
    	    map.put("pressed", new AbstractAction() {
    	      public void actionPerformed(ActionEvent e) {
    	        grabFocus();
    	        model.nextState();
    	      }
    	    });
    	    map.put("released", null);
    	    SwingUtilities.replaceUIActionMap(this, map);
    	    // set the model to the adapted model
    	    model = new TristateDecorator(getModel());
    	    setModel(model);
    	    setState(initial);
    	  }
    	  public TristateCheckBox(String text, State initial) {
    	    this(text, null, initial);
    	  }
    	  public TristateCheckBox(String text) {
    	    this(text, DONT_CARE);
    	  }
    	  public TristateCheckBox() {
    	    this(null);
    	  }

    	  
    	  public void addMouseListener(MouseListener l) { }
    	  /**
    	   * Set the new state to either SELECTED, NOT_SELECTED or
    	   * DONT_CARE.  If state == null, it is treated as DONT_CARE.
    	   * @param State checkbox state
    	   */
    	  public void setState(State state) { model.setState(state); }
    	  /** Return the current state, which is determined by the
    	   * selection status of the model. */
    	  public State getState() { return model.getState(); }
    	  /*
    	   * sets the state to the checkbox
    	   * @param boolean 
    	   * 
    	   */
    	  public void setSelected(boolean b) {
    	    if (b) {
    	      setState(SELECTED);
    	    } else {
    	      setState(NOT_SELECTED);
    	    }
    	  }
    	 
    	  
    	  private class TristateDecorator implements ButtonModel {
    	    private final ButtonModel other;
    	    private TristateDecorator(ButtonModel other) {
    	      this.other = other;
    	    }
    	    private void setState(State state) {
    	      if (state == NOT_SELECTED) {
    	        other.setArmed(false);
    	        setPressed(false);
    	        setSelected(false);
    	      } else if (state == SELECTED) {
    	        other.setArmed(false);
    	        setPressed(false);
    	        setSelected(true);
    	      } else { // either "null" or DONT_CARE
    	        other.setArmed(true);
    	        setPressed(true);
    	        setSelected(true);
    	      }
    	    }
    	    /**
    	     * The current state is embedded in the selection / armed
    	     * state of the model.
    	     * 
    	     * We return the SELECTED state when the checkbox is selected
    	     * but not armed, DONT_CARE state when the checkbox is
    	     * selected and armed (grey) and NOT_SELECTED when the
    	     * checkbox is deselected.
    	     */
    	    private State getState() {
    	      if (isSelected() && !isArmed()) {
    	        // normal black tick
    	        return SELECTED;
    	      } else if (isSelected() && isArmed()) {
    	        // don't care grey tick
    	        return DONT_CARE;
    	      } else {
    	        // normal deselected
    	        return NOT_SELECTED;
    	      }
    	    }
    	    /** We rotate between NOT_SELECTED, SELECTED and DONT_CARE.*/
    	    private void nextState() {
    	      State current = getState();
    	      if (current == NOT_SELECTED) {
    	        setState(SELECTED);
    	      } else if (current == SELECTED) {
    	        setState(DONT_CARE);
    	      } else if (current == DONT_CARE) {
    	        setState(NOT_SELECTED);
    	      }
    	    }
    	    
    	    public void setArmed(boolean b) {
    	    }
    	    /** We disable focusing on the component when it is not
    	     * enabled. */
    	    public void setEnabled(boolean b) {
    	      setFocusable(b);
    	      other.setEnabled(b);
    	    }
    	    /** All these methods simply delegate to the "other" model
    	     * that is being decorated. */
    	    public boolean isArmed() { return other.isArmed(); }
    	    public boolean isSelected() { return other.isSelected(); }
    	    public boolean isEnabled() { return other.isEnabled(); }
    	    public boolean isPressed() { return other.isPressed(); }
    	    public boolean isRollover() { return other.isRollover(); }
    	    public void setSelected(boolean b) { other.setSelected(b); }
    	    public void setPressed(boolean b) { other.setPressed(b); }
    	    public void setRollover(boolean b) { other.setRollover(b); }
    	    public void setMnemonic(int key) { other.setMnemonic(key); }
    	    public int getMnemonic() { return other.getMnemonic(); }
    	    public void setActionCommand(String s) {
    	      other.setActionCommand(s);
    	    }
    	    public String getActionCommand() {
    	      return other.getActionCommand();
    	    }
    	    public void setGroup(ButtonGroup group) {
    	      other.setGroup(group);
    	    }
    	    public void addActionListener(ActionListener l) {
    	      other.addActionListener(l);
    	    }
    	    public void removeActionListener(ActionListener l) {
    	      other.removeActionListener(l);
    	    }
    	    public void addItemListener(ItemListener l) {
    	      other.addItemListener(l);
    	    }
    	    public void removeItemListener(ItemListener l) {
    	      other.removeItemListener(l);
    	    }
    	    public void addChangeListener(ChangeListener l) {
    	      other.addChangeListener(l);
    	    }
    	    public void removeChangeListener(ChangeListener l) {
    	      other.removeChangeListener(l);
    	    }
    	    public Object[] getSelectedObjects() {
    	      return other.getSelectedObjects();
    	    }
    	  }
		
    	}
   	  /*
   	   *   TreeCellRenderer for the checkboxTree
   	   */
    public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer{ 
        private CheckTreeSelectionModel selectionModel; 
        private TreeCellRenderer delegate; 
        private TristateCheckBox checkBox = new TristateCheckBox(); 
     
        public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel){ 
            this.delegate = delegate; 
            this.selectionModel = selectionModel; 
            setLayout(new BorderLayout()); 
            setOpaque(false); 
            checkBox.setOpaque(false); 
        } 
     
     /*
      * this method is called whenever there is a change in the CheckBoxTree
      * @param1 tree 
      * @param2 Object value - node of the tree
      * @param3 boolean selected - node selection
      * @param4 boolean  expanded - true if node is expanded
      * @param5 boolean leaf - true if node is a leaf
      * @param6 int row - node level
      * @param7 boolean true if node has focus
      */
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){ 
            Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 
     
            TreePath path = tree.getPathForRow(row); 
            if(path!=null){
            	
                if(selectionModel.isPathSelected(path, true)){ 
                	checkBox.setState(TristateCheckBox.SELECTED); 
                	               	
                }
                else {
                	checkBox.setState(selectionModel.isPartiallySelected(path) ? null : TristateCheckBox.NOT_SELECTED); 
                }
                
            } 
            removeAll();
            
           if (!(((DefaultMutableTreeNode)value).getUserObject() instanceof String))
            	if(((UGPXData)((DefaultMutableTreeNode)value).getUserObject()).getEnabled())
            		checkBox.setState(TristateCheckBox.SELECTED); 
            	else
            		checkBox.setState(TristateCheckBox.NOT_SELECTED); 
        	   
            add(checkBox, BorderLayout.WEST); 
            add(renderer, BorderLayout.CENTER); 
            return this; 
        } 
    } 
    /*
     * Manages the checkTree implements TreeSelectionListener
     */
    public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener{ 
        private CheckTreeSelectionModel selectionModel; 
        private JTree tree = new JTree(); 
        int hotspot = new JCheckBox().getPreferredSize().width; 
     
        public CheckTreeManager(JTree tree){ 
		    this.tree = tree; 
		    selectionModel = new CheckTreeSelectionModel(tree.getModel()); 
		    tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel)); 
		    tree.addMouseListener(this); 
		    selectionModel.addTreeSelectionListener(this); 
		}

		public void mouseClicked(MouseEvent me){ 
            TreePath path = tree.getPathForLocation(me.getX(), me.getY()); 
            if(path==null) 
                return; 
            if(me.getX()>tree.getPathBounds(path).x+hotspot) 
                return; 
     
            boolean selected = selectionModel.isPathSelected(path, true); 
            selectionModel.removeTreeSelectionListener(this); 
            Object value = path.getLastPathComponent();
            try{ 
            if(!(((DefaultMutableTreeNode)value).getUserObject() instanceof String))
            {
            	UGPXData data = (UGPXData)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                if (data.getEnabled())
                {
                    Object o = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                    if (o instanceof UGPXData)
                    {
                        UGPXData d = (UGPXData)o;
                        d.setEnabled(false);
                        main.view.refresh();
                       
                    }
                     
                    System.out.println("unckecked "+path);
                }
                else 
                {
                    
                    Object o = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                    if (o instanceof UGPXData)
                    {
                        UGPXData d = (UGPXData)o;
                        d.setEnabled(true);
                        main.view.refresh();
                      
                    }
                    
                    System.out.println("check "+path);
                    
                }
            }else{
            	
            }
            if(selected) 
                selectionModel.removeSelectionPath(path); 
            else 
                selectionModel.addSelectionPath(path); 
                        
            } finally{ 
                selectionModel.addTreeSelectionListener(this); 
                tree.treeDidChange(); 
                
            } 
        } 
     
		/*
		 *  gets the selectionmodel of the checkTree
		 *  @return selectionModel
		 */
		public CheckTreeSelectionModel getSelectionModel(){ 
            return selectionModel; 
        } 
     
		/*
		 * notifies the tree if value is changed
		 * @param TreeSelectionEvent e
		 */
        public void valueChanged(TreeSelectionEvent e){ 
            tree.treeDidChange(); 
        } 
    }
    /*
     *  overides the fill() method
     */
    boolean fill()
    {
        removeAll();
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        
	// if file is not loaded created default tree with no data
	 top =
        new DefaultMutableTreeNode((mainfile == null ? "GPX File" : mainfile));
	 
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
       
       if(main.file != null){
           // inserts check boxes for the tree    
    	   checkTreeManager = new CheckTreeManager(tree);
    	    	   
           	       
       }
       wtrPanel = new JScrollPane(tree); 
       c.gridx = 0;
       add(wtrPanel,c);
       
       group_button = new JButton(" Create Group ");
       c.weighty=0.1;
       group_button.setToolTipText("select waypoints/Tracks/Routes to form a Group");
       add(group_button,c);
//     set up button actions
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
        	   if((e.getClickCount() == 2) && (e.getButton() == e.BUTTON1))
                   dblclickEvent(tree.getPathForLocation(e.getX(), e.getY()));
               
           }
       };
       
       tree.addMouseListener(ml);
       if (main.file==null)
    	   return false;
	return true;
       

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
   	   
   			String input = "";
   			while(input.equals("")){
   				 input = ShowDialogBox();
   				 
   				 if(input.equals("")){
   					 // prompt user to enter file name
   					 ShowMessageDialogBox();
   				 }
   				
   			}// end while
   			if(input != null && input != " "){
   				filename = input;
   				creategroup();
   				fill();
  				main.view.refreshmap();
   			}
   			
   		}
   		
   	}
     
    
	/* Creates a group with user selected list */	
   	private void creategroup() 
    {   
        
        Group g = new Group(main.selected.waypoints(),
                            main.selected.tracks(),
                            main.selected.routes());
                            
        
        g.name = filename;
        
        main.file.addGroup(g);
        fill();
        main.view.refresh();
     
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
   		 pane.setVisible(true);
   		 if(input == null){
   			 pane.setVisible(false);
   			 input = " ";
   		 }
   		 return input;
   		 
   		 

   	    }
   	 
    }  
    
   	public void selectEvent(TreePath e)
    {
        System.out.println("SELECT: "+ e);
        
        main.selected.clear();
        
        TreePath p[] = tree.getSelectionModel().getSelectionPaths();
        if (p!=null)
        for (TreePath i : p)
        {
            Object o = ((DefaultMutableTreeNode)i.getLastPathComponent()).getUserObject();
            if (o instanceof UGPXData)
            {
                main.selected.add((UGPXData)o);
                System.out.println((UGPXData)o);
            }
        }
        
        main.selected.selectionChanged();
        
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
    }

  
    
   	// on double click zoom to fit selected on screen
    public void dblclickEvent(TreePath e)
    {
        System.out.println("RIGHT CLICK: "+ e);
        if (e==null) return;
        
        Object o = ((DefaultMutableTreeNode)e.getLastPathComponent()).getUserObject();
        
                //selectThis(e);
        
        if (o instanceof Track)
        {
                if (((Track)o).enabled)
                        main.view.map1.fill((Track)o);
                else
                        main.view.map1.fill();
        }
        else if (o instanceof Waypoint)
        {
                if (((Waypoint)o).enabled)
                        main.view.map1.fill((Waypoint)o);
                else
                        main.view.map1.fill();
        }
        else if (o instanceof Route)
        {
                if (((Route)o).enabled)
                        main.view.map1.fill((Route)o);
                else
                        main.view.map1.fill();
        }
        else
                main.view.map1.fill();

        main.view.refreshmap();
    }
    
   
/*
 * creates nodes for the tree
 */
    
private void createNodes() {
	DefaultMutableTreeNode category = null;
    DefaultMutableTreeNode info = null;
    
    
    int count;
    List<Waypoint>  waypoint;
    List<Track>     track;
    List<Route>     route;
    List<Group>     group;
   
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
    	createsubnodes();
    	
    	   group = main.file.groups();
	  
           count = group.size();
           
           
        	   category = new DefaultMutableTreeNode("Groups"); 
        	   top.add(category);
        	   if(count != 0){ 
        for(int j =0; j<count;j++){
           	DefaultMutableTreeNode subcategory = new DefaultMutableTreeNode(group.get(j).name);
           	DefaultMutableTreeNode subcatinfo_t = new DefaultMutableTreeNode("Tracks");
           	DefaultMutableTreeNode subcatinfo_r = new DefaultMutableTreeNode("Routes");
           	DefaultMutableTreeNode subcatinfo_w = new DefaultMutableTreeNode("Waypoints");
           	
               top.add(category);
               category.add(subcategory);
               subcategory.add(subcatinfo_t);
               subcategory.add(subcatinfo_r);
               subcategory.add(subcatinfo_w);
               
                               
              
            List<Track> tracks = group.get(j).tracks();
           for(int i = 0;i<tracks.size();i++)
           {
              
               Object o = tracks.get(i);
               info = new DefaultMutableTreeNode(o) ;
              	subcatinfo_t.add(info);
           } 
           List<Route> routes = group.get(j).routes();
           for(int i = 0;i<routes.size();i++)
           {
              
               Object o = routes.get(i);
               info = new DefaultMutableTreeNode(o); 
              	subcatinfo_r.add(info);
           }
           List<Waypoint> wypts = group.get(j).waypoints();
           for(int i = 0;i<wypts.size();i++)
           {
              
               Object o = wypts.get(i);
               info = new DefaultMutableTreeNode(o); 
              	subcatinfo_w.add(info);
           }  
               
               
               
           } // end for loop
           } // end if
           
           
        
    }
    
}
/*
 * created subnodes for the nodes in the tree
 */
private void createsubnodes() {
	
	List<Track> track = main.file.tracks();
	int count = track.size();
	     
    DefaultMutableTreeNode category = new DefaultMutableTreeNode("Tracks");
    top.add(category);
    count = track.size();
    
    DefaultMutableTreeNode info;
	for(int i = 0;i<count;i++)
    {
        
        info = new DefaultMutableTreeNode(track.get(i));
        category.add(info);
    }
    
    List<Route> route = main.file.routes();
    category = new DefaultMutableTreeNode("Routes");
    top.add(category);
    count = route.size();
    
    for(int i = 0;i<count;i++)
    {
       
        info = new DefaultMutableTreeNode(route.get(i));
        category.add(info);
        
        
    }
    
    List<Waypoint> waypoint = main.file.waypoints();
    count = waypoint.size();
    category = new DefaultMutableTreeNode("Waypoints");
    top.add(category);
    
    for(int i = 0;i<count;i++)
    {
       
        info = new DefaultMutableTreeNode(waypoint.get(i));
        category.add(info);
    }
	
}

    
    
    
}// end class

