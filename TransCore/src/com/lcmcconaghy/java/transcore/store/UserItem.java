package com.lcmcconaghy.java.transcore.store;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransLocation;

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
	 * Distance between
	 * @param arg0
	 * @return
	 */
	public double distanceBetween(Object arg0)
	{
		if (arg0 instanceof UserItem)
		{
			return ((UserItem)arg0).getTransLocation().distanceBetween(this.getTransLocation());
		}
		else if (arg0 instanceof Entity)
		{
			return new TransLocation(((Entity)arg0)).distanceBetween(this.getTransLocation());
		}
		else if (arg0 instanceof TransLocation)
		{
			return ((TransLocation)arg0).distanceBetween(this.getTransLocation());
		}
		
		return 0;
	}
	
	/**
	 * @return TransLocation of User
	 */
	public TransLocation getTransLocation()
	{
		return new TransLocation(this.player.getLocation());
	}
	
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
