package com.lcmcconaghy.java.transcore;

public interface Init
{
	
	/**
	 * Initialize an initializor
	 * @param arg0 whether to set initialization to positive or negative
	 * @param arg1 TransPlugin which will register the initializor
	 */
	public void initialize(boolean arg0, TransPlugin arg1);
	
	/**
	 * @return whether the initializor is active
	 */
	public boolean isInitialized();
}
