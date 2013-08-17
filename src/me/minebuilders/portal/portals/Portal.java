package me.minebuilders.portal.portals;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.tasks.PortalCreation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Portal {

	private String name;
	private Location tpto;
	private Bound region;
	private Status status;

	public Portal(String name, Bound region, Status status) {
		this.name = name;
		this.region = region;
		this.status = status;
	}

	public void setTarget(String s) {
		String[] h = s.split(":");
		this.tpto = new Location(Bukkit.getServer().getWorld(h[0]), Integer.parseInt(h[1]) + 0.5, Integer.parseInt(h[2]), Integer.parseInt(h[3]) + 0.5, Float.parseFloat(h[4]), Float.parseFloat(h[5]));
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

	public boolean isInRegion(Vector l) {
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
