
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;


public class BasicMapView extends MapView
{
    UGPXFile        file;
    EventHandler    evt;
    Object          selected;
    Graphics2D      g;
    
    ArrayList<Rectangle2D> labelhints;
    
    java.util.List<Waypoint> entities;
    
    
    
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
        
        load();
        repaint();
    }
    
    protected void load()
    {
        if (file == main.file)
            return;
        
        file = main.file;
        
        entities.clear();
        
        if (file == null)
            return;
        
        for (Waypoint i:file.waypoints())
            entities.add(i);
            
        for (Route r:file.routes())
            for (Waypoint i:r)
                entities.add(i);
            
        for (Track r:file.tracks())
            for (TrackSegment s:r)
                for (Waypoint i:s)
                    entities.add(i);
        
        fill();
        
    }
    
    public void fill()
    {
        if (entities.size()==0)
        {
            lon     = 0;
            lat     = 0;
            scale   = 1;
            return;
        }
        
        double max_lon = entities.get(0).lon;
        double max_lat = entities.get(0).lat;
        double min_lon = max_lon;
        double min_lat = max_lat;
        
        for (Waypoint i:entities)
        {
            max_lon = Math.max(max_lon, i.lon);
            max_lat = Math.max(max_lat, i.lat);
            min_lon = Math.min(min_lon, i.lon);
            min_lat = Math.min(min_lat, i.lat);
        }
        
        double lon = (max_lon + min_lon) / 2;
        double lat = (max_lat + min_lat) / 2;
        
        scroll(lon, lat);
        
        scale(0.9 * getWidth() / Math.abs((max_lon - min_lon)));
    }
    
    protected void scroll(double lon, double lat)
    {
        lon = Math.max(-180, lon);
        lon = Math.min( 180, lon);
        
        lat = Math.max(-80, lat);
        lat = Math.min( 80, lat);
        
        this.lon = lon;
        this.lat = lat;
        
        repaint();
    }
    
    protected void scrollBy(double lon, double lat)
    {
        scroll(lon+this.lon, lat+this.lat);
    }
    
    protected void scrollByScreen(double x, double y)
    {
        x = getWidth()  / 2.0 - x;
        y = getHeight() / 2.0 - y;
        
        Point2D p = new Point2D.Double(x, y);
        p = inverseproject(p);
        scroll(p.getX(), p.getY());
        
    }
    
    protected void scale(double s)
    {
        s = Math.min(s, MAX_SCALE);
        s = Math.max(s, MIN_SCALE);
        scale = s;
        repaint();
        
    }
    
    protected void scaleBy(double s)
    {
        scale(scale*s);
    }
    
    protected void select(Waypoint wp)
    {
        selected = wp;
        repaint();
    }
    
    protected void select(Track tk)
    {
        selected = tk;
        repaint();
    }
    
    protected void select(Route rt)
    {
        selected = rt;
        repaint();
    }
    
    // returns a screen coordinate from a world coordinate,
    // by applying the Mercator map projection, scale, and scroll
    protected Point2D project(Waypoint wp)
    {
        // http://en.wikipedia.org/wiki/Mercator_projection
        double x = wp.lon - lon;
        double y = Math.log(Math.tan(Math.PI*(0.25 + wp.lat/360))) -
                   Math.log(Math.tan(Math.PI*(0.25 +    lat/360)));
        
        y  = Math.toDegrees(y);
        
        x *= scale;
        y *= scale; 
        
        x += getWidth() /2.0;
        y -= getHeight()/2.0;
        
        return new Point2D.Double(x, -y);
    }
    
    // returns a world coordinate from a screen coordinate
    protected Point2D inverseproject(Point2D p)
    {
        double lon = p.getX();
        double lat =-p.getY();
        
        lon -= getWidth() /2.0;
        lat += getHeight()/2.0;
        
        lon /= scale;
        lat /= scale;
        
        lon = lon + this.lon;
        
        lat = Math.toRadians(lat);
        lat+= Math.log(Math.tan(Math.PI*(0.25 + this.lat/360)));
        lat = Math.atan(Math.sinh(lat));
        
        lat = Math.toDegrees(lat);
        
        return new Point2D.Double(lon, lat);
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
        
        g.setPaint(Color.BLACK);
        
        g.draw(new Rectangle2D.Double(5, 5,   getWidth()-10, getHeight()-10));
        g.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        
        if (file == null)
            return;
        
        render();
        
    }
    
    protected void render()
    {
        labelhints.clear();
        
        
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
        for (Rectangle2D t : labelhints)
        {
            if (r.intersects(t))
            {
                return;
            }
        }
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
            Track       min_t  = null;
            Waypoint    min_w  = null;
            
            double min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            boolean first = true;
            
            Line2D line = new Line2D.Double(0,0,0,0);
            tk: for (Track tk : file.tracks())
            {
                if (!tk.enabled) continue;
                for (TrackSegment ts : tk)
                    for (Waypoint i : ts)
                    {
                        line.setLine(line.getP2(), project(i));
                        if (first)
                        {
                            first = false;
                            continue;
                        }
                        double t = line.ptLineDistSq(click);
                        if (t < min_d)
                        {
                            min_d = t;
                            min_t = tk;
                            
                            break tk;
                        }
                    }
            }
            
            min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            rt: for (Route rt : file.routes())
            {
                if (!rt.enabled) continue;
                for (Waypoint i : rt)
                {
                    line.setLine(line.getP2(), project(i));
                    if (first)
                    {
                        first = false;
                        continue;
                    }
                    double t = line.ptLineDistSq(click);
                    if (t < min_d)
                    {
                        min_d = t;
                        min_r = rt;
                        
                        break rt;
                    }
                }
            }
            
            min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            
            for (Waypoint i : entities)
            {
                if (!i.enabled) continue;
                double t = click.distanceSq(project(i));
                if (t < min_d)
                {
                    min_d = t;
                    min_w = i;
                }
            }
            
            if (min_t != null) select(min_t);
            if (min_r != null) select(min_r);
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
            //System.out.println(e);
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
            switch (e.getKeyCode())
            {
                case (KeyEvent.VK_ESCAPE):
                    System.exit(0);
            }
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
































