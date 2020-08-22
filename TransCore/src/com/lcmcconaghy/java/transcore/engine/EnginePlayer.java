package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;

public class EnginePlayer extends Engine
{
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void registerPlayer(PlayerJoinEvent event)
	{
		for (UserCollection<?> collection : TransServer.get().getUserCollections())
		{
			if (collection.has(event.getPlayer())) continue;
			
			collection.create(event.getPlayer());
		}
	}
	
	@EventHandler
	public void updateVersionPlayer(PlayerQuitEvent event)
	{
		IUser user = IUser.get(event.getPlayer());
		
		user.setLastVersion();
	}
}
