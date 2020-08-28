package com.lcmcconaghy.java.transcore.command;

import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentInteger;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.pager.PagerHelpCommand;

public class TransCommandHelp extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public TransCommandHelp(TransCommand arg0)
	{
		this.addAliases("help", "?");
		
		this.parent = arg0;
		
		this.setDesc("display all commands");
		
		this.addArgument(ArgumentInteger.get(), "page", false, true);
	}
	
	// { EXECUTE } //
	
	@Override
	public void execute() throws TransCommandException
	{
		int page = this.readArgument(1);
		
		PagerHelpCommand pager = new PagerHelpCommand(this.parent, sender);
		
		pager.sendPage(page, this, sender);
	}
}
