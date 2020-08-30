package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.event.StartupCompleteEvent;

public class EngineServer extends Engine
{
	
	// { SINGLETON } //
	
	private static EngineServer i = new EngineServer();
	public static EngineServer get() { return EngineServer.i; }
	
	@EventHandler
	public void postStart(StartupCompleteEvent event)
	{
		for (TransPlugin plugin : TransCore.get().getRegisteredPlugins())
		{
			plugin.postStartup();
		}
	}
	
}
