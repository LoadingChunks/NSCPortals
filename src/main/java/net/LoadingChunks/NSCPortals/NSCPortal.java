package net.LoadingChunks.NSCPortals;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NSCPortal {
	private Location firstXY;
	private Location lastXY;
	private String server = "";
	private Location exit = null;
	private String permissionNode = null;
	private String noPermissionMsg = null;
	
	public NSCPortal(Location firstXY, Location lastXY) {
		this.firstXY = firstXY;
		this.lastXY = lastXY;
	}
	
	public Boolean isBetween(double first, double second, double test)
	{
		if(first > second) {
			first++;
		} else {
			second++;
		}
		return (second > first) ? (Math.floor(test) >= first && Math.ceil(test) <= second) : (Math.floor(test) >= second && Math.ceil(test) <= first);
	}
	
	public Boolean inPortal(Location entity) {
		if(!isBetween(firstXY.getX(), lastXY.getX(), entity.getX()))
			return false;
		
		if(!isBetween(firstXY.getY(), lastXY.getY(), entity.getY()))
			return false;
					
		if(!isBetween(firstXY.getZ(), lastXY.getZ(), entity.getZ()))
			return false;
		
		return true;
	}
	
	public Boolean isLocal() {
		if(server.length() == 0)
			return true;
		else
			return false;
	}
	
	public Location getExit() {
		return this.exit;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public World getWorld() {
		return this.firstXY.getWorld();
	}
	
	public void setServer(String s) {
		this.server = s;
	}
	
	public void setExit(Location l) {
		this.exit = l;
	}
	
	public void setPermissionNode(String s) {
		this.permissionNode = s;
	}
	
	public void setNoPermissionMsg(String s) {
		this.noPermissionMsg = s;
	}
	
	public boolean checkPermission(Player p) {
		if(permissionNode == null)
			return true;
		
		if(p.hasPermission(permissionNode))
			return true;
		else {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermissionMsg));
			return false;
		}
	}
	
	public String toString() {
		String str = "";
		str = str + firstXY.getX() + ",";
		str = str + firstXY.getY() + ",";
		str = str + firstXY.getZ() + " to ";
		str = str + lastXY.getX() + ",";
		str = str + lastXY.getY() + ",";
		str = str + lastXY.getZ() + " in ";
		str = str + firstXY.getWorld().getName();
		return str;
	}
}
