package com.lcmcconaghy.java.transcore.util;

import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.PermissionBase;

public class UtilPerms
{
	
	// { TRANSFORMATION } //
	
	/**
	 * Transform Perm to String permission node
	 * @param arg0 PermissionBase to be transformed
	 * @param arg1 Plugin of the Permission
	 * @return new Permission node
	 */
	public static String toNode(PermissionBase arg0, Plugin arg1)
	{
		String node = arg1.getName().toLowerCase()+"."+arg0.toString();
		
		if (node.contains("_"))
		{
			node = node.replaceAll("_", "\\.");
		}
		
		return node;
	}
	
}
