package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.entity.User;

public class EnginePlayer extends Engine
{
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void registerPlayer(PlayerJoinEvent event)
	{
		User user = new User(event.getPlayer());
		
		TransServer.get().registerUser(user);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void unregisterPlayer(PlayerQuitEvent event)
	{
		User user = TransServer.get().getUser(event.getPlayer());
		
		TransServer.get().unregisterUser(user);
	}
	
}
