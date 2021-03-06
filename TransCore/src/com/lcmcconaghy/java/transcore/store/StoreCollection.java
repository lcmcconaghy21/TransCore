package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.exceptions.LightningValidationException;

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
	
	@Deprecated
	public T remove(int arg0)
	{
		return super.remove(arg0);
	}
	
	@Deprecated
	public boolean remove(Object arg0)
	{
		return super.remove(arg0);
	}
	
	public boolean remove(String arg0)
	{
		T ret = null;
		
		for (T part : this)
		{
			if (!part.getID().equalsIgnoreCase(arg0)) continue;
			
			ret = part;
			break;
		}
		
		this.remove(ret);
		
		if (ret == null) return false;
		File itemFile = new File(this.path+File.separator+arg0);
		
		if (itemFile.exists())
		{
			itemFile.delete();
		}
		
		return (ret == null);
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
	
	public Class<T> getType()
	{
		return this.type;
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
			
			Object isNull = null;
			
			try
			{
				isNull = json.getSerializable(declared.getName(), get.getClass());
			}
			catch (LightningValidationException exception)
			{
				// Nothing, you good boo x
			}
			
			if (isNull != null)
			{
				get = json.getSerializable(declared.getName(), get.getClass());
			}
			
			try
			{
				declared.set(ret, get);
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
		
		File src = new File(path);
		
		if (!src.exists())
		{
			src.mkdirs();
		}
		
		this.clear();
		
		if (!TransServer.get().isMongoEnabled())
		{
			for (File sub : src.listFiles())
			{
				String fileName = sub.getName();
				
				if (fileName.contains(".json")) fileName = fileName.replaceAll(".json", "");
				
				if ( has( fileName ) ) remove( fileName );
				add( load(sub) );
			}
			
			return;
		}
		
		this.database = DatabaseType.MONGO;
		
		DB database = TransServer.get().getDatabase(this.plugin.getName());
		this.coll = database.getCollection(id);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
		this.save(),
		0L, 100L);
	}

	@Override
	public boolean isInitialized()
	{
		return true;
	}
	
}
