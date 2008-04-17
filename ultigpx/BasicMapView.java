
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
    BasicMapView me = this;
    
    int mouseX;
    int mouseY;
    
    boolean forcecolor;
    Color linecolor;
    Color pointcolor;
    float linesize;
    float pointsize;
    
    
    
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
    
    
    protected void setLineColor(Color c)
    {
        if (c != null && !forcecolor)
            g.setPaint(c);
        else
            g.setPaint(linecolor);
    }
    
    protected void setPointColor(Color c)
    {
        if (c != null && !forcecolor)
            g.setPaint(c);
        else
            g.setPaint(pointcolor);
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
        
        g.setFont(new Font("Arial", 0, FONT_SIZE));
        
        render();
        
    }
    
    
    protected void renderLegend()
    {
        
        int top  = 0;
        int left = 0;
        int right= getWidth();
        
        Point2D topleft     = inverseproject(left,  getHeight()/2.0);
        Point2D topright    = inverseproject(right, getHeight()/2.0);
        
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
        
        //System.out.printf("%d %d %d\n", sx,ex,sy);
        
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
        
        
        
        
        // render selected wps so they have label priority
        forcecolor  = true;
        
        linecolor   = SELECTED_COLOR;
        pointcolor  = SELECTED_COLOR;
        
        linesize    = 5.0f;
        pointsize   = 2*WAYPOINT_SIZE;
        
        g.setStroke(new BasicStroke(5.0f));
        for (Waypoint i : main.selected.waypoints())
            render(i);
        for (Route rt : main.selected.routes())
        {
            render(rt, null);
            for (Waypoint i : rt)
                render(i);
        }
        for (Track tk : main.selected.tracks())
            render(tk);
        
        // render tracks and routes
        
        forcecolor  = false;
        
        linecolor   = TRACK_COLOR;
        pointcolor  = POINT_COLOR;
        
        linesize    = LINE_SIZE;
        pointsize   = POINT_SIZE;
        
        
        g.setStroke(new BasicStroke(2.0f));
        
        for (Track i : file.tracks())
            if (i.enabled)
                render(i);
            
            
        linecolor   = ROUTE_COLOR;
        pointcolor  = WAYPOINT_COLOR;
        for (Route i : file.routes())
            if (i.enabled)
                render(i, i.getColor());
        
        
        // render all waypoints
        
        pointcolor  = WAYPOINT_COLOR;
        pointsize   = WAYPOINT_SIZE;
        for (Route rt : file.routes())
            if (rt.enabled)
                for (Waypoint i : rt)
                    render(i);
        
        for (Waypoint i : file.waypoints())
            if (i.enabled)
                render(i);
        
        
    }
    
    
    protected void render(Track tk)
    {
        for (TrackSegment i : tk.getArray())
        {
            render(i, tk.getColor());
        }
    }
    
    protected void render(ArrayList<Waypoint> ts, Color c)
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
        
        setLineColor(c);
        g.draw(p);
        
        
        double x = Math.pow(2.0, (int)(Math.log(10.0/scale)/Math.log(2.0)));
        double d = 0;
        
        Waypoint i0 = ts.get(0);
        render(i0);
        
        Point2D p1 = new Point2D.Double(i0.lon, i0.lat);
        Point2D p2;
        
        if (ts instanceof Route) return;
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
        
        Ellipse2D e = new Ellipse2D.Double(p.getX() - pointsize/2.0,
                                            p.getY() - pointsize/2.0,
                                            pointsize,
                                            pointsize);
        
        
        setPointColor(i.getColor());
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
    
    /**
     * I had to pull this out of the EventHandler so it
     * could be reimplemented by subclasses. (If this is wrong
     * feel free to fix as you see fit.)
     */
    State state;
    Object data;
    
    public void select(int x, int y) {

    	if (file == null)
    		return;

    	Point2D click = new Point2D.Double(x,y);

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
    			//main.view.select(min_w);
    			main.selected.select(min_w);

    			state = State.TK_RT_WP;
    			return;
    		}
    	}



    	min_r  = getRoute(click);
    	min_t  = getTrack(click);
    	min_w  = getWaypoint(click);
    	UGPXData min_tr = min_r != null ? min_r : min_t;


    	if (min_tr != null)
    	{
    		//main.view.select(min_tr);
    		main.selected.select(min_tr);
    		state = State.TK_RT;
    		data = min_tr;
    	}
    	else
    	{
    		//main.view.select(min_w);
    		main.selected.select(min_w);
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

    
    public enum State  { MAIN, WP, TK_RT, TK_RT_WP, DR_WP, ADD }
    
    class EventHandler
        implements  MouseListener,
                    MouseMotionListener, 
                    MouseWheelListener,
                    KeyListener
    {
        int sx;
        int sy;
        
        DragOperation op;
        
        public EventHandler()
        {
            super();
            state = State.MAIN;
            data  = null;
        }
        
        
        public void mouseClicked(MouseEvent e) 
        {
        	select(e.getX(), e.getY());
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
            //System.out.println("\nPRESS "+e);
            sx = e.getX();
            sy = e.getY();
            
            
            //System.out.println(state);
            if (state == State.WP || state == State.TK_RT_WP)
            {
                
                Point2D click = new Point2D.Double(e.getX(), e.getY());
                Waypoint wp = null;
                
                // REFACTOR UGPXDATA
                if (data instanceof Waypoint)   wp = getWaypoint(click);
                if (data instanceof Track)      wp = getTrackPoint((Track)data, click);
                if (data instanceof Route)      wp = getRoutePoint((Route)data, click);
                
                if (wp == null) return;
                
                if (wp == main.selected.get())
                {
                    state = State.DR_WP;
                    op = new DragOperation(wp);
                    op.setStart(wp);
                }
            }
        }
        
        public void mouseReleased(MouseEvent e)
        {
            //System.out.println("\nRELEASE "+e);
            
            if (state == State.DR_WP)
            {
            	// this is sort of a hack but at least it works with elevation now too
            	if ((me instanceof GridMapView) || (me instanceof PlainMapView))
            	{
            		Point2D pt = inverseproject(new Point2D.Double(e.getX(), e.getY()));
            		op.setEnd(pt.getX(), pt.getY(), null);
            	}
            	else if (me instanceof ElevationView)
            	{
            		Point2D pt = ((ElevationView)me).inverseproject(new Point2D.Double(e.getX(), e.getY()));
            		op.setEnd(pt.getX(), null, pt.getY());
            	}
                main.addOperation(op);
                //System.out.println("Added drag op");
            }
            
            if (data instanceof Track) state = State.TK_RT_WP;
            if (data instanceof Route) state = State.TK_RT_WP;
            if (data instanceof Waypoint) state = State.WP;
            
        }
        
        public void mouseDragged(MouseEvent e)
        {
            //System.out.println("\nDRAG "+e);
            
            
            if (state == State.DR_WP)
            {
                movePoint(e.getX(), e.getY());
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
































