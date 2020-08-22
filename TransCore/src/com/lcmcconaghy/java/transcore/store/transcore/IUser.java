package com.lcmcconaghy.java.transcore.store.transcore;

import java.util.List;

import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.store.UserItem;

public class IUser extends UserItem
{
	
	// { STATIC } //
	
	public static IUser get(Player arg0) { return IUserCollection.get().get(arg0); }
	
	// { FIELDS } //
	
	private String versionLastOnline = TransConfig.get().getVersions().get(0);
	
	// { SETTERS } //
	
	public void setLastVersion()
	{
		int latest = TransConfig.get().getVersions().size()-1;
		
		this.versionLastOnline = TransConfig.get().getVersions().get(latest);
	}
	
	// { GETTERS } //
	
	public String getLatestVersion()
	{
		return this.versionLastOnline;
	}
	
	public List<String> getLatestVersionPatchNotes()
	{
		return TransConfig.get().getVersionMap().get(versionLastOnline);
	}
	
}
