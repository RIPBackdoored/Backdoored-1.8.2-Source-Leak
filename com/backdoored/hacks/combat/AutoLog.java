package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.init.*;
import a.a.k.e.*;

@b.a(name = "Auto Log", description = "Automaticall Disconnect", category = CategoriesInit.COMBAT)
public class AutoLog extends BaseHack
{
    private final Setting<Integer> minTotems;
    
    public AutoLog() {
        super();
        this.minTotems = new IntegerSetting("Min Totems", this, 0, 0, 5);
    }
    
    public void onTick() {
        if (this.getEnabled() && a.b(Items.TOTEM_OF_UNDYING, true) <= this.minTotems.getValInt()) {
            a.a.k.e.b.c();
        }
    }
}
