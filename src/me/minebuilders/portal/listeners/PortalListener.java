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
	private ArrayList<String> queue = new ArrayList<String>();

	public PortalListener(IP instance) {
		plugin = instance;
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
			Player p = (Player)e;
			Location l = getNearPortal(p.getLocation());
			for (Portal portal : plugin.portals) {
				if (portal.isInRegion(l)) {
					if (!queue.contains(p.getName())) {
						if (portal.getStatus() == Status.RUNNING) {
							queue.add(p.getName());
							schedTele(p, portal);
						} else {
							p.teleport(getSafeBlock(l));
							Util.msg(p, "&cSorry, this portal is currently " + portal.getStatus().getName() + "&c!");
						}
					}
				}
			}
		}
	}

	public void schedTele(final Player p, final Portal portal) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				portal.Teleport(p);
				queue.remove(p.getName());
			}
		}, 5L);
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

	private Location getSafeBlock(Location l) {
		Block b = l.getBlock();
		for (BlockFace bf : faces) {
			Block rel = b.getRelative(bf);
			if (rel.getType() == Material.AIR) {
				return rel.getLocation();
			}
		}
		return l;
	}
}
