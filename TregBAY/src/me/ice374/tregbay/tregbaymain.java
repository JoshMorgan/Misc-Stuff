//        TODO
//        *Add player color to player is auctioning off message, does this happen automatically?
//        *Add Tregmine's economy system to the auction and bid commands.
//        *Learn how to make a command only for certain ranks, is it "if (bp.isAdmin() || bp.isGuardian()){}" ect, ect?

//        General Questions
//         *Did you use TagAPI to make colored names over players heads?

package me.ice374.tregbay;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class tregbaymain extends JavaPlugin {

	public Logger log = Logger.getLogger("Minecraft");

	public void createDefault() {
		File f = new File(this.getDataFolder(), "config.yml");
		if (!f.exists())
			this.saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		loadConfiguration();
		System.out.print("TregBAY has been Enabled!");

	}

	@Override
	public void onDisable() {
		saveConfiguration();
		System.out.print("TregBAY has been Disabled!");
	}

	private void saveConfiguration() {
		this.saveConfig();
	}

	public void loadConfiguration() {
		this.getConfig();
	}

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("auction")) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				player.sendMessage(ChatColor.BLACK
						+ "["
						+ ChatColor.RED
						+ "Treg"
						+ ChatColor.BLUE
						+ "B"
						+ ChatColor.YELLOW
						+ "a"
						+ ChatColor.DARK_GREEN
						+ "y"
						+ ChatColor.BLACK
						+ "] "
						+ ChatColor.GOLD
						+ "Correct Usage: "
						+ ChatColor.GRAY
						+ "Type /auction while holding the items that you wish to auction off.");
				return true;
			}
			if (this.getConfig().contains(player.getName())) {
				player.sendMessage(ChatColor.BLACK
						+ "["
						+ ChatColor.RED
						+ "Treg"
						+ ChatColor.BLUE
						+ "B"
						+ ChatColor.YELLOW
						+ "a"
						+ ChatColor.DARK_GREEN
						+ "y"
						+ ChatColor.BLACK
						+ "] "
						+ ChatColor.RED
						+ "You already have items up for auction!, Type /bid <your name> to get them back!");
				return true;
			}
			if (player.getItemInHand() == null) {
				player.sendMessage(ChatColor.BLACK
						+ "["
						+ ChatColor.RED
						+ "Treg"
						+ ChatColor.BLUE
						+ "B"
						+ ChatColor.YELLOW
						+ "a"
						+ ChatColor.DARK_GREEN
						+ "y"
						+ ChatColor.BLACK
						+ "] "
						+ ChatColor.RED
						+ "You must have an item in your hand in order to start an auction!");
				return true;
			}
			ItemStack inHand = player.getItemInHand();
			Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.RED
					+ "Treg" + ChatColor.BLUE + "B" + ChatColor.YELLOW + "a"
					+ ChatColor.DARK_GREEN + "y" + ChatColor.BLACK + "] "
					+ ChatColor.RESET + player.getName() + ChatColor.DARK_GRAY
					+ " is Auctioning off " + ChatColor.GOLD
					+ inHand.getAmount() + " "
					+ player.getItemInHand().getType().toString().toLowerCase()
					+ "! , " + "Type /bid " + player.getName()
					+ " to place a bid. ");

			getConfig().set(player.getName(), player.getItemInHand());
			player.setItemInHand(null);
			saveConfig();
			return true;
		}

		if (commandLabel.equalsIgnoreCase("bid")) {
			Player player = (Player) sender;
			if (args.length != 1) {
				player.sendMessage(ChatColor.BLACK
						+ "["
						+ ChatColor.RED
						+ "Treg"
						+ ChatColor.BLUE
						+ "B"
						+ ChatColor.YELLOW
						+ "a"
						+ ChatColor.DARK_GREEN
						+ "y"
						+ ChatColor.BLACK
						+ "] "
						+ ChatColor.GOLD
						+ "Correct Usage: "
						+ ChatColor.GRAY
						+ "Type /bid <player name> to bid on the specified players auctioned off item!");
				return true;
			}
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				player.sendMessage(ChatColor.BLACK + "[" + ChatColor.RED
						+ "Treg" + ChatColor.BLUE + "B" + ChatColor.YELLOW
						+ "a" + ChatColor.DARK_GREEN + "y" + ChatColor.BLACK
						+ "] " + ChatColor.RED + "Could not find " + args[0]
						+ " did you type their name correctly?");
				return true;
			}
			if (!(this.getConfig().contains(args[0]))) {

				player.sendMessage(ChatColor.BLACK + "[" + ChatColor.RED
						+ "Treg" + ChatColor.BLUE + "B" + ChatColor.YELLOW
						+ "a" + ChatColor.DARK_GREEN + "y" + ChatColor.BLACK
						+ "] " + ChatColor.RED + "Sorry, " + args[0]
						+ " is not currently running an auction!");
				return true;
			}
			if (this.getConfig().contains(args[0])) {
				Bukkit.broadcastMessage(ChatColor.BLACK
						+ "["
						+ ChatColor.RED
						+ "Treg"
						+ ChatColor.BLUE
						+ "B"
						+ ChatColor.YELLOW
						+ "a"
						+ ChatColor.DARK_GREEN
						+ "y"
						+ ChatColor.BLACK
						+ "] "
						+ ChatColor.RESET
						+ player.getName()
						+ ChatColor.GOLD
						+ " placed the highest bid on items that were put up for auction by "
						+ ChatColor.RESET + p.getName() + ChatColor.GOLD + "!");

				getConfig().getItemStack(player.getName());

				ItemStack tradeItem = new ItemStack

				(this.getConfig().getItemStack(p.getName()));
				this.getConfig().set(p.getName(), null);

				PlayerInventory pi = player.getInventory();
				pi.addItem(tradeItem);
				player.sendMessage(ChatColor.BLACK + "[" + ChatColor.RED
						+ "Treg" + ChatColor.BLUE + "B" + ChatColor.YELLOW
						+ "a" + ChatColor.DARK_GREEN + "y" + ChatColor.BLACK
						+ "] " + ChatColor.GOLD
						+ "Your Auction has been completed successfully, "
						+ ChatColor.RESET + p.getName() + ChatColor.GOLD
						+ " placed the highest bid.");
				p.sendMessage(ChatColor.BLACK + "[" + ChatColor.RED + "Treg"
						+ ChatColor.BLUE + "B" + ChatColor.YELLOW + "a"
						+ ChatColor.DARK_GREEN + "y" + ChatColor.BLACK + "] "
						+ ChatColor.GOLD + "You placed the highest bid on "
						+ ChatColor.RESET + player.getName() + "'s"
						+ ChatColor.GOLD + " Auction.");
				return true;
			}

			// Just a bit of fun for someone like Richard :)
			if (commandLabel.equalsIgnoreCase("creephiss")) {
				{
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.playSound(players.getLocation(), Sound.FUSE, 1,
								0);

					}
				}
			}
		}
		return false;
	}
}
