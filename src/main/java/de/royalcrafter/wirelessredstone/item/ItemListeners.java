package de.royalcrafter.wirelessredstone.item;

import de.royalcrafter.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class ItemListeners implements Listener{

     private static final ItemListeners INSTANCE = new ItemListeners();
     public static ItemListeners getInstance(){
          return INSTANCE;
     }

     HashMap<Player, Block> connectionHashMap = new HashMap<>();
     static List<List<Block>> connectedBlocks = new ArrayList<>();


     @EventHandler
     public void onInteract(PlayerInteractEvent event){
          if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                  event.getClickedBlock().getType().equals(Material.LODESTONE) &&
                  event.getItem().getType().equals(Material.REDSTONE) &&
                    !event.getPlayer().isSneaking()){

               event.setCancelled(true);

               Player player = event.getPlayer();

               if(connectionHashMap.containsKey(player)){

                    Block connectTo = connectionHashMap.get(player);

                    if(!connectTo.equals(event.getClickedBlock())){

                         player.sendMessage("\n§4[Wireless Redstone] §aEmpfänger ausgewählt!\n ");

                         List<Block> blocks = new ArrayList<>();

                         blocks.add(connectTo);
                         blocks.add(event.getClickedBlock());

                         getConnectedBlocks().add(blocks);

                         connectionHashMap.remove(player);

                         player.sendMessage("\n§4[Wireless Redstone] §aVerbindung aufgebaut!\n ");
                    } else{

                         player.sendMessage("\n§4[Wireless Redstone] §aSender und Empfänger dürfen nicht der gleiche Block sein!\n ");

                    }

               } else{
                    connectionHashMap.put(player, event.getClickedBlock());

                    player.sendMessage("\n§4[Wireless Redstone] §aSender ausgewählt!\n ");
               }

          }
     }


     public static void tick(WirelessRedstone main){
          Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {

               for(int i = 0; i < getConnectedBlocks().size(); i++){
                    List<Block> blocks = getConnectedBlocks().get(i);

                    if(blocks.size() == 2){

                         Block sender = blocks.get(0);
                         Block receiver = blocks.get(1);

                         Location location = receiver.getLocation();

                         int posX = location.getBlockX();
                         int posY = location.getBlockY();
                         int posZ = location.getBlockZ();

                         List<Block> attachedBlocks = new ArrayList<>();

                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX + 1, posY, posZ)));
                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX - 1, posY, posZ)));
                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX, posY + 1, posZ)));
                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX, posY - 1, posZ)));
                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX, posY, posZ + 1)));
                         attachedBlocks.add(receiver.getWorld().getBlockAt(new Location(receiver.getWorld(), posX, posY, posZ - 1)));

                         if(sender.isBlockPowered()){

                              int power = sender.getBlockPower();

                              for(Block block : attachedBlocks){

                                   BlockData data = block.getBlockData();

                                   if(data instanceof RedstoneWire){
                                        RedstoneWire redstoneWire = (RedstoneWire) data;
                                        redstoneWire.setPower(power);
                                        block.setBlockData(redstoneWire);
                                   }
                                   if(data instanceof Repeater){
                                        Repeater repeater = (Repeater) data;
                                        repeater.setPowered(true);
                                        block.setBlockData(repeater);
                                   }

                              }

                         } else {
                              for(Block block : attachedBlocks){

                                   BlockData data = block.getBlockData();

                                   if(data instanceof RedstoneWire){
                                        RedstoneWire redstoneWire = (RedstoneWire) data;
                                        redstoneWire.setPower(0);
                                        block.setBlockData(redstoneWire);
                                   }
                                   if(data instanceof Repeater){
                                        Repeater repeater = (Repeater) data;
                                        repeater.setPowered(false);
                                        block.setBlockData(repeater);
                                   }

                              }
                         }

                    }

               }

          }, 0, 1);
     }


     private static List<List<Block>> getConnectedBlocks(){
          return connectedBlocks;
     }


     public void saveConnectedBlocks(WirelessRedstone main){
          /*for(Map.Entry<String, List<Block>> entry : connectedBlocks){
               main.getConfig().set("data." + entry.getKey(), entry.getValue());
          }
          main.saveConfig();

           */
     }

     public void getConnectedBlocks(WirelessRedstone main){
          /*if(main.getConfig().getConfigurationSection("data") != null){
               for(String key: main.getConfig().getConfigurationSection("data").getKeys(false)){
                    List<Block> contents = (List<Block>) main.getConfig().get("data." + key);
                    connectedBlocks.put(key, contents);
               }
          }

           */
     }

}
