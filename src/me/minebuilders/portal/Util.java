package me.minebuilders.portal;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {

	private static final Logger log = Logger.getLogger("Minecraft");

	public static void log(String s) { log.info("[IPortal] " + s); }
	
	public static void warning(String s) { log.warning("[IPortal] " + s); }

	public static boolean hp(CommandSender sender, String s) {
		if (sender.hasPermission("iportal." + s)) {
			return true;
		}
		return false;
	}

	public static void msg(CommandSender sender, String s) {
		sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.DARK_AQUA + "IPortal" +ChatColor.DARK_RED +"] " + ChatColor.AQUA + ChatColor.translateAlternateColorCodes('&', s)); 
	}
	
	public static void scm(CommandSender sender, String s) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s)); 
	}

	public static void broadcast(String s) { 
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.DARK_AQUA + "IPortal" +ChatColor.DARK_RED +"] " + ChatColor.AQUA + ChatColor.translateAlternateColorCodes('&', s)); 
	}

	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) { return false; }
		return true;
	}
	
	public static String trim(String[] args, int minArgs) {
		String message = "";
		int maxArgs = 99;
		for (int i = 0; i < args.length; i++) {
			if ((i > minArgs) && (i < maxArgs)) {
				message = message + args[i] + " ";
			}
		}
		return message;
	}
}
