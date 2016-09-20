package me.minebuilders.portal.storage;

import java.io.File;
import java.io.IOException;
import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Data {

	private File file = null;
	private FileConfiguration config = null;
	private IP plugin;

	public Data(IP plugin) {
		this.plugin = plugin;
		load();
	}

	public void load() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		file = new File(plugin.getDataFolder(), "portals.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			}
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create portals.yml!");
			}

			config = YamlConfiguration.loadConfiguration(file);

			save();
			Util.log("New portals.yml file successfully created!");

		} else {
			config = YamlConfiguration.loadConfiguration(file);
			for (String s : config.getConfigurationSection("portals").getKeys(false)) {
				Status status = Status.RUNNING;
				String exit = "";
				Bound b = null;
				PortalType type = PortalType.DEFAULT;
				try
				{
					type = getType(config.getString("portals." + s + ".type"));
					exit = config.getString("portals." + s + ".tpto");
				} catch (Exception e) {
					Util.warning("Unable to load teleport location for portal " + s + "!");
					status = Status.BROKEN;
				}
				try
				{
					b = new Bound(config.getString("portals." + s + "." + "world"), BC(s, "x"), BC(s, "y"), BC(s, "z"), BC(s, "x2"), BC(s, "y2"), BC(s, "z2"));
				} catch (Exception e) {
					Util.warning("Unable to load region bounds for portal " + s + "!");
					status = Status.BROKEN;
				}
				try {
					Portal p = (Portal)type.getPortal().newInstance(new Object[] { s, b, status });
					plugin.portals.add(p);
					if ((exit != null) && (!exit.equals("")))
						p.setTarget(exit);
					else
						p.setStatus(Status.BROKEN);
				}
				catch (Exception e) {
					Util.warning("Unable to load portal " + s + "!");
				}
				Util.log(s + " portal successfully Loaded!");
			}
			Util.log("portals.yml file successfully Loaded!");
		}
	}

	public void save() {
		try {
			config.save(file);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save portals.yml!");
		}
	}

	public FileConfiguration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}

	public void reloadConfig() {
		if (file == null) {
			file = new File(plugin.getDataFolder(), "portals.yml");
		}
		config = YamlConfiguration.loadConfiguration(file);
	}


	public int BC(String s, String st) {
		return config.getInt("portals." + s + "." + st);
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
