package com.lcmcconaghy.java.transcore.store.serializable;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SerializablePlayer extends Serializable<OfflinePlayer>
{
	
	// { SINGLETON } //
	
	private static SerializablePlayer i = new SerializablePlayer();
	public static SerializablePlayer get() { return SerializablePlayer.i; }
	
	// { CONSTRUCTOR } //
	
	public SerializablePlayer()
	{
		super(OfflinePlayer.class);
	}
	
	// { SERIALIZE } //
	
	@Override
	public OfflinePlayer deserialize(Object arg0) throws ClassCastException
	{
		if (arg0 instanceof String)
		{
			String idString = (String) arg0;
			UUID uuid = UUID.fromString(idString);
			return Bukkit.getOfflinePlayer(uuid);
		}
		if (arg0 instanceof UUID)
		{
			UUID uuid = (UUID) arg0;
			return Bukkit.getOfflinePlayer(uuid);
		}
		
		return null;
	}

	@Override
	public Object serialize(OfflinePlayer t) throws ClassCastException
	{
		return t.getUniqueId().toString();
	}
	
	
	
}
