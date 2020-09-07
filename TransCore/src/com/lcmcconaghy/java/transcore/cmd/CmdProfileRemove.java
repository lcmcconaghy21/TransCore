package com.lcmcconaghy.java.transcore.cmd;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentProfileSelf;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class CmdProfileRemove extends TransCommand
{
	
	// { SINGLETON } //
	
	private static CmdProfileRemove i = new CmdProfileRemove();
	public static CmdProfileRemove get() { return CmdProfileRemove.i; }
	
	// { CONSTRUCTOR } //
	
	public CmdProfileRemove()
	{
		this.addAlias("remove");
		
		this.setRequiresPlayer(true);
		this.setPerm(TransPerm.PROFILE_REMOVE);
		this.setDesc("detach a profile from yourself");
		this.addArgument(ArgumentProfileSelf.get(), "name", true);
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
		
		profile.drop();
		
		message("You have removed the profile <d>"+profile.getName()+"<e>.");
	}
	
}
