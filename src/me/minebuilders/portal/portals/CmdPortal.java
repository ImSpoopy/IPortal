package me.minebuilders.portal.portals;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CmdPortal extends Portal {

	private String[] commands;

	public CmdPortal(String name, Bound region, Status status) {
		super(name, region, status);
	}

	@Override
	public void Teleport(Player p) {
		for (String s : commands) {
			Bukkit.getServer().dispatchCommand(p, s);
		}
	}

	@Override
	public void setTarget(String s) {
		commands = s.split(",");
	}

	@Override
	public PortalType getType() {
		return PortalType.CMD;
	}
}