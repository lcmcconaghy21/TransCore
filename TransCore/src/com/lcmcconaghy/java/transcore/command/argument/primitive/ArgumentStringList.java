package com.lcmcconaghy.java.transcore.command.argument.primitive;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ArgumentStringList extends ArgumentAbstract<List<String>>
{
	
	// { SINGLETON } //
	
	private static ArgumentStringList i = new ArgumentStringList();
	public static ArgumentStringList get() { return ArgumentStringList.i; }
	
	// { ARGUMENT } //
	
	@Override
	public List<String> read(String arg0, CommandSender arg1)
	{
		List<String> ret = new ArrayList<String>();
		
		if (!arg0.contains(","))
		{
			ret.add(arg0);
			return ret;
		}
		
		if (arg0.startsWith(","))
		{
			arg0.substring(1);
		}
		if (arg0.endsWith(","))
		{
			arg0.substring(0, arg0.length()-1);
		}
		
		for (String part : arg0.split(","))
		{
			ret.add(part);
		}
		
		return ret;
	}
	@Override
	public List<String> getTabCompleteList()
	{
		return UtilGeneral.list("argumentOnepartOne,argumentTwopartOne",
				                "argumentOnepartTwo,argumentTwopartTwo");
	}
	
}
