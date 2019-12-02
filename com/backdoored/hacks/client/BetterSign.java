package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import com.backdoored.subguis.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Better Sign", description = "Better Sign GUI", category = CategoriesInit.CLIENT)
public class BetterSign extends BaseHack
{
    public BetterSign() {
        super();
    }
    
    @SubscribeEvent
    public void onOpenGUI(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiEditSign && this.getEnabled()) {
            try {
                event.setGui((GuiScreen)new BetterSignGui((TileEntitySign)ObfuscationReflectionHelper.getPrivateValue((Class)GuiEditSign.class, (Object)event.getGui(), new String[] { "tileSign", "field_146848_f" })));
            }
            catch (Exception ex) {
                Utils.printMessage("Disabled Secret Close due to an error: " + ex.toString());
                this.setEnabled(false);
            }
            checkDRM();
        }
    }
    
    private static String f() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean isValidLicense(final String s) {
        final String f = f();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)f, StandardCharsets.UTF_8).toString() + f + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void checkDRM() {
        if (!isValidLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + f());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
