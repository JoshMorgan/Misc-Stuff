package me.ice374.playerip;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;


public class GetIp extends JavaPlugin implements Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player s = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("getip")) {
			if (args.length == 1) {
				String argument = args[0];
				Player player2 = Bukkit.getPlayer(argument);
				if (player2 != null && player2.isOnline()) {
		

					String ip = player2.getAddress().getAddress().toString().replaceAll("/", "");					
					

					s.sendMessage(ChatColor.DARK_RED + player2.getName() + "'s IP is: " + ChatColor.DARK_GRAY + ip);
					
					



					return true;
				}
			}
		}
		return true;
	}
}