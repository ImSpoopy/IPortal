package me.minebuilders.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.minebuilders.portal.listeners.*;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.storage.Data;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class IP extends JavaPlugin {

	public List<Portal> portals = new ArrayList<Portal>();
	public HashMap<String, PlayerSession> playerses = new HashMap<String, PlayerSession>();
	public static IP instance;
	public static Data data;
	
	@Override
	public void onEnable() {
		instance = this;
		data = new Data(this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "iportal");
		getCommand("iportal").setExecutor(new CommandListener());
		getServer().getPluginManager().registerEvents(new WandListener(this), this);
		getServer().getPluginManager().registerEvents(new PortalListener(this), this);
		Util.log("IPortal has been enabled!");
	}
	
	@Override
	public void onDisable() {
		instance = null;
		Util.log("IPortal has been disabled!");
	}
}
