package com.lcmcconaghy.java.transcore.pager;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.command.TransCommand;

import net.md_5.bungee.api.ChatColor;

public class PagerHelpCommand extends PagerAbstract<TransCommand>
{
	
	// CONSTRUCTOR //
	
	public PagerHelpCommand(TransCommand command)
	{
		super(command.getLabel()+" Help", command.getSubCommands());
	}
	
	// { LINES } //
	
	public Message sendLine(TransCommand arg0, CommandSender arg1)
	{
		if (arg0.isHidden())
		{
			return null;
		}
		
		return new Message(new Message("/"+arg0.getFullCommand()).color(ChatColor.AQUA),
				           new Message(arg0.getUsage()).color(ChatColor.BLUE),
				           new Message(": "+arg0.getDesc()).color(ChatColor.YELLOW));
	}
	
}
