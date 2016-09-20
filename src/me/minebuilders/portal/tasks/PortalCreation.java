package me.minebuilders.portal.tasks;


import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("deprecation")
	public PortalCreation(Bound b) {
		this.b = b;
		Bukkit.getPluginManager().registerEvents(this, IP.instance);
		timerID = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(IP.instance, this, 100L); //100 ticks is more then enough
		
		for (Location l : b.getBlocks(Material.PORTAL)) {
			l.getBlock().setType(Material.AIR);
		}
		
		List<Integer> xValues = new ArrayList<Integer>();
		
		for (Location l : b.getBlocks(Material.AIR)) {
			Double dX = l.getX();
			xValues.add(dX.intValue());
		}
		
		boolean movesZ = false;
		int amount = xValues.size();
		int correctCount = 0;
		for (int xx : xValues) {
			if (xx == xValues.get(0)) {
				correctCount++;
			}
		}
		
		if (correctCount == amount) {
			movesZ = true;
		}
		
		if (movesZ) {
			//MOVES ON Z
			for(Location l : b.getBlocks(Material.AIR)) {
				l.getBlock().setType(Material.PORTAL);
				l.getBlock().setData((byte)2);
			}
			Bukkit.broadcastMessage("Portal moves on Z!");
	
		} else {
			//MOVES ON X
			for(Location l : b.getBlocks(Material.AIR)) {
				l.getBlock().setType(Material.PORTAL);
			}
			Bukkit.broadcastMessage("Portal moves on X!");
			
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
