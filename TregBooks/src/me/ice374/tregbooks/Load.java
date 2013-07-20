package me.ice374.tregbooks;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Load {
	
	public static void load (Player player, File dir, String name, boolean firstJoin) {
		
		File folder = new File(dir, name);
		if (!folder.exists()) {
			player.sendMessage("There is no saved book by that name");
			return;
		}
		
		YamlConfiguration yc = YamlConfiguration.loadConfiguration(new File(folder, "book_settings.yml"));

        ItemStack loadedbook = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta book = (BookMeta) loadedbook.getItemMeta();
		book.setTitle(yc.getString("book_name", "ADD-NAME-HERE"));
        book.setAuthor(yc.getString("written_by", "ADD-AUTHOR"));

		File page;
		for (int i=0; (page = new File(folder, i + 1 + " - page" + ".txt")).exists(); i++) try {
			int len;
			char[] chr = new char[1024];
			final StringBuilder builder = new StringBuilder();
			final FileReader reader = new FileReader(page);
			try {
				while ((len = reader.read(chr)) > 0) {
					builder.append(chr, 0, len);
				}
			} finally {
				reader.close();
			}
            book.addPage(builder.toString());
            
		} catch (IOException e) {
			return;
		}
		loadedbook.setItemMeta(book);
        player.getInventory().addItem(loadedbook);
	}
}
