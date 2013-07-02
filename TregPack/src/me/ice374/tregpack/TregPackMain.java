package me.ice374.tregpack;

import org.bukkit.plugin.java.JavaPlugin;

import me.ice374.tregpack.commands.AdminFun;
import me.ice374.tregpack.commands.FancyHats;

public class TregPackMain extends JavaPlugin
{

	@Override
	public void onEnable()
	{	
			this.getCommand("hat").setExecutor(new FancyHats(this));
			this.getCommand("creep").setExecutor(new AdminFun());
			this.getCommand("fireworks").setExecutor(new AdminFun());
		}
	
	@Override
	public void onDisable()
	{
		getLogger().info("PluginPack Is Disabled");
	}
}
