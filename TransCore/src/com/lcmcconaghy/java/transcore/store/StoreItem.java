package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.lang.reflect.Field;

import com.lcmcconaghy.java.transcore.TransServer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.leonhard.storage.Json;

public class StoreItem<T extends StoreItem<T>>
{	
	// { FIELDS } //
	
	protected String id;
	protected StoreCollection<T> store;
	
	// { STORE } //
	
	/**
	 * Alert database that this instance has updated
	 */
	public void update()
	{
		if (getStore().getDatabaseType()==DatabaseType.FLATFILE)
		{
			Json data = new Json(new File(store.getPath()));
			
			for (Field fields : this.getClass().getDeclaredFields())
			{
				fields.setAccessible(true);
				
				Object get = null;
				try
				{
					get = fields.get(this);
				} catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
				
				if (!TransServer.get().isSerializable(get)) continue;
				
				data.set(fields.getName(), get);
			}
			
			return;
		}
		
		DBObject inst = new BasicDBObject("id", this.id);
		
		for (Field fields : this.getClass().getDeclaredFields())
		{
			Object object = null;
			
			try
			{
				object = fields.get(this);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if (!TransServer.get().isSerializable(object))
			{
				continue;
			}
			
			inst.put(fields.getName(), object);
		}
		
		if (this.store.getCollection().findOne(inst)!=null)
		{
			this.store.getCollection().remove(inst);
		}
		
		this.store.getCollection().insert(inst);
	}
	
	/**
	 * @return StoreCollecton that handles this instance
	 */
	public StoreCollection<T> getStore()
	{
		return this.store;
	}
	
}
