package me.minebuilders.portal.portals;

import org.bukkit.entity.Player;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;

public class BungeePortal extends Portal {

	private String server;

	public BungeePortal(String name, String tpto, Bound region, Status status) {
		super(name, "", region, status);
		this.server = tpto;
	}

	@Override
	public void Teleport(Player p) {
		p.sendPluginMessage(IP.instance, "iportal", (server).getBytes());
	}

	@Override
	public void setTarget(String s) {
		this.server = s;
	}

	@Override
	public PortalType getType() {
		return PortalType.BUNGEE;
	}
}
