package com.lcmcconaghy.java.transcore.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.PermissionBase;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.command.argument.Argument;
import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class TransCommand implements Init
{
	
	// { FIELDS } //
	
	protected CommandSender sender;
	private boolean requiresPlayer = false;
	private String[] args;
	private int tracer = -1;
	private int concatCount = 0;
	private boolean hidden = false;
	private String label;
	private List<String> aliases = new ArrayList<String>();
	
	protected String desc;
	protected String permission;
	protected List<Argument<?>> arguments = new ArrayList<Argument<?>>();
	protected List<Boolean> required = new ArrayList<Boolean>();
	protected List<TransCommand> subCommands = new ArrayList<TransCommand>();
	
	protected TransPlugin plugin;
	private TransBukkitCommand internalCommand;
	protected TransCommand parent;
	
	// { EXECUTION } //
	
	public void executeOuter(CommandSender arg0, String[] args)
	{
		this.sender = arg0;
		this.args = args;
		
		if (doesRequirePlayer() && !isPlayer())
		{
			error("You must be a player in order to execute this command.");
			return;
		}
		
		// CHECK IF SENDER CAN EXECUTE COMMAND
		if (getPermissionNode()!=null && !hasPerm(sender))
		{
			error("You do not have permission to execute this command!");
			return;
		}
		
		// CHECK IF SUBCOMMANDS EXIST
		if ( isParent() )
		{
			// IF SENDER SENT NO SUBCOMMANDS
			if (args.length==0)
			{
				String[] helpArgs = new String[1];
				helpArgs[0] = "1";
				
				this.getHelpCommand().executeOuter(sender, helpArgs);
				return;
			}
			
			// IF SENDER STILL TECHNICALLY SENT NO SUBCOMMANDS
			if (args[0]==null || args[0]=="")
			{
				this.getHelpCommand().executeOuter(sender, args);
				return;
			}
			
			// IF SUBCOMMAND ENTERED DOES NOT EXIST
			if (getSubCommand(args[0])==null)
			{
				error("The command <a>"+args[0]+" <c>does not exist!");
				return;
			}
			
			// EXECUTE EXISTING SUBCOMMAND
			getSubCommand(args[0]).executeOuter(sender, getArgsMinus(args));
			return;
		}
		
		this.tracer = -1;
		this.concatCount = 0;
		
		try
		{
			execute();
		}
		catch (TransCommandException e)
		{
			error( Message.format( e.getMessage() ) );
			return;
		}
	}
	
	public void execute() throws TransCommandException
	{
		// main command block
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
					message.format().send(sender);
				}
			}
		});
	}
	
	/**
	 * Send the CommandSender a new Message
	 * @param arg0
	 */
	public void message(String arg0)
	{
		message(new Message("<e>"+arg0).format());
	}
	
	/**
	 * Report a Message to the CommandSender
	 * @param arg0
	 */
	public void error(String arg0)
	{
		message(new Message("<c>"+arg0).error());
	}
	
	/**
	 * @return whether User is a Player
	 */
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
		
		if ( this.getAliases().size() <= 0 )
		{
			System.out.println("Could not implement command. No aliases registered for command "
					          +this.getClass().getName());
			return;
		}
		
		this.internalCommand = new TransBukkitCommand(this);
		
		this.plugin = arg1;
		
		if (this.isParent())
		{
			this.addSubCommand(0, getHelpCommand());
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
	public void setPerm(PermissionBase arg0)
	{
		this.permission = arg0.getNode();
	}
	
	/**
	 * Check if CommandSender can execute command
	 * @return whether CommandSender can execute command
	 */
	public boolean hasPerm(CommandSender sender)
	{
		if (permission == null) return true;
		
		return sender.hasPermission(permission);
	}
	
	/**
	 * @return String Permission node
	 */
	public String getPermissionNode()
	{
		return this.permission;
	}
	
	// { REQUIRES } //
	
	/**
	 * Set that only Players can execute command
	 * @param arg0
	 */
	public void setRequiresPlayer(boolean arg0)
	{
		this.requiresPlayer = arg0;
	}
	
	/**
	 * @return whether only Players can execute a command
	 */
	public boolean doesRequirePlayer()
	{
		return this.requiresPlayer;
	}
	
	// { HIDE } //
	
	/**
	 * Toggle hidden command feature
	 * @param arg0 Boolean whether TransCommand is hidden
	 */
	public void setHidden(boolean arg0)
	{
		this.hidden = arg0;
	}
	
	/**
	 * @return whether TransCommand is hidden
	 */
	public boolean isHidden()
	{
		return this.hidden;
	}
	
	public boolean isHidden(CommandSender arg0)
	{
		if ( !isHidden() ) return false;
		
		if ( hasPerm(arg0) ) return false;
		
		return true;
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
	public void addArgument(ArgumentAbstract<?> arg0, String arg1, boolean arg2, boolean arg3)
	{
		arg0.setDisplay(arg1, this);
		arg0.setConcat(arg2, this);
		
		this.arguments.add(arg0);
		this.required.add(arg3);
	}
	
	/**
	 * Add an Argument
	 * @param arg0 Argument type
	 * @param arg1 String display name
	 * @param arg2 Boolean allow concat
	 * @throws TransCommandException 
	 */
	public void addArgument(ArgumentAbstract<?> arg0, String arg1, boolean arg2)
	{
		addArgument(arg0, arg1, arg2, false);
	}
	
	/**
	 * Add an Argument
	 * @param arg0 Argument type
	 * @param arg1 String display name
	 * @throws TransCommandException 
	 */
	public void addArgument(ArgumentAbstract<?> arg0, String arg1)
	{
		addArgument(arg0, arg1, false, false);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readArgument() throws TransCommandException
	{
		// IF PLUGIN REQUIRES NO ARGUMENTS
		if (arguments==null || arguments.size()<=0 || args.length==0 || args == null)
		{
			throw new TransCommandException("Improper syntax. Proper syntax: <b>/"+this.getFullCommand()+" "+this.getUsage());
		}
		
		// IF SENDER HAS NOT SENT ALL REQUIRED ARGUMENTS
		if (args.length <= tracer)
		{
			throw new TransCommandException("Unable to parse. Too few arguments. Proper syntax: <b>/"+this.getFullCommand()+" "+this.getUsage());
		}
		
		Argument<T> arg = (Argument<T>) getNextArgument();
		
		// IF NEXT ARGUMENT DOES NOT EXIST
		if (arg == null)
		{
			throw new TransCommandException("No argument for position "+(this.tracer+1)+". Proper syntax: <b>/"+this.getFullCommand()+" "+this.getUsage());
		}
		
		// IF ARGUMENT WILL BE EXECUTED WITH ALL FOLLOWING PARAMETERS
		if (arg.willConcat(this))
		{
			int remove = tracer-1;
			if (remove<0) remove = 0;
			
			String[] parts = new String[ (args.length) - remove ];
			
			for (int i = tracer; i<this.args.length; i++)
			{
				parts[concatCount] = args[i];
				concatCount++;
			}
			
			String complete = StringUtils.join(parts, " ");
			
			return arg.read(complete, sender);
		}
		
		return arg.read(args[tracer], sender);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readArgument(T arg0) throws TransCommandException
	{
		// IF NO ARGUMENTS WERE SENT BY SENDER
		if (args.length<=0)
		{
			return arg0;
		}
		
		// IF ARGUMENTS ARE MISSING
		if ((args.length<arguments.size() || args==null) && arguments.size()>=tracer)
		{
			return arg0;
		}
		
		Argument<T> arg = (Argument<T>) getNextArgument();
		
		return arg.read(args[tracer], sender);
	}
	
	public Argument<?> getArgumentAt(int arg0)
	{
		// IF GIVEN ARGUMENTS ARE NULL (OR EQUAL TO ZERO)
		if (this.arguments==null || this.arguments.size()<=0) return null;
		
		// IF THERE ARE NO ARGUMENTS AT DEFINED POSITION
		if (this.arguments.size() <= arg0) return null;
		
		return this.arguments.get(arg0);
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
	
	public List<TransCommand> getVisibleSubCommands(CommandSender sender)
	{
		List<TransCommand> commands = new ArrayList<TransCommand>();
		
		for (TransCommand command : this.subCommands)
		{
			if ( command.isHidden(sender) )
			{
				continue;
			}
			
			commands.add(command);
		}
		
		return commands;
	}
	
	// { ALIASES } //
	
	public void addAliases(String...args)
	{
		for (int i = 0; i<args.length; i++)
		{
			if (this.label == null && i==0)
			{
				this.label = args[i];
			}
			
			this.aliases.add(args[i]);
		}
	}
	
	public void addAlias(String arg0)
	{
		addAliases(arg0);
	}
	
	public List<String> getAliases()
	{
		return this.aliases;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	// { INFORMATION } //
	
	public void setDesc(String arg0)
	{
		this.desc = arg0;
	}
	
	public String getDesc()
	{
		return this.desc;
	}
	
	public String getUsage()
	{
		String ret = "";
		
		if (this.isParent()) return ret;
		
		for (int i = 0; i<this.arguments.size(); i++)
		{
			Argument<?> arg = this.arguments.get(i);
			
			// CHECK IF IS NOT REQUIRED
			if (this.required.get(i) == false) ret += " {"+arg.getDisplay(this)+"}";
			
			// IF IS REQUIRED
			else ret += " ["+arg.getDisplay(this)+"]";
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
		return new TransCommandHelp(this);
	}
	
	public TransCommand getSubCommand(String arg0)
	{
		TransCommand ret = null;
		
		for (TransCommand cmds : this.subCommands)
		{
			if ( !cmds.aliases.contains(arg0) ) continue;
			
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
	
	public String getFullCommand() throws TransCommandException
	{
		TransCommand intParent = this.parent;
		String ret = this.getLabel();
		
		while (intParent != null)
		{
			if (intParent.getLabel() == null)
			{
				throw new TransCommandException("No aliases registered for command "+intParent.getClass().getName());
			}
			
			ret = intParent.getLabel()+" "+ret;
			
			intParent = intParent.parent;
		}
		
		return ret;
	}
	
	
}
