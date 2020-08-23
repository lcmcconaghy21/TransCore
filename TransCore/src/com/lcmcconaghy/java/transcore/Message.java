package com.lcmcconaghy.java.transcore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lcmcconaghy.java.transcore.command.TransCommand;
import com.lcmcconaghy.java.transcore.store.UserItem;
import com.lcmcconaghy.java.transcore.util.UtilGeneral;
import com.lcmcconaghy.java.transcore.util.UtilMaths;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Message
{
	
	// { STATIC FIELDS } //
	
	private static Map<String, String> colours = new HashMap<String, String>();
	
	// { PARSER } //
	
	static
	{
		// COLORS
		
		colours.put("0", "\u00A70"); // BLACK
		colours.put("1", "\u00A71"); // DARK BLUE
		colours.put("2", "\u00A72"); // DARK GREEN
		colours.put("3", "\u00A73"); // DARK AQUA
		colours.put("4", "\u00A74"); // DARK RED
		colours.put("5", "\u00A75"); // PURPLE
		colours.put("6", "\u00A76"); // GOLD
		colours.put("7", "\u00A77"); // SILVER
		colours.put("8", "\u00A78"); // GRAY
		colours.put("9", "\u00A79"); // BLUE
		colours.put("a", "\u00A7a"); // GREEN
		colours.put("b", "\u00A7b"); // AQUA
		colours.put("c", "\u00A7c"); // RED
		colours.put("d", "\u00A7d"); // PINK
		colours.put("e", "\u00A7e"); // YELLOW
		colours.put("f", "\u00A7f"); // WHITE
		
		// FORMATTING
		
		colours.put("l", "\u00A7l"); // BOLD
		colours.put("m", "\u00A7m"); // STRIKETHROUGH
		colours.put("n", "\u00A7n"); // UNDERLINE
		colours.put("o", "\u00A7o"); // ITALIC
	}
	
	/**
	 * Colorize and format String
	 * @param arg0 String to be formatted
	 * @return formatted String
	 */
	public static String format(String arg0)
	{
		String ret = arg0;
		
		for (String key : colours.keySet())
		{
			if (!ret.contains("<"+key+">")) continue;
			
			ret = ret.replaceAll("<"+key+">", colours.get(key));
		}
		
		return ret;
	}
	
	/**
	 * Return String parameter with first letter capitalized
	 * @param arg0 String parameter
	 * @return String parameter with first letter capitalized
	 */
	public static String upperCaseFirst(String arg0)
	{
		String ret = "";
		char[] chars = arg0.toCharArray();
		
		for (int i = 0; i<chars.length; i++)
		{
			char append = chars[i];
			
			if (i==0) append = Character.toUpperCase(append);
			
			ret += append;
		}
		
		return ret;
	}
	
	/**
	 * Formatted, display-friendly Message
	 * @param arg0 String display title
	 * @param arg1 String display value
	 * @return new display-friendly Message
	 */
	public static Message display(String arg0, String arg1)
	{
		return new Message("<a>"+upperCaseFirst(arg0)+": <b>"+arg1).format();
	}
	
	// { FIELDS } //
	
	protected TextComponent component = new TextComponent();
	
	// { CONSTRUCTOR } //
	/**
	 * Build Message from String
	 * @param arg0 String to build from
	 */
	public Message(String arg0)
	{
		this.component = new TextComponent(arg0);
	}
	
	/**
	 * Build Message from other Messages
	 * @param messages Varargs for type Message
	 */
	public Message(Message... messages)
	{
		if (messages.length>0) this.component = messages[0].component;
		if (messages.length==0) this.component = new TextComponent();
		
		if (messages.length<2) return;
		
		for (int i = 1; i<messages.length; i++)
		{
			this.component.addExtra(messages[i].component);
		}
	}
	
	// { GETTERS } //
	
	/**
	 * @return String text of the Message TextComponent
	 */
	public String getText()
	{
		return this.component.getText();
	}
	
	/**
	 * @return String array representing words
	 */
	public String[] getWords()
	{
		String text = getText();
		
		if (text.contains(" "))
		{
			return text.split(" ");
		}
		
		String[] ret = new String[1];
		
		ret[0] = text;
		
		return ret;
	}
	
	/**
	 * Add a String to the Message
	 * @param arg0 String part
	 */
	public void add(String arg0)
	{
		this.component.setText(this.component.getText()+arg0+" ");
	}
	
	// { BUILDERS } //
	
	/**
	 * Apply a color to a Message
	 * @param arg0 ChatColor to apply
	 * @return this Message
	 */
	public Message color(ChatColor arg0)
	{
		this.component.setColor(arg0);
		
		return this;
	}
	
	/**
	 * Apply a clickable command to the Message
	 * @param arg0 Command to apply to Message
	 * @param args Varargs arguments type String
	 * @return
	 */
	public Message command(TransCommand arg0, String... args)
	{
		this.component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, arg0.getFullCommand()+" "+
				                     UtilGeneral.listify(" ", args)));
		
		return this;
	}
	
	/**
	 * Add extra Messages to this Message
	 * @param messages Varargs Messages to apply
	 * @return this Message
	 */
	public Message extra(Message...messages)
	{
		for (Message message : messages)
		{
			this.component.addExtra(message.component);
		}
		
		return this;
	}
	
	/**
	 * Format Message to be in the form of an error
	 * @return this Message
	 */
	public Message error()
	{
		setText(format("<4>Error: <c>"+this.getText()));
		
		return this;
	}
	
	/**
	 * Obfuscate Message
	 * @param arg0 actual distance, minus inner
	 * @param arg1 difference between inner and outer
	 * @return this Message
	 */
	public Message obfuscate(int arg0, int arg1)
	{
		String complete = "";
		
		for (char part : this.component.getText().toCharArray())
		{
			int chance = UtilMaths.randomInt(arg1);
			
			if (arg0<=0 || chance>=arg1)
			{
				complete += part;
				continue;
			}
				
			complete += '-';
		}
		
		this.setText(complete);
		
		return this;
	}
	
	/**
	 * Add hoverable text to Message
	 * @param args Varargs String to add
	 * @return this Message
	 */
	@SuppressWarnings("deprecation")
	public Message hover(String... args)
	{
		ComponentBuilder builder = new ComponentBuilder();
		
		for (String line : args)
		{
			builder.append(new Message(line).format().component);
		}
		
		this.component.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT
				                   , builder.create()));
		
		return this;
	}
	
	/**
	 * Link a url to a Message
	 * @param arg0 String url
	 * @return this Message
	 */
	public Message link(String arg0)
	{
		this.component.setClickEvent(new ClickEvent(Action.OPEN_URL, arg0));
		
		return this;
	}
	
	/**
	 * Repeat this Message x times
	 * @param arg0 Integer times to repeat
	 * @return this Message
	 */
	public Message repeat(int arg0)
	{
		String text = this.component.getText();
		
		for (int i = 0; i<arg0-1; i++)
		{
			text += text;
		}
		
		this.component.setText(text);
		
		return this;
	}
	
	/**
	 * Make a title out of a text
	 * @return titleized Message
	 */
	public Message titleize()
	{
		if (this.component.getExtra()==null || this.component.getExtra().size()<=0) return this;
		
		return UtilGeneral.titleize(getText());
	}
	
	/**
	 * Colorize Message
	 * @return this Message
	 */
	public Message format()
	{
		String set = this.component.getText();
		
		set = format(set);
		
		setText(set);
		
		return this;
	}
	
	/**
	 * Place text before current String text
	 * @param arg0 String text to place before
	 * @return this Message
	 */
	public Message insert(String arg0)
	{
		if (arg0 == null) return this;
		
		return this.setText(arg0+getText());
	}
	
	/**
	 * Add text after current String text
	 * @param arg0 String text to append after
	 * @return this Message
	 */
	public Message append(String arg0)
	{
		if (arg0 == null) return this;
		
		return this.setText(getText()+arg0);
	}
	
	/**
	 * Change the text of a Message
	 * @param arg0 String new text
	 * @return this Message
	 */
	public Message setText(String arg0)
	{
		this.component.setText(arg0);
		
		return this;
	}
	
	// { SEND } //
	
	/**
	 * @deprecated
	 * Send this Message to a Player
	 * @param arg0 Player to send Message to
	 */
	@Deprecated
	public void send(Player arg0)
	{
		arg0.spigot().sendMessage(component);
	}
	
	/**
	 * Send Message to User
	 * @param arg0 User to send this Message to
	 */
	public void send(UserItem arg0)
	{
		arg0.getPlayer().spigot().sendMessage(component);
	}
	
	/**
	 * Send Message to CommandSender
	 * @param sender CommandSender to send this Message to
	 */
	public void send(CommandSender sender)
	{
		if (sender instanceof Player)
		{
			send((Player)sender);
			return;
		}
		
		sender.sendMessage(Message.format(getText()));
	}
}