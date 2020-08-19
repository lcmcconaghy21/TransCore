package com.lcmcconaghy.java.transcore.util;

import java.util.Random;

public class UtilMaths
{
	
	// { RANDOMS } //
	
	public static int randomInt(int min, int max)
	{
		Random r = new Random();
		int i = Integer.MIN_VALUE;
		
		while (i<min)
		{
			i = r.nextInt(max);
		}
		
		return i;
	}
	
	public static int randomInt(int max)
	{
		return randomInt(0, max);
	}
	
	public static int randomInt()
	{
		return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	// { CHECK } //
	
	public static boolean isEven(int arg0)
	{
		return (arg0 % 2) == 0;
	}
}
