package com.i0dev.kitgui.entity;

import com.i0dev.kitgui.entity.object.ConfigItem;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@EditorName("config")
@Setter
public class MConf extends Entity<MConf> {

    protected static transient MConf i;

    public static MConf get() {
        return i;
    }

    public List<String> aliases = MUtil.list("kitgui");
    public boolean makeExampleCategory = true;

    public List<String> openGuiTrigger = MUtil.list("/kit", "/kitgui", "kits", "ekits", "ekit");

    public String kitCommand = "kit %name%";

    public boolean enabled = true;
    public int mainMenuSize = 9;
    public String mainMenuTitle = "&aKitGUI &7- &aMain Menu";
    public String previewTitle = "&aKitGUI &7- &aPreviewing &7%kit%";

    public ConfigItem noAccessKitItem = new ConfigItem(
            "&c&lLOCKED",
            Material.BARRIER,
            MUtil.list("&7You do not have access to this kit."),
            1,
            false
    );

    public ConfigItem kitOnCoolDownItem = new ConfigItem(
            "&e&lON COOLDOWN",
            Material.CLOCK,
            MUtil.list("&7You can use this kit on %cooldown%"),
            1,
            false
    );

    @Override
    public MConf load(MConf that) {
        super.load(that);
        return this;
    }

}
