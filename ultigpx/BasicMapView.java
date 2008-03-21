
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;


public abstract class BasicMapView extends MapView
{
    EventHandler    evt;
    Graphics2D      g;
	
	int mouseX;
	int mouseY;
    
    ArrayList<Rectangle2D> labelhints;
    RectQuadTree labelhints2;
    
    public BasicMapView(UltiGPX main)
    {
        super(main);
        evt = new EventHandler();
        
        addMouseListener        (evt);
        addMouseMotionListener  (evt);
        addMouseWheelListener   (evt);
        addKeyListener          (evt);
        
        entities = new ArrayList<Waypoint>();
        labelhints = new ArrayList<Rectangle2D>();
        
        // North arrow
        JLabel north = new JLabel("\u2191 North \u2191");
        north.setHorizontalAlignment(JLabel.RIGHT);
        add(north);
        
        load();
        fill();
        repaint();
    }
    
    protected void select(Waypoint wp)
    {
        selected = wp;
        main.view.select(wp);
        repaint();
    }
    
    protected void select(Track tk)
    {
        selected = tk;
        main.view.select(tk);
        repaint();
    }
    
    protected void select(Route rt)
    {
        selected = rt;
        main.view.select(rt);
        repaint();
    }
    
    protected void setColor(Color c1, Color c2)
    {
        if (c1 != null)
            g.setPaint(c1);
        else
            g.setPaint(c2);
    }
    
    public void paintComponent(Graphics gfx)
    {
        super.paintComponent(gfx);
        load();
        
        g = (Graphics2D)gfx;
        
        //g.setPaint(Color.BLACK);
        
        //g.draw(new Rectangle2D.Double(5, 5,   getWidth()-10, getHeight()-10));
        //g.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        
        if (file == null)
            return;
        
        render();
        
    }
    
    protected void render()
    {
        labelhints.clear();
        labelhints2 = new RectQuadTree(getVisibleRect());
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        
        g.setFont(new Font("Arial", 0, FONT_SIZE));
        
        
        // render selected so it has label priority
        if (selected != null)
        {
            g.setPaint(SELECTED_COLOR);
            g.setStroke(new BasicStroke(3.0f));
            render(selected);
        }
        
        
        // render tracks and routes
        g.setStroke(new BasicStroke(2.0f));
        
        for (Track i : file.tracks())
            if (i.enabled)
            {
                setColor(i.color, TRACK_COLOR);
                render(i);
            }
        for (Route i : file.routes())
            if (i.enabled)
            {
                setColor(i.color, ROUTE_COLOR);
                render(i);
            }
        
        
        // render all waypoints
        g.setColor(WAYPOINT_COLOR);
        g.setStroke(new BasicStroke(1.0f));
        
        
        for (Track tk : file.tracks())
            if (tk.enabled)
                for (TrackSegment ts : tk)
                    for (Waypoint i : ts)
                        render(i);
        
        for (Route rt : file.routes())
            if (rt.enabled)
                for (Waypoint i : rt)
                    render(i);
        
        for (Waypoint i : file.waypoints())
            if (i.enabled)
            {
                setColor(i.color, WAYPOINT_COLOR);
                render(i);
            }
        
        // rerender selected so that it is on top
        if (selected != null)
        {
            g.setPaint(SELECTED_COLOR);
            g.setStroke(new BasicStroke(3.0f));
            render(selected);
        }
    }
    
    
    protected void render(Object o)
    {
        if (o instanceof Route)     render((Route)o);
        if (o instanceof Track)     render((Track)o);
        if (o instanceof Waypoint)  render((Waypoint)o);
    }
    
    protected void render(Track tk)
    {
        for (TrackSegment i : tk)
            render(i);
    }
    
    protected void render(ArrayList<Waypoint> ts)
    {
        if (ts.size() == 0) return;
        GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD, ts.size()+1);
        Point2D t = project(ts.get(0));
        
        p.moveTo((float)t.getX(), (float)t.getY());
        
        for (Waypoint i : ts)
        {
	    if (i.enabled) {
            	t = project(i);
            	p.lineTo((float)t.getX(), (float)t.getY());
	    }
        }
        
