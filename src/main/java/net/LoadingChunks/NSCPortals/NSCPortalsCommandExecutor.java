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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NSCPortalsCommandExecutor implements CommandExecutor {

    private NSCPortals plugin;

    public NSCPortalsCommandExecutor(NSCPortals plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nsc")) {
        	if(args.length > 0 && args[0].equalsIgnoreCase("reload")) {
        		
        		if(sender.isOp())
        			this.plugin.reloadConfig();
        		else
        			sender.sendMessage("You must be an OP to do that!");
        		return true;
        	} else if(args.length > 0 && args[0].equalsIgnoreCase("debug"))
        	{
        		if(this.plugin.toggleDebug())
        		{
        			sender.sendMessage("Debugger enabled.");
        		} else {
        			sender.sendMessage("Debugger disabled.");
        		}
        		
        		return true;
        	}
        }
        return false;
    }
}
