package com.lcmcconaghy.java.transcore.store.serializable;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;

import de.leonhard.storage.internal.serialize.LightningSerializable;
import de.leonhard.storage.internal.serialize.LightningSerializer;

public abstract class Serializable<T> implements Init,LightningSerializable<T>
{
	
	// { FIELDS } //
	
	private Class<T> type;
	
	// { CONSTRUCTOR } //
	
	public Serializable(Class<T> arg0)
	{
		this.type = arg0;
	}
	
	// { SERIALIZABLE } //
	
	@Override
	public Class<T> getClazz()
	{
		return this.type;
	}
	
	// { INIT } //
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		LightningSerializer.registerSerializable(this);
	}

	@Override
	public boolean isInitialized()
	{
		return LightningSerializer.isSerializable(type);
	}
	
}
