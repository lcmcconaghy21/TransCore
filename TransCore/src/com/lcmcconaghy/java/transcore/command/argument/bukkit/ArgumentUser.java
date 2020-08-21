package com.lcmcconaghy.java.transcore.command.argument.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.UserItem;

public class ArgumentUser<T extends UserItem> extends ArgumentAbstract<T>
{
	// { FIELDS } //
	
	private UserCollection<T> collection;
	
	// { CONSTRUCTOR } //
	
	public ArgumentUser(Class<T> arg0, UserCollection<T> arg1)
	{
		super(arg0);
		
		this.collection = arg1;
	}
	
	// { ARGUMENT } //
	
	@Override
	public T read(String arg0, CommandSender arg1)
	{
		return collection.get(arg0);
	}

	@Override
	public List<String> getTabCompleteList()
	{
		List<String> usernames = new ArrayList<String>();
		
		for (T item : this.collection)
		{
			usernames.add(item.getPlayer().getName());
		}
		
		return usernames;
	}
	
}
