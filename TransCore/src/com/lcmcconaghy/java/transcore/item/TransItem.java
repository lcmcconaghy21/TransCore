package com.lcmcconaghy.java.transcore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

public class TransItem
{
	// { STATIC } //
	
	public static TransItem get(ItemStack arg0) { return new TransItem(arg0); }
	
	// { FIELDS } //
	
	private ItemStack source;
	
	// { CONSTRUCTOR } //
	
	public TransItem(ItemStack arg0)
	{
		this.source = arg0;
	}
	
	// { CUSTOM DATA } //
	
	/**
	 * Attach custom tag
	 * @param arg0 String tag label
	 * @param arg1 PersistentData type of the object to be stored
	 * @param arg2 Object to be stored
	 * @return
	 */
	public <K,V> TransItem attachTag(String arg0, PersistentDataType<K,V> arg1, V arg2)
	{
		ItemMeta itemMeta = getItemMeta();
		itemMeta.getPersistentDataContainer().set( getNamespacedKey(arg0) , arg1, arg2);
		
		this.source.setItemMeta(itemMeta);
		
		return this;
	}
	
	/**
	 * Get NamespaceKey of stored value
	 * @param arg0 String key of the stored value
	 * @return corresponding NamespacedKey
	 */
	public NamespacedKey getNamespacedKey(String arg0)
	{
		return new NamespacedKey(TransCore.get(), arg0);
	}
	
	/**
	 * @param arg0 String label of value to be retrieved
	 * @param arg1 PersistentDataType of value to be retrieved
	 * @return whether value is available
	 */
	public <K,V> boolean containsAttachedValue(String arg0, PersistentDataType<K,V> arg1)
	{
		return getItemMeta().getPersistentDataContainer().has( getNamespacedKey(arg0) , arg1);
	}
	
	/**
	 * 
	 * @param arg0 String label of object stored
	 * @param arg1 PersistentDataType of stored object
	 * @return
	 */
	public <K,V> V getAttachedValue(String arg0, PersistentDataType<K, V> arg1)
	{
		return getItemMeta().getPersistentDataContainer().get( getNamespacedKey(arg0) , arg1);
	}
	
	// { ITEM META } //
	
	/**
	 * @return ItemMeta of the source ItemStack
	 */
	public ItemMeta getItemMeta()
	{
		return this.source.getItemMeta();
	}
	
	/**
	 * @param arg0 String display name which will be set
	 * @return this TransItem
	 */
	public TransItem setName(String arg0)
	{
		ItemMeta edit = getItemMeta();
		edit.setDisplayName( Message.format(arg0) );
		
		this.source.setItemMeta(edit);
		
		return this;
	}
	
	public TransItem setLore(String...args)
	{
		String[] lore = args;
		
		for (int i = 0; i<lore.length; i++)
		{
			lore[i] = Message.format( args[i] );
		}
		
		ItemMeta edit = getItemMeta();
		edit.setLore( UtilGeneral.list(lore) );
		
		this.source.setItemMeta(edit);
		
		return this;
	}
	
	public String getName()
	{
		return this.getItemMeta().getDisplayName();
	}
	
	// { PLAYER } //
	
	/**
	 * Give Player this TransItem in an equipment slot
	 * @param arg0 Player to be sent TransItem
	 * @param arg1 EquipmentSlot in which it will be placed
	 */
	public void give(Player arg0, EquipmentSlot arg1)
	{
		arg0.getInventory().setItem(arg1, this.source);
	}
	
	/**
	 * Give Player this TransItem in a specific slot
	 * @param arg0 Player to be sent TransItem
	 * @param arg1 int slot in which it will be placed
	 */
	public void give(Player arg0, int arg1)
	{
		arg0.getInventory().setItem(arg1, this.source);
	}
	
	/**
	 * Give Player this TransItem in any slot
	 * @param arg0 Player to be sent TransItem
	 */
	public void give(Player arg0)
	{
		arg0.getInventory().addItem(this.source);
	}
}
