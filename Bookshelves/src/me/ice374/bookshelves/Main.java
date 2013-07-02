package me.ice374.bookshelves;
 
import java.util.HashMap;
import java.util.Set;
 
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Main extends JavaPlugin implements Listener
{
    public HashMap<String, Location> openBlock = new HashMap<String, Location>();
    public String invName = "BlockInv";
    public HashMap<String, Inventory> BlockInventory = new HashMap<String, Inventory>();
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        //System.out.print("Enabled!"); - This Line Of Code IS Not Needed As It
        //Is Already Done By Bukkit
   
        //Loads the inventorys when the plugin starts
        loadBlockInventorys();
    }
    @Override
    public void onDisable()
    {
        //saves the inventorys when the plugin is stopped
        saveBlockInventorys();
    }
 
 
    //A Function To Save The Inventorys
    private void saveBlockInventorys()
    {
        //get all locations from hashmap and put them into an array
        Set<String> keys = BlockInventory.keySet();
        Object[] keyarray = keys.toArray();
   
        //check if there is the config section if there isnt create it
        if(!checkIfConfigSectionExists("Blocks"))
        {
            getConfig().createSection("Blocks");
        }
   
        //loop through all inventorys and save them to config
        for(int num = 0; num < keyarray.length; num++)
        {
            //check if the block is in the chest if not create it, if so enter the items
            if(!checkIfConfigSectionExists("Blocks."+keyarray[num]))
            {
                //create sections
                getConfig().createSection("Blocks"+keyarray[num]);
                getConfig().createSection("Blocks"+keyarray[num] + ".Owner");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot1");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot2");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot3");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot4");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot5");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot6");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot7");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot8");
                getConfig().createSection("Blocks"+keyarray[num] + ".Slot9");
           
                //save config
                saveConfig();
            }
            //get the inventory to save
            Inventory inv = BlockInventory.get(keyarray[num]);
       
            //save each individual slot
            getConfig().set("Blocks."+keyarray[num] + ".Owner", inv.getHolder());
            for(int slot = 1; slot < 10; slot++)
            {
                getConfig().set("Blocks."+keyarray[num] + ".Slot"+slot, inv.getItem(slot-1));
            }
       
            //save config
            saveConfig();
        }
   
    }
 
    //A Function To Load The Inventorys
    private void loadBlockInventorys()
    {
        //checks if configsecion is there if not it ignores (so there are no errors)
        if(checkIfConfigSectionExists("Blocks"))
        {
            //gets every block to be added into the hashmap
            Set<String> sections = getConfig().getConfigurationSection("Blocks").getKeys(false);
            //put to array to use in for loop
            Object[] sectionsarray = sections.toArray();
       
            //loop through every section
            for(int set = 0; set < sectionsarray.length; set++)
            {
                //create an empty item stack
                ItemStack[] items = new ItemStack[9];
           
                //loop through the diffrent blocks and load the inventory
                for(int list = 0; list < 9; list++)
                {
                    //set a integer to +1
                    int add = list + 1;
               
                    //add each itemstack into the blank itemstack array in the relivant slot
                    items[list] = getConfig().getItemStack("Blocks." + sectionsarray[set] + ".Slot" + add);
                }
           
                //create the inventory (it looks complex but it gets the owner from the config and puts them as owner)
                Inventory inv = Bukkit.createInventory((InventoryHolder) Bukkit.getOfflinePlayer(getConfig().getString("Blocks." + sectionsarray[set] + ".Owner")), 9);
           
                //add the itemstack array to the inventory
                inv.setContents(items);
           
                //put itemstack array into the block inventory hashmap
                BlockInventory.put(sectionsarray[set].toString(), inv);
            }
        }
        else
        {
            //error message if the config didnt load
            getLogger().warning("No Blocks Configuration Section");
        }
    }
 
    private Boolean checkIfConfigSectionExists(String ConfigSection)
    {
        if(getConfig().contains(ConfigSection))
        {
            return true;
        }
        return false;
    }
 
    //A Function To Turn The Location Into A String (To Keep It All the Same)
    private String createLocationString(Location loc)
    {
        //changes the location to a string
        String Result = loc.toString();
   
        //returns the string
        return Result;
    }
 
    //block place event to register the chest This Will Register The Block As An
    //Inventory And Add It To The Hash Map
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        //Get The Placed Block
        Block placedBlock = e.getBlockPlaced();
        //check To see If The Block Is the Gold Block
        if(placedBlock.getType().equals(Material.GOLD_BLOCK))
        {
            //Get The Gold Blocks Location
            Location blockLocation = placedBlock.getLocation();
       
            //check if hashmap does not contain this block, it it does return error sting.
            if(!BlockInventory.containsKey(createLocationString(blockLocation)))
            {
                //create the inventory
                Inventory inv = Bukkit.createInventory(e.getPlayer(), 9, invName);
                //add it to the hashmap
                BlockInventory.put(createLocationString(blockLocation), inv);
                //send message to say its complete
                e.getPlayer().sendMessage("Inventory Created");
            }
            else
            {
                e.getPlayer().sendMessage("Oops Something Went Wrong");
            }
        }
    }
    //block break event to deregister the chest this will remove it from the hashmap
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        //Get The Placed Block
                Block placedBlock = e.getBlock();
                //check To see If The Block Is the Gold Block
                if(placedBlock.getType().equals(Material.GOLD_BLOCK))
                {
                    //Get The Gold Blocks Location
                    Location blockLocation = placedBlock.getLocation();
               
                    //check if hashmap contains this block, it it does not return error sting.
                    if(!BlockInventory.containsKey(createLocationString(blockLocation)))
                    {
                        //delete it from the hashmap
                        BlockInventory.remove(createLocationString(blockLocation));
                        //send message to say its complete
                        e.getPlayer().sendMessage("InventoryDestroyed");
                    }
                    else
                    {
                        e.getPlayer().sendMessage("Oops Something Went Wrong");
                    }
                }
    }
 
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(e.getClickedBlock().getType().equals(Material.GOLD_BLOCK))
            {
                //get clicked block location
                Location blockLoc = e.getClickedBlock().getLocation();
           
                //get the relevant inventory
                Inventory inv = BlockInventory.get(createLocationString(blockLoc));
           
                //open inventory
                e.getPlayer().openInventory(inv);
           
                //put open block in hashmap for future use
                openBlock.put(e.getPlayer().getName(), blockLoc);
            }
        }
    }
 
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e)
    {
        if(e.getPlayer() instanceof Player)
        {
            if(e.getInventory().getName().equals(invName))
            {
                //get block inventory
                Inventory inv = e.getInventory();
           
                //put the inventory back into the hashmap
                BlockInventory.put(createLocationString(openBlock.get(e.getPlayer().getName())), inv);
           
                //clear the player from the open block inventory
                openBlock.put(e.getPlayer().getName(), null);
            }
        }
    }
}