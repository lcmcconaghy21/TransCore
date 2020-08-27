package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.TransCommandVersion;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class CmdProfile extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdProfile() throws TransCommandException
	{
		super("profile");
		
		this.addSubCommand(new CmdProfileCreate());
		this.addSubCommand(new CmdProfileRemove());
		
		this.addSubCommand(new TransCommandVersion( TransCore.get() ));
	}
	
}
