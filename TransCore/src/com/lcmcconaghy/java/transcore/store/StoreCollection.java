package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class StoreCollection<T extends StoreItem<T>> extends ArrayList<T> implements Init
{
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { FIELDS } //
	
	private Plugin plugin;
	private String path;
	
	private String id;
	private DatabaseType database;
	private Class<T> type;
	
	private DBCollection coll;
	
	// { CONSTRUCTOR } //
	
	public StoreCollection(String arg0, DatabaseType arg1, Class<T> arg2)
	{
		this.id = arg0;
		this.database = arg1;
		this.type = arg2;
	}
	
	// { MANAGEMENT } //
	
	/**
	 * Create new instance of StoreItem
	 * @param arg0 String id of StoreItem
	 * @return new StoreItem instance
	 */
	public T create(String arg0)
	{
		T ret = null;
		
		try
		{
			ret = type.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		ret.id = arg0;
		ret.store = this;
		add(ret);
		
		return ret;
	}
	
	/**
	 * Create new instance of StoreItem with UUID
	 * @return new StoreItem instance assigned to a UUID
	 */
	public T create()
	{
		return create(UUID.randomUUID().toString());
	}
	
	/**
	 * Get StoreItem from String ID
	 * @param arg0 String id of StoreItem
	 * @return StoreItem with matching String ID
	 */
	public T get(String arg0)
	{
		T ret = null;
		
		for (T inst : this)
		{
			if (!inst.id.equalsIgnoreCase(arg0)) continue;
			
			ret = inst;
			break;
		}
		
		return ret;
	}
	
	// { DATABASE } //
	
	public String getID()
	{
		return this.id;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public DatabaseType getDatabaseType()
	{
		return this.database;
	}
	
	public DBCollection getCollection()
	{
		return this.coll;
	}
	
	public Plugin getPlugin()
	{
		return this.plugin;
	}
	
	// { INIT } //
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		this.plugin = arg1;
		this.path = arg1.getDataFolder().getPath()+File.separator+this.id;
		
		if (!TransConfig.get().willUseMongo()) return;
		
		DB database = TransServer.get().getDatabase(this.plugin.getName());
		this.coll = database.getCollection(id);
	}

	@Override
	public boolean isInitialized()
	{
		return true;
	}
	
}
