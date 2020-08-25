package com.lcmcconaghy.java.transcore.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;

public class TransEco implements Init
{
	
	// { SINGLETON } //
	
	private static TransEco i = new TransEco();
	public static TransEco get() { return TransEco.i; }
	
	// { FIELDS } //
	
	private boolean active;
	
	// { INIT } // 
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
		
		this.active = (vault != null && vault.isEnabled());
	}

	@Override
	public boolean isInitialized()
	{
		return active;
	}
	
	// { GETTERS } //
	
	public net.milkbowl.vault.economy.Economy getEconomy()
	{
		return Bukkit.getServicesManager()
				     .getRegistration(net.milkbowl.vault.economy.Economy.class)
				     .getProvider();
	}
	
}
