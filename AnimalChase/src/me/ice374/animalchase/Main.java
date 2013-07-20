package me.ice374.animalchase;

//import java.util.Random;

//import java.util.ArrayList;

//import java.lang.reflect.Field;

//import net.minecraft.server.v1_5_R3.ItemStack;
//import net.minecraft.server.v1_5_R3.NBTTagCompound;
//import net.minecraft.server.v1_5_R3.NBTTagList;
 
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

//import org.bukkit.Bukkit;
//import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
/*import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;*/

//import org.bukkit.entity.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginManager;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	/*
	@Override
	public boolean onCommand(CommandSender sender, Command e,
			String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("gianthunt")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {

					Entity giant = p.getWorld().spawnEntity(p.getLocation(),
							EntityType.GIANT);
					LivingEntity giant1 = (LivingEntity) giant;

					giant1.addPotionEffect(new PotionEffect(
							PotionEffectType.DAMAGE_RESISTANCE,
							Integer.MAX_VALUE, 1));
					giant1.addPotionEffect(new PotionEffect(
							PotionEffectType.SPEED, Integer.MAX_VALUE, 30));
					giant1.addPotionEffect(new PotionEffect(
							PotionEffectType.INCREASE_DAMAGE,
							Integer.MAX_VALUE, 1));

					giant1.setCustomName("123456789098764");

					p.sendMessage(ChatColor.DARK_RED + "[Giant] "
							+ ChatColor.GRAY + "A Giant has been spawned at "
							+ ChatColor.DARK_GRAY + "X: " + ChatColor.GRAY
							+ giant1.getLocation().getBlockX()
							+ ChatColor.DARK_GRAY + ", Y: " + ChatColor.GRAY
							+ giant1.getLocation().getBlockY()
							+ ChatColor.DARK_GRAY + ", Z: " + ChatColor.GRAY
							+ giant1.getLocation().getBlockZ());

				} else {
					p.sendMessage(ChatColor.RED
							+ "Sorry, you dont have permission to perform this command.");
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity().getCustomName().contentEquals("123456789098764")) {
			if (e.getEntity().getType().equals(EntityType.GIANT)
					&& e.getEntity().getKiller() instanceof Player) {
				e.setDroppedExp(15000);
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(ChatColor.DARK_RED + "[Giant] "
							+ ChatColor.GOLD + "The Giant has been slain by "
							+ e.getEntity().getKiller().getName() + "!");
					shootFireworks();
				}
			}
		}
	}

	private void shootFireworks() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Firework fw = (Firework) player.getWorld().spawn(
					player.getLocation(), Firework.class);
			FireworkMeta fm = fw.getFireworkMeta();

			Random r = new Random();
			int fType = r.nextInt(5) + 1;
			Type type = null;
			switch (fType) {
			default:
			case 1:
				type = Type.BALL;
				break;
			case 2:
				type = Type.BALL_LARGE;
				break;
			case 3:
				type = Type.BURST;
				break;
			case 4:
				type = Type.CREEPER;
				break;
			case 5:
				type = Type.STAR;
			}
			int c1i = r.nextInt(16) + 1;
			int c2i = r.nextInt(16) + 1;
			Color c1 = getColor(c1i);
			Color c2 = getColor(c2i);
			FireworkEffect effect = FireworkEffect.builder()
					.flicker(r.nextBoolean()).withColor(c1).withFade(c2)
					.with(type).trail(r.nextBoolean()).build();
			fm.addEffect(effect);
			int power = r.nextInt(2) + 1;
			fm.setPower(power);
			fw.setFireworkMeta(fm);

		}
	}

	public Color getColor(int c) {
		switch (c) {
		default:
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.PURPLE;
		case 12:
			return Color.RED;
		case 13:
			return Color.SILVER;
		case 14:
			return Color.TEAL;
		case 15:
			return Color.WHITE;
		case 16:
			return Color.YELLOW;
		}
	}
	 */
	/*
	public void onAttackMob(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		World world = entity.getWorld();
		if (!(entity instanceof Player)) {

			if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) { 

				EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entity
						.getLastDamageCause();
				if (entityDamageByEntityEvent.getDamager() instanceof Player) { 

					Location location = entity.getLocation();
					world.strikeLightning(location);

				}
			}
		}
	}
	*/
	
	@EventHandler
	public void onAttackMob(EntityDamageByEntityEvent e){
	    if ((e.getDamager() != null && e.getDamager() instanceof Player)
	        && (e.getEntity() != null && !(e.getEntity() instanceof Player))){
	        // Will fire only if a Player attacks a non-player. Added precautionary null checks even though they should NOT be needed with this event.
	        e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
	    }
	}
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event){
		Player player = event.getPlayer();
		World world = player.getWorld();
		if(player.getInventory().getItemInHand().equals(Material.DIAMOND_SWORD)){
			if (player.getItemInHand().getItemMeta().getDisplayName().equals("§5Excalibur")) {
				if (player.getName().equals("ice374")){
					Block targetBlock = player.getTargetBlock(null, 100);
					Location location = targetBlock.getLocation();
					world.strikeLightning(location);
				}else{ player.sendMessage(ChatColor.RED + "Only ice374 may weild this sword!");
				}
			}
		}
	}
	
	final private static Enchantment DURAB = Enchantment.DURABILITY;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("excalibur")){
			ItemStack swordItem = new ItemStack(Material.DIAMOND_SWORD, 1);
			swordItem.addEnchantment(DURAB, 3);
			ItemMeta im = swordItem.getItemMeta();
			im.setDisplayName("Broken Sword");
			im.setLore(Arrays.asList("Type: Weapon"));
			swordItem.setItemMeta(im);
			player.getInventory().setItemInHand(swordItem);
		}
		return true;
	}
	
/*	 @Override
	    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	        if (sender instanceof Player) {
	            Player player = (Player) sender;
	            CraftItemStack test = CraftItemStack.asCraftCopy(new org.bukkit.inventory.ItemStack(Material.IRON_AXE, 1));
	            
	            addGlow(test);
	            player.getInventory().addItem(test);
	        }
	        
	        return true;
	    }
	 
	    private void addGlow(org.bukkit.inventory.ItemStack stack) {
	        ItemStack nmsStack = (ItemStack) getField(stack, "handle");
	        NBTTagCompound compound = nmsStack.tag;
	        
	        // Initialize the compound if we need to
	        if (compound == null) {
	            compound = new NBTTagCompound();
	            nmsStack.tag = compound;
	        }
	        
	        // Empty enchanting compound
	        compound.set("ench", new NBTTagList());
	    }
	    
	    private Object getField(Object obj, String name) {
	        try {
	            Field field = obj.getClass().getDeclaredField(name);
	            field.setAccessible(true);
	 
	            return field.get(obj);
	        } catch (Exception e) {
	            // We don't care
	            throw new RuntimeException("Unable to retrieve field content.", e);
	        }
	    }
	    */
	}

