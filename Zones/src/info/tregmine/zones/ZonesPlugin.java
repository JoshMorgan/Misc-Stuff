package info.tregmine.zones;

import info.tregmine.api.TregminePlayer;
import info.tregmine.api.Zone;
import info.tregmine.api.Zone.Permission;
import info.tregmine.database.Mysql;
import info.tregmine.listeners.ZoneBlockListener;
import info.tregmine.listeners.ZoneEntityListener;
import info.tregmine.listeners.ZonePlayerListener;
import info.tregmine.quadtree.IntersectionException;
import info.tregmine.quadtree.Point;
import info.tregmine.quadtree.QuadTree;
import info.tregmine.quadtree.Rectangle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import info.tregmine.Tregmine; 
import info.tregmine.ZonesDAO;

public class ZonesPlugin extends JavaPlugin 
{
	public static class ZoneWorld
	{
		private World world;
		private QuadTree<Zone> zonesLookup;
		private Map<String, Zone> zoneNameLookup;
		
		public ZoneWorld(World world)
		{
			this.world = world;
			this.zonesLookup = new QuadTree<Zone>(0);
			this.zoneNameLookup = new HashMap<String, Zone>();
		}
		
		public String getName()
		{
			return world.getName();
		}
		
		public void addZone(Zone zone)
		throws IntersectionException
		{
			for (Rectangle rect : zone.getRects()) {
				zonesLookup.insert(rect, zone);
			}	
			zoneNameLookup.put(zone.getName(), zone);
		}
		
		public boolean zoneExists(String name)
		{
			return zoneNameLookup.containsKey(name);
		}
		
		public Zone getZone(String name)
		{
			return zoneNameLookup.get(name);
		}
		
		public Zone findZone(Point p)
		{
			return zonesLookup.find(p);
		}
		
