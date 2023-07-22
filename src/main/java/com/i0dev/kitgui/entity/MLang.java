package com.i0dev.kitgui.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;

@EditorName("config")
public class MLang extends Entity<MLang> {

    protected static transient MLang i;

    public static MLang get() {
        return i;
    }

    public String prefix = "&8[&aKitGUI&8]&7";

    public String previewingKit = "%prefix% &aPreviewing kit &7%kit%";

    @Override
    public MLang load(MLang that) {
        super.load(that);
        return this;
    }
}
