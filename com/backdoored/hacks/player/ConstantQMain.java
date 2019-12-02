package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "ConstantQMain", description = "Does \"/queue main\" once a minute to help you get through the 2b2t queue", category = CategoriesInit.PLAYER)
public class ConstantQMain extends BaseHack
{
    private static long so;
    private Setting<Boolean> onlyinend;
    
    public ConstantQMain() {
        super();
        this.onlyinend = new BooleanSetting("Only in end", this, true);
    }
    
    public void onTick() {
        if (System.currentTimeMillis() >= ConstantQMain.so + 30000L && this.getEnabled()) {
            if (ConstantQMain.mc.player == null) {
                return;
            }
            if (!this.onlyinend.getValInt() || (ConstantQMain.mc.player.dimension != -1 && ConstantQMain.mc.player.dimension != 0)) {
                ConstantQMain.so = System.currentTimeMillis();
                ConstantQMain.mc.player.sendChatMessage("/queue main");
                Utils.printMessage("/queue main");
                e();
            }
        }
    }
    
    public void onDisabled() {
        ConstantQMain.so = 0L;
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
    
    static {
        ConstantQMain.so = 0L;
    }
}