		public void deleteZone(String name)
		{
			if (!zoneNameLookup.containsKey(name)) {
				return;
			}
			
			zoneNameLookup.remove(name);
			
			this.zonesLookup = new QuadTree<Zone>(0);
			for (Zone zone : zoneNameLookup.values()) {
				try {
					for (Rectangle rect: zone.getRects()) {
						zonesLookup.insert(rect, zone);
					}
				} catch (IntersectionException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public final Logger log = Logger.getLogger("Minecraft");
	
	public Tregmine tregmine = null;
	public Map<String, ZoneWorld> worlds;
	
	public void onLoad() 
	{
	}
	
	public void onEnable()
	{
		Server server = getServer();
		PluginManager pluginMgm = server.getPluginManager();
		
		if (this.tregmine == null) {
			Plugin plugin = pluginMgm.getPlugin("Tregmine");
			if (plugin != null) {
				this.tregmine = (Tregmine)plugin;
			} else {
				log.info(this.getDescription().getName() + " " + this.getDescription().getVersion() + " - could not find Tregmine");
				pluginMgm.disablePlugin(this);
			}
		}
		
		pluginMgm.registerEvent(Event.Type.BLOCK_BREAK, new ZoneBlockListener(this), Priority.High, this);
		pluginMgm.registerEvent(Event.Type.BLOCK_PLACE, new ZoneBlockListener(this), Priority.High, this);
		pluginMgm.registerEvent(Event.Type.PLAYER_INTERACT, new ZonePlayerListener(this), Priority.High, this);
		pluginMgm.registerEvent(Event.Type.PLAYER_MOVE, new ZonePlayerListener(this), Priority.High, this);
		pluginMgm.registerEvent(Event.Type.PLAYER_TELEPORT, new ZonePlayerListener(this), Priority.High, this);
		//pluginMgm.registerEvent(Event.Type.CREATURE_SPAWN, new ZoneEntityListener(this), Priority.High, this);
		pluginMgm.registerEvent(Event.Type.ENTITY_DAMAGE, new ZoneEntityListener(this), Priority.High, this);
		
		worlds = new HashMap<String, ZoneWorld>();
		
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			
			for (World world : server.getWorlds()) {
				ZoneWorld zoneWorld = new ZoneWorld(world);
				
				List<Zone> zones = dao.getZones(world.getName());
				for (Zone zone : zones) {
					zoneWorld.addZone(zone);
				}
				
				worlds.put(world.getName(), zoneWorld);
			}
		} catch (IntersectionException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
	}

	public void onDisable()
	{
	}

	public ZoneWorld getWorld(World world)
	{
		return worlds.get(world.getName());
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) 
	{
		String commandName = command.getName().toLowerCase();
		TregminePlayer player = null;

		if (sender instanceof Player) {
			player = tregmine.getPlayer((Player)sender);
		} else {
			return false;
		}
		
		if (commandName.equals("zone")) {
			if (args.length == 0) {
				return true;
			}
			
			if ("create".equals(args[0]) && player.getMetaBoolean("zones")) {
				createZone(player, args);
				return true;
			}
			else if ("delete".equals(args[0]) && player.getMetaBoolean("zones")) {
				deleteZone(player, args);
				return true;
			}
			else if ("adduser".equals(args[0])) {
				addUser(player, args);
				return true;
			}
			else if ("deluser".equals(args[0])) {
				delUser(player, args);
				return true;
			}
			else if ("entermsg".equals(args[0])) {
				changeValue(player, args);
				return true;
			}
			else if ("exitmsg".equals(args[0])) {
				changeValue(player, args);
				return true;
			}
			else if ("pvp".equals(args[0]) && player.getMetaBoolean("zones")) {
				changeValue(player, args);
				return true;
			}
			else if ("enter".equals(args[0])) {
				changeValue(player, args);
				return true;				
			}
			else if ("place".equals(args[0])) {
				changeValue(player, args);
				return true;				
			}
			else if ("destroy".equals(args[0])) {
				changeValue(player, args);
				return true;				
			}
			else if ("info".equals(args[0])) {
				zoneInfo(player, args);
				return true;
			}
		}
		
		return false;
	}
	
	public void createZone(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		if (args.length < 2) {
			player.sendMessage("syntax: /zone create [name]");
			return;
		}
		
		String name = args[1];
		if (world.zoneExists(name)) {
			player.sendMessage(ChatColor.RED + "A zone named " + name + " does already exist.");
			return;
		}
		
		Block b1 = player.getBlock("b1");
		Block b2 = player.getBlock("b2");
		
		Rectangle rect = new Rectangle(b1.getX(), b1.getZ(), b2.getX(), b2.getZ());
		
		Zone zone = new Zone();
		zone.setWorld(world.getName());
		zone.setName(name);
		zone.addRect(rect);
		zone.setTextEnter("Welcome to " + name + "!");
		zone.setTextExit("Now leaving " + name + ".");
		zone.addUser(player.getName(), Zone.Permission.Owner);
		
		player.sendMessage(ChatColor.RED + "Creating zone at " + rect);
		
		try {
			world.addZone(zone);
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Zone created successfully.");
		} catch (IntersectionException e) {
			player.sendMessage(ChatColor.RED + "The zone you tried to create overlaps an existing zone.");
		}
		
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			int userId = dao.getUserId(player.getName());
			if (userId == -1) {
				player.sendMessage("Player " + player.getName() + " was not found.");
				return;
			}
			
			dao.createZone(zone);
			dao.addRectangle(zone.getId(), rect);
			dao.addUser(zone.getId(), userId, Zone.Permission.Owner);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
	}
	
	public void deleteZone(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		if (args.length < 2) {
			player.sendMessage("syntax: /zone delete [name]");
			return;
		}
		
		String name = args[1];
		
		Zone zone = world.getZone(name);
		if (zone == null) {
			player.sendMessage(ChatColor.RED + "A zone named " + name + " does not exist.");
			return;
		}
		
		world.deleteZone(name);
		player.sendMessage(ChatColor.RED + "[" + name + "] " + "Zone deleted.");
		
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			dao.deleteZone(zone.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
	}
	
	public void addUser(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		if (args.length < 4) {
			player.sendMessage(ChatColor.RED + "syntax: /zone adduser [zone] [player] [perm]");
			return;
		}
		
		String zoneName = args[1];
		String userName = args[2];
		Zone.Permission perm = Zone.Permission.fromString(args[3]);
		
		Zone zone = world.getZone(zoneName);
		if (zone == null) {
			player.sendMessage(ChatColor.RED + "Specified zone does not exist.");
			return;
		}
		
		if (zone.getUser(player.getName()) != Permission.Owner) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "You do not have permission to add users to this zone.");
			return;
		}
		
		if (perm == null) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Unknown permission " + args[3] + ".");
			return;
		}
		
		// store permission change in db
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			int userId = dao.getUserId(userName);
			if (userId == -1) {
				player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Player " + userName + " was not found.");
				return;
			}
			
			dao.deleteUser(zone.getId(), userId);
			dao.addUser(zone.getId(), userId, perm);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
		
		zone.addUser(userName, perm);
		String addedConfirmation = perm.getAddedConfirmation();
		player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + String.format(addedConfirmation, userName, zoneName));
		
		TregminePlayer player2 = tregmine.getPlayer(userName);
		if (player2 != null) {
			String addedNotification = perm.getAddedNotification();
			player2.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + String.format(addedNotification, zoneName));
		}
	}
	
