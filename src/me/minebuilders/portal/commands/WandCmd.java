package me.minebuilders.portal.commands;

import me.minebuilders.portal.IP;
import me.minebuilders.portal.PlayerSession;
import me.minebuilders.portal.Util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WandCmd extends BaseCmd {

	public WandCmd() {
		forcePlayer = true;
		cmdName = "wand";
		argLength = 1;
	}

	@Override
	public boolean run() {
		IP plugin = IP.instance;
		if (plugin.playerses.containsKey(player.getName())) {
			plugin.playerses.remove(player.getName());
			Util.msg(player, "Wand disabled!");
		} else {
			player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));
			plugin.playerses.put(player.getName(), new PlayerSession(null, null));
			Util.msg(player, "Wand enabled!");
		}
		return true;
	}
}