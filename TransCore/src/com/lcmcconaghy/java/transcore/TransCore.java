package com.lcmcconaghy.java.transcore;

import java.util.List;

import org.bukkit.Bukkit;

import com.lcmcconaghy.java.transcore.cmd.CmdTransCore;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.economy.TransEco;
import com.lcmcconaghy.java.transcore.engine.Engine;
import com.lcmcconaghy.java.transcore.engine.EnginePlayer;
import com.lcmcconaghy.java.transcore.engine.EngineServer;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.Config;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.serializable.Serializable;
import com.lcmcconaghy.java.transcore.store.serializable.SerializableNametag;
import com.lcmcconaghy.java.transcore.store.serializable.SerializablePlayer;
import com.lcmcconaghy.java.transcore.store.serializable.SerializableProfile;
import com.lcmcconaghy.java.transcore.store.transcore.IUserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.ProfileCollection;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;
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
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run()
			{
				alertVault();
			}
		}, 15L);
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
		return UtilGeneral.list(SerializableProfile.get(),
				                SerializablePlayer.get(),
				                SerializableNametag.get());
	}

	@Override
	public List<StoreCollection<?>> getStoreCollections()
	{
		return UtilGeneral.list(ProfileCollection.get(),
				                IUserCollection.get());
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

	@Override
	public Config getTransConfig()
	{
		return TransConfig.get();
	}
	
	public void alertVault()
	{
		log("Attempting to hook <d>Vault<e>...");
		
		TransEco.get().initialize(true, this);
		boolean result = TransEco.get().isInitialized();
		
		if (!result)
		{
			log("<d>Vault <c>not installed! Will not hook into <d>Vault<e>.");
			return;
		}
		
		log("Hooked into <d>Vault<e>!");
	}
	
}
