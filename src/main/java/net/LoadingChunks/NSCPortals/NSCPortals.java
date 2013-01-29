package net.LoadingChunks.NSCPortals;

/*
    This file is part of NSCPortals

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar. If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NSCPortals extends JavaPlugin {
	
	/* TODO */
	// Retain Velocity/Relative position
	// Minecarts
	// Plugin<->Plugin comms between servers
	// Look for library to do that for us. - Redis?
	

	//ClassListeners
	private final NSCPortalsCommandExecutor commandExecutor = new NSCPortalsCommandExecutor(this);
	private final NSCPortalsEventListener eventListener = new NSCPortalsEventListener(this);
	//ClassListeners
	
	private HashMap<String, NSCPortal> portals = new HashMap<String, NSCPortal>();

	public void onDisable() {
		// Nothing.
	}

	public void onEnable() { 

		PluginManager pm = this.getServer().getPluginManager();
		
		loadConfig();

		getCommand("nsc").setExecutor(commandExecutor);

		// you can register multiple classes to handle events if you want
		// just call pm.registerEvents() on an instance of each class
		pm.registerEvents(eventListener, this);

		// do any other initialisation you need here...
	}
	
	public void reloadConfig() {
		super.reloadConfig();
		loadConfig();
	}
	
	public void loadConfig() {
		ConfigurationSection p = this.getConfig().getConfigurationSection("portals");
		
		for(Entry<String, Object> e : p.getValues(false).entrySet())
		{
			ConfigurationSection portal = (ConfigurationSection)e.getValue();

			NSCPortal tmp = new NSCPortal(
					new Location(this.getServer().getWorld(portal.getString("world")), portal.getDouble("firstCorner.x"), portal.getDouble("firstCorner.y"), portal.getDouble("firstCorner.z")),
					new Location(this.getServer().getWorld(portal.getString("world")), portal.getDouble("secondCorner.x"), portal.getDouble("seconcCorner.y"), portal.getDouble("secondCorner.z")));
			
			if(portal.get("exit") != null)
				tmp.setExit(new Location(this.getServer().getWorld(portal.getString("exit.world")), portal.getDouble("exit.x"), portal.getDouble("exit.y"), portal.getDouble("exit.z")));
			else if(portal.get("server") != null)
				tmp.setServer(portal.getString("server"));
			
			addPortal(e.getKey(), tmp);
			this.getLogger().info("Added " + e.getKey() + " : " + tmp.toString());
		}
	}
	
	public void addPortal(String name, NSCPortal p)
	{
		this.portals.put(name, p);
	}
}
