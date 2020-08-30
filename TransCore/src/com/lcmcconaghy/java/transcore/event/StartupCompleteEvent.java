package com.lcmcconaghy.java.transcore.event;

import org.bukkit.event.HandlerList;

public class StartupCompleteEvent extends TransEvent
{
	
	// { HANLDERS } //
	
	public static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() { return handlers; }
	@Override public HandlerList getHandlers() { return handlers; }
	
	// { CONSTRUCTOR } //
	
	public StartupCompleteEvent()
	{
		super(false);
	}
	
}
