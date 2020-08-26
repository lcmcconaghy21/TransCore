package com.lcmcconaghy.java.transcore.command.argument;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.store.transcore.Profile;
import com.lcmcconaghy.java.transcore.store.transcore.ProfileCollection;

public class ArgumentProfile extends ArgumentAbstract<Profile>
{
	
	// { SINGLETON } //
	
	private static ArgumentProfile i = new ArgumentProfile();
	public static ArgumentProfile get() { return ArgumentProfile.i; }
	
	// { CONSTRUCTOR } //
	
	public ArgumentProfile()
	{
		super();
	}
	
	// { ARGUMENT } //
	
	@Override
	public Profile read(String arg0, CommandSender arg1)
	{
		Profile profile = null;
		
		for (Profile prof : ProfileCollection.get())
		{
			if ( !prof.getID().equalsIgnoreCase(arg0) ) continue;
			
			profile = prof;
			break;
		}
		
		return profile;
	}

	@Override
	public List<String> getTabCompleteList()
	{
		List<String> profiles = new ArrayList<String>();
		
		for (Profile profile : ProfileCollection.get())
		{
			profiles.add(profile.getID());
		}
		
		return profiles;
	}
	
}
