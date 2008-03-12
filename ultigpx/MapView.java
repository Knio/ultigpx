
package ultigpx;

import java.awt.*;
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;


public class MapView extends JPanel
{
    UltiGPX         main;
    UGPXFile        file;
    EventHandler    evt;
    Waypoint        selected;
    
    java.util.List<Waypoint> entities;
    
    double lon;
    double lat;
    double scale;
    
    static final int        WAYPOINT_SIZE   = 20;
    static final double     MAX_SCALE       = 1000.0;
    static final double     MIN_SCALE       = 1.0;
    
    public MapView(UltiGPX main)
    {
        super();
        this.main = main;
        evt = new EventHandler();
        
        addMouseListener        (evt);
        addMouseMotionListener  (evt);
        addMouseWheelListener   (evt);
        addKeyListener          (evt);
        
        entities = new ArrayList<Waypoint>();
        load();
    }
    
    protected void load()
    {
        if (file == main.file)
            return;
        
        file = main.file;
        
        entities.clear();
        
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
    
    protected void fill()
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
        
        lon = (max_lon + min_lon) / 2;
        lat = (max_lat + min_lat) / 2;
        
        scale(0.9 * getWidth() / (max_lon - min_lon));
        
    }
    
    protected void scroll(double lon, double lat)
    {
        this.lon = lon;
        this.lat = lat;
        
        repaint();
    }
    
    protected void scrollBy(double lon, double lat)
    {
        scroll(lon+this.lon, lat+this.lat);
    }
    
    protected void scrollByScreen(int x, int y)
    {
        double lon =-x / scale;
        double lat = y / scale * Math.cos(Math.PI*this.lat/180);
        scrollBy(lon, lat);
    }
    
    protected void scale(double s)
    {
        s = Math.min(s, MAX_SCALE);
        s = Math.max(s, MIN_SCALE);
        scale = s;
        repaint();
        
        //System.out.println(scale);
    }
    
    protected void scaleBy(double s)
    {
        scale(scale*s);
    }
    
    protected void select(Waypoint wp)
    {
        System.out.println("Select");
        selected = wp;
        repaint();
    }
    
    protected Point2D project(Waypoint wp)
    {
        
        double x = wp.lon - lon;
        double y = Math.log(Math.tan(Math.PI*(0.25 + wp.lat/360))) -
                   Math.log(Math.tan(Math.PI*(0.25 +    lat/360)));
        
        y *= 180/Math.PI;
        
        x *= scale;
        y *= scale; 
        
        x += getWidth() /2.0;
        y -= getHeight()/2.0;
        
        return new Point2D.Double(x, -y);
    }
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        load();
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setPaint(Color.BLACK);
        
        g2d.draw(new Rectangle2D.Double(10, 10, getWidth()-20, getHeight()-20));
        
        Rectangle r = getBounds();
        r.grow(-20,-20);
        g2d.draw(r);
        
        render(g2d);
        
    }
    
    protected void render(Graphics2D g)
    {
        
        g.setPaint(Color.BLUE);
        
        for (Waypoint i : entities)
            render(g, i);
        
        
        
        if (selected != null)
            render(g, selected);
        
    }
    
    protected void render(Graphics2D g, Waypoint i)
    {
        Point2D  p = project(i);
        
        Ellipse2D e = new Ellipse2D.Double(p.getX() - WAYPOINT_SIZE/2,
                                           p.getY() - WAYPOINT_SIZE/2,
                                           WAYPOINT_SIZE,
                                           WAYPOINT_SIZE);
        
        g.setPaint(Color.BLACK);
        
        if (i == selected)
            g.setPaint(Color.RED);
        
        g.fill(e);
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
            System.out.println(e);
            
            Point2D click = new Point2D.Double(e.getX(), e.getY());
            Waypoint min  = null;
            double min_d  = (WAYPOINT_SIZE/2+2)*(WAYPOINT_SIZE/2+2);
            
            for (Waypoint i : entities)
            {
                double t = click.distanceSq(project(i));
                if (t < min_d)
                {
                    min_d = t;
                    min   = i;
                }
            }
            
            if (min == null) return;
            
            select(min);
        }
        
        public void mouseEntered(MouseEvent e) 
        {
            System.out.println(e);
        }
        
        public void mouseExited(MouseEvent e) 
        {
            System.out.println(e);
        }
        
        public void mousePressed(MouseEvent e) 
        {
            System.out.println(e);
            sx = e.getX();
            sy = e.getY();
        }
        
        public void mouseReleased(MouseEvent e)
        {
            System.out.println(e);
        }
        
        public void mouseDragged(MouseEvent e)
        {
            System.out.println(e);
            
            scrollByScreen(e.getX() - sx, (e.getY() - sy));
            
            sx = e.getX();
            sy = e.getY();
        }
        
        public void mouseMoved(MouseEvent e)
        {
            System.out.println(e);
        }
        
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            //System.out.println(e);
            scaleBy(1.0 + (e.getWheelRotation() * 0.1));
        }
        
        
        // these don't work
        public void keyPressed(KeyEvent e) 
        {
            System.out.println(e);
            switch (e.getKeyCode())
            {
                case (KeyEvent.VK_ESCAPE):
                    System.exit(0);
            }
        }
        
        public void keyReleased(KeyEvent e) 
        {
            System.out.println(e);
        }
        
        public void keyTyped(KeyEvent e)
        {
            System.out.println(e);
        }
        
    }
    
}
































