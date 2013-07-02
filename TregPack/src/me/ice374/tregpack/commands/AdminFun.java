package me.ice374.tregpack.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminFun extends JavaPlugin implements CommandExecutor
{

	@Override
	public boolean onCommand (CommandSender sender, Command cmd,String commandLabel, String[] args)
	{

		if (commandLabel.equalsIgnoreCase("creep")){
			Player p = (Player) sender;
			if (p.isOp()) {
		        for(Player peeps : Bukkit.getOnlinePlayers()) {
		            peeps.playSound(peeps.getLocation(), Sound.FUSE, 1, 0); 
		        }
			}else{
				p.sendMessage(ChatColor.RED + "Sorry, you dont have permission to perform this command.");
		}
			}
			
		if (commandLabel.equalsIgnoreCase("fireworks")){
			Player p = (Player) sender;
			if (p.isOp()) {
			shootFireworks();
			}else{
				p.sendMessage(ChatColor.RED + "Sorry, you dont have permission to perform this command.");
			}
		}
		return false;
			}	
			
			private void shootFireworks(){
	        for(Player player : Bukkit.getOnlinePlayers()) {
	            Firework fw =(Firework) player.getWorld().spawn(
	            		player.getLocation(), Firework.class);
	            FireworkMeta fm = fw.getFireworkMeta();
	            
	            Random r = new Random();
	            int fType = r.nextInt(5) + 1;
	            Type type = null;
	            switch (fType){
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
	            int power = r.nextInt(2)+1;
	            fm.setPower(power);
	            fw.setFireworkMeta(fm);
	            
	            }
	        }
		public Color getColor(int c) {
			switch (c) {
			default:
			case 1:return Color.AQUA;
			case 2:return Color.BLACK;
			case 3:return Color.BLUE;
			case 4:return Color.FUCHSIA;
			case 5:return Color.GRAY;
			case 6:return Color.GREEN;
			case 7:return Color.LIME;
			case 8:return Color.MAROON;
			case 9:return Color.NAVY;
			case 10:return Color.OLIVE;
			case 11:return Color.PURPLE;
			case 12:return Color.RED;
			case 13:return Color.SILVER;
			case 14:return Color.TEAL;
			case 15:return Color.WHITE;
			case 16:return Color.YELLOW;
			}
	}
}