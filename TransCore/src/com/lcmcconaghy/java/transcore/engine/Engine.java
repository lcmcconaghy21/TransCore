package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;

public class Engine implements Init, Listener
{
	// { FIELDS } //
	
	protected boolean enabled = false;
	
	protected PluginManager manager;
	protected TransPlugin plugin;
	
	// { INIT } //
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		this.manager = arg1.getServer().getPluginManager();
		this.plugin = arg1;
		
		if (!arg0)
		{
			this.enabled = false;
			return;
		}
		
		this.enabled = arg0;
		
		this.manager.registerEvents(this, arg1);
	}

	@Override
	public boolean isInitialized()
	{
		return this.enabled;
	}
	
}
