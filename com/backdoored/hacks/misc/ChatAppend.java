package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.d.d.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Chat Append", description = "Show off your new client", category = CategoriesInit.MISC, defaultOn = true)
public class ChatAppend extends BaseHack
{
    public ChatAppend() {
        super();
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (c.packet instanceof CPacketChatMessage && this.getEnabled()) {
            final CPacketChatMessage cPacketChatMessage = (CPacketChatMessage)c.packet;
            if (!cPacketChatMessage.getMessage().startsWith("/") && !cPacketChatMessage.getMessage().startsWith("!")) {
                final String concat = cPacketChatMessage.getMessage().concat(" » \u0299\u1d00\u1d04\u1d0b\u1d05\u1d0f\u1d0f\u0280\u1d07\u1d05");
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)cPacketChatMessage, (Object)concat, new String[] { "message", "field_149440_a" });
                }
                catch (Exception ex) {
                    Utils.printMessage("Disabled chat append due to error: " + ex.getMessage());
                    this.setEnabled(false);
                    ex.printStackTrace();
                }
                e();
            }
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
