package com.lcmcconaghy.java.transcore;

import com.lcmcconaghy.java.transcore.util.UtilPerms;

public enum TransPerm implements PermissionBase
{
	
	PROFILE_CREATE,
	PROFILE_REMOVE,
	PROFILE_SELECT,
	
	RELOAD,
	
	VERSION;
	
	// { FIELDS } //
	
	private String node;
	
	// { CONSTRUCTOR } //
	
	TransPerm()
	{
		this.node = UtilPerms.toNode(this, TransCore.get());
	}
	
	// { PERM } //
	
	@Override
	public String getNode()
	{
		return "transcore."+this.node;
	}
	
}
