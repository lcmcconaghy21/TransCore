package com.lcmcconaghy.java.transcore.store;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.transcore.Init;
import com.lcmcconaghy.java.transcore.TransPlugin;

import de.leonhard.storage.Yaml;

public class Config implements Init
{
	
	// { FIELDS } //
	
	private Yaml yaml;
	private Plugin plugin;
	
	// { CONSTRUCTOR } //
	
	public Config(Plugin arg0)
	{
		this.plugin = arg0;
		
		if (!(arg0 instanceof TransPlugin)) return;
		
		((TransPlugin)arg0).setTransConfig(this);
	}
	
	// { INIT } //
	
	@Override
	public void initialize(boolean arg0, TransPlugin arg1)
	{
		if (!arg0) return;
		
		load();
	}

	@Override
	public boolean isInitialized()
	{
		if (this.plugin!=null && this.yaml!=null) return true;
		
		return false;
	}
	
	// { LOAD } //
	
	public void load()
	{
		File src = new File(plugin.getDataFolder().getPath()+File.separator+"config.yml");
		
		if (!src.getParentFile().exists()) src.getParentFile().mkdirs();
		
		if (!src.exists())
		{
			try
			{
				src.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		yaml = new Yaml(src);
		
		for (Field fields : this.getClass().getDeclaredFields())
		{
			if (Modifier.isStatic(fields.getModifiers())) continue;
			
			fields.setAccessible(true);
			
			Object value = null;
			
			try
			{
				value = fields.get(this);
			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}
			
			if (!yaml.contains(fields.getName()))
			{
				yaml.set(fields.getName(), value);
				continue;
			}
			
			value = yaml.get(fields.getName(), value);
			
			try
			{
				fields.set(this, value);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
