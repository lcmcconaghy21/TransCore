package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentBoolean extends ArgumentAbstract<Boolean>
{
	// { SINGLETON } //
	
	private static ArgumentBoolean i = new ArgumentBoolean();
	public static ArgumentBoolean get() { return ArgumentBoolean.i; }
	
	// { ARGUMENT } //
	
	@Override
	public Boolean read(String arg0, CommandSender arg1)
	{
		return Boolean.valueOf(arg0);
	}

	@Override
	public List<String> getTabCompleteList()
	{
		return UtilGeneral.list("true", "false");
	}
	
}
