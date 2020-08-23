package com.lcmcconaghy.java.transcore.store;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
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
	
	// { LOCATION } //
	
	/**
	 * Get HashSet of nearby Players within radius
	 * @param arg0 Double radius
	 * @return HashSet of nearby Players
	 */
	public Set<Player> getNearbyPlayers(double arg0)
	{
		if (!this.player.isOnline()) return null;
		
		Set<Player> nearbyPlayers = new HashSet<Player>();
		
		for (Entity part : player.getNearbyEntities(arg0, arg0, arg0))
		{
			if (!(part instanceof Player)) continue;
			
			Player player = (Player) part;
			nearbyPlayers.add(player);
		}
		
		return nearbyPlayers;
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
