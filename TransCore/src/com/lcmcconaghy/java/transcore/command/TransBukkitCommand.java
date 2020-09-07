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
		super(arg0.getLabel());
		
		if (arg0 == null) TransCore.get().log("For some reason, the parameter is null.");
		
		this.command = arg0;
		
		if (this.command == null) TransCore.get().log("The command is null for some reason.");
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
		return this.command.getAliases();
	}
	
	// { TAB COMPLETE } //
	
	@Override
	public List<String> tabComplete(CommandSender arg0, String arg1, String[] args)
	{	
		TransCommand base = this.command;
		
		// IF A CORE COMMAND CANNOT BE FOUND, THERE ARE NO ARGUMENTS
		if (base == null) return new ArrayList<String>();
		
		// IF COMMAND IS NOT A PARENT AND HAS NO ARGUMENTS,
		// MEANING THAT IT ONLY NEEDS TO BE CALLED TO PERFORM
		// ITS FUNCTIONS
		if (!base.isParent() && base.arguments.size()<=0)
		{
			return new ArrayList<String>();
		}
		
		// WHILE ARGUMENTS TO BASE EXIST
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
		
		// IF SUBCOMMAND DOES NOT EXIST
		if (base == null) return new ArrayList<String>();
		
		// IF MORE SUBCOMMANDS EXIST...
		if (base.isParent())
		{
			List<String> aliases = new ArrayList<String>();
			
			//... ADD ALL OF THEIR ALIASES AS TABCOMPLETE OPTIONS
			for (TransCommand commands : base.subCommands)
			{
				aliases.addAll(commands.getAliases());
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
		
		// IF NO REGISTERED ARGUMENT EXISTS FOR THAT POSITION...
		if (argument==null)
		{
			//... RETURN NO TABCOMPLETE OPTIONS
			return new ArrayList<String>();
		}
		
		return argument.getTabCompleteList(arg0);
	}
}
