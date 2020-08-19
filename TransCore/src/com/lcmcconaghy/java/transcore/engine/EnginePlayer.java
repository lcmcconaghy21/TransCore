package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.entity.User;

public class EnginePlayer extends Engine
{
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler
	public void registerPlayer(PlayerJoinEvent event)
	{
		User user = new User(event.getPlayer());
		
		TransServer.get().registerUser(user);
		
		
	}
}
