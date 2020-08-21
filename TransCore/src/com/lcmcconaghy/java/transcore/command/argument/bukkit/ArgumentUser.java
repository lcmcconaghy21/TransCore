package com.lcmcconaghy.java.transcore.command.argument.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.entity.User;

public class ArgumentUser extends ArgumentAbstract<User>
{
	
	// { SINGLETON } //
	
	private static ArgumentUser i = new ArgumentUser();
	public static ArgumentUser get() { return ArgumentUser.i; }
	
	// { CONSTRUCTOR } //
	
	public ArgumentUser()
	{
		super(User.class);
	}
	
	// { ARGUMENT } //
	
	@Override
	public User read(String arg0, CommandSender arg1)
	{
		return TransServer.get().getUser(arg0);
	}

	@Override
	public List<String> getTabCompleteList()
	{
		List<String> usernames = new ArrayList<String>();
		
		for (User users : TransServer.get().getUsers())
		{
			usernames.add(users.getPlayer().getName());
		}
		
		return usernames;
	}
	
}
