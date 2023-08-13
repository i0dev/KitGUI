package com.i0dev.kitgui.engine;

import com.earth2me.essentials.Kit;
import com.earth2me.essentials.User;
import com.i0dev.kitgui.KitGUIPlugin;
import com.i0dev.kitgui.action.ActionPreviewKit;
import com.i0dev.kitgui.action.ActionSelectKit;
import com.i0dev.kitgui.entity.Category;
import com.i0dev.kitgui.entity.CategoryColl;
import com.i0dev.kitgui.entity.MConf;
import com.i0dev.kitgui.entity.object.KitItem;
import com.i0dev.kitgui.util.Pair;
import com.i0dev.kitgui.util.Utils;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.TimeZoneUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;

public class EngineKit extends Engine {

    private static final EngineKit i = new EngineKit();

    public static EngineKit get() {
        return i;
    }


    /**
     * This event is used to open the main menu when the player types the command.
     */
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (!MConf.get().enabled) return;

        for (String s : MConf.get().getOpenGuiTrigger()) {
            if (!e.getMessage().equalsIgnoreCase(s)) continue;
            e.setCancelled(true);
            e.getPlayer().openInventory(getMainInventory());
            return;
        }
    }


    public Inventory getMainInventory() {
        Inventory mainInventory = Bukkit.createInventory(null, MConf.get().getMainMenuSize(), Utils.color(MConf.get().getMainMenuTitle()));
        ChestGui menu = ChestGui.getCreative(mainInventory);
        menu.setAutoclosing(true);
        menu.setAutoremoving(true);
        menu.setSoundOpen(null);
        menu.setSoundClose(null);

        for (int i = 0; i < MConf.get().getMainMenuSize(); i++) {
            menu.getInventory().setItem(i, MConf.get().borderItem.getItemStack());
        }

        for (Category category : CategoryColl.get().getAll()) {
            menu.getInventory().setItem(category.getSlot(), category.getItemStack(1));

            menu.setAction(category.getSlot(), e -> {
                e.getWhoClicked().openInventory(getCategoryInventory(category.getId(), (Player) e.getWhoClicked()));
                return true;
            });
        }

        return menu.getInventory();
    }


    @SneakyThrows
    public Inventory getCategoryInventory(String categoryID, Player observer) {
        Category category = Category.get(categoryID);
        Inventory inventory = Bukkit.createInventory(null, category.getGuiSize(), Utils.color(category.getGuiTitle()));
        ChestGui menu = ChestGui.getCreative(inventory);
        menu.setAutoclosing(true);
        menu.setAutoremoving(true);
        menu.setSoundOpen(null);
        menu.setSoundClose(null);

        for (int i = 0; i < category.getGuiSize(); i++) {
            menu.getInventory().setItem(i, MConf.get().borderItem.getItemStack());
        }

        User user = KitGUIPlugin.get().getEssentials().getUser(observer);
        for (KitItem kitItem : category.getKits()) {
            Kit kit = new Kit(kitItem.getKitName().toLowerCase(), KitGUIPlugin.get().getEssentials());
            menu.setAction(kitItem.getSlot(), new ActionPreviewKit(category, kitItem));
            if (!observer.hasPermission("essentials.kits." + kitItem.getKitName())) {
                menu.getInventory().setItem(kitItem.getSlot(), MConf.get().getNoAccessKitItem().getItemStack());
                menu.setAction(kitItem.getSlot(), new ActionSelectKit(category, kitItem));
                continue;
            }

            long nextUse = kit.getNextUse(user);
            if (nextUse > 0 && nextUse > System.currentTimeMillis()) {
                menu.getInventory().setItem(kitItem.getSlot(), MConf.get().getKitOnCoolDownItem().getItemStack(new Pair<>("%cooldown%", TimeZoneUtil.getAdjustedTime(nextUse, "EST"))));
                continue;
            }

            menu.getInventory().setItem(kitItem.getSlot(), kitItem.getItemStack());
            menu.setAction(kitItem.getSlot(), new ActionSelectKit(category, kitItem));
        }

        if (category.getBackButtonItemSlot() != -1) {
            menu.getInventory().setItem(category.getBackButtonItemSlot(), MConf.get().categoryBackButton.getItemStack());
            menu.setAction(category.getBackButtonItemSlot(), e -> {
                e.getWhoClicked().openInventory(getMainInventory());
                return true;
            });
        }
        return menu.getInventory();
    }


}
