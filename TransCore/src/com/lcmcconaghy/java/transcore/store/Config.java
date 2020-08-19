package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;

import de.leonhard.storage.Yaml;

public class Config implements Init
{
	
	// { FIELDS } //
	
	private Yaml yaml;
	private Plugin plugin;
	
	// { INIT } //
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		yaml = new Yaml(new File(plugin.getDataFolder().getPath()+File.separator+"config.yml"));
		
		for (Field fields : this.getClass().getDeclaredFields())
		{
			fields.setAccessible(true);
			
			try
			{
				fields.set(this, yaml.get(fields.getName(), false));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isInitialized()
	{
		return true;
	}
	
}
