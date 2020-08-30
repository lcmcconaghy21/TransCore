package com.lcmcconaghy.java.transcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class TransLocation
{
	
	// { FIELDS } //
	
	private String worldId;
	
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
	// { CONSTRUCTORS } //
	
	/**
	 * Create new TransLocation
	 * @param arg0 String world id
	 * @param arg1 Double block x
	 * @param arg2 Double block y
	 * @param arg3 Double block z
	 * @param arg4 Float yaw
	 * @param arg5 Float pitch
	 */
	public TransLocation(String arg0, double arg1, double arg2, double arg3, float arg4, float arg5)
	{
		this.worldId = arg0;
		this.x = arg1;
		this.y = arg2;
		this.z = arg3;
		this.yaw = arg4;
		this.pitch = arg5;
	}
	
	/**
	 * Get new TransLocation from Bukkit Location
	 * @param arg0
	 */
	public TransLocation(Location arg0)
	{
		this.worldId = arg0.getWorld().getName();
		this.x = arg0.getX();
		this.y = arg0.getY();
		this.z = arg0.getZ();
		this.yaw = arg0.getYaw();
		this.pitch = arg0.getPitch();
	}
	
	public TransLocation(Entity arg0)
	{
		this.worldId = arg0.getWorld().getName();
		this.x = arg0.getLocation().getX();
		this.y = arg0.getLocation().getY();
		this.z = arg0.getLocation().getZ();
		this.yaw = arg0.getLocation().getYaw();
		this.pitch = arg0.getLocation().getPitch();
	}
	
	public TransLocation(World arg0)
	{
		this.worldId = arg0.getName();
		this.x = arg0.getSpawnLocation().getX();
		this.y = arg0.getSpawnLocation().getY();
		this.z = arg0.getSpawnLocation().getZ();
		this.yaw = arg0.getSpawnLocation().getYaw();
		this.pitch = arg0.getSpawnLocation().getPitch();
	}
	
	// { COMPARATOR } //
	
	/**
	 * Calculate the distance between this Location and another
	 * @param arg0 TransLocation to compare
	 * @return Double distance between the points
	 */
	public double distanceBetween(TransLocation arg0)
	{
		if (!arg0.worldId.equalsIgnoreCase(this.worldId)) return -404;
		
		double distX = Math.pow(x-arg0.x, 2);
		double distY = Math.pow(y-arg0.y, 2);
		double distZ = Math.pow(z-arg0.z, 2);
		
		if (distX<0) distX *= -1;
		if (distY<0) distY *= -1;
		if (distZ<0) distZ *= -1;
		
		double dist = Math.sqrt(distX+distY+distZ);
		
		return dist;
	}
	
	// { GETTERS } //
	
	public World getWorld()
	{
		return Bukkit.getWorld(this.worldId);
	}
	
	public double getBlockX()
	{
		return this.x;
	}
	
	public double getBlockY()
	{
		return this.y;
	}
	
	public double getBlockZ()
	{
		return this.z;
	}
	
	public float getYaw()
	{
		return this.yaw;
	}
	
	public float getPitch()
	{
		return this.pitch;
	}
	
}
