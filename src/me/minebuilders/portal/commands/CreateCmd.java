package me.minebuilders.portal.commands;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;
import me.minebuilders.portal.tasks.PortalCreation;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;

public class CreateCmd extends BaseCmd {

	public CreateCmd() {
		forcePlayer = true;
		cmdName = "create";
		argLength = 3;
		usage = "<name> <portal-type>";
	}
	
	@Override
	public boolean run() {
		IP plugin = IP.instance;
		if (!plugin.playerses.containsKey(player.getName()))
			Util.msg(player, ChatColor.RED + "You need to make a selection before making a portal!");
		else {
			PlayerSession st = plugin.playerses.get(player.getName());
			if (!st.hasValidSelection()) {
				Util.msg(player, ChatColor.RED + "You need to make a selection before making a portal!");	
			} else {
				PortalType type = IP.data.getType(args[2]);
				Location l = st.getLoc1();
				Location l2 = st.getLoc2();
				String s = args[1];
				Configuration c = IP.data.getConfig();
				c.set("portals." + s + ".world", player.getWorld().getName());
				c.set("portals." + s + ".x", l.getX());
				c.set("portals." + s + ".y", l.getY());
				c.set("portals." + s + ".z", l.getZ());
				c.set("portals." + s + ".x2", l2.getX());
				c.set("portals." + s + ".y2", l2.getY());
				c.set("portals." + s + ".z2", l2.getZ());
				c.set("portals." + s + ".type", type.name());
				IP.data.save();
				
				Bound b = new Bound(player.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
				new PortalCreation(b);
				try {
					plugin.portals.add((Portal) type.getPortal().newInstance(s, b, Status.NOT_READY));
				} catch (Exception e) {
					Util.msg(player, "&cFailed to add portal to local list!");
					return true;
				}
				Util.msg(player, "You created portal " + s + "!");
			}
		}
		return true;
	}
}
