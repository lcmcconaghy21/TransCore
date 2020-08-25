package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.store.UserCollection;

public class EnginePlayer extends Engine
{
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void registerPlayer(PlayerJoinEvent event)
	{
		TransServer.get().registerNametag(event.getPlayer());
		
		for (UserCollection<?> collection : TransServer.get().getUserCollections())
		{
			if (collection.has(event.getPlayer()))
			{
				collection.get(event.getPlayer());
				continue;
			}
			
			collection.create(event.getPlayer());
		}
	}
}
