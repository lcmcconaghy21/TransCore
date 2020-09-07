package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentInteger extends ArgumentAbstract<Integer>
{
	// { SINGLETON } //
	
	private static ArgumentInteger i = new ArgumentInteger();
	public static ArgumentInteger get() { return ArgumentInteger.i; }
	
	// { ARG } //
	
	@Override
	public Integer read(String arg0, CommandSender arg1) throws TransCommandException
	{
		int ret = 1;
		
		try
		{
			ret = Integer.valueOf(arg0);
		}
		catch (NumberFormatException exception)
		{
			throw new TransCommandException("Could not format <d>"+arg0+" <c>as integer.");
		}
		
		return ret;
	}
	
	@Override
	public List<String> getTabCompleteList(CommandSender arg0)
	{
		return UtilGeneral.list("1");
	}
	
}
