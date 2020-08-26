package com.lcmcconaghy.java.transcore.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

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
	
	// { ECON } //
	
	/**
	 * 
	 * @param arg0 Profile from
	 * @param arg1 Profile to
	 * @param arg2 Double amount to transfer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean move(Profile arg0, Profile arg1, double arg2, Plugin arg3)
	{
		MoneyTransferEvent transferEvent = new MoneyTransferEvent(arg0, arg1, arg2, arg3);
		transferEvent.run();
		
		if ( transferEvent.isCancelled() ) return false;
		
		arg2 = transferEvent.getAmount();
		
		net.milkbowl.vault.economy.EconomyResponse response = getEconomy().withdrawPlayer(arg0.getID(), arg2);
		
		if ( !response.transactionSuccess() ) return false;
		
		getEconomy().depositPlayer(arg1.getID(), arg2);
		
		return true;
	}
}
