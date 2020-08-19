package com.lcmcconaghy.java.transcore.command.argument;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface Argument<T>
{
	
	public T read(String arg0, CommandSender arg1);
	
	public List<String> getTabCompleteList();
	
	public Class<T> getType();
	
	public String getDisplay();
	
	public boolean willConcat();
}
