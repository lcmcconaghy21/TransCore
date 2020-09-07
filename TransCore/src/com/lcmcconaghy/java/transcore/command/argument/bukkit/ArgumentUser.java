package com.lcmcconaghy.java.transcore.command.argument.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.UserItem;

public class ArgumentUser<T extends UserItem> extends ArgumentAbstract<T>
{
	// { FIELDS } //
	
	private UserCollection<T> collection;
	
	// { CONSTRUCTOR } //
	
	public ArgumentUser(UserCollection<T> arg0)
	{
		this.collection = arg0;
	}
	
	// { ARGUMENT } //
	
	@Override
	public T read(String arg0, CommandSender arg1) throws TransCommandException
	{
		if ( !collection.has(arg0) )
		{
			throw new TransCommandException("A user by the name of <f>"+arg0+"<c>does not exist.");
		}
		
		return collection.get(arg0);
	}

	@Override
	public List<String> getTabCompleteList(CommandSender arg0)
	{
		List<String> usernames = new ArrayList<String>();
		
		for (T item : this.collection)
		{
			usernames.add(item.getPlayer().getName());
		}
		
		return usernames;
	}
	
}
