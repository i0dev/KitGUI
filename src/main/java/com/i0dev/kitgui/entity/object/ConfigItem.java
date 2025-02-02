package com.i0dev.kitgui.entity.object;

import com.i0dev.kitgui.util.ItemBuilder;
import com.i0dev.kitgui.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.List;

@Data
public class ConfigItem {

    String name;
    Material material;
    List<String> lore;
    int amount;
    boolean glow;

    Material trimMaterial = null;
    String trimPattern = null;

    public ItemStack getItemStack(Pair<String, String>... loreReplacements) {
        ItemStack item = getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        List<String> lore = meta.getLore();
        if (lore == null) return item;

        for (Pair<String, String> loreReplacement : loreReplacements) {
            lore.replaceAll(s -> s.replace(loreReplacement.getKey(), loreReplacement.getValue()));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }


    public ItemStack getItemStack() {
        ItemStack stack = new ItemBuilder(material)
                .name(name)
                .setNewLore(lore)
                .amount(amount)
                .addGlow(glow);

        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        stack.setItemMeta(meta);
        meta = stack.getItemMeta();
        if (meta instanceof ArmorMeta armorMeta && trimMaterial != null && trimPattern != null) {
            armorMeta.setTrim(new ArmorTrim(
                    Registry.TRIM_MATERIAL.get(NamespacedKey.minecraft(trimMaterial.toString().toLowerCase())),
                    Registry.TRIM_PATTERN.get(NamespacedKey.minecraft(trimPattern.toLowerCase())
                    )));
            stack.setItemMeta(armorMeta);
        }
        return stack;
    }

    public ConfigItem(String name, Material material, List<String> lore, int amount, boolean glow) {
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.amount = amount;
        this.glow = glow;
    }
}
