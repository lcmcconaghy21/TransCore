package com.lcmcconaghy.java.transcore.cmd;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentProfile;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class CmdProfileSelect extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdProfileSelect()
	{
		super("select");
		
		this.setPerm(TransPerm.PROFILE_SELECT);
		this.setDesc("play as a profile");
		this.addArgument(ArgumentProfile.get(), "name", true);
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		if ( !isPlayer() )
		{
			error("You must be a player to execute that command.");
			return;
		}
		
		Profile profile = this.readArgument();
		
		if ( !profile.getOwner().getUniqueId().toString()
				     .equalsIgnoreCase( ((Player)sender).getUniqueId().toString() ) )
		{
			error("You do not own this profile.");
			return;
		}
		
		IUser.get(sender).setActiveProfile(profile);
		
		message("You are now playing as <a>"+profile.getName()+"<e>.");
	}
}
