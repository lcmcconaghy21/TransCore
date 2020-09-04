package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

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
		for (Plugin plugins : Bukkit.getPluginManager().getPlugins())
		{
			if ( !(plugins instanceof TransPlugin) ) continue;
			
			((TransPlugin)plugin).postStartup();
		}
	}
	
}
