package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Swing", description = "Popup console", category = CategoriesInit.UI)
public class Swing extends BaseHack
{
    public static Setting<Boolean> xn;
    private boolean xo;
    private a.a.g.b.i.c.b xp;
    
    public Swing() {
        super();
        this.xo = false;
        Swing.xn = new BooleanSetting("Show Chat", this, true);
    }
    
    public void onEnabled() {
        if (this.xp == null || this.xo) {
            this.xp = new a.a.g.b.i.c.b();
        }
        this.xo = false;
        e();
    }
    
    public void onDisabled() {
        this.xp.b();
        this.xo = true;
        e();
    }
    
    public void onTick() {
        if (this.xp != null) {
            this.xp.a();
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
