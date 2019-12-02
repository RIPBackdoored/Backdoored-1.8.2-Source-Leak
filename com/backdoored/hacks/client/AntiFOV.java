package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Anti FOV", description = "Cap your FOV", category = CategoriesInit.CLIENT)
public class AntiFOV extends BaseHack
{
    private Setting<Integer> maxFOV;
    
    public AntiFOV() {
        super();
        this.maxFOV = new IntegerSetting("Max FOV", this, 125, 0, 360);
    }
    
    @SubscribeEvent
    public void onRenderFOV(final EntityViewRenderEvent.FOVModifier event) {
        if (this.getEnabled()) {
            event.setFOV(Math.min(event.getFOV(), this.maxFOV.getValInt()));
        }
    }
}
