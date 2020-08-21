package com.lcmcconaghy.java.transcore.event;

import org.bukkit.event.HandlerList;

public class VersionUpdateEvent extends TransEvent
{
	
	// { HANDLERS } //
	
	public static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() { return handlers; }
	@Override public HandlerList getHandlers() { return handlers; }
	
	// { FIELDS } //
	
	private String prevVersion;
	private String nextVersion;
	
	// { CONSTRUCTOR } //
	
	public VersionUpdateEvent(String arg0, String arg1)
	{
		this.prevVersion = arg0;
		this.nextVersion = arg1;
	}
	
	// { GETTERS } //
	
	public String getLastVersion()
	{
		return this.prevVersion;
	}
	
	public String getNextVersion()
	{
		return this.nextVersion;
	}
	
}
