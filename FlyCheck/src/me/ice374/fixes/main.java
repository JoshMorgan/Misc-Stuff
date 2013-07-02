package me.ice374.fixes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		System.out.print("FlyCheck have been Enabled!");
	}

	@Override
	public void onDisable() {
		System.out.print("FlyCheck have been Disabled!");
	}

	public Map<String, Boolean> playerData = new HashMap<String, Boolean>();
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
	    new BukkitRunnable()
	    {
	        public void run()
	        {
	    	    final Player player = event.getPlayer();
	            if (player.isOnline())
	            {
	            	
		            if (!player.canfly())
		            {
		            	if(player.getLocation().clone().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
		            	if(!playerData.get(player.getName())) {
		            	player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "Please dont fly!");
		            	player.sendMessage(ChatColor.GRAY + "Flying is a perk for players who donate to keep the server running.");
		            	playerData.put(player.getName(), Boolean.valueOf(true));
		            	}
		            	} else {
		            	playerData.put(player.getName(), Boolean.valueOf(false));
		            }         	
		            }
		            else
		            {
		                cancel();
		            }         	
	            }
	            else
	            {
	                cancel();
	            }
	        }
	    }.runTaskLater(this, 150L);
	}
}

	/*
	public Map<String, Boolean> playerData = new HashMap<String, Boolean>();
	 
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	final Player player = event.getPlayer();
	 
	if(player.getLocation().clone().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
	if(!playerData.get(player.getName())) {
	player.sendMessage("You are in the air.");
	playerData.put(player.getName(), Boolean.valueOf(true));
	}
	} else {
	playerData.put(player.getName(), Boolean.valueOf(false));
	}
	}
}
*/
/*	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		
			Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable() {
				 
				  public void run() {
					  if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.AIR) {
						  player.sendMessage(ChatColor.GRAY + "Your flying!");
				      //player.sendMessage(ChatColor.DARK_RED + "[Notifier] " + ChatColor.AQUA + getConfig().getString("message"));
				      //player.sendMessage(ChatColor.DARK_RED + "[Notifier] " + ChatColor.AQUA + getConfig().getString("help"));
				  }
				  }
				}, 100L, 600L);
		}

	//for before event handler
}
*/

	
/*			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this,
					new Runnable() {
				public void run() {
					if (!getConfig().contains(player.getName())) {
						player.sendMessage(ChatColor.BOLD + ""
								+ ChatColor.DARK_PURPLE + "1");
						getConfig().set(player.getName(), null);
						saveConfig();
					}
				}
			}, 300L);

			
				Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this,
						new Runnable() {
					public void run() {
						if (getConfig().contains(player.getName())) {

							getConfig().set("wait", player.getName());
							saveConfig();
							
							player.sendMessage(ChatColor.GOLD + "2");
						}
					}
				}, 200L);


					Bukkit.getScheduler().scheduleSyncDelayedTask(
							(Plugin) this, new Runnable() {
								public void run() {
									if (!getConfig().getStringList(ChatColor.BLUE + "wait").contains(player.getName())) {
										
										getConfig().set(player.getName(),player);
										getConfig().set("wait" + player.getName(), null);
										saveConfig();
										
//msg?
										player.sendMessage("DO stuff");
									}
								}
							}, 40L);
				}
}
}

*/

// @EventHandler
// public void onPlayerMove(PlayerMoveEvent e){
// Player player = e.getPlayer();
//
// if(player.getLocation().add(0,-1,0).getBlock().getType() == Material.AIR){
// player.sendMessage(ChatColor.GREEN + "falling...");
// }
// }}

// try this to make delay
//
// getServer().getBukkitScheduler().delaySyncEvent(plugin, thread, 20)

