package com.lcmcconaghy.java.transcore.command.argument;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;
import com.lcmcconaghy.java.transcore.store.transcore.ProfileCollection;

public class ArgumentProfileOther extends ArgumentAbstract<Profile>
{
	
	// { SINGLETON } //
	
	private static ArgumentProfileOther i = new ArgumentProfileOther();
	public static ArgumentProfileOther get() { return ArgumentProfileOther.i; }
	
	// { ARGUMENT } //
	
	@Override
	public Profile read(String arg0, CommandSender arg1) throws TransCommandException
	{
		Profile ret = null;
		
		for (Profile profile : ProfileCollection.get())
		{
			if (arg0.equalsIgnoreCase( profile.getName() )) continue;
			
			ret = profile;
			break;
		}
		
		if (ret == null) throw new TransCommandException("There is no other character named <b>"+arg0);
		
		return ret;
	}
	@Override
	public List<String> getTabCompleteList(CommandSender arg0)
	{
		List<String> profileNames = new ArrayList<String>();
		
		for (Profile profile : ProfileCollection.get())
		{
			if (profile.getOwner().getUniqueId().toString()
					   .equalsIgnoreCase( IUser.get(arg0).getID() )) continue;
			
			profileNames.add( profile.getName() );
			break;
		}
		
		return profileNames;
	}
	
	
}
