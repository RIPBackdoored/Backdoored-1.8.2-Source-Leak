package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;

@b.a(name = "Auto Dupe", description = "", category = CategoriesInit.PLAYER)
public class AutoDupe extends BaseHack
{
    private boolean rw;
    
    public AutoDupe() {
        super();
        this.rw = false;
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        if (!this.rw) {
            AutoDupe.mc.player.sendChatMessage(".vanish dismount");
            this.rw = true;
        }
    }
}
