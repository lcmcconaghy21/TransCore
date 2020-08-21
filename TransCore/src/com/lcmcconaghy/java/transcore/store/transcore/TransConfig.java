package com.lcmcconaghy.java.transcore.store.transcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lcmcconaghy.java.transcore.TransCore;
import com.lcmcconaghy.java.transcore.store.Config;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;

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
	
	private Map<String, List<String>> versions = UtilGeneral.map("0.9.0", 
			                                                     UtilGeneral.list("Server Release"));
	
	// { SETTERS } //
	
	/**
	 * Update the Server
	 * @param arg0 String version name
	 * @param args Varargs of type String, representing patch notes
	 */
	public void patch(String arg0, String...args)
	{
		List<String> patchNotes = Arrays.asList(args);
		
		this.versions.put(arg0, patchNotes);
	}
	
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
	 * Get the patch notes for a Server update
	 * @param arg0 String version
	 * @return List of Strings as patch notes
	 */
	public List<String> getPatchNotes(String arg0)
	{
		return this.versions.get(arg0);
	}
	
	/**
	 * @return List of Strings representing all Server versions
	 */
	public List<String> getVersions()
	{
		List<String> all = new ArrayList<String>();
		
		for (String version : this.versions.keySet())
		{
			all.add(version);
		}
		
		return all;
	}
	
	/**
	 * @return String current Server version
	 */
	public String getLatestVersion()
	{
		int latest = getVersions().size()-1;
		
		return getVersions().get(latest-1);
	}
	
	/**
	 * @return String versions with corresponding List of String patch notes
	 */
	public Map<String, List<String>> getVersionMap()
	{
		return this.versions;
	}
	
}
