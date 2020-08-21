package com.lcmcconaghy.java.transcore.store;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.Message;

public class UserItem extends StoreItem<UserItem>
{
	// { FIELDS } //
	
	protected Player player;
	
	// { PLAYER } //
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	/**
	 * @return whether User is NPC
	 */
	public boolean isNpc()
	{
		return this.getPlayer().hasMetadata("NPC");
	}
	
	// { CHAT } //
	
	/**
	 * Send chat messages
	 * @param messages Varargs of type Message to be sent
	 */
	public void message(Message...messages)
	{
		for (Message message : messages)
		{
			message.send(this);
		}
	}
	
}
