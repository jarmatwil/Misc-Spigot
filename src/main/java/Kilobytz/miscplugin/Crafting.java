package Kilobytz.miscplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;


public class Crafting {

    List<ItemStack> customItems = new ArrayList<>();

    public void addRecipe(Plugin plugin) {
        ItemStack dye = new ItemStack(Material.INK_SACK,1, (short) 13);
        ItemMeta dyeMeta = dye.getItemMeta();
        List<String> iLore = new ArrayList<>();
        iLore.add("Right click to heal mangled wounds");
        dyeMeta.setLore(iLore);
        dyeMeta.setDisplayName("Healing Salve");
        dye.setItemMeta(dyeMeta);
        ShapelessRecipe healingSalve = new ShapelessRecipe(new NamespacedKey(plugin,"heal_salve"),dye);

        healingSalve.addIngredient(1,new MaterialData(Material.INK_SACK, (byte) 1));
        healingSalve.addIngredient(1,new MaterialData(Material.INK_SACK, (byte) 2));
        Bukkit.addRecipe(healingSalve);
        customItems.add(dye);
    }
}
