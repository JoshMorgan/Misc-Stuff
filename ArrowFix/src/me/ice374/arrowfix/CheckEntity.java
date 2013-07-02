package me.ice374.arrowfix;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CheckEntity extends JavaPlugin implements Listener{
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
	    Entity e1 = e.getEntity();
	    Entity d1 = e.getDamager();

	    /**  CHECK IF 
	     * e1.getLocation().equals(however you check if the player is in a non-pvp zone) &
	    **/

	    if(e1 instanceof Player && d1 instanceof Arrow){
	        Player hurt = (Player) e1;
	        if(((Arrow)d1).getShooter() instanceof Player){
	            Arrow arrow = (Arrow) d1;
	            Player shooter = (Player) arrow.getShooter();
	                	  hurt.sendMessage(ChatColor.DARK_PURPLE + shooter.getName() + ChatColor.RED + " Just tried to shoot you in a non-pvp zone");
	                	  hurt.sendMessage(ChatColor.RED + "The event has been canceled!");
	                      e.setCancelled(true);
	                      
	                  }
	            }
	       }
	   }

