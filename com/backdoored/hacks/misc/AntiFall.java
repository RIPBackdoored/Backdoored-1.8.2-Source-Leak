package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;

@b.a(name = "Anti Fall", description = "Tries to prevent you falling a certain distance by lagging you back", category = CategoriesInit.MISC)
public class AntiFall extends BaseHack
{
    private final Setting<Integer> maxFallDistance;
    private boolean qm;
    
    public AntiFall() {
        super();
        this.maxFallDistance = new IntegerSetting("Max Fall Distance", this, 10, 3, 40);
        this.qm = false;
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            if (AntiFall.mc.player.fallDistance >= this.maxFallDistance.getValInt()) {
                AntiFall.mc.player.capabilities.isFlying = true;
                this.qm = true;
            }
            else if (this.qm) {
                AntiFall.mc.player.capabilities.isFlying = false;
                this.qm = false;
            }
        }
    }
}
