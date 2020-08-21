package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

public class EngineServer extends Engine
{
	
	// { SINGLETON } //
	
	private static EngineServer i = new EngineServer();
	public static EngineServer get() { return EngineServer.i; }
	
	// { MOTD } //
	
	@EventHandler
	public void updateMotd(ServerListPingEvent event)
	{
		event.setMotd(TransConfig.get().getMotd());
	}
	
}
