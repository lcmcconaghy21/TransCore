package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.TransCommandException;
import com.lcmcconaghy.java.transcore.command.TransCommandVersion;

public class CmdTransCore extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdTransCore() throws TransCommandException
	{
		super("transcore", "trans");
		
		this.addSubCommand(new TransCommandVersion(TransCore.get()));
	}
	
}
