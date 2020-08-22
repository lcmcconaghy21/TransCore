package com.lcmcconaghy.java.transcore.cmd;

import java.util.List;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentStringList;
import com.lcmcconaghy.java.transcore.event.VersionUpdateEvent;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

public class CmdUpdateMinor extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdUpdateMinor() throws TransCommandException
	{
		super("minor");
		
		this.setDesc("update minor version");
		this.setPerm(TransPerm.UPDATE_MINOR);
		
		this.addArgument(ArgumentStringList.get(), "patchNotes");
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		List<String> list = this.readArgument();
		
		String latest = TransConfig.get().getLatestVersion();
		
		String[] parts = latest.split("\\.");
		
		String minorString = parts[1];
		
		int minor = Integer.parseInt(minorString)+1;
		
		String update = parts[0]+"."+minor+".0.0";
		
		VersionUpdateEvent versionUpdate = new VersionUpdateEvent(latest, update);
		versionUpdate.run();
		
		TransConfig.get().patch(update, list.toArray(new String[list.size()]));
		
		message("<a>Version <d>"+update+" <a>has been loaded!");
	}
}
