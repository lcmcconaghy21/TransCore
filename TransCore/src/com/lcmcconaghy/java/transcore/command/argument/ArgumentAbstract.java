package com.lcmcconaghy.java.transcore.command.argument;

public abstract class ArgumentAbstract<T> implements Argument<T>
{
	// { FIELDS } //
	
	protected String displayName;
	protected Class<T> clazz;
	protected boolean concat;
	
	// { CONSTRUCTOR } //
	
	public ArgumentAbstract(Class<T> arg0)
	{
		this.clazz = arg0;
	}
	
	// { SETTERS } //
	
	public void setDisplay(String arg0)
	{
		this.displayName = arg0;
	}
	
	public void setConcat(boolean arg0)
	{
		this.concat = arg0;
	}
	
	// { GETTERS } //
	
	public String getDisplay()
	{
		return this.displayName;
	}
	
	public boolean willConcat()
	{
		return this.concat;
	}
	
	public Class<T> getType()
	{
		return this.clazz;
	}
	
}
