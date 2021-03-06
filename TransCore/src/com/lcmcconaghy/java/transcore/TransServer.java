package com.lcmcconaghy.java.transcore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.inventory.InventoryView;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.gui.ChestGui;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;
import com.lcmcconaghy.java.transcore.tag.Nametag;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class TransServer
{
	
	// { FIELDS } //
	
	private Server server;
	private SimpleCommandMap commandMap;
	private Map<UUID,Nametag> nametags = new HashMap<UUID,Nametag>();
	
	private Map<Class<?>, StoreCollection<?>> collections = new HashMap<Class<?>, StoreCollection<?>>();
	private Map<String,ChestGui<?>> guis = new HashMap<String,ChestGui<?>>();
	
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
	
	public void registerNametag(OfflinePlayer arg0)
	{
		this.nametags.put(arg0.getUniqueId(), new Nametag(arg0));
	}
	
	public Nametag getNametagOf(OfflinePlayer arg0)
	{
		return this.nametags.get(arg0.getUniqueId());
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
	
	// { INTERFACES } //
	
	/**
	 * Register a new GUI
	 * @param arg0 ChestGui to be registered
	 */
	public void registerGui(ChestGui<?> arg0)
	{
		if ( containsGui(arg0) ) return;
		
		this.guis.put(arg0.getID(), arg0);
	}
	
	/**
	 * @param arg0 InventoryView
	 * @return whether Inventory is a GUI
	 */
	public boolean isGui(InventoryView arg0)
	{
		return this.guis.containsKey(arg0.getTitle());
	}
	
	/**
	 * Check if GUI is registered
	 * @param arg0 ChestGui which is to be checked for
	 * @return whether the GUI is registered
	 */
	public boolean containsGui(ChestGui<?> arg0)
	{
		return this.guis.containsKey(arg0.getID());
	}
	
	/**
	 * @param arg0 InventoryView
	 * @return GUI matching the InventoryView
	 */
	public ChestGui<?> getGui(InventoryView arg0)
	{
		if ( !isGui(arg0) )
		{
			return null;
		}
		
		return this.guis.get( arg0.getTitle() );
	}
	
	// { DATABASE } //
	
	public boolean containsCollection(StoreCollection<?> arg0)
	{
		return this.collections.containsKey(arg0.getType());
	}
	
	public void registerCollection(StoreCollection<?> arg0)
	{
		if ( containsCollection(arg0) ) return;
		
		this.collections.put(arg0.getType(), arg0);
	}
	
	public List<UserCollection<?>> getUserCollections()
	{
		ArrayList<UserCollection<?>> userCollections = new ArrayList<UserCollection<?>>();
		
		for (StoreCollection<?> collection : this.collections.values())
		{
			if (!(collection instanceof UserCollection)) continue;
			
			userCollections.add((UserCollection<?>) collection);
		}
		
		return userCollections;
	}
	
	public Collection<StoreCollection<?>> getStoreCollections()
	{
		return this.collections.values();
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
