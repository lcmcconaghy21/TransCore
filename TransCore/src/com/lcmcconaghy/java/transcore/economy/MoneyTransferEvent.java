package com.lcmcconaghy.java.transcore.economy;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.event.TransEvent;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class MoneyTransferEvent extends TransEvent
{
	
	// { HANDLERS } //
	
	public static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() { return handlers; }
	@Override public HandlerList getHandlers() { return handlers; }
	
	// { FIELDS } //
	
	private Profile from;
	private Profile to;
	private double amount;
	private Plugin plugin;
	
	// { CONSTRUCTOR } //
	
	/**
	 * @param arg0 Profile from
	 * @param arg1 Profile to
	 * @param arg2 Double amount that is transfered
	 */
	public MoneyTransferEvent(Profile arg0, Profile arg1, double arg2, Plugin arg3)
	{
		this.from = arg0;
		this.to = arg1;
		this.amount = arg2;
		this.plugin = arg3;
	}
	
	// { SETTERS } //
	
	public void setAmount(double arg0)
	{
		this.amount = arg0;
	}
	
	// { GETTERS } //
	
	public Profile getFrom()
	{
		return this.from;
	}
	
	public Profile getTo()
	{
		return this.to;
	}
	
	public double getAmount()
	{
		return this.amount;
	}
	
	public Plugin getPlugin()
	{
		return this.plugin;
	}
}
