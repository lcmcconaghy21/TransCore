package com.lcmcconaghy.java.transcore.cmd;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentProfile;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class CmdProfileRemove extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdProfileRemove() throws TransCommandException
	{
		super("remove");
		
		this.setPerm(TransPerm.PROFILE_REMOVE);
		this.setDesc("detach a profile from yourself");
		this.addArgument(ArgumentProfile.get(), "name", true);
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		if ( !isPlayer() )
		{
			error("You must be a player to execute this command.");
			return;
		}
		
		Profile profile = this.readArgument();
		
		if ( !profile.getOwner().getUniqueId().toString()
				     .equalsIgnoreCase( ((Player)sender).getUniqueId().toString() ) )
		{
			error("You do not own this profile.");
			return;
		}
		
		profile.drop();
		
		message("You have removed the profile <d>"+profile.getName()+"<e>.");
	}
	
}
