package com.lcmcconaghy.java.transcore.command.argument;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public interface Argument<T>
{
	
	public T read(String arg0, CommandSender arg1) throws TransCommandException;
	
	public List<String> getTabCompleteList(CommandSender arg0);
	
	public String getDisplay(TransCommand arg0);
	
	public boolean willConcat(TransCommand arg0);
}
