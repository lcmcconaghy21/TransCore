package com.lcmcconaghy.java.transcore.store.transcore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.lcmcconaghy.java.transcore.store.StoreItem;
import com.lcmcconaghy.java.transcore.store.UserItem;

public class Profile extends StoreItem<Profile>
{
	
	// { FIELDS } //
	
	private String name;
	private String desc;
	private String owner;
	
	private Map<String,Object> properties = new HashMap<String,Object>();
	
	// { GETTERS } //
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDesc()
	{
		return this.desc;
	}
	
	public OfflinePlayer getOwner()
	{
		return Bukkit.getOfflinePlayer( UUID.fromString( this.owner ) );
	}
	
	public Object getProperty(String arg0)
	{
		return this.properties.get(arg0);
	}
	
	public boolean containsProperty(String arg0)
	{
		return this.properties.containsKey(arg0);
	}
	
	
	// { SETTERS } //
	
	public void setName(String arg0)
	{
		this.name = arg0;
		this.update();
	}
	
	public void setDesc(String arg0)
	{
		this.desc = arg0;
		this.update();
	}
	
	public void setOwner(UserItem arg0)
	{
		this.owner = arg0.getID();
		this.update();
	}
	
	public void setProperty(String arg0, Object arg1)
	{
		this.properties.put(arg0, arg1);
		this.update();
	}
	
}
