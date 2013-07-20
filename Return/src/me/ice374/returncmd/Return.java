package me.ice374.returncmd;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Return extends JavaPlugin {

	public final Logger log = Logger.getLogger("Minecraft");
	public static Return plugin;

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String[] args) {
		Player player = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("return")){
			// add if player is a donor
			if(args.length == 0){
				if (PlayerListener.canreturn.containsKey(player)) {
					player.teleport(PlayerListener.canreturn.get(player));
					player.sendMessage(ChatColor.GREEN + "Successfully teleported you to your death point!");
					PlayerListener.canreturn.remove(player);
				} else {
					player.sendMessage(ChatColor.RED + "Unable to find the last time you died!");
				}


			}
		}

		return false;

	}

}