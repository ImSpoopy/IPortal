package me.minebuilders.portal.listeners;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WandListener implements Listener {
	private IP plugin;

	public WandListener(IP instance) {
		plugin = instance;
	}

	@EventHandler
	public void onSelection(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (p.getItemInHand().getType() == Material.BLAZE_ROD && plugin.playerses.containsKey(p.getName())) {
				Location l = event.getClickedBlock().getLocation();
				PlayerSession ses = plugin.playerses.get(p.getName());
				ses.setLoc1(l);

				Util.msg(p, "Pos1: "+l.getX()+", "+l.getY()+", "+l.getZ());
				if (!ses.hasValidSelection()) {
					Util.msg(p, "Now you need to set position 2!");
				}
			}
		}
	}

	@EventHandler
	public void onSelection(BlockBreakEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.BLAZE_ROD && plugin.playerses.containsKey(p.getName())) {
			Location l = event.getBlock().getLocation();
			PlayerSession ses = plugin.playerses.get(p.getName());
			ses.setLoc2(l);
			Util.msg(p, "Pos2: "+l.getX()+", "+l.getY()+", "+l.getZ());

			if (!ses.hasValidSelection()) {
				Util.msg(p, "Now you need to set position 1!");
			}
			event.setCancelled(true);
		}
	}
}