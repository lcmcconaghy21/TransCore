package com.lcmcconaghy.java.transcore.engine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransServer;
import com.lcmcconaghy.java.transcore.gui.ChestGui;
import com.lcmcconaghy.java.transcore.gui.GuiProfile;
import com.lcmcconaghy.java.transcore.store.UserCollection;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

import net.md_5.bungee.api.ChatColor;

public class EnginePlayer extends Engine
{
	
	// { FIELDS } //
	
	private Message profileCreate = new Message("Click here to create a new profile.")
			                            .color( ChatColor.AQUA )
			                            .suggest("/profile create ");
	
	// { SINGLETON } //
	
	private static EnginePlayer i = new EnginePlayer();
	public static EnginePlayer get() { return EnginePlayer.i; }
	
	// { EVENTS } //
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void registerPlayer(PlayerJoinEvent event)
	{
		TransServer.get().registerNametag(event.getPlayer());
		
		for (UserCollection<?> collection : TransServer.get().getUserCollections())
		{
			if (collection.has(event.getPlayer())) continue;
			
			collection.create(event.getPlayer());
		}
	}
	
	// { GUI } //
	
	@EventHandler
	public void onGUISelect(InventoryClickEvent event)
	{
		if (!TransServer.get().isGui( event.getView() )) return;
		
		ChestGui<?> gui = TransServer.get().getGui( event.getView() );
		ItemStack selection = event.getCurrentItem();
		
		Runnable execution = gui.getExecutable(selection);
		
		execution.run();
	}
	
	// { PROFILES } //
	
	@EventHandler(priority=EventPriority.HIGH)
	public void profileSelect(PlayerJoinEvent event)
	{
		if ( IUser.get( event.getPlayer() ).getActiveProfile() != null 
		  || IUser.get( event.getPlayer() ).getProfiles().size() == 0) return;
		
		GuiProfile profileUI = GuiProfile.get().build( event.getPlayer() );
		
		profileUI.display( event.getPlayer() );
	}
	
	@EventHandler
	public void actNullProfileMove(PlayerMoveEvent event)
	{
		if ( TransConfig.get().allowsActivityWithoutProfile() ) return;
		if ( IUser.get( event.getPlayer() ).getActiveProfile() != null ) return;
		
		IUser user = IUser.get( event.getPlayer() );
		
		user.message( new Message("You cannot interact with the world until you create a profile!\n")
				          .error()
				          .extra(profileCreate) );
		
		event.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void actNullProfileChat(AsyncPlayerChatEvent event)
	{
		if ( TransConfig.get().allowsActivityWithoutProfile() ) return;
		if ( IUser.get( event.getPlayer() ).getActiveProfile() != null ) return;
		
		IUser user = IUser.get( event.getPlayer() );
		
		user.message( new Message("You cannot chat until you create a profile!\n")
				          .error()
				          .extra(profileCreate) );
		
		event.setCancelled(true);
	}
}
