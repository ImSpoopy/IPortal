package me.minebuilders.portal.portals;

import java.lang.reflect.Constructor;

import me.minebuilders.portal.Bound;
import me.minebuilders.portal.Status;

public enum PortalType {
	DEFAULT(Portal.class), BUNGEE(BungeePortal.class);

	private Class<?> con;

	PortalType(Class<?> con) {
		this.con = con;
	}

	public Constructor<?> getPortal() {
		Constructor<?> c = null;
		try {
			c = con.getConstructor(String.class, Bound.class, Status.class);
		} catch (Exception e) { }
		return c;
	}
}
