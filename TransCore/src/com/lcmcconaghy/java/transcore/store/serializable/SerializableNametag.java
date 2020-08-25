package com.lcmcconaghy.java.transcore.store.serializable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.lcmcconaghy.java.transcore.exception.NametagException;
import com.lcmcconaghy.java.transcore.tag.Nametag;

public class SerializableNametag extends Serializable<Nametag>
{
	
	// { SINGLETON } //
	
	private static SerializableNametag i = new SerializableNametag();
	public static SerializableNametag get() { return SerializableNametag.i; }
	
	// { CONSTRUCTOR } //
	
	public SerializableNametag()
	{
		super(Nametag.class);
	}
	
	// { SERIALIZABLE } //
	
	@Override
	public Nametag deserialize(Object arg0) throws ClassCastException
	{
		@SuppressWarnings("unchecked")
		HashMap<String,String> serializedMap = (HashMap<String, String>) arg0;
		
		UUID id = UUID.fromString( serializedMap.get(serializedMap.get("id") ) );
		
		Nametag tag = new Nametag( Bukkit.getOfflinePlayer(id) );
		
		if (serializedMap.containsKey("prefix"))
		{
			try
			{
				tag.setPrefix( serializedMap.get("prefix") );
			}
			catch (NametagException e)
			{
				e.printStackTrace();
			}
		}
		if (serializedMap.containsKey("suffix"))
		{
			try
			{
				tag.setSuffix(serializedMap.get("suffix"));
			}
			catch (NametagException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public Object serialize(Nametag arg0) throws ClassCastException 
	{
		HashMap<String,String> nametag = new HashMap<String,String>();
		
		nametag.put("id", arg0.getPlayerID().toString());
		nametag.put("prefix", arg0.getPrefix());
		nametag.put("suffix", arg0.getSuffix());
		
		return nametag;
	}
	
}
