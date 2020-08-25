package com.lcmcconaghy.java.transcore.store.transcore;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.store.Config;

public class TransConfig extends Config
{
	
	// { SINGLETON } //
	
	private static TransConfig i = new TransConfig();
	public static TransConfig get() { return TransConfig.i; }
	
	// { CONSTRUCTOR } //
	
	public TransConfig()
	{
		super(TransCore.get());
	}
	
	// { FIELDS } //
	
	private boolean useMongo = false;
	private String mongoHost = "hostname";
	private int mongoPort = 29;
	private String mongoUser = "username";
	private String mongoPassword = "password";
	
	private String serverMOTD = "Welcome to v{$config.version} of My Server! :)";
	
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
	
	/**
	 * @return formatted MOTD
	 */
	public String getMotd()
	{
		String motd = this.serverMOTD;
		
		if (motd.contains("{$config.version}"))
		{
			motd.replaceAll("\\{$config.version\\}", TransCore.get().getVersion());
		}
		
		return Message.format(motd);
	}
	
}
