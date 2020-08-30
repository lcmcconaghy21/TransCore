package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentDouble extends ArgumentAbstract<Double>
{
	// { SINGLETON } //
	
	private static ArgumentDouble i = new ArgumentDouble();
	public static ArgumentDouble get() { return ArgumentDouble.i; }
	
	// { ARGUMENT } //
	
	@Override
	public Double read(String arg0, CommandSender arg1) throws TransCommandException
	{
		double ret = 1.0;
		try
		{
			ret = Double.parseDouble(arg0);
		}
		catch (NumberFormatException exception)
		{
			throw new TransCommandException("Could not format <d>"+arg0+" <c>into a number.");
		}
		
		return ret;
	}

	@Override
	public List<String> getTabCompleteList()
	{
		return UtilGeneral.list("1");
	}
	
}
