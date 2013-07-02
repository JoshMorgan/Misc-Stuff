package me.ice374.tregpack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ice374.tregpack.TregPackMain;

public class FancyHats implements CommandExecutor
{
	private final TregPackMain plugin;

	public FancyHats(TregPackMain plugin) 
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand (CommandSender sender, Command cmd,String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		ItemStack blockhat = player.getItemInHand();

		if (commandLabel.equalsIgnoreCase("hat")){
			if(blockhat == null || blockhat.getTypeId() == 0)
			{
				player.sendMessage("No item in hand!");
			}else if (blockhat != null || blockhat.getTypeId() == 0)

				if(player.getInventory().getHelmet() != null){
					player.sendMessage("Please remove your current hat first!");
				}else{

					plugin.getServer().broadcastMessage("");
					player.getInventory().setHelmet(blockhat); 
					player.sendMessage("Hat Set!");
					player.setItemInHand(null);

				}
		}
		return true;
	}
}

