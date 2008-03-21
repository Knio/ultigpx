package ultigpx;


import java.awt.*;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;


public class WayptView extends JPanel {
	
	UltiGPX         main;
	UGPXFile        file;
	Color white;

    private List<InfoView>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
    protected JList  waypt_list;
    private JScrollPane wtrPanel;
    private JTree tree;
    
	
	MainView parent;
	//UltiGPX main;
	TextArea tArealabel;
	
	public WayptView(UltiGPX main) {
		super();
		this.main = main;
		setLayout(new GridBagLayout());
		
    	// sets up the gridbag for the text box
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 20.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,10,0,9);
        
        
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("GPX FILE");
        createNodes(top);
        tree = new JTree(top);
        
        wtrPanel = new JScrollPane(tree);
        
        add(wtrPanel,c);
        		
		if (main.file == null)
			return;
	    
		waypoint = main.file.waypoints();
		

    	
		this.setPreferredSize(new Dimension(100, 100));
		repaint();
	}

	private void createNodes(DefaultMutableTreeNode top) {
		    DefaultMutableTreeNode category1 = null;
		    DefaultMutableTreeNode category2 = null;
		    DefaultMutableTreeNode category3 = null;
		    
		    category1 = new DefaultMutableTreeNode("Waypoints");
		    top.add(category1);
		    category2 = new DefaultMutableTreeNode("Tracks");
		    top.add(category2);
		    category3 = new DefaultMutableTreeNode("Routes");
		    top.add(category3);
		
		
	}


}


