package com.lcmcconaghy.java.transcore.pager;

import com.lcmcconaghy.java.transcore.Message;

public interface Pager<T>
{
	
	public Message sendLine(T arg0);
	
}
