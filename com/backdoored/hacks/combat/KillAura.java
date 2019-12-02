package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Kill Aura", description = "Attack players near you", category = CategoriesInit.COMBAT)
public class KillAura extends BaseHack
{
    private Setting<Double> nq;
    private Setting<Boolean> 32kOnly;
    private Setting<Boolean> playersonly;
    private Setting<Integer> delayinticks;
    private int nu;
    private static final Random nv;
    
    public KillAura() {
        super();
        this.nq = new DoubleSetting("Range", this, 5.0, 1.0, 15.0);
        this.32kOnly = new BooleanSetting("32k Only", this, false);
        this.playersonly = new BooleanSetting("Players only", this, true);
        this.delayinticks = new IntegerSetting("Delay in ticks", this, 0, 0, 50);
        this.nu = 0;
    }
    
    public void onTick() {
        if (!this.getEnabled() || KillAura.mc.player.isDead || KillAura.mc.world == null) {
            return;
        }
        if (this.nu < this.delayinticks.getValInt()) {
            ++this.nu;
            return;
        }
        this.nu = 0;
        for (final Entity entity : KillAura.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity == KillAura.mc.player) {
                    continue;
                }
                if (KillAura.mc.player.getDistance(entity) > this.nq.getValInt() || ((EntityLivingBase)entity).getHealth() <= 0.0f || (!(entity instanceof EntityPlayer) && this.playersonly.getValInt()) || (entity instanceof EntityPlayer && FriendUtils.a((EntityPlayer)entity)) || (!Auto32k.a(KillAura.mc.player.getHeldItemMainhand()) && this.32kOnly.getValInt())) {
                    continue;
                }
                KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.player, entity);
                KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    public void onDisabled() {
        e();
    }
    
    private static String c() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean c(final String s) {
        final String c = c();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)c, StandardCharsets.UTF_8).toString() + c + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void e() {
        if (KillAura.nv.nextBoolean() && !c(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + c());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    static {
        nv = new Random();
    }
}
