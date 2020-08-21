package com.lcmcconaghy.java.transcore.command;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.Config;

public class TransCommandReload extends TransCommand
{
	// { FIELDS } //
	
	private Config config;
	
	// { CONSTRUCTOR } //
	
	public TransCommandReload(TransPlugin arg0) throws TransCommandException
	{
		super("reload");
		
		this.setDesc("reload plugin");
		this.setPerm(TransPerm.RELOAD);
		
		this.config = arg0.getTransConfig();
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		config.load();
		
		message("<d>"+this.plugin.getName()+" <a>has been reloaded.");
	}
	
}
