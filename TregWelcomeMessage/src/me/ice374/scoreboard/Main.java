package me.ice374.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		System.out.print("Enabled WelcomeBoard!");
	}

	@Override
	public void onDisable() {
		System.out.print("Disabled WelcomeBoard!");
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
	    	    final Player player = event.getPlayer();
	            if (player.isOnline())
	            {

	            	final ScoreboardManager manager = Bukkit.getScoreboardManager();
	            	Scoreboard board = manager.getNewScoreboard();
	            	 
	            	Objective objective = board.registerNewObjective("1", "2");
	            	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	            	objective.setDisplayName("" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Welcome to Tregmine!");
	            	 
	            	Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BLACK + "Your Balance:")); //Get a fake offline player
	            	score.setScore(3400000);
	            	  player.setScoreboard(board);
	            	
	            
	            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() { 
	            	public void run() {
	            		player.setScoreboard(manager.getNewScoreboard());
	            	}
	            	}, 400); //400 = 20 seconds. 1 second = 20 ticks, 20*20=400
	            
	            }
	            }
	}