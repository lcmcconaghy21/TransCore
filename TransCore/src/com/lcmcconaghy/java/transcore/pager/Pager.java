package com.lcmcconaghy.java.transcore.pager;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.Message;

public interface Pager<T>
{
	
	public Message sendLine(T arg0, CommandSender arg1);
	
}
