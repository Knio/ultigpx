package ultigpx;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Installer
{
    
    public static void install() throws IOException
    {
        
        ZipFile f = new ZipFile("UltiGPX.jar");
        
        for (Enumeration en = f.entries(); en.hasMoreElements();)
        {
            ZipEntry e = (ZipEntry)en.nextElement();
            
            if (!e.getName().startsWith("resources"))
                  continue;
                  
            if (e.getName().indexOf(".svn") != -1)
                continue;
            
            String fname = e.getName().substring(10);
            
            System.out.println("installing "+ fname);
            
            if(e.isDirectory())
            {
                (new File(fname)).mkdir();
                continue;
            }
            
            InputStream  i = f.getInputStream(e);
            OutputStream o = new FileOutputStream(fname);
            copy(i, new BufferedOutputStream(o));
            
        }
        
    }
    
    public static void copy(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[102400];
        int len;
        
        while((len = in.read(buffer)) >= 0)
          out.write(buffer, 0, len);
        
        in.close();
        out.close();
    }
    
    
}