package com.lcmcconaghy.java.transcore.pager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public abstract class PagerAbstract<T> implements Pager<T>
{
	
	// { FIELDS } //
	
	private String name;
	private Collection<T> coll;
	
	// { CONSTRUCTOR } //
	
	/**
	 * @param arg0 String title
	 * @param args Collection of generic type
	 */
	public PagerAbstract(String arg0, List<T> args)
	{
		this.name = arg0;
		this.coll = args;
	}
	
	// { MESSAGE } //
	
	/**
	 * @param arg0 Integer page name
	 * @return
	 */
	public void sendPage(TransCommand arg0, CommandSender arg1)
	{
		final Message title = UtilGeneral.titleize(this.name);
		
		final List<Message> contents = new ArrayList<Message>();
		
		for (T ts : this.coll)
		{
			Message line = sendLine(ts, arg1);
			
			if (line == null) continue;
			
			contents.add(line);
		}
		
		Bukkit.getScheduler().runTaskAsynchronously(TransCore.get(), new Runnable()
		{
			@Override
			public void run()
			{
				title.send(arg1);
				
				for (Message content : contents)
				{
					content.send(arg1);
				}
			}
		});
	}
	
}
