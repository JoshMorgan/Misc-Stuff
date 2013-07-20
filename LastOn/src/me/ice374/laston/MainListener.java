package me.ice374.laston;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ice374.laston.Main;

public class MainListener implements Listener {
	public static Main plugin;

	public MainListener(Main instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		long time = System.currentTimeMillis();
		plugin.putlaston(event.getPlayer(), time);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event) {
		long time = System.currentTimeMillis();
		plugin.putlaston(event.getPlayer(), time);
	}
}