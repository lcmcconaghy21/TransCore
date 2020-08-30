package com.lcmcconaghy.java.transcore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.StoreItem;

import net.md_5.bungee.api.ChatColor;

public class UtilGeneral
{
	// { TEXT TOOLS } //
	
	public static Message titleize(String arg)
	{
		int times = 13-(arg.length()/2);
		
		if (times<=0) times = 0;
		
		String side = repeat("=", times);
		
		return new Message(arg).color(ChatColor.AQUA)
				               .insert("<a>"+side+"<{ ")
				               .append(" <a>}>"+side)
				               .format();
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
	
	/**
	 * @param arg0 String separator
	 * @param args String array arguments
	 * @return String combining args
	 */
	public static String listify(String arg0, String[] args)
	{
		return StringUtils.join(args, arg0);
	}
	
	/**
	 * Get List of IDS from a StoreCollection
	 * @param <T> Object extending StoreItem
	 * @param coll StoreCollection of Generic Type T
	 * @return new String List
	 */
	public static <T extends StoreItem<T>> List<String> listComponents(StoreCollection<T> coll)
	{
		List<String> ret = new ArrayList<String>();
		
		for (T part : coll)
		{
			ret.add(part.getID());
		}
		
		return ret;
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
