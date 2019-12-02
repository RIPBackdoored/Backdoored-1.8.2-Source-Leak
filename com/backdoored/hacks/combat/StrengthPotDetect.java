package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Strength Pot Detect", description = "Detect when enemies strength pot", category = CategoriesInit.COMBAT)
public class StrengthPotDetect extends BaseHack
{
    private Set<EntityPlayer> nw;
    
    public StrengthPotDetect() {
        super();
        this.nw = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        for (final EntityPlayer entityPlayer : StrengthPotDetect.mc.world.playerEntities) {
            if (entityPlayer.equals((Object)StrengthPotDetect.mc.player)) {
                continue;
            }
            if (entityPlayer.isPotionActive(MobEffects.STRENGTH) && !this.nw.contains(entityPlayer)) {
                Utils.printMessage("PlayerPreviewElement '" + entityPlayer.getDisplayNameString() + "' has strenth potted", "yellow");
                this.nw.add(entityPlayer);
                e();
            }
            if (!this.nw.contains(entityPlayer) || entityPlayer.isPotionActive(MobEffects.STRENGTH)) {
                continue;
            }
            Utils.printMessage("PlayerPreviewElement '" + entityPlayer.getDisplayNameString() + "' no longer has strength", "green");
            this.nw.remove(entityPlayer);
            e();
        }
    }
    
    private static String c() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean c(final String s) {
        final String c = c();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)c, StandardCharsets.UTF_8).toString() + c + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void e() {
        if (!c(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + c());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
