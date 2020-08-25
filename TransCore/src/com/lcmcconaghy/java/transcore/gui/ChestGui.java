package com.lcmcconaghy.java.transcore.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class ChestGui implements Init
{
	
	// { FIELDS } //
	
	private String id;
	
	private Map<Integer, Map<ItemStack,TransCommand>> options = new HashMap<Integer,Map<ItemStack,TransCommand>>();
	
	// { CONSTRUCTOR } //
	
	public ChestGui(String arg0, InventoryHolder arg1)
	{
		this.id = arg0;
	}
	
	// { IDENTIFY } //
	
	public String getID()
	{
		return this.id;
	}
	
	// { OPTIONS } //
	
	public void addOption(ItemStack arg0, TransCommand arg1, String[] args)
	{
		addOption( getNextSpace() , arg0, arg1, args);
	}
	
	public void addOption(int arg0, ItemStack arg1, TransCommand arg2, String[] args)
	{
		if ( isOption(arg1) )
		{
			this.options.remove(arg0);
		}
		
		arg2.args = args;
		
		this.options.put(arg0, UtilGeneral.map(arg1, arg2));
	}
	
	public boolean removeOption(ItemStack arg0)
	{
		if ( !isOption(arg0) )
		{
			return false;
		}
		
		this.options.remove( getOptionSpace(arg0) );
		return true;
	}
	
	public boolean isOption(ItemStack arg0)
	{
		boolean ret = false;
		
		for (int i : this.options.keySet())
		{
			if ( !this.options.get(i).containsKey(arg0) ) continue;
			
			ret = true;
			break;
		}
		
		return ret;
	}
	
	public int getOptionSpace(ItemStack arg0)
	{
		int ret = 0;
		
		for (int i : this.options.keySet())
		{
			if ( !this.options.get(i).containsKey(arg0) ) continue;
			
			ret = i;
			break;
		}
		
		return ret;
	}
	
	public int getNextSpace()
	{
		int ret = 0;
		
		for (int i = 0; i<23; i++)
		{
			if ( this.options.get(i) != null ) continue;
			
			ret = i+1;
			break;
		}
		
		return ret;
	}
	
	public ItemStack getStack(int arg0)
	{
		Map<ItemStack,TransCommand> map = this.options.get(arg0);
		
		ItemStack[] internal = map.keySet().toArray(new ItemStack[1]);
		
		return internal[0];
	}
	
	public TransCommand getExecutable(ItemStack arg0)
	{
		TransCommand ret = null;
		
		for (int i : this.options.keySet())
		{
			if ( !this.options.get(i).containsKey(arg0) ) continue;
			
			ret = this.options.get(i).get(arg0);
			break;
		}
		
		return ret;
	}
	
	// { DISPLAY } //
	
	public void display(InventoryHolder arg0)
	{
		Inventory gui = Bukkit.createInventory(arg0, InventoryType.CHEST, this.id);
		
		for (int i : this.options.keySet())
		{
			gui.setItem(i, getStack(i) );
		}
	}
	
	// { INIT } //
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		TransServer.get().registerGui(this);
	}

	@Override
	public boolean isInitialized()
	{
		return TransServer.get().containsGui(this);
	}
}
