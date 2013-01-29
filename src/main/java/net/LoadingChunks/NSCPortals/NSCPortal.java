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
	
	public Boolean inPortal(Location entity) {
		if(entity.getX() < firstXY.getX() || entity.getX() > lastXY.getX())
			return false;
		
		if(entity.getY() < firstXY.getY() || entity.getY() > lastXY.getY())
			return false;
					
		if(entity.getZ() < firstXY.getZ() || entity.getZ() > lastXY.getZ())
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
