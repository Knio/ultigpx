
package ultigpx;

import java.io.*;
import org.jdom.*; 
import org.jdom.input.*;

public class GPXImporter
{
    
    static UGPXFile importGPX(String filename) throws JDOMException, IOException
    {
        Document d = new SAXBuilder().build(new File(filename));
        
        
        
        
        return null;
    }
    
}