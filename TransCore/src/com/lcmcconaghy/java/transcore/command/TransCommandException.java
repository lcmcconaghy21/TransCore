package com.lcmcconaghy.java.transcore.command;

public class TransCommandException extends Exception
{
	
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { FIELDS } //
	
	private String message;
	
	// { CONSTRUCTOR } //
	
	public TransCommandException(String arg0)
	{
		this.message = arg0;
	}
	
	// { GETTER } //
	
	@Override
	public String getMessage()
	{
		return this.message;
	}
	
}
