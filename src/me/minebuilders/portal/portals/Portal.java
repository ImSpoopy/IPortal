package me.minebuilders.portal.portals;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.tasks.PortalCreation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Portal {

	private String name;
	private Location tpto;
	private Bound region;
	private Status status;

	public Portal(String name, String tpto, Bound region, Status status) {
		Util.log("one");
		if (!tpto.equals("")) this.tpto = IP.data.uncompressLoc(tpto);
		this.name = name;
		this.region = region;
		this.status = status;
		Util.log("two");
	}

	public void setTarget(String s) {
		this.tpto = IP.data.uncompressLoc(s);
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void Teleport(Player p) {
		p.teleport(tpto);
	}

	public void refresh() {
		new PortalCreation(region);
	}

	public String getName() {
		return this.name;
	}

	public boolean isInRegion(Location l) {
		return region.isInRegion(l);
	}

	public Bound getBound() {
		return this.region;
	}

	public Status getStatus() {
		return this.status;
	}
	
	public PortalType getType() {
		return PortalType.DEFAULT;
	}
}
