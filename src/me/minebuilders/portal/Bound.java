package me.minebuilders.portal;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Bound {

	private int x;
	private int y;
	private int z;
	private int x2;
	private int y2;
	private int z2;
	private World world;

	public Bound(String world, int x, int y, int z, int x2, int y2, int z2) {
		this.world = Bukkit.getWorld(world);
		this.x = Math.min(x,x2);
		this.y = Math.min(y, y2);
		this.z = Math.min(z, z2);
		this.x2 = Math.max(x,x2);
		this.y2 = Math.max(y, y2);
		this.z2 = Math.max(z, z2);
	}

	public boolean isInRegion(Location loc) {
		if (!loc.getWorld().equals(world)) return false;
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		if ((cx > x - 1 && cx < x2 + 1) && (cy > y - 1 && cy < y2 + 1) && (cz > z  - 1&& cz < z2 + 1)) {
			return true;
		}
		return false;
	}

	public ArrayList<Location> getBlocks(Material type) {
		ArrayList <Location> array = new ArrayList<Location>();
		for (int x3 = x; x3 <= x2; x3++) {
			for (int y3 = y; y3 <= y2; y3++) {
				for (int z3 = z; z3 <= z2; z3++) {
					Block b = world.getBlockAt(x3, y3, z3);
					if (b.getType() == type) {
						array.add(b.getLocation());
					}
				}
			}
		}
		return array;
	}
}
