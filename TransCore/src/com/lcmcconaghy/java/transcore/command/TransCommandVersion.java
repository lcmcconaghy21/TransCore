package com.lcmcconaghy.java.transcore.command;

import org.apache.commons.lang.StringUtils;

import com.lcmcconaghy.java.transcore.Message;
import com.lcmcconaghy.java.transcore.TransPlugin;
import com.lcmcconaghy.java.transcore.exception.TransCommandException;

public class TransCommandVersion extends TransCommand
{
	// { FIELDS } //
	
	protected TransPlugin source;
	
	// { CONSTRUCTOR } //
	
	public TransCommandVersion(TransPlugin arg0)
	{
		super("version", "v");
		
		this.setDesc("check plugin information");
		
		this.source = arg0;
	}
	
	// { PERFORM } //
	
	@Override
	public void execute() throws TransCommandException
	{
		Message title = new Message(source.getName()+" "+source.getVersion()).titleize();
		
		Message version = Message.display("version", "1.0.0");
		
		if (source.getVersion()!=null)
		{
			version = Message.display("version", source.getVersion());
		}
		
		Message desc = Message.display("description","A plugin");
		
		if (source.getDescription().getDescription()!=null)
		{
			desc = Message.display("description", source.getDescription().getDescription());
		}
		
		Message authors = Message.display("authors","The Inkwell Team");
		
		if (source.getAuthors()!=null && source.getAuthors().size()>0)
		{
			String commas = StringUtils.join(source.getAuthors(), "<f>,<b>");
			
			authors = Message.display("authors", commas);
		}
		
		message(title, version, desc, authors);
	}
}
