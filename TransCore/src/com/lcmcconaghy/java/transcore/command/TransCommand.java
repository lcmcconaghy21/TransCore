package com.lcmcconaghy.java.transcore.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.Perm;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.command.argument.Argument;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;

public class TransCommand implements Init
{
	
	// { FIELDS } //
	
	protected CommandSender sender;
	protected String[] args;
	private int tracer = -1;
	
	protected List<String> aliases = new ArrayList<String>();
	protected String desc;
	protected String permission;
	protected Map<Argument<?>,Boolean> arguments = new HashMap<Argument<?>,Boolean>();
	protected List<TransCommand> subCommands = new ArrayList<TransCommand>();
	
	protected TransPlugin plugin;
	private TransBukkitCommand internalCommand;
	protected TransCommand parent;
	protected TransCommandHelp help;
	
	// { CONSTRUCTOR } //
	
	public TransCommand(String...aliases) throws TransCommandException
	{
		for (String alias : aliases)
		{
			this.aliases.add(alias);
		}
		
		if (this.aliases.size()<=0)
		{
			throw new TransCommandException("No aliases provided for command in class "+this.getClass().getName());
		}
		
		this.internalCommand = new TransBukkitCommand(this);
		this.internalCommand.setLabel(aliases[0]);
	}
	
	// { EXECUTION } //
	
	public void executeOuter(CommandSender arg0, String[] args)
	{
		this.sender = arg0;
		this.args = args;
		
		if (getPermissionNode()!=null && !hasPerm())
		{
			error("You do not have permission to execute this command!");
			return;
		}
		
		if (isParent())
		{
			if (this.getHelpCommand()==null)
			{
				error("Help command is null, please contact the developer.");
				return;
			}
			
			if (args.length==0)
			{
				this.getHelpCommand().executeOuter(sender, new String[0]);
				return;
			}
			
			if (args[0]==null || args[0]=="")
			{
				this.getHelpCommand().executeOuter(sender, args);
				return;
			}
			
			if (getSubCommand(args[0])==null)
			{
				error("The command <a>"+args[0]+" <c>does not exist!");
				return;
			}
			
			getSubCommand(args[0]).executeOuter(sender, getArgsMinus(args));
			return;
		}
		
		this.tracer = -1;
		
		try
		{
			execute();
		}
		catch (TransCommandException e)
		{
			e.printStackTrace();
		}
	}
	
	public void execute() throws TransCommandException
	{
		// main command block
	}
	
	@SuppressWarnings("deprecation")
	public void message(Message arg0)
	{
		if (!isPlayer())
		{
			plugin.log(arg0.getText());
			return;
		}
		
		Player player = (Player) sender;
		arg0.send(player);
	}
	
	public void message(final Message... args)
	{
		Bukkit.getScheduler().runTaskAsynchronously(TransCore.get(), new Runnable()
		{
			@Override
			public void run()
			{
				for (Message message : args)
				{
					message.send(sender);
				}
			}
		});
	}
	
	public void message(String arg0)
	{
		message(new Message(arg0).parse());
	}
	
	public void error(String arg0)
	{
		message(new Message("<c>"+arg0).error());
	}
	
	public boolean isPlayer()
	{
		return (sender instanceof Player);
	}
	
	public String[] getArgsMinus(String[] args)
	{
		if (args.length==0) return new String[0];
		
		String[] ret = new String[args.length-1];
		
		for (int i = 1; i<args.length; i++)
		{
			ret[i-1] = args[i];
		}
		
		return ret;
	}
	
	// { INIT } //
	
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		if (this.isParent())
		{
			try
			{
				this.help = new TransCommandHelp(this);
			}
			catch (TransCommandException e)
			{
				e.printStackTrace();
			}
			
			this.addSubCommand(0, help);
		}
		
