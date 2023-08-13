package com.i0dev.kitgui.action;

import com.i0dev.kitgui.engine.EngineKit;
import com.i0dev.kitgui.entity.Category;
import com.i0dev.kitgui.entity.MConf;
import com.i0dev.kitgui.entity.MLang;
import com.i0dev.kitgui.entity.object.ConfigItem;
import com.i0dev.kitgui.entity.object.KitItem;
import com.i0dev.kitgui.util.Pair;
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
public class ActionSelectKit implements ChestAction {

    Category backCategory;
    KitItem kitItem;


    @Override
    public boolean onClick(InventoryClickEvent e) {

        // claim kit
        if (e.getClick().isLeftClick()) {
            return MixinCommand.get().dispatchCommand(e.getWhoClicked(), MConf.get().getKitCommand().replace("%name%", kitItem.getKitName()));
        }

        if (e.getClick().isRightClick())
            new ActionPreviewKit(backCategory, kitItem).onClick(e);

        return true;
    }

}
