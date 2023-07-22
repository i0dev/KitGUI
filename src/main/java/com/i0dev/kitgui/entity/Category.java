package com.i0dev.kitgui.entity;

import com.i0dev.kitgui.entity.object.ConfigItem;
import com.i0dev.kitgui.entity.object.KitItem;
import com.i0dev.kitgui.util.ItemBuilder;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class Category extends Entity<Category> {

    public static Category get(Object oid) {
        return CategoryColl.get().get(oid);
    }

    int slot;
    String guiTitle;
    int guiSize;
    String displayName;
    Material material;
    List<String> lore;
    boolean glow;
    List<KitItem> kits;

    public ItemStack getItemStack(int amount) {
        return new ItemBuilder(material)
                .name(displayName)
                .lore(lore)
                .amount(amount)
                .addGlow(glow);
    }

    @Override
    public Category load(@NotNull Category that) {
        super.load(that);
        this.slot = that.slot;
        this.guiTitle = that.guiTitle;
        this.guiSize = that.guiSize;
        this.displayName = that.displayName;
        this.material = that.material;
        this.lore = that.lore;
        this.glow = that.glow;
        this.kits = that.kits;
        return this;
    }

    public static void example() {
        if (CategoryColl.get().containsId("example")) return;
        Category category = CategoryColl.get().create("example");
        category.setSlot(0);
        category.setGuiTitle("&aExample Category");
        category.setGuiSize(9);
        category.setDisplayName("&aExample Category");
        category.setMaterial(Material.PAPER);
        category.setLore(MUtil.list("&7This is an example category", "&7You can use it to get a reward"));
        category.setGlow(true);
        category.setKits(MUtil.list(
                new KitItem(
                        0,
                        "dtools",
                        "&aTools",
                        Material.DIAMOND_AXE,
                        MUtil.list("&7This is rank 1 kit"),
                        true,
                        MUtil.list(
                                new ConfigItem(
                                        "Diamond Axe",
                                        Material.DIAMOND_AXE,
                                        MUtil.list("&7This is a diamond axe"),
                                        1,
                                        true
                                ),
                                new ConfigItem(
                                        "Diamond Pickaxe",
                                        Material.DIAMOND_PICKAXE,
                                        MUtil.list("&7This is a diamond pickaxe"),
                                        1,
                                        true
                                )
                        )
                )
        ));
    }

}
