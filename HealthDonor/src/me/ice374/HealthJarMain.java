	//----------------------------------------Package---------------------------------------------------------------

	package me.ice374;

	//----------------------------------------Imports---------------------------------------------------------------

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

	//-----------------------------------Load & Enable/Disable------------------------------------------------------

	public class HealthJarMain extends JavaPlugin {
		
		public final Logger logger = Logger.getLogger("Minecraft");
		public static HealthJarMain plugin;
	//off
		@Override
		public void onDisable() {
			PluginDescriptionFile pdffile = this.getDescription();
			this.logger.info(pdffile.getName() + "is now disabled.");
		}
	//on
		@Override
		public void onEnable() {
			PluginDescriptionFile pdffile = this.getDescription();
			this.logger.info(pdffile.getName() + "Version" + pdffile.getVersion() + "is now enabled!");
		}	
	

	//-----------------------------------Commands------------------------------------------------------------------
	
public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String[] args) {
	Player player = (Player) sender;
	if (commandLabel.equalsIgnoreCase("storehealth")){
        }
	{if(player.getInventory().contains(Material.GLASS_BOTTLE)){
		
		int health = player.getHealth();
		int currentexp = player.getTotalExperience();
		int currentlevel = player.getExpToLevel();
		
		if (currentexp < 17){
			player.sendMessage(ChatColor.DARK_RED + "Sorry, You need more EXP to store your health!");
			
		}else if (currentexp >= 17){
			player.setTotalExperience(player.getTotalExperience() - 17);
			player.setLevel(currentlevel - 18);
			
		if (health < 7){
			player.sendMessage(ChatColor.DARK_RED + "Sorry, you would die if you tried to take more blood!");
		
		}else if(health > 6){
			player.setHealth(health -6);
			player.sendMessage(ChatColor.GOLD + "You have successfully stored some of your health in a bottle!"); 
			ItemStack deljar = new ItemStack(Material.GLASS_BOTTLE);
			ItemStack healthjar = (new ItemStack(Material.POTION, 1, (short) 5));
			PlayerInventory pi = player.getInventory();
			pi.addItem(healthjar);
			pi.removeItem(deljar);}}
		
	}else{player.sendMessage(ChatColor.RED + "You must have an empty bottle in your inventory to store your health in!"); }
		
}
	return false;
}
}