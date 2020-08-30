package com.lcmcconaghy.java.transcore.store.transcore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lcmcconaghy.java.transcore.store.UserItem;

public class IUser extends UserItem
{
	
	// { STATIC } //
	
	public static IUser get(Object arg0) { return IUserCollection.get().get(arg0); }
	
	// { FIELDS } //
	
	private String activeProfile;
	private Map<String,Profile> profiles = new HashMap<String,Profile>();
	
	// { SETTERS } //
	
	public Profile createProfile(String arg0)
	{
		Profile profile = ProfileCollection.get().create(arg0);
		profile.setName(arg0);
		addProfile(profile);
		
		return profile;
	}
	
	public void addProfile(Profile arg0)
	{
		if ( this.profiles.containsKey( arg0.getName() ) ) return;
		
		this.profiles.put(arg0.getID(), arg0);
	}
	
	public void removeProfile(Profile arg0)
	{
		if ( !this.profiles.containsKey(arg0.getName()) ) return;
		
		this.profiles.remove( arg0.getName() );
	}
	
	public boolean containsProfile(Profile arg0)
	{
		return this.profiles.containsKey(arg0.getName());
	}
	
	public void setActiveProfile(Profile arg0)
	{
		this.activeProfile = arg0.getID();
	}
	
	// { GETTERS } //
	
	public Collection<Profile> getProfiles()
	{
		return this.profiles.values();
	}
	
	public Profile getActiveProfile()
	{
		return ProfileCollection.get().get(this.activeProfile);
	}
	
}
