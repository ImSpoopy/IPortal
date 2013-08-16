package me.minebuilders.portal.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Data {

	private FileConfiguration data = null;
	private File customConfigFile = null;
	private IP plugin;

	public Data(IP plugin) {
		this.plugin = plugin;
		reloadCustomConfig();
		load();
	}

	public void reloadCustomConfig() {
		if (customConfigFile == null) {
			customConfigFile = new File(plugin.getDataFolder(), "portals.yml");
		}
		data = YamlConfiguration.loadConfiguration(customConfigFile);

		InputStream defConfigStream = plugin.getResource("portals.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			data.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (data == null) {
			this.reloadCustomConfig();
		}
		return data;
	}

	public void saveCustomConfig() {
		if (data != null && customConfigFile != null) {
			try {
				getConfig().save(customConfigFile);
			} catch (IOException ex) {
				Util.warning("Could not save portals.yml! Error:" + ex.getMessage());
			}
		}
	}

	public void load() {
		if (new File(plugin.getDataFolder(), "portals.yml").exists()) {
			for (String s : data.getConfigurationSection("portals").getKeys(false)) {
				Status status = Status.RUNNING;
				String exit = "";
				Bound b = null;
				PortalType type = PortalType.DEFAULT;

				try {
					type = getType(data.getString("portals." + s + ".type"));
					exit = data.getString("portals." + s + ".tpto");
				} catch (Exception e) { 
					Util.warning("Unable to load teleport location for portal " + s + "!"); 
					status = Status.BROKEN;
				}

				try {
					b = new Bound(data.getString("portals." + s + "." + "world"), BC(s, "x"), BC(s, "y"), BC(s, "z"), BC(s, "x2"), BC(s, "y2"), BC(s, "z2"));
				} catch (Exception e) { 
					Util.warning("Unable to load region bounds for portal " + s + "!"); 
					status = Status.BROKEN;
				}
				try {
					Portal p = (Portal) type.getPortal().newInstance(s, b, status);
					plugin.portals.add(p);
					if (exit != null && !exit.equals("")) {
						p.setTarget(exit); //Set the target after the constructor, more easy this way
					} else {
						p.setStatus(Status.BROKEN);
					}
				} catch (Exception e) { 
					Util.warning("Unable to load portal " + s + "!"); 
				}
			}
		}
	}

	public int BC(String s, String st) {
		return data.getInt("portals." + s + "." + st);
	}

	public String compressLoc(Location l) {
		return (l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ() + ":" + l.getYaw() + ":" + l.getPitch());
	}

	public PortalType getType(String s) {
		for (PortalType t : PortalType.values()) {
			if (s.equalsIgnoreCase(t.name())) {
				return t;
			}
		}
		return PortalType.DEFAULT;
	}
}
