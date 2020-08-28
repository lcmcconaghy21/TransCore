package com.lcmcconaghy.java.transcore.pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

import net.md_5.bungee.api.ChatColor;

public abstract class PagerAbstract<T> implements Pager<T>
{
	
	// { FIELDS } //
	
	private String name;
	private Map<Integer, List<T>> lines = new HashMap<Integer, List<T>>();
	
	// { CONSTRUCTOR } //
	
	/**
	 * @param arg0 String title
	 * @param args Collection of generic type
	 */
	public PagerAbstract(String arg0, List<T> args)
	{
		this.name = arg0;
		int last = 0;
		
		for (int k = 0; k < args.size()/5; k++)
		{
			if (args.size() <= last) break;
			
			for (int v = 0; v < 5; v++)
			{
				if (args.size() <= last) break;
				
				T obj = args.get(last);
				
				List<T> list = new ArrayList<T>();
				
				if (this.lines.containsKey(k))
				{
					list = this.lines.get(0);
				}
				
				list.add(obj);
				
				this.lines.put(k, list);
				last++;
			}
		}
		
	}
	
	// { MESSAGE } //
	
	/**
	 * @param arg0 Integer page name
	 * @return
	 */
	public void sendPage(int arg0, TransCommand arg1, CommandSender arg2)
	{
		final Message title = UtilGeneral.titleize(this.name);
		
		final List<Message> contents = new ArrayList<Message>();
		
		arg0 = arg0-1;
		
		if (arg0>=this.lines.size() || arg0<0)
		{
			new Message("There are only <b>"+this.lines.size()+" <c>pages in this category!").error()
			           .send(arg2);
			return;
		}
		
		for (T ts : this.lines.get(arg0))
		{
			Message line = sendLine(ts, arg2);
			
			if (line == null) continue;
			
			contents.add(line);
		}
		
		int nextPage = arg0+1;
		int prevPage = arg0-1;
		
		Message left = new Message("[<]").color(ChatColor.GRAY);
		Message right = new Message("[>]").color(ChatColor.GRAY);
		
		if (this.lines.get(prevPage)!=null) left = left.color(ChatColor.AQUA)
		                                               .command(arg1, ""+prevPage);
		if (this.lines.get(nextPage)!=null) right = right.color(ChatColor.AQUA)
				                                       .command(arg1, ""+nextPage);
		
		final Message bottom = new Message(new Message("=").repeat(3).color(ChatColor.GREEN),
				  left,
				  right,
				  new Message("=").repeat(3).color(ChatColor.GREEN));
		
		Bukkit.getScheduler().runTaskAsynchronously(TransCore.get(), new Runnable()
		{
			@Override
			public void run()
			{
				title.send(arg2);
				
				for (Message content : contents)
				{
					content.send(arg2);
				}
				
				bottom.send(arg2);
			}
		});
	}
	
}
