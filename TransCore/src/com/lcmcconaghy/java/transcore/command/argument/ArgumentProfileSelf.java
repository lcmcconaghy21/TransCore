package com.lcmcconaghy.java.transcore.command.argument;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class ArgumentProfileSelf extends ArgumentAbstract<Profile>
{
	
	// { SINGLETON } //
	
	private static ArgumentProfileSelf i = new ArgumentProfileSelf();
	public static ArgumentProfileSelf get() { return ArgumentProfileSelf.i; }
	
	// { PROFILE } //
	
	@Override
	public Profile read(String arg0, CommandSender arg1) throws TransCommandException
	{
		if (IUser.get( arg1 ) == null)
		{
			throw new TransCommandException("Only players can execute this command!");
		}
		
		Profile ret = null;
		
		for (Profile profile : IUser.get(arg1).getProfiles())
		{
			if ( !profile.getName().equalsIgnoreCase(arg0) ) continue;
			
			ret = profile;
			break;
		}
		
		return ret;
	}

	@Override
	public List<String> getTabCompleteList(CommandSender arg0)
	{
		List<String> profileNames = new ArrayList<String>();
		
		for (Profile profile : IUser.get(arg0).getProfiles())
		{
			profileNames.add( profile.getName() );
		}
		
		return profileNames;
	}
	
}
