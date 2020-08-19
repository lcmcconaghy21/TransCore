package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;

public class ArgumentString extends ArgumentAbstract<String>
{
	
	// { CONSTRUCTOR } //
	
	public ArgumentString()
	{
		super(String.class);
	}
	
	// { ARG } //
	
	public String read(String arg0, CommandSender arg1)
	{
		return arg0;
	}

	public List<String> getTabCompleteList()
	{
		return new ArrayList<String>();
	}
	
}
