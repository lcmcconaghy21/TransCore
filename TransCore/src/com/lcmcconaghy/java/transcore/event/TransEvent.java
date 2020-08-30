package com.lcmcconaghy.java.transcore.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class TransEvent extends Event implements Cancellable, Runnable
{
	// { FIELDS } //
	
	private boolean active = true;
	
	// { CONSTRUCTORS } //
	
	public TransEvent(boolean arg0)
	{
		super(arg0);
	}
	
	public TransEvent()
	{
		super(false);
	}
	
	// { CANCELLABLE } //
	
	public void cancel()
	{
		this.active = false;
	}
	
	@Override
	public void setCancelled(boolean arg0)
	{
		this.active = arg0;
	}
	
	@Override
	public boolean isCancelled()
	{
		return this.active;
	}
	
	// { RUNNABLE } //
	
	@Override
	public void run()
	{
		Bukkit.getPluginManager().callEvent(this);
	}
	
}
