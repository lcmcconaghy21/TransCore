package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentInteger extends ArgumentAbstract<Integer>
{
	// { SINGLETON } //
	
	private static ArgumentInteger i = new ArgumentInteger();
	public static ArgumentInteger get() { return ArgumentInteger.i; }
	
	// { ARG } //

	public Integer read(String arg0, CommandSender arg1)
	{
		return Integer.valueOf(arg0);
	}

	public List<String> getTabCompleteList()
	{
		return UtilGeneral.list("1");
	}
	
}
