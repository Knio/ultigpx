
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

    /**
    * Creates a new BasicMapView
    * @param UltiGPX main
    */
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
        //add(north);
        
        load();
        fill();
        repaint();
    }
    
    protected void selectEvent(Waypoint wp)
    {
        selected = wp;
        //repaint();
    }
    
    protected void selectEvent(Track tk)
    {
        selected = tk;
        //repaint();
    }
    
    protected void selectEvent(Route rt)
    {
        selected = rt;
        //repaint();
    }
    
    
    
    protected void setColor(Color c1, Color c2)
    {
        if (c1 != null)
            g.setPaint(c1);
        else
            g.setPaint(c2);
    }
    
    /**
    * Renders this map
    * @param Graphics gfx
    */
    public void paintComponent(Graphics gfx)
    {
        super.paintComponent(gfx);
        load();
        
        g = (Graphics2D)gfx;
        
        if (file == null)
            return;
        
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        g.setColor(Color.WHITE);
        g.fill(getVisibleRect());
        
        
        
        render();
        
    }
    
    
    protected void renderLegend()
    {
        
        int top  = 0;
        int left = 0;
        int right= getWidth();
        
        Point2D topleft     = inverseproject(left,  top);
        Point2D topright    = inverseproject(right, top);
        
        double lat1 = Math.toRadians(topleft.getY());
        double lon1 = Math.toRadians(topleft.getX());
        double lon2 = Math.toRadians(topright.getX());
        double radius = 6371000;			// Approx radius of the earth in meters
        
        // Spherical law of cosines
        double dist1    = Math.acos(Math.sin(lat1) * Math.sin(lat1)
                        + Math.cos(lat1) * Math.cos(lat1) * Math.cos(lon2-lon1)) * radius;
        
        double dist2    = Math.pow(10, (int)Math.log10(dist1/1.5));
        
        g.setColor(Color.BLACK);
        
        int ex = right - 10;
        int sx = ex - (int)((dist2/dist1)*(right-left));
        int sy = 10;
        
        System.out.printf("%d %d %d\n", sx,ex,sy);
        
        g.drawLine(sx, sy, ex, sy);
        
        g.drawLine(sx, sy-5, sx, sy+5);
        g.drawLine(ex, sy-5, ex, sy+5);
        
        for (double i=sx;i<ex;i+=(ex-sx)/10.0)
        {
            g.drawLine((int)i, sy, (int)i, sy-5);
        }
        
        String s = "m";
        if (dist2 >= 1000)
        {
            dist2 /= 1000;
            s = "Km";
        }
        
        g.drawString(String.format("%.0f%s", dist2, s), sx+2, sy+10);
        
        
        
        
    }
    
    
    protected void render()
    {
        
        
        labelhints.clear();
        labelhints2 = new RectQuadTree(getVisibleRect());
        
        
        renderLegend();
        
        
        
        g.setFont(new Font("Arial", 0, FONT_SIZE));
        
        
        // render selected so it has label priority
        if (selected != null)
        {
            g.setPaint(SELECTED_COLOR);
            g.setStroke(new BasicStroke(5.0f));
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
        
        /*
        for (Track tk : file.tracks())
            if (tk.enabled)
                for (TrackSegment ts : tk)
                    for (Waypoint i : ts)
                        render(i);
        */
        
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
        //*
        if (selected != null)
        {
            g.setPaint(SELECTED_COLOR);
            g.setStroke(new BasicStroke(3.0f));
            render(selected);
        } //*/
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
            t = project(i);
            p.lineTo((float)t.getX(), (float)t.getY());
        }
        
        //setColor(null, TRACK_COLOR);
        g.draw(p);
        
        
        //setColor(null, WAYPOINT_COLOR);
        
        double x = Math.pow(2.0, (int)(Math.log(10.0/scale)/Math.log(2.0)));
        double d = 0;
        
        Waypoint i0 = ts.get(0);
        render(i0);
        
        Point2D p1 = new Point2D.Double(i0.lon, i0.lat);
        Point2D p2;
        
        for (Waypoint i : ts)
        {
            p2 = new Point2D.Double(i.lon, i.lat);
            d += p2.distance(p1);
            p1 = p2;
            if (d > x)
            {
                d %= x;
                render(i);
            }
        }
        
        
    }
    
    
    protected void render(Waypoint i)
    {
        if (!i.enabled) return;
        Point2D  p = project(i);
        
        Ellipse2D e = new Ellipse2D.Double(p.getX() - WAYPOINT_SIZE/2,
                                            p.getY() - WAYPOINT_SIZE/2,
                                            WAYPOINT_SIZE,
                                            WAYPOINT_SIZE);
        
        
        renderLabel(i.getName(), p);
        g.fill(e);
    }
    
    protected void renderLabel(String name, Point2D p)
    {
        if (name.length()==0) return;
        double x = p.getX() + WAYPOINT_SIZE;
        double y = p.getY() + WAYPOINT_SIZE;
        
        Rectangle2D r = new Rectangle2D.Double(x, y, name.length()*FONT_SIZE*2/3, FONT_SIZE);
        //*
        for (Rectangle2D t : labelhints)
            if (r.intersects(t))
                return;
        //*/
        
        //if (labelhints2.intersects(r)) return;
        //labelhints2.add(r);
        
        labelhints.add(r);
        g.drawString(name, (float)x, (float)y);
    }
    
    
    public enum State  { MAIN, WP, TK_RT, TK_RT_WP, DR_WP, ADD }
    
    class EventHandler
        implements  MouseListener,
                    MouseMotionListener, 
                    MouseWheelListener,
                    KeyListener
    {
        int sx;
        int sy;
        
        
        State state;
        Object data;
        DragOperation op;
        
        public EventHandler()
        {
            super();
            state = State.MAIN;
            data  = null;
        }
        
        
        public void mouseClicked(MouseEvent e) 
        {
            System.out.println("\nCLICK "+e);
            if (file == null)
                return;
            
            Point2D click = new Point2D.Double(e.getX(), e.getY());
            
            Route       min_r  = null;
            Track       min_t  = null;
            Waypoint    min_w  = null;
            
            if (state == State.TK_RT || state == State.TK_RT_WP)
            {
                // REFACTOR FOR UGPXDATA
                if (data instanceof Track) min_w = getTrackPoint((Track)data, click);
                if (data instanceof Route) min_w = getRoutePoint((Route)data, click);
                
                if (min_w != null)
                {
                    main.view.select(min_w);
                    state = State.TK_RT_WP;
                    return;
                }
            }
            
            
            
            min_r  = getRoute(click);
            min_t  = getTrack(click);
            min_w  = getWaypoint(click);
            Object min_tr = min_r != null ? min_r : min_t;
            
            
            if (min_tr != null)
            {
                main.view.select(min_tr);
                state = State.TK_RT;
                data = min_tr;
            }
            else
            {
                main.view.select(min_w);
                if (min_w == null)
                {
                    state = State.MAIN;
                    data  = null;
                }
                else
                {
                    state = State.WP;
                    data  = min_w;
                }
            }
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
            System.out.println("\nPRESS "+e);
            sx = e.getX();
            sy = e.getY();
            
            
            System.out.println(state);
            System.out.println(selected);
            if (state == State.WP || state == State.TK_RT_WP)
            {
                
                Point2D click = new Point2D.Double(e.getX(), e.getY());
                Waypoint wp = null;
                // REFACTOR UGPXDATA
                if (data instanceof Waypoint) wp = getWaypoint(click);
                if (data instanceof Track) wp = getTrackPoint((Track)data, click);
                if (data instanceof Route) wp = getRoutePoint((Route)data, click);
                
                System.out.println(wp);
                System.out.println(selected);
                System.out.println(wp==(Waypoint)selected);
                if (wp == (Waypoint)selected)
                {
                    state = State.DR_WP;
                    op = new DragOperation(wp);
                    op.setStart(new Point2D.Double(wp.lon, wp.lat));
                }
            }
        }
        
        public void mouseReleased(MouseEvent e)
        {
            System.out.println("\nRELEASE "+e);
            
            if (state == State.DR_WP)
            {
                op.setEnd(inverseproject(new Point2D.Double(e.getX(), e.getY())));
                main.addOperation(op);
                System.out.println("Added drag op");
            }
            
            if (data instanceof Track) state = State.TK_RT_WP;
            if (data instanceof Route) state = State.TK_RT_WP;
            if (data instanceof Waypoint) state = State.WP;
            
        }
        
        public void mouseDragged(MouseEvent e)
        {
            System.out.println("\nDRAG "+e);
            
            
            if (state == State.DR_WP)
            {
                Point2D click = new Point2D.Double(e.getX(), e.getY());
                Point2D world = inverseproject(click);
                
                Waypoint wp = (Waypoint)selected;
                wp.lon = world.getX();
                wp.lat = world.getY();
                
                repaint();
                return;
            }
            
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
































