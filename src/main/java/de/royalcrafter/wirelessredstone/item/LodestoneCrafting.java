package de.royalcrafter.wirelessredstone.item;

import de.royalcrafter.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LodestoneCrafting{

     public static void registerRecipe(WirelessRedstone main){

          ItemStack lodestone = new ItemStack(Material.LODESTONE);

          NamespacedKey LODESTONE_RECIPE_KEY = new NamespacedKey(main, "key.lodestone");

          ShapedRecipe lodestoneRecipe = new ShapedRecipe(LODESTONE_RECIPE_KEY, lodestone);
          lodestoneRecipe.shape("SCS", "SES", "SSS");
          lodestoneRecipe.setIngredient('S', Material.STONE);
          lodestoneRecipe.setIngredient('C', Material.COMPARATOR);
          lodestoneRecipe.setIngredient('E', Material.ENDER_PEARL);

          Bukkit.addRecipe(lodestoneRecipe);

     }
}
