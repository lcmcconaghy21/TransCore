package com.lcmcconaghy.java.transcore.store.transcore;

import com.lcmcconaghy.java.transcore.store.UserCollection;

public class IUserCollection extends UserCollection<IUser>
{
	// { CONSTRUCTOR } //
	
	private static IUserCollection i = new IUserCollection();
	public static IUserCollection get() { return IUserCollection.i; }
	
	// { SERIAL ID } //
	
	private static final long serialVersionUID = 1L;
	
	// { CONSTRUCTOR } //

	public IUserCollection()
	{
		super("users", IUser.class);
	}
	
}
