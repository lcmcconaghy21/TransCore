package com.lcmcconaghy.java.transcore;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.engine.Engine;
import com.lcmcconaghy.java.transcore.store.Config;
import com.lcmcconaghy.java.transcore.store.StoreCollection;
import com.lcmcconaghy.java.transcore.store.serializable.Serializable;

public interface TPlugin
{
	
	/**
	 * Tasks to run on Plugin enable
	 */
	public void startup();
	
	/**
	 * Tasks to run on Plugin disable
	 */
	public void disable();
	
	/**
	 * Place all serializables in here
	 * @return List of all Serializables
	 */
	public @NotNull List<Serializable<?>> getAdapters();
	
	/**
	 * Place all StoreCollections in here
	 * @return List of all StoreCollections
	 */
	public @NotNull List<StoreCollection<?>> getStoreCollections();
	
	/**
	 * @return one instance of Config
	 */
	public Config getTransConfig();
	
	/**
	 * Place all core TransCommands here
	 * @return List of all TransCommands
	 */
	public @NotNull List<TransCommand> getTransCommands();
	
	/**
	 * Place all Engines here
	 * @return List of all Engines
	 */
	public @NotNull List<Engine> getEngines();
	
}
