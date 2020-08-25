package com.lcmcconaghy.java.transcore.store.serializable;

import com.lcmcconaghy.java.transcore.store.transcore.Profile;
import com.lcmcconaghy.java.transcore.store.transcore.ProfileCollection;

public class SerializableProfile extends Serializable<Profile>
{
	
	// { SINGLETON } //
	
	private static SerializableProfile i = new SerializableProfile();
	public static SerializableProfile get() { return SerializableProfile.i; }
	
	// { CONSTRUCTOR } //
	
	public SerializableProfile()
	{
		super(Profile.class);
	}
	
	// { SERIALIZABLE } //
	
	@Override
	public Profile deserialize(Object arg0) throws ClassCastException
	{
		if ( arg0 instanceof String )
		{
			String id = (String) arg0;
			return ProfileCollection.get().get(id);
		}
		
		return null;
	}

	@Override
	public Object serialize(Profile t) throws ClassCastException
	{
		return t.getID();
	}
}
