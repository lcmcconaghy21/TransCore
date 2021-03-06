package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentString;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;
import com.lcmcconaghy.java.transcore.store.transcore.ProfileCollection;

public class CmdProfileCreate extends TransCommand
{
	
	// { SINGLETON } //
	
	private static CmdProfileCreate i = new CmdProfileCreate();
	public static CmdProfileCreate get() { return CmdProfileCreate.i; }
	
	// { CONSTRUCTOR } //
	
	public CmdProfileCreate()
	{
		this.addAlias("create");
		
		this.setRequiresPlayer(true);
		this.setPerm(TransPerm.PROFILE_CREATE);
		this.setDesc("create a profile");
		this.addArgument(ArgumentString.get(), "name", true);
	}
	
	// { EXECUTE } //
	
	@Override
	public void execute() throws TransCommandException
	{
		String name = this.readArgument();
		
		if (ProfileCollection.get().has(name))
		{
			error("Profile with this name already exists.");
			return;
		}
		
		Profile profile = ProfileCollection.get().create(name);
		profile.setName(name);
		profile.setOwner( IUser.get(sender) );
		
		IUser.get( sender ).setActiveProfile(profile);
		
		message("You have created the profile <d>"+name+"<e>.");
	}
	
}
