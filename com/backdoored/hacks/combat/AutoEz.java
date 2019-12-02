package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.c.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.backdoored.setting.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.event.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "AutoEz", description = "Automatically ez", category = CategoriesInit.COMBAT)
public class AutoEz extends BaseHack
{
    private Setting<e.a> text;
    private int hasBeenCombat;
    private EntityPlayer target;
    private static final Random random;
    
    public AutoEz() {
        super();
        this.text = new EnumSetting<e.a>("Text", (BaseHack)this, (Enum)e.a.mq);
    }
    
    @SubscribeEvent
    public void a(final AttackEntityEvent attackEntityEvent) {
        if (this.getEnabled() && attackEntityEvent.getTarget() instanceof EntityPlayer) {
            final EntityPlayer target = (EntityPlayer)attackEntityEvent.getTarget();
            if (attackEntityEvent.getEntityPlayer().getUniqueID().equals(AutoEz.mc.player.getUniqueID())) {
                if (target.getHealth() <= 0.0f || target.isDead || !AutoEz.mc.world.playerEntities.contains(target)) {
                    AutoEz.mc.player.sendChatMessage(this.text.toString());
                    checkDRM();
                    return;
                }
                this.hasBeenCombat = 500;
                this.target = target;
            }
        }
    }
    
    public void onTick() {
        if (AutoEz.mc.player.isDead) {
            this.hasBeenCombat = 0;
        }
        if (this.hasBeenCombat > 0 && (this.target.getHealth() <= 0.0f || this.target.isDead || !AutoEz.mc.world.playerEntities.contains(this.target))) {
            if (this.getEnabled()) {
                AutoEz.mc.player.sendChatMessage(this.text.toString());
                checkDRM();
            }
            this.hasBeenCombat = 0;
        }
        --this.hasBeenCombat;
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final PlayerDeath event) {
        if (!this.getEnabled()) {
            return;
        }
        if (!event.getPlayer().equals((Object)AutoEz.mc.player) && event.getPlayer().equals((Object)this.target)) {
            AutoEz.mc.player.sendChatMessage(this.text.toString());
            checkDRM();
        }
    }
    
    private static String getHWID() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean getLicense(final String s) {
        final String hwid = getHWID();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString() + hwid + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void checkDRM() {
        if (AutoEz.random.nextBoolean() && !getLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    static {
        random = new Random();
    }
}
