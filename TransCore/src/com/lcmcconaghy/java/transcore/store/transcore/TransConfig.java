package com.lcmcconaghy.java.transcore.store.transcore;

import com.lcmcconaghy.java.transcore.store.Config;

public class TransConfig extends Config
{
	
	// { SINGLETON } //
	
	private static TransConfig i = new TransConfig();
	public static TransConfig get() { return TransConfig.i; }
	
	// { FIELDS } //
	
	private boolean useMongo = false;
	private String mongoHost = "hostname";
	private int mongoPort = 29;
	private String mongoUser = "username";
	private String mongoPassword = "password";
	
	// { GETTERS } //
	
	public boolean willUseMongo()
	{
		return this.useMongo;
	}
	
	public String getMongoHost()
	{
		return this.mongoHost;
	}
	
	public int getMongoPort()
	{
		return this.mongoPort;
	}
	
	public String getMongoUsername()
	{
		return this.mongoUser;
	}
	
	public String getMongoPassword()
	{
		return this.mongoPassword;
	}
	
}
