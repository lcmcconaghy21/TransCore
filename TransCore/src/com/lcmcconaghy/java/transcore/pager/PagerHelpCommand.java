package com.lcmcconaghy.java.transcore.pager;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

import net.md_5.bungee.api.ChatColor;

public class PagerHelpCommand extends PagerAbstract<TransCommand>
{
	
	// CONSTRUCTOR //
	
	public PagerHelpCommand(TransCommand command, CommandSender sender)
	{
		super(command.getLabel()+" Help", command.getVisibleSubCommands(sender));
	}
	
	// { LINES } //
	
	public Message sendLine(TransCommand arg0, CommandSender arg1)
	{
		if (arg0.isHidden())
		{
			return null;
		}
		
		String fullCommand = "";
		
		try
		{
			fullCommand = arg0.getFullCommand();
		}
		catch (TransCommandException e)
		{
			e.printStackTrace();
		}
		
		return new Message(new Message("/"+fullCommand).color(ChatColor.AQUA),
				           new Message(arg0.getUsage()).color(ChatColor.BLUE),
				           new Message(": "+arg0.getDesc()).color(ChatColor.YELLOW));
	}
	
}
