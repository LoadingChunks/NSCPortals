package net.LoadingChunks.NSCPortals;

import org.bukkit.Location;

public class NSCPortal {
	private Location firstXY;
	private Location lastXY;
	private String server = "";
	private Location exit = null;
	
	public NSCPortal(Location firstXY, Location lastXY) {
		this.firstXY = firstXY;
		this.lastXY = lastXY;
	}
	
	public Boolean isBetween(double first, double second, double test)
	{
		return second > first ? test > first && test < second : test > second && test < first;
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
}
