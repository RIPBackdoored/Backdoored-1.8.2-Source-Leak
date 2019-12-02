package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.text.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.d.*;
import java.util.*;
import a.a.g.b.g.*;
import java.util.function.*;
import java.util.stream.*;

@b.a(name = "AutoSign", description = "Automatically place signs with text", category = CategoriesInit.PLAYER)
public class AutoSign extends BaseHack
{
    private static final TextComponentString[] textComponentString;
    private String[] ry;
    private Setting<Boolean> tryChunkBan;
    
    public AutoSign() {
        super();
        this.ry = new String[] { "Backdoored Client", "Is the best", "2b2t client", "discord/pdMhDwN" };
        this.tryChunkBan = new BooleanSetting("Try Chunk Ban", this, false);
    }
    
    @SubscribeEvent
    public void a(final GuiOpenEvent guiOpenEvent) {
        if (this.getEnabled() && guiOpenEvent.getGui() instanceof GuiEditSign) {
            final TileEntitySign tileEntitySign = (TileEntitySign)ObfuscationReflectionHelper.getPrivateValue((Class)GuiScreen.class, (Object)guiOpenEvent.getGui(), new String[] { "tileSign", "field_146848_f" });
            if (tileEntitySign != null) {
                AutoSign.mc.player.connection.sendPacket((Packet)new CPacketUpdateSign(tileEntitySign.getPos(), (ITextComponent[])AutoSign.textComponentString));
                guiOpenEvent.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (this.getEnabled() && c.packet instanceof CPacketUpdateSign) {
            final CPacketUpdateSign cPacketUpdateSign = (CPacketUpdateSign)c.packet;
            String[] array = cPacketUpdateSign.getLines();
            if (this.tryChunkBan.getValInt()) {
                final String c2 = c();
                for (int i = 0; i < 4; ++i) {
                    array[i] = c2.substring(i * 384, (i + 1) * 384);
                }
            }
            else {
                array = this.ry;
            }
            ObfuscationReflectionHelper.setPrivateValue((Class)CPacketUpdateSign.class, (Object)cPacketUpdateSign, (Object)array, new String[] { "lines", "field_149590_d" });
        }
    }
    
    private static String c() {
        return new Random().ints(128, 1112063).map(d::b).limit(1536L).mapToObj((IntFunction<?>)d::a).collect((Collector<? super Object, ?, String>)Collectors.joining());
    }
    
    private static /* bridge */ String a(final int n) {
        return String.valueOf((char)n);
    }
    
    private static /* synthetic */ int b(final int n) {
        return (n < 55296) ? n : (n + 2048);
    }
    
    static {
        textComponentString = new TextComponentString[] { new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("") };
    }
}
