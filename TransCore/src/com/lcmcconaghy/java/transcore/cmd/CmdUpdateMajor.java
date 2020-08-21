package com.lcmcconaghy.java.transcore.cmd;

import java.util.List;

import com.lcmcconaghy.java.transcore.TransPerm;
import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.command.argument.primitive.ArgumentStringList;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;
import com.lcmcconaghy.java.transcore.store.transcore.TransConfig;

public class CmdUpdateMajor extends TransCommand
{
	
	// { CONSTRUCTOR } //
	
	public CmdUpdateMajor() throws TransCommandException
	{
		super("major");
		
		this.setDesc("update major version");
		this.setPerm(TransPerm.UPDATE_MAJOR);
		
		this.addArgument(ArgumentStringList.get(), "patchNotes");
	}
	
	// { EXECUTION } //
	
	@Override
	public void execute() throws TransCommandException
	{
		List<String> patchNotes = this.readArgument();
		
		String version = TransConfig.get().getLatestVersion();
		
		String[] parts = version.split(".");
		
		int newMajor = Integer.parseInt(parts[0])+1;
		
		String update = newMajor+"";
		
		for (int i = 1; i<parts.length; i++)
		{
			update += "."+0;
		}
		
		TransConfig.get().patch(update, patchNotes.toArray(new String[patchNotes.size()]));
		
		message("<a>Version <d>"+update+" <a>has been uploaded!");
	}
	
}
