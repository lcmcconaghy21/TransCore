package com.lcmcconaghy.java.transcore.store;

import java.util.UUID;

import org.bukkit.entity.Player;

public class UserCollection<T extends UserItem> extends StoreCollection<T>
{
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { CONSTRUCTOR } //

	public UserCollection(String arg0, Class<T> arg1)
	{
		super(arg0, arg1);
	}
	
	// { CREATE } //
	
	@Override
	@Deprecated
	public T create()
	{
		return super.create();
	}
	
	/**
	 * Create new UserItem from Player
	 * @param arg0 Player to be used as source of UserItem
	 * @return new UserItem
	 */
	public T create(Player arg0)
	{
		return super.create(arg0.getUniqueId().toString());
	}
	
	// { HAS } //
	
	public boolean has(UUID arg0)
	{
		return get(arg0)!=null;
	}
	
	public boolean has(Player arg0)
	{
		return get(arg0)!=null;
	}
	
	public boolean has(String arg0)
	{
		return get(arg0)!=null;
	}
	
	// { GET } //
	
	/**
	 * Get the Generic Type from a broad Object argument
	 * @param arg0 Player, UUID or String id
	 * @return matching Generic Type extending UserItem
	 */
	public T get(Object arg0)
	{
		String id = null;
		T item = null;
		
		if (arg0 instanceof String)
		{
			id = (String) arg0;
		}
		else if (arg0 instanceof UUID)
		{
			id = ((UUID)arg0).toString();
		}
		else if (arg0 instanceof Player)
		{
			id = ((Player)arg0).getUniqueId().toString();
		}
		
		for (T user : this)
		{
			if (!user.id.equalsIgnoreCase(id)) continue;
			
			item = user;
			break;
		}
		
		return item;
	}
	
}
