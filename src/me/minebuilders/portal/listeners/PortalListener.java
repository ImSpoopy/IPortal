package me.minebuilders.portal.listeners;

import java.util.ArrayList;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {
	private IP plugin;
	private BlockFace[] faces = new BlockFace[] {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
	private ArrayList<String> delays = new ArrayList<String>();

	public PortalListener(IP instance) {
		plugin = instance;
		delays.clear();
	} 

	@EventHandler
	public void onTeleport(PlayerPortalEvent event) {
		Player p = event.getPlayer();
		Location l = getNearPortal(p.getLocation());
		for (Portal portal : plugin.portals) {
			if (portal.isInRegion(l)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onTeleport(EntityPortalEnterEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) { 
			final Player p = (Player)e;
			Location l = getNearPortal(p.getLocation());
			for (final Portal portal : plugin.portals) {
				if (portal.isInRegion(l)) {
					if (!delays.contains(p.getName())) {
						if (portal.getStatus() == Status.RUNNING) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									portal.Teleport(p); //Delay, cannot teleport on movement events!
								}
							}, 5L);
						} else {
							Util.msg(p, "&cSorry, this portal is currently " + portal.getStatus().getName() + "&c!");
						}
						delay(p.getName()); //This event is probably fired every tick for a player in a portal, delaying is ESSENTIAL!
					}
				}
			}
		}
	}

	private void delay(final String s) {
		delays.add(s);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				delays.remove(s);
			}
		}, 30L);
	}

	private Location getNearPortal(Location l) {
		Block b = l.getBlock();
		for (BlockFace bf : faces) {
			Block rel = b.getRelative(bf);
			if (rel.getType() == Material.PORTAL) {
				return rel.getLocation();
			}
		}
		return l;
	}
}
