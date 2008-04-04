package ultigpx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SearchResult extends JDialog 
{
	JFrame frame;
	UltiGPX main;
	
	public SearchResult(MainView frame)
	{
		super(frame, "Name Conflicts Detected");		
		//gets the pointer to the main class from the calling frame.
		// ugly yes, but hey this is iterative. We can refactor later.
		this.main = frame.main;
		this.frame = frame;
		
		checkForConflicts();
	}
	/**
	 * checks the file for conflicts and opens window if necessary.
	 */
	void checkForConflicts()
	{
		Vector conflicts = getConflicts();
		if(conflicts != null)
			display(conflicts);
	}
	/**
	 * Opens up the actual list of conflicters
	 */
	void display(Vector conflicters)
	{
		JLabel label = new JLabel("<html><p align=center>"
			    + "Name conflicts have been detected.<br>");
			label.setHorizontalAlignment(JLabel.CENTER);
			Font font = label.getFont();
			label.setFont(label.getFont().deriveFont(font.PLAIN,
			                                         14.0f));
			
			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        setVisible(false);
			        dispose();
			    }
			});
			
			String[] data = {"one", "two", "three","four", "five","six"};
			JList list = new JList(conflicters);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollpane = new JScrollPane(list);

			JPanel closePanel = new JPanel();
			closePanel.setLayout(new BoxLayout(closePanel,
			                                   BoxLayout.LINE_AXIS));
			closePanel.add(Box.createHorizontalGlue());
			closePanel.add(closeButton);
			closePanel.setBorder(BorderFactory.
			    createEmptyBorder(0,0,5,5));

			JPanel contentPane = new JPanel(new BorderLayout());
			contentPane.add(label, BorderLayout.NORTH);
			contentPane.add(scrollpane, BorderLayout.CENTER);
			contentPane.add(closePanel, BorderLayout.PAGE_END);
			contentPane.setOpaque(true);
			this.setContentPane(contentPane);

			//Show it.
			this.setSize(new Dimension(300, 300));
			this.setLocationRelativeTo(frame);
			this.setVisible(true);
	}
	
	
	/**
	 * returns a collection of the conflicting points.
	 * @return
	 */
	Vector getConflicts()
	{
		//a little bit fancy, since I don't wan't to add duplicate
		//objects to the output collection of conflicters.
		// this way I push the "original" guy over and over
		// again into a hash table,And he only is added to the list
		// the first time.
		Hashtable<String,Object> conf = new Hashtable<String,Object>();
		Hashtable<String,Object> added = new Hashtable<String,Object>();
		Vector<String> list = new Vector<String>();
		UGPXFile file = main.file;
		
		//there is no data to check!
		if(file == null)
			return null;
		
		for(Waypoint wp : file.waypoints()){
			String name = wp.getName();
			Object existing = conf.get(name); 
			if(existing != null) {	
				list.add(name + " (waypoint)");
				added.put(name, existing);
			} else {
				conf.put(name, wp);
			}
		}
		
		for(Track tr : file.tracks()){
			String name = tr.getName();
			Object existing = conf.get(name); 
			if(existing != null) {	
				list.add(name + " (track)");
				added.put(name, existing);
			} else {
				conf.put(name, tr);
			}
		}
		
		for(Route rt : file.routes()){
			String name = rt.getName();
			Object existing = conf.get(name); 
			if(existing != null) {	
				list.add(name + " (route)");
				added.put(name, existing);
			} else {
				conf.put(name, rt);
			}
		}
		
		//add in all the "original" conflicters
		for(Object ob : added.values()) {
			if(ob.getClass() == Waypoint.class)
				list.add(((Waypoint)ob).getName() + " (waypoint)");
			if(ob.getClass() == Track.class)
				list.add(((Waypoint)ob).getName() + " (track)");
			if(ob.getClass() == Route.class)
				list.add(((Waypoint)ob).getName() + " (route)");
		}
		
		return list;
	}
}