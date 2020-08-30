package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.TransCommandVersion;

public class CmdProfile extends TransCommand
{
	
	// { SINGLETON } //
	
	private static CmdProfile i = new CmdProfile();
	public static CmdProfile get() { return CmdProfile.i; }
	
	// { CONSTRUCTOR } //
	
	public CmdProfile()
	{
		this.addAlias("profile");
		
		this.addSubCommand(CmdProfileCreate.get());
		this.addSubCommand(CmdProfileRemove.get());
		
		this.addSubCommand(CmdProfileSelect.get());
		
		this.addSubCommand(new TransCommandVersion( TransCore.get() ));
	}
	
}
