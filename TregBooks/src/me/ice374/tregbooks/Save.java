package me.ice374.tregbooks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.meta.BookMeta;

public class Save {
	
	public static void save (BookMeta book, File dir,
			CommandSender sender, String name) {
		try {
			File folder = new File(dir, name);
			if (folder.exists()) {
				sender.sendMessage("A save with that name already exists!");
				return;
			}
			folder.mkdirs();
			
			YamlConfiguration yc = new YamlConfiguration();
			yc.set("book_name", book.getTitle());
			yc.set("written_by", book.getAuthor());
			yc.save(new File(folder, "book_settings.yml"));
			
			List<String> pages = book.getPages();
			String[] bookContents = new String[pages.size()];
			for (int i=0; i < bookContents.length; i++) {
				bookContents[i] = pages.get(i).toString();
			}
			for (int i=0; i < bookContents.length; i++) {
				File page = new File(folder, (i + 1 + " - page") + ".txt");
				page.createNewFile();
				FileWriter fw = new FileWriter(page);
				fw.write(bookContents[i]);
				fw.close();
			}
			sender.sendMessage("Your book has been successfully saved!");
			
		} catch (IOException e) {
		}
	}
}