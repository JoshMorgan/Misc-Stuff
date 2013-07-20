package me.ice374.returncmd;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

	static HashMap<Player, Location> canreturn = new HashMap<Player, Location>();


	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		Player p = event.getEntity();
		//add if player is donor
		canreturn.put(p, p.getLocation());
	}
	


	
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player respawned = event.getPlayer();
		//add if player is donor
		respawned.sendMessage(ChatColor.GREEN + "You may Type /return to return to you death point!");
	}
}