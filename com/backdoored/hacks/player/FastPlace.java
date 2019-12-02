package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.g.*;
import com.backdoored.setting.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;

@b.a(name = "FastPlace", description = "Place blocks or use items faster", category = CategoriesInit.PLAYER)
public class FastPlace extends BaseHack
{
    private Setting<k.b> whitelist;
    
    public FastPlace() {
        super();
        this.whitelist = new EnumSetting<k.b>("Whitelist", (BaseHack)this, (Enum)k.b.ss);
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final Item item = FastPlace.mc.player.inventory.getCurrentItem().getItem();
        final boolean b = item instanceof ItemExpBottle;
        final boolean b2 = item instanceof ItemEndCrystal;
        switch (k.k$a.sr[this.whitelist.getValInt().ordinal()]) {
            case 1: {
                this.c();
                break;
            }
            case 2: {
                if (b) {
                    this.c();
                    break;
                }
                break;
            }
            case 3: {
                if (b2) {
                    this.c();
                    break;
                }
                break;
            }
            case 4: {
                if (b2 || b) {
                    this.c();
                    break;
                }
                break;
            }
        }
    }
    
    private void c() {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)FastPlace.mc, (Object)0, new String[] { "rightClickDelayTimer", "field_71467_ac" });
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.setEnabled(false);
            Utils.printMessage("Disabled fastplace due to error: " + ex.toString());
        }
    }
}
