package com.lcmcconaghy.java.transcore.store.transcore;

import com.lcmcconaghy.java.transcore.store.UserItem;

public class IUser extends UserItem
{
	
	// { STATIC } //
	
	public static IUser get(Object arg0) { return IUserCollection.get().get(arg0); }
	
}
