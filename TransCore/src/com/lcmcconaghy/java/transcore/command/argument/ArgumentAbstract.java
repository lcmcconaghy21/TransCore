package com.lcmcconaghy.java.transcore.command.argument;

import java.util.HashMap;
import java.util.Map;

import com.lcmcconaghy.java.transcore.command.TransCommand;

public abstract class ArgumentAbstract<T> implements Argument<T>
{
	// { FIELDS } //
	
	private Map<TransCommand,Boolean> concat = new HashMap<TransCommand,Boolean>();
	private Map<TransCommand,String> displayName = new HashMap<TransCommand,String>();
	
	protected Class<T> clazz;
	
	// { CONSTRUCTOR } //
	
	public ArgumentAbstract(Class<T> arg0)
	{
		this.clazz = arg0;
	}
	
	// { SETTERS } //
	
	public void setDisplay(String arg0, TransCommand arg1)
	{
		this.displayName.put(arg1, arg0);
	}
	
	public void setConcat(boolean arg0, TransCommand arg1)
	{
		this.concat.put(arg1, arg0);
	}
	
	// { GETTERS } //
	
	public String getDisplay(TransCommand arg0)
	{
		return this.displayName.get(arg0);
	}
	
	public boolean willConcat(TransCommand arg0)
	{
		return this.concat.get(arg0);
	}
	
	public Class<T> getType()
	{
		return this.clazz;
	}
	
}
