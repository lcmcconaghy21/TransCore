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
	private List<T> coll;
	
	// { CONSTRUCTOR } //
	
	/**
	 * @param arg0 String title
	 * @param args Collection of generic type
	 */
	public PagerAbstract(String arg0, List<T> args)
	{
		this.name = arg0;
		this.coll = args;
		int key = 1;
		int last = 0;
		
		if (coll.size()<=5)
		{
			List<T> line = new ArrayList<T>();
			
			for (int i = 0; i<args.size(); i++)
			{
				line.add(args.get(i));
			}
			this.lines.put(0, line);
			
			return;
		}
		
		while (key<coll.size()/5)
		{
			List<T> line = new ArrayList<T>();
			
			for (int v = 0; v<5; v++)
			{
				if (args.size()>=last) break;
				
				line.add(args.get(last));
				last++;
			}
			
			this.lines.put(key, line);
			key++;
			
			if (args.size()>=last) break;
		}
		
	}
	
	// { MESSAGE } //
	
	/**
	 * @param arg0 Integer page name
	 * @return
	 */
	public void sendPage(int arg0, TransCommand arg1, CommandSender sender)
	{
		final Message title = UtilGeneral.titleize(this.name);
		
		final List<Message> contents = new ArrayList<Message>();
		
		arg0 = arg0-1;
		
		if (arg0>=this.lines.size() || arg0<0)
		{
			new Message("There are only <b>"+this.lines.size()+" <c>pages in this category!").error()
			           .send(sender);
			return;
		}
		
		for (T ts : this.lines.get(arg0))
		{
			contents.add(sendLine(ts));
		}
		
		int nextPage = arg0+1;
		int prevPage = arg0-1;
		
		Message left = new Message("[<]").color(ChatColor.GRAY);
		Message right = new Message("[>]").color(ChatColor.GRAY);
		
		if (this.lines.get(prevPage)!=null) left = left.color(ChatColor.AQUA)
		                                               .command(arg1, ""+prevPage);
		if (this.lines.get(nextPage)!=null) right = right.color(ChatColor.AQUA)
				                                       .command(arg1, ""+nextPage);
		
		final Message bottom = new Message(new Message("=").repeat(15).color(ChatColor.GREEN),
				  left,
				  right,
				  new Message("=").repeat(15).color(ChatColor.GREEN));
		
		Bukkit.getScheduler().runTaskAsynchronously(TransCore.get(), new Runnable()
		{
			@Override
			public void run()
			{
				title.send(sender);
				
				for (Message content : contents)
				{
					content.send(sender);
				}
				
				bottom.send(sender);
			}
		});
	}
	
}
