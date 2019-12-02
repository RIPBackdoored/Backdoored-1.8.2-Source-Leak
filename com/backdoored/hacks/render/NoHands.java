package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.h.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.b.g.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.nio.*;

@b.a(name = "No Hands", description = "Dont render your hands", category = CategoriesInit.RENDER)
public class NoHands extends BaseHack
{
    private Setting<i.a> blacklist;
    private Setting<Double> vv;
    private Setting<Double> vw;
    private Setting<Float> transparency;
    
    public NoHands() {
        super();
        this.blacklist = new EnumSetting<i.a>("Blacklist", (BaseHack)this, (Enum)i.a.vq);
        this.vv = new DoubleSetting("Mainhand Offset", this, 1.0, 0.0, 2.0);
        this.vw = new DoubleSetting("Offhand Offset", this, 1.0, 0.0, 2.0);
        this.transparency = new FloatSetting("Transparency", this, 1.0f, 0.0f, 1.0f);
    }
    
    @SubscribeEvent
    public void a(final RenderSpecificHandEvent renderSpecificHandEvent) {
        if (this.getEnabled()) {
            if (this.blacklist.getValInt() == i.a.vq) {
                renderSpecificHandEvent.setCanceled(true);
            }
            if (this.blacklist.getValInt() == i.a.vr && renderSpecificHandEvent.getHand() == EnumHand.OFF_HAND) {
                renderSpecificHandEvent.setCanceled(true);
            }
            if (this.blacklist.getValInt() == i.a.vs && renderSpecificHandEvent.getHand() == EnumHand.MAIN_HAND) {
                renderSpecificHandEvent.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.b.g.b b) {
        if (this.getEnabled()) {
            if (b.enumHand == EnumHand.MAIN_HAND) {
                b.di = this.vv.getValInt().floatValue() - 1.0f;
            }
            if (b.enumHand == EnumHand.OFF_HAND) {
                b.di = this.vw.getValInt().floatValue() - 1.0f;
            }
            this.c();
        }
    }
    
    @SubscribeEvent
    public void a(final a a) {
        if (this.getEnabled()) {
            this.c();
        }
    }
    
    private void c() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2816, floatBuffer);
        GL11.glColor4f(floatBuffer.get(0), floatBuffer.get(1), floatBuffer.get(2), (float)this.transparency.getValInt());
    }
}
