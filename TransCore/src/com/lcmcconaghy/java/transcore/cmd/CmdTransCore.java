package com.lcmcconaghy.java.transcore.cmd;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.TransCommandReload;
import com.lcmcconaghy.java.transcore.command.TransCommandVersion;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class CmdTransCore extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdTransCore() throws TransCommandException
	{
		super("transcore", "trans");
		
		
		// Default subs
		this.addSubCommand(new TransCommandReload(TransCore.get()));
		this.addSubCommand(new TransCommandVersion(TransCore.get()));
	}
	
}
