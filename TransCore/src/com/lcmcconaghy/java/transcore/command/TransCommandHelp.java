package com.lcmcconaghy.java.transcore.command;

import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentInteger;
import com.lcmcconaghy.java.transcore.pager.PagerHelpCommand;

public class TransCommandHelp extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public TransCommandHelp(TransCommand arg0) throws TransCommandException
	{
		super("help", "?");
		
		this.parent = arg0;
		
		this.setDesc("display all commands");
		
		this.addArgument(new ArgumentInteger(), "page", false, true);
	}
	
	// { EXECUTE } //
	
	@Override
	public void execute() throws TransCommandException
	{
		int page = this.readArgument(1);
		
		PagerHelpCommand pager = new PagerHelpCommand(this.parent);
		
		pager.sendPage(page, this, sender);
	}
}
