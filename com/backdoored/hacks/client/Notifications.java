package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.player.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import com.google.common.hash.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

@b.a(name = "Notifications", description = "Toast Notifications", category = CategoriesInit.CLIENT)
public class Notifications extends BaseHack
{
    public static Notifications INSTANCE;
    private String jt;
    private Setting<Boolean> discordWebhook;
    private Setting<Boolean> visualRange;
    private Setting<Boolean> queue;
    
    public Notifications() {
        super();
        this.jt = null;
        this.discordWebhook = new BooleanSetting("Discord Webhook", this, false);
        this.visualRange = new BooleanSetting("Visual Range", this, true);
        this.queue = new BooleanSetting("Queue", this, true);
        Notifications.INSTANCE = this;
    }
    
    public void visualRangeTrigger(final EntityPlayer player) {
        if (!this.getEnabled() || !this.visualRange.getValInt()) {
            return;
        }
        TrayUtils.sendMessage("Visual Range", player.getDisplayNameString() + " entered your visual range");
        checkDRM();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (this.getEnabled() && this.queue.getValInt() && event.packet instanceof SPacketChat) {
            final SPacketChat sPacketChat = (SPacketChat)event.packet;
            if (sPacketChat.getChatComponent().getUnformattedText().toLowerCase().startsWith("connecting to")) {
                TrayUtils.sendMessage(sPacketChat.getChatComponent().getUnformattedText());
            }
        }
    }
    
    public void onEnabled() {
        try {
            final File file = new File("Backdoored/discordwebhook.txt");
            if (!file.exists()) {
                file.createNewFile();
                throw new RuntimeException("discordwebhook.txt did not exist");
            }
            this.jt = FileUtils.readFileToString(file, StandardCharsets.UTF_8).trim();
            if (this.jt.isEmpty()) {
                throw new RuntimeException("discordwebhook.txt was empty");
            }
            Utils.printMessage("Set discord webhook to: " + this.jt);
        }
        catch (Exception ex) {
            Utils.printMessage("Couldnt get discord webhook: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void c(final String s) {
        if (this.discordWebhook.getValInt()) {}
    }
    
    private static String getHWID() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean getLicense(final String s) {
        final String hwid = getHWID();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString() + hwid + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void checkDRM() {
        if (!getLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
