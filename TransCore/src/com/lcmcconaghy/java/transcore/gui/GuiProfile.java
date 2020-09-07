package com.lcmcconaghy.java.transcore.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lcmcconaghy.java.transcore.cmd.CmdProfileSelect;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;
import com.lcmcconaghy.java.transcore.store.transcore.Profile;

public class GuiProfile extends ChestGui<GuiProfile>
{
	
	// { SINGLETON } //
	
	private static GuiProfile i = new GuiProfile();
	public static GuiProfile get() { return GuiProfile.i; }
	
	// { CONSTRUCTOR } //
	
	public GuiProfile()
	{
		super("Profile Selection");
	}
	
	// { GUI } //
	
	@Override
	public GuiProfile build(final Player arg0)
	{
		for (Profile profile : IUser.get(arg0).getProfiles())
		{
			this.addOption(new ItemStack(Material.PLAYER_HEAD, 1), 
			new Runnable()
			{
				@Override
				public void run()
				{
					String[] args = new String[0];
					String name = profile.getName();
					
					if (name.contains(" "))
					{
						args = name.split(" ");
					}
					else
					{
						args = new String[1];
						args[0] = name;
					}
					
					CmdProfileSelect.get().executeOuter(arg0, args);
				}
			});
		}
		
		return this;
	}
	
}
