package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.g.*;
import com.backdoored.setting.*;

@b.a(name = "Rotation Lock", description = "Lock your rotation", category = CategoriesInit.PLAYER)
public class RotationLock extends BaseHack
{
    private final Setting<p.b> facing;
    
    public RotationLock() {
        super();
        this.facing = new EnumSetting<p.b>("Facing", (BaseHack)this, (Enum)p.b.td);
    }
    
    public void onEnabled() {
        RotationLock.mc.player.setRotationYawHead((float)this.c());
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            RotationLock.mc.player.rotationYaw = (float)this.c();
        }
    }
    
    private int c() {
        int n = 0;
        switch (p.p$a.tc[this.facing.getValInt().ordinal()]) {
            default: {
                n = 0;
                break;
            }
            case 2: {
                n = 90;
                break;
            }
            case 3: {
                n = 180;
                break;
            }
            case 4: {
                n = -90;
                break;
            }
        }
        return n;
    }
}
