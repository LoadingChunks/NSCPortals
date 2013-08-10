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
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.HashMap;

import org.bukkit.Location;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class NSCPortalsEventListener implements Listener {

	private NSCPortals plugin;

	public NSCPortalsEventListener(NSCPortals plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();
		
		if(plugin.delayQueue.containsKey(event.getPlayer().getName()) && plugin.delayQueue.get(event.getPlayer().getName()) > (System.currentTimeMillis())) {
			return;
		} else {
			plugin.delayQueue.remove(event.getPlayer().getName());
		}

		for(NSCPortal p : this.plugin.getPortals())
		{
			if(p.getWorld() != loc.getWorld())
				continue;
			
			if(p.inPortal(loc))
			{
				if(this.plugin.isDebug() && event.getPlayer().isOp()) {
					event.getPlayer().sendMessage("You just stood in a portal!");
				}
				if(p.isLocal())
				{
					event.getPlayer().teleport(p.getExit());
				} else if(p.checkPermission(event.getPlayer())) {
					event.getPlayer().sendMessage("Sending you to " + p.getServer());
					
					/* Until I get the forwarder working, gonna have to do this... */
					if(this.plugin.getConfig().get("locations." + loc.getWorld().getName()) != null) {
						ConfigurationSection newloc = this.plugin.getConfig().getConfigurationSection("locations." + loc.getWorld().getName());
						event.getPlayer().teleport(new Location(loc.getWorld(), newloc.getDouble("x"), newloc.getDouble("y"), newloc.getDouble("z"), (float)newloc.getDouble("yaw"), (float)newloc.getDouble("pitch")));
					}
					
					this.plugin.getCoilAPI().connectPlayerToServer(event.getPlayer(), p.getServer());
					//this.plugin.getCoilAPI().sendToServerAsPlayer(event.getPlayer(), p.getServer(), "NSCPortals", "tp");
				}
				
				plugin.delayQueue.put(event.getPlayer().getName(), System.currentTimeMillis() + (3L * 1000L));
				
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.delayQueue.put(event.getPlayer().getName(), System.currentTimeMillis() + (7L*1000L)); // Stops insta-TP on join.
	}
}