        g.draw(p);
    }
    
    
    protected void render(Waypoint i)
    {
		if (i.enabled) {
			Point2D  p = project(i);
        
			Ellipse2D e = new Ellipse2D.Double(p.getX() - WAYPOINT_SIZE/2,
												p.getY() - WAYPOINT_SIZE/2,
												WAYPOINT_SIZE,
												WAYPOINT_SIZE);
        
			renderLabel(i.getName(), p);
			g.fill(e);
		}
    }
    
    protected void renderLabel(String name, Point2D p)
    {
        double x = p.getX() + WAYPOINT_SIZE;
        double y = p.getY() + WAYPOINT_SIZE;
        
        Rectangle2D r = new Rectangle2D.Double(x, y, name.length()*FONT_SIZE*4/3, FONT_SIZE);
        //*
        for (Rectangle2D t : labelhints)
        {
            if (r.intersects(t))
            {
                return;
            }
        }
        //*/
        
        //if (labelhints2.intersects(r)) return;
        //labelhints2.add(r);
        
        labelhints.add(r);
        g.drawString(name, (float)x, (float)y);
    }
    
    
    
    class EventHandler
        implements  MouseListener,
                    MouseMotionListener, 
                    MouseWheelListener,
                    KeyListener
    {
        int sx;
        int sy;
        
        public void mouseClicked(MouseEvent e) 
        {
            //System.out.println(e);
            if (file == null)
                return;
            
            Point2D click = new Point2D.Double(e.getX(), e.getY());
            
            Route       min_r  = null;
            Object      min_o  = null;
            Track       min_t  = null;
            Waypoint    min_w  = null;
            
            double min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            boolean first = true;
            
            Line2D line = new Line2D.Double(0,0,0,0);
            tk: for (Track tk : file.tracks())
            {
                if (!tk.enabled) continue;
                for (TrackSegment ts : tk)
                {
                    first = true;
                    for (Waypoint i : ts)
                    {
                        line.setLine(line.getP2(), project(i));
                        if (first)
                        {
                            first = false;
                            continue;
                        }
                        double t = line.ptSegDistSq(click);
                        if (t < min_d)
                        {
                            min_d = t;
                            min_o = tk;
                            //break tk;
                        }
                    }
                }
            }
            
            //min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            rt: for (Route rt : file.routes())
            {
                if (!rt.enabled) continue;
                first = true;
                for (Waypoint i : rt)
                {
                    line.setLine(line.getP2(), project(i));
                    if (first)
                    {
                        first = false;
                        continue;
                    }
                    double t = line.ptSegDistSq(click);
                    if (t < min_d)
                    {
                        min_d = t;
                        min_o = rt;
                        //break rt;
                    }
                }
            }
            
            min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            
            for (Waypoint i : file.waypoints())
            {
                if (!i.enabled) continue;
                double t = click.distanceSq(project(i));
                if (t < min_d)
                {
                    min_d = t;
                    min_w = i;
                }
            }
            
            //if (min_t != null) select(min_t);
            //if (min_r != null) select(min_r);
            
            if (min_o != null)
            {
                if (min_o instanceof Route) select((Route)min_o);
                if (min_o instanceof Track) select((Track)min_o);
            }
            if (min_w != null) select(min_w);
            
        }
        
        public void mouseEntered(MouseEvent e) 
        {
            //System.out.println(e);
        }
        
        public void mouseExited(MouseEvent e) 
        {
            //System.out.println(e);
        }
        
        public void mousePressed(MouseEvent e) 
        {
            //System.out.println(e);
            sx = e.getX();
            sy = e.getY();
        }
        
        public void mouseReleased(MouseEvent e)
        {
            //System.out.println(e);
        }
        
        public void mouseDragged(MouseEvent e)
        {
            //System.out.println(e);
            
            scrollByScreen(e.getX() - sx, (e.getY() - sy));
            
            sx = e.getX();
            sy = e.getY();
        }
        
        public void mouseMoved(MouseEvent e)
        {
            mouseX = e.getX();
			mouseY = e.getY();
            
        }
        
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            //System.out.println(e);
            int x = getWidth() /2 - e.getX();
            int y = getHeight()/2 - e.getY();
            
            scrollByScreen( x, y);
            scaleBy(1.0 + (e.getWheelRotation() * 0.1));
            scrollByScreen(-x,-y);
        }
        
        
        // these don't work
        public void keyPressed(KeyEvent e) 
        {
            //System.out.println(e);
        }
        
        public void keyReleased(KeyEvent e) 
        {
            //System.out.println(e);
        }
        
        public void keyTyped(KeyEvent e)
        {
            //System.out.println(e);
        }
		
        
    }
    
}
































