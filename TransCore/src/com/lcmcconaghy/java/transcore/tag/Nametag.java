package com.lcmcconaghy.java.transcore.tag;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.lcmcconaghy.java.transcore.exception.NametagException;
import com.lcmcconaghy.java.transcore.store.transcore.IUser;

public class Nametag
{
	// { STATIC } //
	
	private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	
	// { FIELDS } //
	
	private String prefix;
	private String suffix;
	private UUID playerId;
	private String teamId;
	
	// { CONSTRUCTOR } //
	
	public Nametag(OfflinePlayer arg0)
	{
		this.playerId = arg0.getUniqueId();
		this.teamId = playerId.toString().substring(0, 15);
		
		Team personalTeam = scoreboard.getTeam(teamId);
		
		if (personalTeam != null)
		{
			if (personalTeam.hasEntry(getPlayer().getName())) return;
			
			personalTeam.addEntry(getPlayer().getName());
			return;
		}
		
		scoreboard.registerNewTeam(teamId).addEntry(getPlayer().getName());
	}
	
	// { SETTERS } //
	
	public void reset()
	{
		getHandler().setPrefix("");
		getHandler().setSuffix("");
	}
	
	public Nametag setPrefix(String arg0) throws NametagException
	{
		if (arg0.length()>16)
		{
			throw new NametagException(IUser.get(playerId), arg0);
		}
		
		getHandler().setPrefix(arg0);
		return this;
	}
	
	public Nametag setSuffix(String arg0) throws NametagException
	{
		if (arg0.length()>16)
		{
			throw new NametagException(IUser.get(playerId), arg0);
		}
		
		getHandler().setSuffix(arg0);
		return this;
	}
	
	// { GETTERS } //
	
	public String getPrefix()
	{
		return this.prefix;
	}
	
	public String getSuffix()
	{
		return this.suffix;
	}
	
	public UUID getPlayerID()
	{
		return this.playerId;
	}
	
	public OfflinePlayer getPlayer()
	{
		return Bukkit.getOfflinePlayer(playerId);
	}
	
	public Team getHandler()
	{
		return scoreboard.getTeam(this.teamId);
	}
}
