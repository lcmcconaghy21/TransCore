package com.lcmcconaghy.java.transcore;

import com.lcmcconaghy.java.transcore.cmd.CmdTransCore;
import com.lcmcconaghy.java.transcore.engine.EnginePlayer;
import com.lcmcconaghy.java.transcore.engine.EngineServer;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.IUserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

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
		try
		{
			initialize(// { STORAGE } //
					   
					   TransConfig.get(),
					   IUserCollection.get(),
					   
					   // { COMMANDS } // 
					   new CmdTransCore(),
					   
					   // { ENGINE } //
					   
					   EnginePlayer.get(),
					   EngineServer.get());
		}
		catch (TransCommandException e)
		{
			e.printStackTrace();
		}
	}
	
}
