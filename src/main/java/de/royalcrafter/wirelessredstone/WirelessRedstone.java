package de.royalcrafter.wirelessredstone;

import de.royalcrafter.wirelessredstone.item.ItemListeners;
import de.royalcrafter.wirelessredstone.item.LodestoneCrafting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WirelessRedstone extends JavaPlugin{

     @Override
     public void onEnable(){
          registerEvents();
          LodestoneCrafting.registerRecipe(this);
          ItemListeners.getInstance().getConnectedBlocks(this);

          Bukkit.getConsoleSender().sendMessage("§4[Wireless Redstone] §aPlugin aktiviert!");

          ItemListeners.tick(this);
     }

     private void registerEvents(){
          PluginManager pm = Bukkit.getPluginManager();
          pm.registerEvents(new ItemListeners(), this);
     }

     @Override
     public void onDisable(){
          ItemListeners.getInstance().saveConnectedBlocks(this);

          Bukkit.getConsoleSender().sendMessage("§4[Wireless Redstone] §cPlugin deaktiviert!");
     }


}
