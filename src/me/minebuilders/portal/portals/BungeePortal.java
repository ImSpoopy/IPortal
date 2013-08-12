package me.minebuilders.portal.portals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.IP;
import me.minebuilders.portal.Status;

public class BungeePortal extends Portal {

	private ByteArrayOutputStream stream;

	public BungeePortal(String name, Bound region, Status status) {
		super(name, region, status);
	}

	@Override
	public void Teleport(Player p) {
		p.sendPluginMessage(IP.instance, "BungeeCord", stream.toByteArray());
	}

	@Override
	public void setTarget(String s) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bao);
		try {
			out.writeUTF("Connect"); //Connect = connect player to server
			out.writeUTF(s); // Target Server
		} catch (IOException e) { }
		stream = bao;
	}

	@Override
	public PortalType getType() {
		return PortalType.BUNGEE;
	}
}
