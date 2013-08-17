package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class TpCmd extends BaseCmd {

	public TpCmd() {
		forcePlayer = true;
		cmdName = "tp";
		usage = "<name>";
		argLength = 2;
	}

	@Override
	public boolean run() {
		IP plugin = IP.instance;
		String name = args[1];
		for (Portal p : plugin.portals) {
			if (p.getName().equalsIgnoreCase(name)) {
				if (p.getStatus() == Status.RUNNING) {
					p.Teleport(player);
				} else {
					Util.msg(sender, "&c" + name + " is not running!!");
				}
				return true;
			}
		}
		Util.msg(sender, "&c" + name + " is not a valid portal!");
		return true;
	}
}