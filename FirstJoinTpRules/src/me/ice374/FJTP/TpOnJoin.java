package me.ice374.FJTP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TpOnJoin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		new BukkitRunnable() {
			public void run() {
				final Player player = event.getPlayer();
				if (player.isOnline()) {
					if (!player.hasPlayedBefore()) {
						player.sendMessage(ChatColor.AQUA
								+ "Teleporting you to First Join Spawn!");
						FileConfiguration config = getConfig();
						String worldName = config.getString("FirstJoinSpawn.world");
						double x = config.getDouble("FirstJoinSpawn.x");
						double y = config.getDouble("FirstJoinSpawn.y");
						double z = config.getDouble("FirstJoinSpawn.z");
						float yaw = config.getInt("FirstJoinSpawn.yaw");
						float pitch= config.getInt("FirstJoinSpawn.pitch");
						World world = Bukkit.getWorld(worldName);
						player.teleport(new Location(world, x, y, z, yaw, pitch));

					} else {
						cancel();
					}
				} else {
					cancel();
				}
			}
		}.runTaskLater(this, 20L);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (commandLabel.equalsIgnoreCase("SetFirstSpawn")) {
			if (player.isOp()) {

			Location loc = player.getLocation();
			FileConfiguration config = this.getConfig();
			config.set("FirstJoinSpawn.world", loc.getWorld().getName());
			config.set("FirstJoinSpawn.x", loc.getX());
			config.set("FirstJoinSpawn.y", loc.getY());
			config.set("FirstJoinSpawn.z", loc.getZ());
			config.set("FirstJoinSpawn.yaw", loc.getYaw());
			config.set("FirstJoinSpawn.pitch", loc.getPitch());
			this.saveConfig();

			player.sendMessage(ChatColor.AQUA
					+ "First join spawn set at Position "
					+ "X: "
					+ player.getLocation().getBlockX() + " "
					+ "Y: "
					+ player.getLocation().getBlockY() + " "
					+ "Z: "
					+ player.getLocation().getBlockZ() + "!");
			}else{
				player.sendMessage(ChatColor.RED + "I'm sorry but you dont have permission to perform this command. Please contact the server administrators if you believe that this is an error.");
			}
		}
		return true;
	}
}
