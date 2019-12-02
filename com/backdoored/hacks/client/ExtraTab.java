package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Extra Tab", description = "Display full tab menu", category = CategoriesInit.CLIENT)
public class ExtraTab extends BaseHack
{
    public static ExtraTab INSTANCE;
    
    public ExtraTab() {
        super();
        ExtraTab.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onTabRender(final RenderTab event) {
        if (this.getEnabled()) {
            event.size = event.players.size();
        }
    }
}
