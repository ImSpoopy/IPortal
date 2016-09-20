package me.minebuilders.portal.commands;

import org.bukkit.Location;
import org.bukkit.Material;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class DeleteCmd extends BaseCmd {

	public DeleteCmd() {
		forcePlayer = true;
		cmdName = "delete";
		argLength = 2;
		usage = "<name>";
	}

	@Override
	public boolean run() {
		IP plugin = IP.instance;
		String name = args[1];
		Portal por = null;
		for (Portal p : plugin.portals) {
			if (p.getName().equalsIgnoreCase(name)) {
				por = p; //Bad to remove from the map we're looping through!
				Util.msg(player, "&aAttempting to remove " + name + "!");
				for (Location l : p.getBound().getBlocks(Material.PORTAL)) {
					l.getBlock().breakNaturally();
				}
				IP.data.getConfig().set("portals." + name, null);
				IP.data.save();
				Util.msg(player, "&a" + name + " has been deleted!");
			}
		}
		if (por == null)
			Util.msg(player, "&c" + name + " is not a valid portal!");
		else plugin.portals.remove(por);
		return true;
	}
}
