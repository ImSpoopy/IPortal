package me.minebuilders.portal.listeners;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;
import me.minebuilders.portal.Util;
import me.minebuilders.portal.portals.Portal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {
	private IP plugin;
	private BlockFace[] faces = new BlockFace[] {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};

	public PortalListener(IP instance) {
		plugin = instance;
	}

	@EventHandler
	public void onTeleport(PlayerPortalEvent event) {
		Player p = event.getPlayer();
		Location l = getNearPortal(p.getLocation());
		for (Portal portal : plugin.portals) {
			if (portal.isInRegion(l)) {
				if (portal.getStatus() == Status.RUNNING) {
					portal.Teleport(p);
				} else {
					Util.msg(p, "&cSorry, this portal is currently " + portal.getStatus().getName() + "&c!");
				}
				event.setCancelled(true);
			}
		}
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
