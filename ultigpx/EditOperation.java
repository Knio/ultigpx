package ultigpx;

/**
 * Allows editing of a track/route/group/waypoint to be undone.
 * @author Steven
 */
public class EditOperation extends Operation
{
    String oldname;
    String newname;
    boolean newenabled;
    boolean oldenabled;
    String olddesc;
    String newdesc;
    double oldele;
    double newele;
    long oldtime;
    long newtime;
    double oldlon;
    double newlon;
    double oldlat;
    double newlat;
    boolean isgrp;
    boolean isrt;
    boolean istrk;
    boolean iswp;
    Waypoint wp;
    Track trk;
    Route rt;
    Group grp;
    
    /**
     * Sets the old data for the undo.
     * @param pt A waypoint
     */
    public EditOperation(Waypoint pt)
    {
        this.wp = pt;
        this.iswp = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
        this.oldele = pt.getEle();
        this.oldtime = pt.getTime();
        this.oldlon = pt.getLon();
        this.oldlat = pt.getLat();
    }
    
    /**
     * Sets the old data for the undo.
     * @param pt A track
     */
    public EditOperation(Track pt)
    {
        this.trk = pt;
        this.istrk = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
    }
    
    /**
     * Sets the old data for the undo.
     * @param pt A route
     */
    public EditOperation(Route pt)
    {
        this.rt = pt;
        this.isrt = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
    }
    
    /**
     * Sets the old data for the undo.
     * @param pt A group
     */
    public EditOperation(Group pt)
    {
        this.grp = pt;
        this.isgrp = true;
        this.oldname = pt.name;
        this.oldenabled = pt.getEnabled();
    }

    /**
     * Sets the new data for the undo.
     * @param pt A waypoint
     */
    public void setnew(Waypoint pt)
    {
    	if (iswp)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    		this.newele = pt.getEle();
            this.newtime = pt.getTime();
            this.newlon = pt.getLon();
            this.newlat = pt.getLat();
    	}
    }
    
    /**
     * Sets the new data for the undo.
     * @param pt A track
     */
    public void setnew(Track pt)
    {
    	if (istrk)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    /**
     * Sets the new data for the undo.
     * @param pt A route
     */
    public void setnew(Route pt)
    {
    	if (isrt)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    /**
     * Sets the new data for the undo.
     * @param pt A group
     */
    public void setnew(Group pt)
    {
    	if (isgrp)
    	{
    		this.newname = pt.name;
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    /**
     * Redoes an edit.
     */
    public void redo()
    {
    	if (iswp)
    	{
    		wp.setName(newname);
    		wp.setDesc(newdesc);
    		wp.setEnabled(newenabled);
    		wp.setEle(newele);
            wp.setTime(newtime);
            wp.setLon(newlon);
            wp.setLat(newlat);
    	}
    	if (istrk)
    	{
    		trk.setName(newname);
    		trk.setDesc(newdesc);
    		trk.setEnabled(newenabled);
    	}
    	if (isrt)
    	{
    		rt.setName(newname);
    		rt.setDesc(newdesc);
    		rt.setEnabled(newenabled);
    	}
    	if (isgrp)
    	{
    		grp.name = newname;
    		grp.setEnabled(newenabled);
    	}
    }
    
    /**
     * Undoes an edit.
     */
    public void undo()
    {
    	if (iswp)
    	{
    		wp.setName(oldname);
    		wp.setDesc(olddesc);
    		wp.setEnabled(oldenabled);
    		wp.setEle(oldele);
    		wp.setTime(oldtime);
            wp.setLon(oldlon);
            wp.setLat(oldlat);
    	}
    	if (istrk)
    	{
    		trk.setName(oldname);
    		trk.setDesc(olddesc);
    		trk.setEnabled(oldenabled);
    	}
    	if (isrt)
    	{
    		rt.setName(oldname);
    		rt.setDesc(olddesc);
    		rt.setEnabled(oldenabled);
    	}
    	if (isgrp)
    	{
    		grp.name = oldname;
    		grp.setEnabled(oldenabled);
    	}
    }

}