	public void delUser(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "syntax: /zone deluser [zone] [player]");
			return;
		}
		
		String zoneName = args[1];
		String userName = args[2];
		
		Zone zone = world.getZone(zoneName);
		if (zone == null) {
			player.sendMessage(ChatColor.RED + "Specified zone does not exist.");
			return;
		}
		
		if (zone.getUser(player.getName()) != Permission.Owner) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "You do not have permission to add users to this zone.");
			return;
		}
		
		Zone.Permission oldPerm = zone.getUser(userName);
		if (oldPerm == null) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "]" +
					userName + " doesn't have any permissions.");
			return;
		}
		
		// store permission change in db
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			int userId = dao.getUserId(userName);
			if (userId == -1) {
				player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Player " + userName + " was not found.");
				return;
			}
			
			dao.deleteUser(zone.getId(), userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
		
		zone.deleteUser(userName);
		String delConfirmation = oldPerm.getDeletedConfirmation();
		player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + String.format(delConfirmation, userName, zoneName));
		
		TregminePlayer player2 = tregmine.getPlayer(userName);
		if (player2 != null) {
			String delNotification = oldPerm.getDeletedNotification();
			player2.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + String.format(delNotification, zoneName));
		}
	}
	
	public void changeValue(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		// entermsg [zone] [message]
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "unknown command");
			return;
		}
		
		String zoneName = args[1];
		
		Zone zone = world.getZone(zoneName);
		if (zone == null) {
			player.sendMessage(ChatColor.RED + "Specified zone does not exist.");
			return;
		}
		
		if (zone.getUser(player.getName()) != Permission.Owner) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "You do not have permission to change settings for this zone.");
			return;
		}
		
		if ("entermsg".equals(args[0]) || "exitmsg".equals(args[0])) {
			StringBuilder buf = new StringBuilder();
			for (int i = 2; i < args.length; i++) {
				buf.append(args[i]);
				buf.append(" ");
			}
			
			String message = buf.toString().trim();
			if ("entermsg".equals(args[0])) {
				zone.setTextEnter(message);
				player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Welcome message changed to \"" + message + "\".");
			} else if ("exitmsg".equals(args[0])) {
				zone.setTextExit(message);
				player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Exit message changed to \"" + message + "\".");
			}
		}
		else if ("pvp".equals(args[0])) {
			boolean status = Boolean.parseBoolean(args[2]);
			zone.setPvp(status);
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "PVP changed to \"" + (status ? "allowed" : "disallowed") + "\".");
		}
		else if ("enter".equals(args[0])) {
			boolean status = Boolean.parseBoolean(args[2]);
			zone.setEnterDefault(status);
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Enter default changed to \"" + (status ? "everyone" : "whitelisted") + "\".");			
		}
		else if ("place".equals(args[0])) {
			boolean status = Boolean.parseBoolean(args[2]);
			zone.setPlaceDefault(status);
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Place default changed to \"" + (status ? "everyone" : "whitelisted") + "\".");			
		}
		else if ("destroy".equals(args[0])) {
			boolean status = Boolean.parseBoolean(args[2]);
			zone.setDestroyDefault(status);
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "Destroy default changed to \"" + (status ? "everyone" : "whitelisted") + "\".");			
		}
		
		Mysql mysql = null;
		try {
			mysql = new Mysql();
			mysql.connect();
			ZonesDAO dao = new ZonesDAO(mysql.connect);
			dao.updateZone(zone);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			mysql.close();
		}
	}
	
	public void zoneInfo(TregminePlayer player, String[] args)
	{
		ZoneWorld world = getWorld(player.getWorld());
		if (world == null) {
			return;
		}
		
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "unknown command");
			return;
		}
		
		String zoneName = args[1];
		
		Zone zone = world.getZone(zoneName);
		if (zone == null) {
			player.sendMessage(ChatColor.RED + "Specified zone does not exist.");
			return;
		}
		
		if (zone.getUser(player.getName()) != Permission.Owner && !player.isAdmin()) {
			player.sendMessage(ChatColor.RED + "[" + zone.getName() + "] " + "You do not have permission to change settings for this zone.");
			return;
		}

		player.sendMessage(ChatColor.RED + "Info about " + zone.getName());
		player.sendMessage(ChatColor.RED + "ID: " + zone.getId());
		player.sendMessage(ChatColor.RED + "World: " + zone.getWorld());
		for (Rectangle rect : zone.getRects()) {
			player.sendMessage(ChatColor.RED + "Rect: " + rect);
		}
		player.sendMessage(ChatColor.RED + "Enter: " + (zone.getEnterDefault() ? "Everyone (true)" : "Only allowed (false)"));
		player.sendMessage(ChatColor.RED + "Place: " + (zone.getPlaceDefault() ? "Everyone (true)" : "Only makers (false)"));
		player.sendMessage(ChatColor.RED + "Destroy: " + (zone.getDestroyDefault() ? "Everyone (true)" : "Only makers (false)"));
		player.sendMessage(ChatColor.RED + "PVP: " + zone.isPvp());
		player.sendMessage(ChatColor.RED + "Enter message: " + zone.getTextEnter());
		player.sendMessage(ChatColor.RED + "Exit message: " + zone.getTextExit());

		for (String user : zone.getUsers()) {
			Zone.Permission perm = zone.getUser(user);
			player.sendMessage(ChatColor.RED + user + " - " + perm);
		}
	}
}