package com.lcmcconaghy.java.transcore.exception;

import com.lcmcconaghy.java.transcore.store.UserItem;

public class NametagException extends Exception
{
	
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { FIELDS } //
	
	private String to;
	private UserItem player;
	
	// { CONSTRUCTOR } //
	
	public NametagException(UserItem arg0, String arg1)
	{
		this.to = arg1;
	}
	
	// { MESSAGE } //
	
	public String getMessage()
	{
		return "Could not change the nametag of "+player.getName()+" to "
	           +this.to+", as the new tag is too long ("+this.to.length()+")!";
	}
	
}
