package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.d.d.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Fake Rotation", description = "Fake your rotation", category = CategoriesInit.PLAYER)
public class FakeRotation extends BaseHack
{
    public FakeRotation() {
        super();
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (!this.getEnabled()) {
            return;
        }
        if (c.packet instanceof CPacketPlayer) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)CPacketPlayer.class, (Object)c.packet, (Object)(-90), new String[] { "pitch", "field_149473_f" });
            }
            catch (Exception ex) {
                Utils.printMessage("Disabled fake rotation due to error: " + ex.toString(), "red");
                ex.printStackTrace();
            }
        }
    }
}
