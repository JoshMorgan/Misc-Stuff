package me.ice374.laston;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	static String mainDirectory = "plugins" + File.separator + "laston";
	static String dataDirectory = "plugins" + File.separator + "laston"+ File.separator + "data";
	static File config = new File(mainDirectory + File.separator + "config");
	static File language = new File(mainDirectory + File.separator + "lang");
	static File version = new File(mainDirectory + File.separator + "VERSION");
	static Properties prop = new Properties();

	public String norecordfor;
	public String norecord;
	public String laston;

	public Locale locale = Locale.getDefault();
	public DateFormat dateformat = DateFormat.getDateInstance();

	private final MainListener playerListener = new MainListener(this);

	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		new File(mainDirectory).mkdir();
		new File(dataDirectory).mkdir();
		if (!config.exists()) {
			try {
				config.createNewFile();
				FileOutputStream out = new FileOutputStream(config);
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		if (!language.exists()) {
			Properties langpropout = new Properties();
			try {
				language.createNewFile();
				FileOutputStream out = new FileOutputStream(language);
				langpropout.put("norecordfor",
						"&7No record for &a<player> &7found.");
				langpropout.put("norecord", "&cNo record.");
				langpropout.put("laston", "&7Last seen on &a<date>");
				langpropout.store(out, "Loaclization.");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		FileInputStream in;
		try {
			in = new FileInputStream(config);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		FileInputStream langin;
		Properties langpropin = new Properties();
		try {
			langin = new FileInputStream(language);
			langpropin.load(langin);
			langin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if (!version.exists()) {
			try {
				version.createNewFile();
				BufferedWriter vout = new BufferedWriter(
						new FileWriter(version));
				vout.write(this.getDescription().getVersion());
				vout.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (SecurityException ex) {
				ex.printStackTrace();
			}

			String[] dataEntries = new File(dataDirectory).list();

			for (int i = 0; i < dataEntries.length; i++) {
				if (containsUpperCase(dataEntries[i])) {
					File invalid = new File(dataDirectory + File.separator
							+ dataEntries[i]);
					if (!invalid.renameTo(new File(dataDirectory
							+ File.separator + dataEntries[i].toLowerCase()))) {
						log.info("Could not rename " + dataEntries[i] + " to "
								+ dataEntries[i].toLowerCase());
					}
				}
			}
		} else {
			byte[] buffer = new byte[(int) version.length()];
			BufferedInputStream f = null;
			try {
				f = new BufferedInputStream(new FileInputStream(version));
				f.read(buffer);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (f != null)
					try {
						f.close();
					} catch (IOException ignored) {
					}
			}
		}
		if (!langpropin.containsKey("norecordfor")
				|| !langpropin.containsKey("norecord")
				|| !langpropin.containsKey("laston")) {
			log.info("Lang file not complete!  Reverting file to english.");
			Properties langpropout = new Properties();
			try {
				language.createNewFile();
				FileOutputStream out = new FileOutputStream(language);
				langpropout.put("norecordfor", "No record for <player> found.");
				langpropout.put("norecord", "No record.");
				langpropout.put("laston", "Last seen on <date>");
				langpropout.store(out, "Loaclization.");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		this.norecordfor = this.colorizeText(langpropin
				.getProperty("norecordfor"));
		this.norecord = this.colorizeText(langpropin.getProperty("norecord"));
		this.laston = this.colorizeText(langpropin.getProperty("laston"));

		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(playerListener, this);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("laston")) {
			if (args.length != 1)
				return false;
			try {
				if (getServer().getPlayer(args[0]).isOnline()) {
					args[0] = getServer().getPlayer(args[0]).getName();
				}
			} catch (NullPointerException ex) {
			}
			args[0] = args[0].toLowerCase();

			File targetfile = new File(dataDirectory + File.separator + args[0]);
			if (!targetfile.exists()) {
				sender.sendMessage(norecordfor
						.replaceFirst("<player>", args[0]));
				return true;
			}
			sender.sendMessage(args[0]);

			sender.sendMessage(args[0] + ChatColor.GRAY + " was last seen on "
					+ ChatColor.RESET
					+ laston.replaceFirst("<date>", getlaston(args[0])));
			return true;
		}
		return false;
	}

	public void updateVersion() {
		try {
			version.createNewFile();
			BufferedWriter vout = new BufferedWriter(new FileWriter(version));
			vout.write(this.getDescription().getVersion());
			vout.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SecurityException ex) {
			ex.printStackTrace();
		}
	}

	public void putlaston(Player player, long time) {
		storeTime(player, time, "laston");
	}

	public void storeTime(Player player, long time, String property) {
		File savefile = new File(dataDirectory + File.separator
				+ player.getName().toLowerCase());

		if (!savefile.exists()) {
			try {
				savefile.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		FileInputStream timedatain;
		Properties timepropin = new Properties();

		try {
			timedatain = new FileInputStream(savefile);
			timepropin.load(timedatain);
			timedatain.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Properties timepropout = new Properties();

		try {
			FileOutputStream outtime = new FileOutputStream(savefile);
			if (timepropin.containsKey("laston"))
				timepropout.put("laston", timepropin.get("laston"));
			timepropout.put(property, Long.toString(time));
			timepropout.store(outtime, "");
			outtime.flush();
			outtime.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getlaston(String name) {
		FileInputStream getseenin;
		File getseen = new File(dataDirectory + File.separator + name);
		Properties getseenprop = new Properties();
		try {
			getseenin = new FileInputStream(getseen);
			getseenprop.load(getseenin);
			getseenin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (!getseenprop.containsKey("laston"))
			return norecord;
		Date date = new Date(Long.parseLong(getseenprop.getProperty("laston")));
		return DateFormat.getDateTimeInstance().format(date);
	}

	public static long getlastonLong(String name) {
		FileInputStream getseenin;
		File getseen = new File(dataDirectory + File.separator + name);
		Properties getseenprop = new Properties();
		try {
			getseenin = new FileInputStream(getseen);
			getseenprop.load(getseenin);
			getseenin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}
		if (!getseenprop.containsKey("laston"))
			return -1;
		return Long.parseLong(getseenprop.getProperty("laston"));
	}

	public boolean isPlayer(CommandSender sender) {
		return sender != null && sender instanceof Player;
	}

	private boolean containsUpperCase(String string) {
		for (char c : string.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		return false;
	}

	public String colorizeText(String string) {
		string = string.replaceAll("&0", ChatColor.BLACK + ""); string = string.replaceAll("&1", ChatColor.DARK_BLUE + ""); string = string.replaceAll("&2", ChatColor.DARK_GREEN + ""); string = string.replaceAll("&3", ChatColor.DARK_AQUA + "");string = string.replaceAll("&4", ChatColor.DARK_RED + "");string = string.replaceAll("&5", ChatColor.DARK_PURPLE + "");string = string.replaceAll("&6", ChatColor.GOLD + "");string = string.replaceAll("&7", ChatColor.GRAY + "");string = string.replaceAll("&8", ChatColor.DARK_GRAY + "");string = string.replaceAll("&9", ChatColor.BLUE + "");string = string.replaceAll("&a", ChatColor.GREEN + "");string = string.replaceAll("&b", ChatColor.AQUA + "");string = string.replaceAll("&c", ChatColor.RED + "");string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");string = string.replaceAll("&e", ChatColor.YELLOW + "");string = string.replaceAll("&f", ChatColor.WHITE + "");
		return string;
	}
}