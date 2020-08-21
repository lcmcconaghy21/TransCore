package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentDouble extends ArgumentAbstract<Double>
{
	// { SINGLETON } //
	
	private static ArgumentDouble i = new ArgumentDouble();
	public static ArgumentDouble get() { return ArgumentDouble.i; }
	
	// { CONSTRUCTOR } //
	
	public ArgumentDouble()
	{
		super(Double.class);
	}
	
	// { ARGUMENT } //
	
	@Override
	public Double read(String arg0, CommandSender arg1)
	{
		return Double.parseDouble(arg0);
	}

	@Override
	public List<String> getTabCompleteList()
	{
		return UtilGeneral.list("1");
	}
	
}
