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

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class NSCPortalsEventListener implements Listener {

	private NSCPortals plugin;

	public NSCPortalsEventListener(NSCPortals plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location loc = event.getPlayer().getLocation();

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
				} else {
					event.getPlayer().sendMessage("Sending you to " + p.getServer());
					this.plugin.getCoilAPI().connectPlayerToServer(event.getPlayer(), p.getServer());
				}
				return;
			}
		}
	}
}
