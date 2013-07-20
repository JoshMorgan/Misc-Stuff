package me.ice374.test;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		getConfig();
	}
	
	public void onDisable() {
		saveConfig();
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
        String name = player.getName();
	        if(commandLabel.equalsIgnoreCase("checkplayer")){
	        	if (getConfig().getString("HasAlreadyPurchasedExcalibur").contains(name)){
	        		player.sendMessage(ChatColor.GOLD + "You may weild Excalibur!");
	        }else{player.sendMessage(ChatColor.DARK_RED + "You do not have permission to weild this sword!");
	        }
	           // player.sendMessage(ChatColor.AQUA + getConfig().getString("website message"));
	           // getConfig().getBoolean("Broadcast_website");
	           // Bukkit.broadcastMessage(ChatColor.GREEN + player.getDisplayName() + " used /website to get a link to the server's website!");
	        }else if(commandLabel.equalsIgnoreCase("setbutton")){   
	        	int X = player.getEyeLocation().getBlockX();
	        	int Y = player.getEyeLocation().getBlockY();
	        	int Z = player.getEyeLocation().getBlockZ();
	        	String World = player.getWorld().getName();
	        	getConfig().set("ExcaliburButtonLocation.X", X);
	        	getConfig().set("ExcaliburButtonLocation" + Y, Y);
	        	getConfig().set("ExcaliburButtonLocation" + Z, Z);
	        	getConfig().set("ExcaliburButtonLocation" + World, World);
	        	
	            player.sendMessage(ChatColor.GOLD + getConfig().getString("ExcaliburButton Set"));
	            
	        }else if(commandLabel.equalsIgnoreCase("addplayer")){   
	        	
	        	//player.sendMessage(ChatColor.GOLD + getConfig().getString("HasAlreadyPurchasedExcalibur").add(name));
	        	
	                List<String> configPlayers = this.getConfig().getStringList(
	                        "Punishment.Players");
	         
	                // String playerName = player.getName();
	         
	                for (String playerName : configPlayers) {
	                    playerName = player.getName();
	                    if (playerName.equals(configPlayers)) {
	                    	player.sendMessage("HAHA");
	                    }
	                }
	         
	            }
	        	//getConfig().createSection("PurchasedExcalibur").createSection(name).set(name, true);
			return false;
}
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
            FallingBlock block = event.getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation(), Material.LAVA, (byte) 0);
            float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
            float y = (float) -5 + (float)(Math.random() * ((5 - -5) + 1));
            float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
            Bukkit.broadcastMessage("§c" + x + ", §a" + y + ", §d" + z);
            block.setVelocity(new Vector(x, y, z));
        }
    }
}