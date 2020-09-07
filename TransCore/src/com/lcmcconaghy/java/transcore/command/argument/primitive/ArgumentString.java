package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class ArgumentString extends ArgumentAbstract<String>
{
	// { SINGLETON } //
	
	private static ArgumentString i = new ArgumentString();
	public static ArgumentString get() { return ArgumentString.i; }
	
	// { ARG } //
	
	@Override
	public String read(String arg0, CommandSender arg1) throws TransCommandException
	{
		return arg0;
	}
	
	@Override
	public List<String> getTabCompleteList(CommandSender arg0)
	{
		return new ArrayList<String>();
	}
	
}
