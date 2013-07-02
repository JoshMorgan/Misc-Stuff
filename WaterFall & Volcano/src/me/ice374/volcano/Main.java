package me.ice374.volcano;

//import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
//import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
//import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
//import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		getConfig();
	}

	public void onDisable() {
		saveConfig();
	}

	/*
	 * @EventHandler public void onInteract(PlayerInteractEvent event){
	 * if(event.getAction().equals(Action.LEFT_CLICK_AIR)){ Player player =
	 * event.getPlayer(); Location location = player.getLocation(); FallingBlock
	 * block = player.getLocation().getWorld() .spawnFallingBlock(location,
	 * Material.DIRT, (byte) 0); player.sendMessage("HEH");
	 * 
	 * 
	 * //Vector dir = location.getDirection(); //Vector dir =
	 * this.getConfig().getInt("direction.location1");
	 * 
	 * Vector v = new Vector(0,1,0); //Vector this.getConfig().set("Vector", v);
	 * //Set config value to vector Vector v2 =
	 * this.getConfig().getVector("Vector"); //Load vector //
	 * block.setVelocity(v2.multiply(10)); //Do stuff with loaded vector
	 * 
	 * int multiplier = this.getConfig().getInt("speed.location1");
	 * block.setVelocity(v2.multiply(multiplier)); } }
	 */
	
	
	
	
	
	
	
	/*
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if (block.getType() == Material.IRON_BLOCK && player.getItemInHand().getType() == Material.BLAZE_ROD) {
			Location pos1 = player.getLocation();
			this.getConfig().set("position1", pos1);
			player.sendMessage(ChatColor.GREEN + "Set Position 1");
		}
		if (block.getType() == Material.REDSTONE_BLOCK && player.getItemInHand().getType() == Material.BLAZE_ROD) {
			Location pos2 = player.getLocation();
			this.getConfig().set("position2", pos2);
			player.sendMessage(ChatColor.GREEN + "Set Position 2");
		}
		if (block.getType() == Material.DIAMOND_BLOCK && player.getItemInHand().getType() == Material.BLAZE_ROD) {
			
			
			Location pos2 = (Location) this.getConfig().get("position2");
			FallingBlock flyingblock = player.getLocation().getWorld().spawnFallingBlock((Location) this.getConfig().get("position1"), Material.DIRT, (byte) 0);
			Vector dir = pos2.getDirection();
            flyingblock.setVelocity(dir);
			player.sendMessage(ChatColor.GREEN + "please work");
		}
		
		
		*/
		
	@EventHandler 
	public void onInteract(PlayerInteractEvent event){
		  if(event.getAction().equals(Action.LEFT_CLICK_AIR)){ 
			  Player player = event.getPlayer();
			  final FallingBlock block = player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Material.STATIONARY_WATER, (byte) 0);
		 // final Player player = (Player) sender;	
	 
		  class Counter {
			  private double count = 0;
			  public Counter() {}
			  public void up() {
				  count += 0.2d;
			  }
			  public double value() {
				  return count;
			  }
		  }
	 
	final Counter counter = new Counter();
	 
	final int id = this.getServer().getScheduler().runTaskTimer(this, new Runnable() {
	    public void run() {
	        Vector v = new Vector(Math.cos(counter.value()), 0.5d, Math.sin(counter.value()));
	        block.setVelocity(v);
	        counter.up();
	    }
	}, 1L, 1L).getTaskId();
		  }
	/* 
	this.getServer().getScheduler().runTaskLater(this, new Runnable() {
	    public void run() {
	        getServer().getScheduler().cancelTask(id);
	    }
	}, 600L);
		
		  }
		*/
		
		
		/*
		 * Location loc1 = this.getConfig().getString("speed.location1");
		 * //b.setVelocity(dir.multiply(multiplier)); Vector loc2 =
		 * this.getConfig().getVector("loc2");
		 * 
		 * FallingBlock block = player.getLocation().getWorld()
		 * .spawnFallingBlock(loc1, Material.DIRT, (byte) 0);
		 * player.sendMessage("HEH");
		 * 
		 * 
		 * //Vector dir = location.getDirection(); //Vector dir =
		 * this.getConfig().getInt("direction.location1");
		 * 
		 * Vector v = new Vector(0,1,0); //Vector this.getConfig().set("Vector",
		 * v); //Set config value to vector Vector v2 =
		 * this.getConfig().getVector("Vector"); //Load vector //
		 * block.setVelocity(v2.multiply(10)); //Do stuff with loaded vector
		 * 
		 * int multiplier = this.getConfig().getInt("speed.location1");
		 * block.setVelocity(v2.multiply(multiplier));
		 */
	}


	/*
	 * @EventHandler public void onInteract(PlayerInteractEvent event){
	 * if(event.getAction().equals(Action.LEFT_CLICK_AIR)){ FallingBlock block =
	 * event
	 * .getPlayer().getWorld().spawnFallingBlock(event.getPlayer().getLocation
	 * (), Material.LAVA, (byte) 0); float x = (float) -1 + (float)
	 * (Math.random() * ((1 - -1) + 1)); float y = (float) -5 +
	 * (float)(Math.random() * ((5 - -5) + 1)); float z = (float) -0.3 +
	 * (float)(Math.random() * ((0.3 - -0.3) + 1)); Bukkit.broadcastMessage("§c"
	 * + x + ", §a" + y + ", §d" + z); block.setVelocity(new Vector(x, y, z)); }
	 * }
	 */
	/*
	 * @EventHandler public void onEntityExplode(EntityExplodeEvent e){
	 * e.setCancelled(true);
	 * 
	 * for(Block b : e.blockList()) { bounceBlock(b); } }
	 * 
	 * public void bounceBlock(Block b) { if(b == null) return;
	 * 
	 * FallingBlock fb = b.getWorld() .spawnFallingBlock(b.getLocation(),
	 * b.getType(), b.getData());
	 * 
	 * b.setType(Material.AIR);
	 * 
	 * float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1)); float y
	 * = 2;//(float) -5 + (float)(Math.random() * ((5 - -5) + 1)); float z =
	 * (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
	 * 
	 * fb.setVelocity(new Vector(x, y, z)); }
	 */
}