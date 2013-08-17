package me.minebuilders.portal.tasks;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PortalCreation implements Runnable, Listener {

	private int timerID;
	private Bound b;

	public PortalCreation(Bound b) {
		this.b = b;
		Bukkit.getPluginManager().registerEvents(this, IP.instance);
		timerID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(IP.instance, this, 100L); //100 ticks is more then enough
		for (Location l : b.getBlocks(Material.AIR)) {
			l.getBlock().setType(Material.PORTAL);
		}
	}

	public void run() {
		HandlerList.unregisterAll(this);
		Bukkit.getServer().getScheduler().cancelTask(timerID);
	}

	@EventHandler
	public void onPortalBreak(BlockPhysicsEvent event) {
		if (event.getBlock().getType() == Material.PORTAL && b.isInRegion(event.getBlock().getLocation().toVector())) {
			event.setCancelled(true);
		}
	}
}
