package me.ice374.tregbooks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String name = cmd.getName();
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use the commands associated with this plugin //ice374");
			return true;
		}
		Player player = (Player) sender;
		
		if (name.equals("book")) {
			if (args.length < 1) return false;
			Load.load(player, getDataFolder(), args[0], false);
			return true;
		}
		
		ItemStack abook = player.getItemInHand();
		if (!abook.getType().equals(Material.WRITTEN_BOOK)) return false;
        BookMeta book = (BookMeta) abook.getItemMeta();
        
        if (name.equals("savebook")) {
        	if (sender.isOp()){
				Save.save(book, getDataFolder(), sender, args[0]);
				return true;
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "Sorry you must be a Senior Admin to perform this command!");
			}
        }
		return true;
	}
}
