package com.lcmcconaghy.java.transcore;

import java.util.List;

import com.lcmcconaghy.java.transcore.cmd.CmdTransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.engine.Engine;
import com.lcmcconaghy.java.transcore.engine.EnginePlayer;
import com.lcmcconaghy.java.transcore.engine.EngineServer;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.serializable.Serializable;
import com.lcmcconaghy.java.transcore.store.serializable.SerializablePlayer;
import com.lcmcconaghy.java.transcore.store.transcore.IUserCollection;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class TransCore extends TransPlugin
{
	
	// { SINGLETON } //
	
	private static TransCore i;
	public static TransCore get() { return TransCore.i; }
	
	// { CONSTRUCTOR } //
	
	public TransCore()
	{
		TransCore.i = this;
	}
	
	// { ENABLE } //
	
	@Override
	public void startup()
	{
		
	}
	
	// { DISABLE } //
	
	@Override
	public void disable()
	{
		return;
	}

	@Override
	public List<Serializable<?>> getAdapters()
	{
		return UtilGeneral.list(SerializablePlayer.get());
	}

	@Override
	public List<StoreCollection<?>> getStoreCollections()
	{
		return UtilGeneral.list(IUserCollection.get());
	}

	@Override
	public List<TransCommand> getTransCommands()
	{
		try
		{
			return UtilGeneral.list(new CmdTransCore());
		}
		catch (TransCommandException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Engine> getEngines()
	{
		return UtilGeneral.list(EnginePlayer.get(),
				                EngineServer.get());
	}
	
	
	
}