		TransServer.get().registerCommand(this);
	}

	public boolean isInitialized()
	{
		return TransServer.get().hasCommand(this);
	}
	
	// { PERM } //
	
	/**
	 * Set permission for command
	 * @param arg0 Perm permission
	 */
	public void setPerm(Perm arg0)
	{
		this.permission = arg0.getNode();
	}
	
	/**
	 * Check if CommandSender can execute command
	 * @return whether CommandSender can execute command
	 */
	public boolean hasPerm()
	{
		return this.sender.hasPermission(permission);
	}
	
	/**
	 * @return String Permission node
	 */
	public String getPermissionNode()
	{
		return this.permission;
	}
	
	// { ARGS } //
	
	/**
	 * Add an Argument
	 * @param arg0 Argument type
	 * @param arg1 String display name
	 * @param arg2 Boolean allow concat
	 * @param arg3 Boolean has default value
	 * @throws TransCommandException 
	 */
	public void addArgument(ArgumentAbstract<?> arg0, String arg1, boolean arg2, boolean arg3) throws TransCommandException
	{
		int lastPlace = this.arguments.keySet().size()-2;
		
		if (getArgumentAt(lastPlace)!=null && getArgumentAt(lastPlace).willConcat())
		{
			throw new TransCommandException("Cannot add Argument after a concat Argument!");
		}
		
		arg0.setDisplay(arg1);
		arg0.setConcat(arg2);
		
		this.arguments.put(arg0, arg3);
	}
	
	/**
	 * Add an Argument
	 * @param arg0 Argument type
	 * @param arg1 String display name
	 * @param arg2 Boolean allow concat
	 * @throws TransCommandException 
	 */
	public void addArgument(ArgumentAbstract<?> arg0, String arg1, boolean arg2) throws TransCommandException
	{
		addArgument(arg0, arg1, arg2, false);
	}
	
	/**
	 * Add an Argument
	 * @param arg0 Argument type
	 * @param arg1 String display name
	 * @throws TransCommandException 
	 */
	public void addArgument(ArgumentAbstract<?> arg0, String arg1) throws TransCommandException
	{
		addArgument(arg0, arg1, false, false);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readArgument() throws TransCommandException
	{
		if (arguments==null || arguments.size()<=0)
		{
			throw new TransCommandException("Arguments are null, cannot read them dimwit.");
		}
		
		Argument<T> arg = (Argument<T>) getNextArgument();
		
		return arg.read(args[tracer], sender);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readArgument(T arg0)
	{
		if (args.length<=0)
		{
			return arg0;
		}
		
		if ((args.length<arguments.size() || args==null) && arguments.size()>=tracer)
		{
			return arg0;
		}
		
		Argument<T> arg = (Argument<T>) getNextArgument();
		
		return arg.read(args[tracer], sender);
	}
	
	public Argument<?> getArgumentAt(int arg0)
	{
		Argument<?> ret = null;
		int count = -1;
		
		if (arg0<=count) return null;
		
		for (Argument<?> arg : this.arguments.keySet())
		{
			count++;
			
			if (count!=arg0) continue;
			
			ret = arg;
			break;
		}
		
		return ret;
	}
	
	public Argument<?> getNextArgument()
	{
		this.tracer++;
		
		return getArgumentAt(this.tracer);
	}
	
	// { COMMANDS } //
	
	public void addSubCommand(int arg0, TransCommand arg1)
	{
		this.subCommands.add(arg0, arg1);
		arg1.parent = this;
	}
	
	public void addSubCommand(TransCommand arg0)
	{
		this.subCommands.add(arg0);
		arg0.parent = this;
	}
	
	public List<TransCommand> getSubCommands()
	{
		return this.subCommands;
	}
	
	// { INFORMATION } //
	
	public void setDesc(String arg0)
	{
		this.desc = arg0;
	}
	
	public String getLabel()
	{
		return this.internalCommand.getLabel();
	}
	
	public String getDesc()
	{
		return this.desc;
	}
	
	public String getUsage()
	{
		String ret = "";
		
		if (this.isParent()) return ret;
		
		for (Argument<?> arg : this.arguments.keySet())
		{
			if (arguments.get(arg) == false) ret += " {"+arg.getDisplay()+"}";
			else ret += " ["+arg.getDisplay()+"]";
		}
		
		return ret;
	}
	
	// { GETTERS } //
	
	public TransBukkitCommand getBukkitCommand()
	{
		return this.internalCommand;
	}
	
	public TransCommandHelp getHelpCommand()
	{
		return this.help;
	}
	
	public TransCommand getSubCommand(String arg0)
	{
		TransCommand ret = null;
		
		for (TransCommand cmds : this.subCommands)
		{
			if (!cmds.aliases.contains(arg0)) continue;
			
			ret = cmds;
			break;
		}
		
		return ret;
	}
	
	public Plugin getPlugin()
	{
		return this.plugin;
	}
	
	public boolean isParent()
	{
		return !this.subCommands.isEmpty();
	}
	
	public boolean hasParent()
	{
		return this.parent!=null;
	}
	
	public TransCommand getParent()
	{
		return this.parent;
	}
	
	public String getFullCommand()
	{
		TransCommand intParent = this.parent;
		String ret = this.getLabel();
		
		while (intParent!=null)
		{
			ret = intParent.aliases.get(0)+" "+ret;
			
			intParent = intParent.parent;
		}
		
		return ret;
	}
	
	
}