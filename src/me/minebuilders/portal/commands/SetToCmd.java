package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;
import me.minebuilders.portal.portals.PortalType;

public class SetToCmd extends BaseCmd {

	public SetToCmd() {
		forcePlayer = true;
		cmdName = "setto";
		argLength = 2;
		usage = "<name> <extra>";
	}

	@Override
	public boolean run() {
		IP plugin = IP.instance;
		String name = args[1];
		for (Portal p : plugin.portals) {
			if (p.getName().equalsIgnoreCase(name)) {
				PortalType type = IP.data.getType(name);
				String data = IP.data.compressLoc(player.getLocation());

				if (type == PortalType.BUNGEE && args[2] == null) {
					Util.msg(player,  "&aPlease include the server's name for bungeecord!");
					return true;
				}
				data = convert(type);

				p.setTarget(data);
				IP.data.getConfig().set("portals." + name + ".tpto", data);
				IP.data.saveCustomConfig();

				Util.msg(player, "&a" + name + "'s target has been set!");
				p.setStatus(Status.RUNNING);
				return true;
			}
		}
		Util.msg(player, "&c" + name + " is not a valid portal!");
		return true;
	}

	private String convert(PortalType type) {
		switch(type) {
		case DEFAULT: return IP.data.compressLoc(player.getLocation());
		case BUNGEE: return args[2];
		}
		return IP.data.compressLoc(player.getLocation());
	}
}