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
	// { SINGLETON } //
	
	private static CmdProfileSelect i = new CmdProfileSelect();
	public static CmdProfileSelect get() { return CmdProfileSelect.i; }
	
	// { CONSTRUCTOR } //
	
	public CmdProfileSelect()
	{
		this.addAlias("select");
		
		this.setRequiresPlayer(true);
		this.setPerm(TransPerm.PROFILE_SELECT);
		this.setDesc("play as a profile");
		this.addArgument(ArgumentProfile.get(), "name", true);
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{	
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
