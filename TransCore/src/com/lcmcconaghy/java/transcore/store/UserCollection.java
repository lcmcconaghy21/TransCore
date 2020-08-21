package com.lcmcconaghy.java.transcore.store;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
	 * Get UserItem from String username
	 * @return UserItem
	 */
	public T get(String arg0)
	{
		T ret = null;
		
		for (T user : this)
		{
			UUID id = UUID.fromString(user.getID());
			OfflinePlayer player = Bukkit.getOfflinePlayer(id);
			
			if (!player.getName().equalsIgnoreCase(arg0)) continue;
			
			ret = user;
		}
		
		return ret;
	}
	
	/**
	 * Get UserItem from UUID
	 * @param arg0 UUID of UserItem
	 * @return UserItem
	 */
	public T get(UUID arg0)
	{
		T ret = null;
		
		for (T items : this)
		{
			UUID compare = UUID.fromString(items.getID());
			
			if (!arg0.equals(compare)) continue;
			
			ret = items;
		}
		
		return ret;
	}
	
	/**
	 * Get UserItem from Player
	 * @param arg0 Player to get UserItem from
	 * @return UserItem
	 */
	public T get(Player arg0)
	{
		UUID id = arg0.getUniqueId();
		
		return get(id);
	}
	
}
