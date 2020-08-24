package com.lcmcconaghy.java.transcore.store;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransLocation;

public class UserItem extends StoreItem<UserItem>
{
	
	// { PLAYER } //
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(UUID.fromString(this.id));
	}
	
	public OfflinePlayer getOfflinePlayer()
	{
		return Bukkit.getOfflinePlayer(UUID.fromString(this.id));
	}
	
	/**
	 * @return whether User is NPC
	 */
	public boolean isNpc()
	{
		return this.getPlayer().hasMetadata("NPC");
	}
	
	public boolean isOnline()
	{
		return getOfflinePlayer().isOnline();
	}
	
	// { COMPARATOR } //
	
	/**
	 * Check if two UserItems are the same
	 * @param arg0
	 * @return
	 */
	public boolean equals(UserItem arg0)
	{
		return this.id.equals(arg0.id);
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
		return new TransLocation(getPlayer().getLocation());
	}
	
	/**
	 * Get HashSet of nearby Players within radius
	 * @param arg0 Double radius
	 * @return HashSet of nearby Players
	 */
	public Set<Player> getNearbyPlayers(double arg0)
	{
		if (!getPlayer().isOnline()) return null;
		
		Set<Player> nearbyPlayers = new HashSet<Player>();
		
		for (Entity part : getPlayer().getNearbyEntities(arg0, arg0, arg0))
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
