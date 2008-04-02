package ultigpx;

import java.util.*;
import java.awt.Color;

public class GPXData extends ArrayList{
	public boolean enabled;
	public String name;
	public String desc;
	public Color color;
	
	public GPXData() {
		super();
	}
	
	/**
	 * Returns enabled status
	 * @return enabled
	 */
    public boolean getEnabled() {
        return enabled;
    }
    
    /**
	 * Sets enabled status
	 * @param boolean x value
	 */
    public void setEnabled(boolean x) {
    	enabled = x;
    }
    
    /**
     * Returns color parameter
     * @return color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Sets color parameter
     * @param Color x
     */
    public void setColor(Color x) {
    	color = x;
    }
    
    /**
     * Get name parameter
     * @return
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Sets name parameter
     * @param String x
     */
    public void setName(String x) {
    	name = x;
    }
    
    /**
     * Get description parameter
     * @return desc
     */
    public String getDesc() {
    	return desc;
    }
    
    /**
     * Set description parameter
     * @param String x
     */
    public void setDesc(String x) {
    	desc = x;
    }
}
