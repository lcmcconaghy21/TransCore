package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.TransCommandVersion;

public class CmdProfile extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdProfile()
	{
		this.addAlias("profile");
		
		this.addSubCommand(new CmdProfileCreate());
		this.addSubCommand(new CmdProfileRemove());
		
		this.addSubCommand(new CmdProfileSelect());
		
		this.addSubCommand(new TransCommandVersion( TransCore.get() ));
	}
	
}
