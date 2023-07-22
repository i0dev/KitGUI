package com.i0dev.kitgui;

import com.earth2me.essentials.Essentials;
import com.i0dev.kitgui.entity.*;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class KitGUIPlugin extends MassivePlugin {

    private static KitGUIPlugin i;

    public KitGUIPlugin() {
        KitGUIPlugin.i = this;
    }

    public static KitGUIPlugin get() {
        return i;
    }

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }


    @Override
    public List<Class<?>> getClassesActiveColls() {
        return new MassiveList<>(
                MConfColl.class,
                MLangColl.class,
                CategoryColl.class
        );
    }

    public Essentials getEssentials() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        return p instanceof Essentials ? (Essentials) p : null;
    }

    @Override
    public void onEnablePost() {
        super.onEnablePost();

        if (MConf.get().makeExampleCategory) Category.example();
    }
}