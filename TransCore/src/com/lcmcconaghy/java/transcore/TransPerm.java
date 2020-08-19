package com.lcmcconaghy.java.transcore;

public enum TransPerm implements Perm
{
	
	VERSION;
	
	// { FIELDS } //
	
	private String node;
	
	// { CONSTRUCTOR } //
	
	TransPerm()
	{
		String node = this.toString().toLowerCase();
		
		if (node.contains("_"))
		{
			node = node.replaceAll("_", ".");
		}
		
		this.node = node;
	}
	
	// { PERM } //
	
	@Override
	public String getNode()
	{
		return "transcore."+this.node;
	}
	
}
