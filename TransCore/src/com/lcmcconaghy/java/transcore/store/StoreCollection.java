package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import de.leonhard.storage.Json;

@SuppressWarnings("rawtypes")
public class StoreCollection<T extends StoreItem> extends ArrayList<T> implements Init
{
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { FIELDS } //
	
	private Plugin plugin;
	private String path;
	
	private String id;
	private DatabaseType database = DatabaseType.FLATFILE;
	private Class<T> type;
	
	private DBCollection coll;
	
	// { CONSTRUCTOR } //
	
	public StoreCollection(String arg0, Class<T> arg1)
	{
		this.id = arg0;
		this.type = arg1;
	}
	
	// { MANAGEMENT } //
	
	/**
	 * Create new instance of StoreItem
	 * @param arg0 String id of StoreItem
	 * @return new StoreItem instance
	 */
	@SuppressWarnings("unchecked")
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
		
		save(ret);
		
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
	
	/**
	 * @param arg0 String id
	 * @return whether an object with the ID exists
	 */
	public boolean has(String arg0)
	{
		if ( get(arg0) == null ) return false;
		
		return true;
	}
	
	// { DATABASE } //
	
	/**
	 * Load StoreItem from File
	 * @param src File to load StoreItem from
	 * @return a StoreItem
	 */
	@SuppressWarnings("unchecked")
	public T load(File src)
	{
		T ret = null;
		try
		{
			ret = this.type.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		Json json = new Json(src);
		
		String name = json.getFile().getName();
		if (name.contains(".json")) name = name.replaceAll(".json", "");
		
		ret.id = name;
		ret.store = this;
		
		for (Field declared : ret.getClass().getDeclaredFields())
		{
			declared.setAccessible(true);
			
			Object get = json.get(declared.getName());
			
			if (get == null) continue;
			
			try
			{
				declared.set(ret, json.get(declared.getName()));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * Save the entire Collection
	 */
	public void save()
	{
		for (T item : this)
		{
			save(item);
		}
	}
	
	/**
	 * Save a singular StoreItem
	 * @param arg0 StoreItem to be saved
	 */
	public void save(T arg0)
	{
		arg0.update();
	}
	
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
		if (arg0 == false)
		{
			save();
			return;
		}
		
		this.plugin = arg1;
		this.path = arg1.getDataFolder().getPath()+File.separator+this.id;
		
		TransServer.get().registerCollection(this);
		
		if (TransServer.get().isMongoEnabled())
		{
			this.database = DatabaseType.MONGO;
		}
		
		File src = new File(path);
		
		if (!src.exists())
		{
			src.mkdirs();
		}
		
		if (!TransServer.get().isMongoEnabled())
		{
			for (File sub : src.listFiles())
			{
				if (!sub.getName().endsWith(".json")) continue;
				
				add( load(sub) );
			}
			
			return;
		}
		
		DB database = TransServer.get().getDatabase(this.plugin.getName());
		this.coll = database.getCollection(id);
	}

	@Override
	public boolean isInitialized()
	{
		return true;
	}
	
}
