package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class CmdUpdate extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdUpdate() throws TransCommandException
	{
		super("update");
		
		this.setDesc("update version");
		
		this.addSubCommand(new CmdUpdateMajor());
		this.addSubCommand(new CmdUpdateMinor());
		this.addSubCommand(new CmdUpdateBuild());
	}
	
}
