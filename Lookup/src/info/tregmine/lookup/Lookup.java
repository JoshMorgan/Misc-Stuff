package info.tregmine.lookup;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import info.tregmine.Tregmine; 
//import com.tregdev.tregmine.TregmineBlock;
 

public class Lookup extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	public Tregmine tregmine = null;
	
	public final LookupPlayer lookupplayer = new LookupPlayer(this);
	
	public void onEnable(){
		Plugin test = this.getServer().getPluginManager().getPlugin("Tregmine");

		if(this.tregmine == null) {
		    if(test != null) {
//				String enable = this.getDescription().getName() + " " + this.getDescription().getVersion() + " enable"; 
//				log.info(enable);
			this.tregmine = ((Tregmine)test);
		    } else {
				log.info(this.getDescription().getName() + " " + this.getDescription().getVersion() + " - could not find Tregmine");
				this.getServer().getPluginManager().disablePlugin(this);
		    }
		}
		  getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, lookupplayer, Priority.Lowest, this);
	}
	 
	public void onDisable(){
//		String enable = this.getDescription().getName() + " " + this.getDescription().getVersion() + " disable"; 
//		log.info(enable);
		this.getServer().getScheduler().cancelTasks(this);
	}
	public void onLoad() {

		getServer().getScheduler().scheduleAsyncRepeatingTask(this,new Runnable() {
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					if (p.getAddress().getAddress().getHostAddress().matches("127.0.0.1")) {
						p.sendMessage(ChatColor.RED + "You are connected to the wrong server, use" + ChatColor.AQUA + " mc.tregmine.info");
						p.sendMessage(ChatColor.RED + "You are warned until you change server, and can't build");
						info.tregmine.api.TregminePlayer tregPlayer = tregmine.tregminePlayer.get(p.getName());
						tregPlayer.setTempMetaString("color", "warned");
						tregPlayer.setTempMetaString("trusted", "false");
					}
				}
			}},400L, 400L);

		
	}

}