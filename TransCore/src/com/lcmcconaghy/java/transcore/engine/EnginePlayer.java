package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.gui.ChestGui;
import com.lcmcconaghy.java.transcore.store.UserCollection;

public class EnginePlayer extends Engine
{
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void registerPlayer(PlayerJoinEvent event)
	{
		TransServer.get().registerNametag(event.getPlayer());
		
		for (UserCollection<?> collection : TransServer.get().getUserCollections())
		{
			if (collection.has(event.getPlayer()))
			{
				collection.get(event.getPlayer());
				continue;
			}
			
			collection.create(event.getPlayer());
		}
	}
	
	// { GUI } //
	
	@EventHandler
	public void onGUISelect(InventoryClickEvent event)
	{
		if (!TransServer.get().isGui( event.getView() )) return;
		
		ChestGui gui = TransServer.get().getGui( event.getView() );
		ItemStack selection = event.getCurrentItem();
		
		TransCommand executable = gui.getExecutable(selection);
		
		if (executable == null) return;
		
		executable.executeOuter(event.getWhoClicked(), executable.args);
	}
}
