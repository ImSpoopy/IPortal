package me.minebuilders.portal;

import org.bukkit.ChatColor;

public enum Status {
	RUNNING(ChatColor.GREEN  + "Running"), NOT_READY(ChatColor.YELLOW + "Not-Ready"),
	BROKEN(ChatColor.RED + "Broken");

	private String name;

	Status(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}