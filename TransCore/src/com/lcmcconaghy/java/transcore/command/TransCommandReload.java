package com.lcmcconaghy.java.transcore.command;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.Config;

public class TransCommandReload extends TransCommand
{
	// { FIELDS } //
	
	private TransPlugin plugin;
	private Config config;
	private Init[] inits;
	
	// { CONSTRUCTOR } //
	
	public TransCommandReload(TransPlugin arg0, Init...args)
	{
		this.addAliases("reload", "rl");
		
		this.setDesc("reload plugin");
		this.setPerm(TransPerm.RELOAD);
		
		this.plugin = arg0;
		this.config = arg0.getTransConfig();
		this.inits = args;
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		if (!config.isInitialized())
		{
			error("Could not reload config. There is no defined Config");
			return;
		}
		
		config.load();
		
		if (inits.length > 0)
		{
			for (int i = 0; i < inits.length; i++)
			{
				this.inits[i].initialize(true, plugin);
			}
		}
		
		message("<d>"+this.plugin.getName()+" <a>has been reloaded.");
	}
	
}
