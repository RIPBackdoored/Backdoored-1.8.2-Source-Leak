package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import a.a.g.b.h.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Anti Overlay", description = "Prevents Overlay", category = CategoriesInit.RENDER)
public class AntiOverlay extends BaseHack
{
    private Setting<Boolean> fire;
    private Setting<Boolean> blocks;
    private Setting<Boolean> water;
    
    public AntiOverlay() {
        super();
        this.fire = new BooleanSetting("Fire", this, true);
        this.blocks = new BooleanSetting("Blocks", this, true);
        this.water = new BooleanSetting("Water", this, true);
    }
    
    @SubscribeEvent
    public void a(final RenderBlockOverlayEvent renderBlockOverlayEvent) {
        if (!this.getEnabled()) {
            return;
        }
        boolean canceled = false;
        switch (a.a$a.ub[renderBlockOverlayEvent.getOverlayType().ordinal()]) {
            case 1: {
                if (this.fire.getValInt()) {
                    canceled = true;
                    break;
                }
                break;
            }
            case 2: {
                if (this.blocks.getValInt()) {
                    canceled = true;
                    break;
                }
                break;
            }
            case 3: {
                if (this.water.getValInt()) {
                    canceled = true;
                    break;
                }
                break;
            }
        }
        renderBlockOverlayEvent.setCanceled(canceled);
    }
}
