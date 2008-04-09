package ultigpx;

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
    boolean isgrp;
    boolean isrt;
    boolean istrk;
    boolean iswp;
    Waypoint wp;
    Track trk;
    Route rt;
    Group grp;
    
    
    public EditOperation(Waypoint pt)
    {
        this.wp = pt;
        this.iswp = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
        this.oldele = pt.getEle();
    }
    
    public EditOperation(Track pt)
    {
        this.trk = pt;
        this.istrk = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
    }
    
    public EditOperation(Route pt)
    {
        this.rt = pt;
        this.isrt = true;
        this.oldname = pt.getName();
        this.olddesc = pt.getDesc();
        this.oldenabled = pt.getEnabled();
    }
    
    public EditOperation(Group pt)
    {
        this.grp = pt;
        this.isgrp = true;
        this.oldname = pt.name;
        this.oldenabled = pt.getEnabled();
    }

    public void setnew(Waypoint pt)
    {
    	if (iswp)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    		this.newele = pt.getEle();
    	}
    }
    
    public void setnew(Track pt)
    {
    	if (istrk)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    public void setnew(Route pt)
    {
    	if (isrt)
    	{
    		this.newname = pt.getName();
    		this.newdesc = pt.getDesc();
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    public void setnew(Group pt)
    {
    	if (isgrp)
    	{
    		this.newname = pt.name;
    		this.newenabled = pt.getEnabled();
    	}
    }
    
    public void redo()
    {
    	if (iswp)
    	{
    		wp.setName(newname);
    		wp.setDesc(newdesc);
    		wp.setEnabled(newenabled);
    		wp.setEle(newele);
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
    
    public void undo()
    {
    	if (iswp)
    	{
    		wp.setName(oldname);
    		wp.setDesc(olddesc);
    		wp.setEnabled(oldenabled);
    		wp.setEle(oldele);
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