package com.i0dev.kitgui.cmd;

import com.i0dev.kitgui.KitGUIPlugin;
import com.i0dev.kitgui.Perm;
import com.i0dev.kitgui.engine.EngineKit;
import com.i0dev.kitgui.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import java.util.List;

public class CmdKitGUI extends KitGUICommand {

    private static CmdKitGUI i = new CmdKitGUI();

    public static CmdKitGUI get() {
        return i;
    }

    public MassiveCommandVersion cmdFactionsVersion = new MassiveCommandVersion(KitGUIPlugin.get()).setAliases("v", "version").addRequirements(RequirementHasPerm.get(Perm.VERSION));

    @Override
    public List<String> getAliases() {
        return MConf.get().aliases;
    }


    @Override
    public void perform() {
        me.openInventory(EngineKit.get().getMainInventory());
    }


}
