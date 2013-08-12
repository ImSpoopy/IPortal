package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

public class RefreshCmd extends BaseCmd {

	public RefreshCmd() {
		forcePlayer = true;
		cmdName = "refresh";
		argLength = 2;
	}

	@Override
	public boolean run() {
		IP plugin = IP.instance;
		String name = args[1];
		for (Portal p : plugin.portals) {
			if (p.getName().equalsIgnoreCase(name)) {
				p.refresh();
				Util.msg(player, "&a" + name + "'s has been refreshed!");
				p.setStatus(Status.RUNNING);
				return true;
			}
		}
		Util.msg(player, "&c" + name + " is not a valid portal!");
		return true;
	}
}