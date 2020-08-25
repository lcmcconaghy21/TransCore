package com.lcmcconaghy.java.transcore.store.transcore;

import com.lcmcconaghy.java.transcore.store.StoreCollection;

public class ProfileCollection extends StoreCollection<Profile>
{
	
	// { SINGLETON } //
	
	private static ProfileCollection i = new ProfileCollection();
	public static ProfileCollection get() { return ProfileCollection.i; }
	
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { CONSTRUCTOR } //

	public ProfileCollection()
	{
		super("characters", Profile.class);
	}
	
}
