package com.lcmcconaghy.java.transcore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class TransServer
{
	
	// { FIELDS } //
	
	private Server server;
	private SimpleCommandMap commandMap;
	
	private List<StoreCollection<?>> collections = new ArrayList<StoreCollection<?>>();
	
	private MongoClient mongoClient;
	
	// { SINGLETON } //
	
	private static TransServer i = new TransServer(Bukkit.getServer());
	public static TransServer get() { return TransServer.i; }
	
	// { CONSTRUCTOR } //
	
	public TransServer(Server arg0)
	{
		this.server = arg0;
		
		try
		{
			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			this.commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	// { COMMANDS } //
	
	/**
	 * Register type TransCommand
	 * @param arg0 TransCommand to be registered
	 * @return success
	 */
	public boolean registerCommand(TransCommand arg0)
	{
		if (hasCommand(arg0.getLabel()))
		{
			this.commandMap.getCommand(arg0.getLabel()).unregister(commandMap);
		}
		
		return this.commandMap.register(arg0.getLabel(), arg0.getBukkitCommand());
	}
	
	public SimpleCommandMap getCommandMap()
	{
		return this.commandMap;
	}
	
	public boolean hasCommand(TransCommand arg0)
	{
		boolean ret = false;
		
		for (Command registered : this.commandMap.getCommands())
		{
			if (registered.getClass()==arg0.getBukkitCommand().getClass()) continue;
			
			ret = true;
			break;
		}
		
		return ret;
	}
	
	public boolean hasCommand(String arg0)
	{
		return this.commandMap.getCommand(arg0)!=null;
	}
	
	// { DATABASE } //
	
	public void registerCollection(StoreCollection<?> arg0)
	{
		this.collections.add(arg0);
	}
	
	public List<UserCollection<?>> getUserCollections()
	{
		ArrayList<UserCollection<?>> userCollections = new ArrayList<UserCollection<?>>();
		
		for (StoreCollection<?> collection : this.collections)
		{
			if (!(collection instanceof UserCollection)) continue;
			
			userCollections.add((UserCollection<?>) collection);
		}
		
		return userCollections;
	}
	
	public List<StoreCollection<?>> getStoreCollections()
	{
		return this.collections;
	}
	
	public boolean isSerializable(Object arg0)
	{
		if (arg0 instanceof String ||
			arg0 instanceof Double ||
			arg0 instanceof Float ||
			arg0 instanceof Integer ||
			arg0 instanceof Long)
		{
			return true;
		}
		if (arg0 instanceof Map ||
			arg0 instanceof List)
		{
			return true;
		}
		
		return false;
	}
	
	public MongoClient getMongoClient()
	{
		if (this.mongoClient==null)
		{
			String hostName = TransConfig.get().getMongoHost();
			int port = TransConfig.get().getMongoPort();
			
			this.mongoClient = new MongoClient(hostName, port);
		}
		
		return this.mongoClient;
	}
	
	public boolean isMongoEnabled()
	{
		return TransConfig.get().willUseMongo();
	}
	
	/**
	 * Get MongoDB
	 * @param arg0 String database, will generate if not existing
	 * @return Database matching String id
	 */
	public DB getDatabase(String arg0)
	{
		return (DB) getMongoClient().getDatabase(arg0);
	}
	
	// { GETTERS } //
	
	public Server getAsBukkitServer()
	{
		return this.server;
	}
}
