package ultigpx;


import java.awt.*;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class WayptView extends JPanel {
	
	UltiGPX         main;
	UGPXFile        file;

    private List<InfoView>  waypoint;
    private List<Track>     track;
    private List<Route>     route;
    protected JList  waypt_list;
    private JScrollPane wtrPanel;
    
	
	MainView parent;
	//UltiGPX main;
	TextArea tArealabel;
	
	public WayptView(MainView view) {
		super();
		parent = view;
		waypoint = parent.main.file.waypoints();
		
		   InstallData[] waypts = new InstallData[waypoint.size()];
		    
		    for(int i = 0;i<waypoint.size();i++)

		        waypts[i]= new InstallData(waypoint.get(i).getName());
		    
	
		    waypt_list = new JList(waypts);

		   CheckListCellRenderer renderer = new CheckListCellRenderer();

		    waypt_list.setCellRenderer(renderer);

		    waypt_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	    

		setLayout(new GridBagLayout());
		
    	// sets up the gridbag for the text box
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 20.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,10,0,9);
        
        wtrPanel = new javax.swing.JScrollPane();
        
        if(!waypoint.isEmpty()){
        	
        	       
        	        	wtrPanel.getViewport().add(waypt_list);
        	        	add(wtrPanel,c);
        	        
        }
    	
		this.setPreferredSize(new Dimension(100, 100));
		repaint();
	}

//	public WayptView(UltiGPX main2) {
		// TODO Auto-generated constructor stub
	//}

}
class InstallData

{

  protected String m_name;

  protected boolean m_selected;

  public InstallData(String name) {

    m_name = name;

    m_selected = false;

  }

  public String getName() { return m_name; }

  //public int getSize() { return m_size; }

  public void setSelected(boolean selected) {

    m_selected = selected;

  }

  public void invertSelected() { m_selected = !m_selected; }

  public boolean isSelected() { return m_selected; }

  public String toString() { return m_name; }
  
 }
class CheckListCellRenderer extends JCheckBox

implements ListCellRenderer

{

 protected static Border m_noFocusBorder =

   new EmptyBorder(1, 1, 1, 1);

 public CheckListCellRenderer() {

   super();

   setOpaque(true);

   setBorder(m_noFocusBorder);

 }

 public Component getListCellRendererComponent(JList list,

  Object value, int index, boolean isSelected, boolean cellHasFocus)

 {

   setText(value.toString());

   setBackground(isSelected ? list.getSelectionBackground() :

     list.getBackground());

   setForeground(isSelected ? list.getSelectionForeground() :

     list.getForeground());

   InstallData data = (InstallData)value;

     setSelected(data.isSelected());

   setFont(list.getFont());

   setBorder((cellHasFocus) ?

   UIManager.getBorder("List.focusCellHighlightBorder")

     : m_noFocusBorder);

   return this;

 }

}

