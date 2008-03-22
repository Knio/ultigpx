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

    private List<Waypoint>  waypoint;
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
        this.setPreferredSize(new Dimension(100, 300));
		repaint();
	}
	
	void fill() {
		removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("GPX FILE");
        createNodes(top);
        tree = new JTree(top);
        wtrPanel = new JScrollPane(tree);
        add(wtrPanel,c);
	}



	private void createNodes(DefaultMutableTreeNode top) {
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
				for(int i = 0;i<count;i++){
					System.out.println(" waypt = " + waypoint.get(i).getName()+ "count " + i);
					 info = new DefaultMutableTreeNode(waypoint.get(i).getName());
				        category.add(info);

				       
				}
				category = new DefaultMutableTreeNode("Tracks");
				DefaultMutableTreeNode seg = null;
				track = main.file.tracks();
				count = track.size();
				for(int i = 0;i<count;i++){
					System.out.println(" track = " + track.get(i)+ " count " + i);
					 info = new DefaultMutableTreeNode("track"+i+1);
					/* if(track.get(i) ){
						 seg = new DefaultMutableTreeNode();
					 }*/
				
				        category.add(info);

				       
				}
				category = new DefaultMutableTreeNode("Routes");
				
				route = main.file.routes();
				System.out.println("route info " + route.size());
				count = route.size();
				for(int i = 0;i<count;i++){
					System.out.println(" route = " + route.get(i)+ " count " + i);
					 info = new DefaultMutableTreeNode("route"+i+1);
					/* if(track.get(i) ){
						 seg = new DefaultMutableTreeNode();
					 }*/
				
				        category.add(info);

				       
				}
				
			}
		    
		    
		
	}

}



