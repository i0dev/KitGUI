package com.i0dev.kitgui.action;

import com.i0dev.kitgui.engine.EngineKit;
import com.i0dev.kitgui.entity.Category;
import com.i0dev.kitgui.entity.MConf;
import com.i0dev.kitgui.entity.object.ConfigItem;
import com.i0dev.kitgui.entity.object.KitItem;
import com.i0dev.kitgui.util.Utils;
import com.massivecraft.massivecore.chestgui.ChestAction;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.mixin.MixinCommand;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ActionPreviewKit implements ChestAction {

    Category backCategory;
    KitItem kitItem;


    @Override
    public boolean onClick(InventoryClickEvent e) {
        // preview kit
        if (e.getClick().isRightClick()) {
            e.getWhoClicked().openInventory(getPreviewInventory(kitItem, (Player) e.getWhoClicked()));
        }

        return true;
    }

    @SneakyThrows
    public Inventory getPreviewInventory(KitItem kitI, Player player) {
        List<ItemStack> items = getItemsInKit(player);
        Inventory inventory = Bukkit.createInventory(null, kitI.getPreviewGuiSize(), Utils.color(MConf.get().getPreviewTitle().replace("%kit%", kitI.getKitName())));
        ChestGui menu = ChestGui.getCreative(inventory);
        menu.setAutoclosing(true);
        menu.setAutoremoving(true);
        menu.setSoundOpen(null);
        menu.setSoundClose(null);

        for (int i = 0; i < items.size(); i++) {
            menu.getInventory().setItem(i, items.get(i));
        }


        menu.getInventory().setItem(kitItem.getPreviewBackButtonItemSlot(), MConf.get().previewBackButton.getItemStack());
        menu.setAction(kitItem.getPreviewBackButtonItemSlot(), e -> {
            e.getWhoClicked().openInventory(EngineKit.get().getCategoryInventory(backCategory.getId(), (Player) e.getWhoClicked()));
            return true;
        });

        return menu.getInventory();
    }

    @SneakyThrows
    public List<ItemStack> getItemsInKit(Player observer) {
        List<ItemStack> items = new ArrayList<>();

        for (ConfigItem item : kitItem.getItems()) {
            ItemStack itemStack = item.getItemStack();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) continue;
            List<String> newLore = new ArrayList<>();
            for (String s : itemMeta.getLore()) {
                newLore.add(s.replace("%player%", observer.getName()));
            }
            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);

            items.add(itemStack);
        }

        return items;
    }

}
