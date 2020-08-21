package com.lcmcconaghy.java.transcore;

import com.lcmcconaghy.java.transcore.util.UtilPerms;

public enum TransPerm implements Perm
{
	
	UPDATE_MAJOR,
	UPDATE_MINOR,
	UPDATE_BUILD,
	UPDATE_REVISION,
	
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
