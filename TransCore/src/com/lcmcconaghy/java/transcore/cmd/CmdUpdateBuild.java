package com.lcmcconaghy.java.transcore.cmd;

import java.util.List;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentStringList;
import com.lcmcconaghy.java.transcore.event.VersionUpdateEvent;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

public class CmdUpdateBuild extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdUpdateBuild() throws TransCommandException
	{
		super("build");
		
		this.setDesc("update build version");
		this.setPerm(TransPerm.UPDATE_BUILD);
		
		this.addArgument(ArgumentStringList.get(), "patchNotes");
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		List<String> patchNotes = this.readArgument();
		String[] patches = patchNotes.toArray(new String[patchNotes.size()]);
		
		String latest = TransConfig.get().getLatestVersion();
		String[] parts = latest.split(".");
		
		int build = Integer.parseInt(parts[2])+1;
		parts[2] = build+"";
		
		String update = parts[0];
		
		for (int i = 1; i<parts.length; i++)
		{
			if (i>2) update += ".0";
			
			update += "."+parts[i];
		}
		
		VersionUpdateEvent versionUpdate = new VersionUpdateEvent(latest, update);
		versionUpdate.run();
		
		TransConfig.get().patch(update, patches);
		
		message("<a>Version <d>"+update+" <a>has been uploaded!");
	}
	
}