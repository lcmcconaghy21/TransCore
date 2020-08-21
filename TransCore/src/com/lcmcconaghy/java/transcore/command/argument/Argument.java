package com.lcmcconaghy.java.transcore.command.argument;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.TransCommand;

public interface Argument<T>
{
	
	public T read(String arg0, CommandSender arg1);
	
	public List<String> getTabCompleteList();
	
	public Class<T> getType();
	
	public String getDisplay(TransCommand arg0);
	
	public boolean willConcat(TransCommand arg0);
}
