package com.lcmcconaghy.java.transcore.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.argument.Argument;

public class TransBukkitCommand extends BukkitCommand
{
	
	// { FIELDS } //
	
	protected TransCommand command;
	
	// { CONSTRUCTOR } //
	
	@SuppressWarnings("unused")
	public TransBukkitCommand(TransCommand arg0)
	{
		super(arg0.aliases.get(0));
		
		if (arg0==null) TransCore.get().log("For some reason, the parameter is null.");
		
		this.command = arg0;
		
		if (this.command==null) TransCore.get().log("The command is null for some reason.");
	}
	
	// { EXECUTION } //

	@Override
	public boolean execute(CommandSender arg0, String arg1, String[] args)
	{
		this.command.executeOuter(arg0, args);
		
		return true;
	}
	
	// { GETTERS } //
	
	@Override
	public List<String> getAliases()
	{
		return this.command.aliases;
	}
	
	// { TAB COMPLETE } //
	
	@Override
	public List<String> tabComplete(CommandSender arg0, String arg1, String[] args)
	{	
		TransCommand base = this.command;
		
		if (base==null) return new ArrayList<String>();
		
		if (!base.isParent() && base.arguments.size()<=0)
		{
			return new ArrayList<String>();
		}
		
		while (args.length>1)
		{
			if (!base.isParent()) break;
			
			base = base.getSubCommand(args[0]);
			
			String[] repl = new String[args.length-1];
			
			if (args.length<=0)
			{
				args = new String[0];
				break;
			}
			
			for (int i = 1; i<args.length; i++)
			{
				repl[i-1] = args[i];
			}
			
			args = repl;
		}
		
		if (base.isParent())
		{
			List<String> aliases = new ArrayList<String>();
			
			for (TransCommand commands : base.subCommands)
			{
				aliases.addAll(commands.aliases);
			}
			
			return aliases;
		}
		
		Argument<?> argument = null;
		
		try
		{
			argument = base.getArgumentAt(args.length-1);
		}
		catch (Exception e)
		{
			return new ArrayList<String>();
		}
		
		if (argument==null)
		{
			return new ArrayList<String>();
		}
		
		return argument.getTabCompleteList();
	}
}
