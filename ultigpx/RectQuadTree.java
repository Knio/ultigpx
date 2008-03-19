
package ultigpx;

import java.util.*;
import java.awt.geom.*;

public class RectQuadTree
{
    final static int MAX_LEAF = 4;
    
    Rectangle2D rect;
    ArrayList<Rectangle2D> rects;
    RectQuadTree c1;
    RectQuadTree c2;
    RectQuadTree c3;
    RectQuadTree c4;
    
    public RectQuadTree(Rectangle2D rect) 
    {
        this.rect = rect;
        rects = new ArrayList<Rectangle2D>();
        
        
    }
    
    void node()
    {
        double x1 = rect.getX();
        double x2 = rect.getCenterX();
        double x3 = rect.getMaxX();
        
        double y1 = rect.getY();
        double y2 = rect.getCenterY();
        double y3 = rect.getMaxY();
        
        c1 = new RectQuadTree(new Rectangle2D.Double(x1, y1, x2, y2));
        c2 = new RectQuadTree(new Rectangle2D.Double(x2, y1, x3, y2));
        c3 = new RectQuadTree(new Rectangle2D.Double(x1, y2, x2, y3));
        c4 = new RectQuadTree(new Rectangle2D.Double(x2, y2, x3, y3));
        
        for (int i = rects.size()-1; i>=0; i--)
        {
            Rectangle2D r = rects.get(i);
            System.out.println(r);
            if (r.contains(rect)) continue;
            c1.add(r);
            c2.add(r);
            c3.add(r);
            c4.add(r);
            rects.remove(i);
        }
    }
    
    public void add(Rectangle2D r)
    {
        if (!rect.intersects(r)) return;
        if (c1 == null)
        {
            rects.add(r);
            if (rects.size() > MAX_LEAF) node();
            return;
        }
        c1.add(r);
        c2.add(r);
        c3.add(r);
        c4.add(r);
        
    }
    
    public boolean intersects(Rectangle2D r)
    {
        if (!rect.intersects(r)) return false;
        for (Rectangle2D i : rects)
        {
            if (i.intersects(r)) return true;
        }
        if (c1 == null) return false;
        if (c1.intersects(r)) return true;
        if (c2.intersects(r)) return true;
        if (c3.intersects(r)) return true;
        if (c4.intersects(r)) return true;
        return false;
        
    }
    
}