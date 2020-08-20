package com.lcmcconaghy.java.transcore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lcmcconaghy.java.transcore.Message;

import net.md_5.bungee.api.ChatColor;

public class UtilGeneral
{
	// { TEXT TOOLS } //
	
	public static Message titleize(String arg)
	{
		String side = repeat("=", 13-(arg.length()/2));
		
		return new Message(new Message(side+"<{ ").color(ChatColor.GREEN),
				           new Message(arg).color(ChatColor.AQUA),
				           new Message(" }>"+side).color(ChatColor.GREEN));
	}
	
	public static String repeat(String arg0, int arg1)
	{
		String ret = "";
		
		for (int i = 0; i<arg1; i++)
		{
			ret += arg0;
		}
		
		return ret;
	}
	
	public static String listify(String arg0, String[] args)
	{
		return StringUtils.join(args, arg0);
	}
	
	// { COLLECTION } //
	
	@SafeVarargs
	public static <T> List<T> list(T...ts)
	{
		ArrayList<T> list = new ArrayList<T>();
		
		for (T part : ts)
		{
			list.add(part);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> map(K key, V value, Object...objects)
	{
		HashMap<K,V> map = new HashMap<K,V>();
		
		map.put(key, value);
		
		for (int i = 0; i<objects.length; i++)
		{
			map.put((K) objects[i], (V) objects[i+1]);
		}
		
		return map;
	}
	
}