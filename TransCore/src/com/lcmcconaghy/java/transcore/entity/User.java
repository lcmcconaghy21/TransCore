package com.lcmcconaghy.java.transcore.entity;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.Message;

public class User
{
	// { FIELDS } //
	
	protected Player player;
	
	// { CONSTRUCTOR } //
	
	public User(Player arg0)
	{
		this.player = arg0;
	}
	
	// { MESSAGE } //
	
	public void message(Message arg0)
	{
		arg0.send(this);
	}
	
	// { GETTERS } //
	
	/**
	 * @return Player attached to this User
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	
}
