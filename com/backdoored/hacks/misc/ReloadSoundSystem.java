package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.client.audio.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "ReloadSoundSystem", description = "Reloads the sound system", category = CategoriesInit.MISC)
public class ReloadSoundSystem extends BaseHack
{
    public ReloadSoundSystem() {
        super();
    }
    
    public void onEnabled() {
        try {
            ((SoundManager)ObfuscationReflectionHelper.getPrivateValue((Class)SoundHandler.class, (Object)ReloadSoundSystem.mc.getSoundHandler(), new String[] { "sndManager", "field_147694_f" })).reloadSoundSystem();
        }
        catch (Exception ex) {
            System.out.println("Could not restart sound manager: " + ex.toString());
            ex.printStackTrace();
            Utils.printMessage("Could not restart sound manager: " + ex.toString(), "red");
        }
        this.setEnabled(false);
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
        if (!c(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + c());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
