package com.lcmcconaghy.java.transcore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.lcmcconaghy.java.transcore.store.Config;

public class TransPlugin extends JavaPlugin
{
	
	// { FIELDS } //
	
	protected String name;
	protected String version;
	protected ConsoleCommandSender console;
	
	protected Config config;
	
	protected List<Init> initialized = new ArrayList<Init>();
	
	// { CONSTRUCTOR } //
	
	public TransPlugin()
	{
		this.name = this.getName();
		this.version = this.getDescription().getVersion();
		
		this.console = Bukkit.getConsoleSender();
	}
	
	// { ENABLE } //
	
	@Override
	public void onEnable()
	{
		startup();
	}
	
	public void startup()
	{
		// THIS IS WHERE YOU PUT YOUR CODE
	}
	
	public void disable()
	{
		// THIS IS WHERE YOU PUT YOUR CODE
	}
	
	// { DISABLE } //
	
	public void onDisable()
	{
		super.onDisable();
		
		for (Init init : this.initialized)
		{
			init.initialize(false, this);
		}
		
		this.disable();
	}
	
	// { INIT } //
	
	public void initialize(Init... inits)
	{
		for (Init init : inits)
		{
			initialized.add(init);
			init.initialize(true, this);
		}
	}
	
	// { CONFIG } //
	
	public void setTransConfig(Config arg0)
	{
		this.config = arg0;
	}
	
	public Config getTransConfig()
	{
		return this.config;
	}
	
	// { LOGGER } //
	
	/**
	 * Log Varargs in Console
	 * @param msgs Varargs String[] messages
	 */
	public void log(String... msgs)
	{
		for (String message : msgs)
		{
			getConsoleLogger().sendMessage(Message.format(message));
		}
	}
	
	public ConsoleCommandSender getConsoleLogger()
	{
		return this.console;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public List<String> getAuthors()
	{
		return this.getDescription().getAuthors();
	}
	
}
