package com.lcmcconaghy.java.transcore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.engine.Engine;
import com.lcmcconaghy.java.transcore.store.Config;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.serializable.Serializable;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public abstract class TransPlugin extends JavaPlugin implements TPlugin
{
	
	// { FIELDS } //
	
	protected String name;
	protected String version;
	protected ConsoleCommandSender console;
	
	protected Config config;
	
	protected Map<Class<? extends Init>, List<? extends Init>> initialized = new HashMap<Class<? extends Init>,List<? extends Init>>();
	
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
		log(new Message("Loading "+this.name+" v"+this.version).titleize());
		
		log("<e>Registering <b>"+getAdapters().size()+" <e>Adapters...");
		registerAdapters();
		log("<a>Adapters registered!");
		
		separate();
		
		log("<e>Loading <b>"+getStoreCollections().size()+" <e>Collections...");
		registerCollections();
		log("<a>Collections loaded!");
		
		separate();
		
		log("<e>Configuring plugin...");
		registerConfig();
		log("<a>Done!");
		
		separate();
		
		log("<e>Registering <b>"+getTransCommands().size()+" <e>Commands...");
		registerCommands();
		log("<a>Commands registered!");
		
		separate();
		
		log("<e>Loading engines...");
		registerEngines();
		log("<e>Listening to <b>"+getEngines().size()+" <e>Engines!");
		
		separate();
		
		this.startup();
		
		log("<a>Finished loading <b>"+this.name+" v"+this.version+"<a>!");
	}
	
	// { DISABLE } //
	
	public void onDisable()
	{
		super.onDisable();
		
		for (List<? extends Init> inits : this.initialized.values())
		{
			for (Init init : inits)
			{
				init.initialize(false, this);
			}
		}
		
		this.disable();
	}
	
	// { INIT } //
	
	/**
	 * Register commands
	 */
	public void registerCommands()
	{
		if (getTransCommands() == null) return;
		
		for (TransCommand arg : getTransCommands())
		{
			arg.initialize(true, this);
		}
		
		this.initialized.put(TransCommand.class, getTransCommands());
	}
	
	/**
	 * Register Event Engines
	 */
	public void registerEngines()
	{
		if (getEngines() == null) return;
		
		for (Engine arg : getEngines())
		{
			arg.initialize(true, this);
		}
		
		this.initialized.put(Engine.class, getEngines());
	}
	
	/**
	 * Register single Config instance
	 */
	public void registerConfig()
	{
		if (getTransConfig() == null) return;
		
		getTransConfig().initialize(true, this);
		
		this.initialized.put(Config.class, UtilGeneral.list(getTransConfig()));
	}
	
	/**
	 * Register serializable data collections
	 */
	public void registerCollections()
	{
		if (getStoreCollections() == null) return;
		
		for (StoreCollection<?> collection : getStoreCollections())
		{
			collection.initialize(true, this);
		}
		
		this.initialized.put(StoreCollection.class, getStoreCollections());
	}
	
	/**
	 * Register serializables
	 */
	public void registerAdapters()
	{
		if (getAdapters() == null) return;
		
		for (Serializable<?> serializable : getAdapters())
		{
			serializable.initialize(true, this);
		}
		
		this.initialized.put(Serializable.class, getAdapters());
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
	 * Log messages in Console
	 * @param msgs Varargs String[] messages
	 */
	public void log(String... msgs)
	{
		for (String message : msgs)
		{
			getConsoleLogger().sendMessage(Message.format(message));
		}
	}
	
	/**
	 * Log messages in Console
	 * @param msgs Varargs Message[] messages
	 */
	public void log(Message...msgs)
	{
		for (Message message : msgs)
		{
			String text = message.format().getText();
			
			log(text);
		}
	}
	
	/**
	 * Separate different tasks from each other visually in the console
	 */
	public void separate()
	{
		log("<7>-----------------------------------------------");
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
