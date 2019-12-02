package com.backdoored;

import net.minecraftforge.fml.common.*;
import club.minnced.discord.rpc.*;
import a.a.*;
import net.minecraft.client.multiplayer.*;

public class DiscordPresence
{
    private static final String APP_ID = "647848427949916180";
    private static final DiscordRPC rpc;
    private static DiscordRichPresence presence;
    private static boolean hasStarted;
    
    public DiscordPresence() {
        super();
    }
    
    public static boolean start() {
        FMLLog.log.info("Starting Discord RPC");
        if (DiscordPresence.hasStarted) {
            return false;
        }
        DiscordPresence.hasStarted = true;
        final DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
        discordEventHandlers.disconnected = b::a;
        DiscordPresence.rpc.Discord_Initialize("647848427949916180", discordEventHandlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = "Main Menu";
        DiscordPresence.presence.state = "discord.gg/pdMhDwN";
        DiscordPresence.presence.largeImageKey = "backdoored_logo";
        DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
        new Thread(b::b, "Discord-RPC-Callback-Handler").start();
        FMLLog.log.info("Discord RPC initialised succesfully");
        return true;
    }
    
    private static /* synthetic */ void b() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DiscordPresence.rpc.Discord_RunCallbacks();
                String details = "";
                String state = "";
                int intValue = 0;
                int intValue2 = 0;
                if (Globals.mc.isIntegratedServerRunning()) {
                    details = "Singleplayer";
                }
                else if (Globals.mc.getCurrentServerData() != null) {
                    final ServerData currentServerData = Globals.mc.getCurrentServerData();
                    if (!currentServerData.serverIP.equals("")) {
                        details = "Multiplayer";
                        state = currentServerData.serverIP;
                        if (currentServerData.populationInfo != null) {
                            try {
                                final String[] split = currentServerData.populationInfo.replaceAll("\\b(?![0-9]|\\/)\\b\\S+", "").split("/");
                                if (split.length >= 2) {
                                    intValue = Integer.valueOf(split[0]);
                                    intValue2 = Integer.valueOf(split[1]);
                                }
                            }
                            catch (Exception ex3) {}
                        }
                        if (state.contains("2b2t.org")) {
                            try {
                                if (Backdoored.lastChat.startsWith("Position in queue: ")) {
                                    state = state + " " + Integer.parseInt(Backdoored.lastChat.substring(19)) + " in queue";
                                }
                            }
                            catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    details = "Main Menu";
                    state = "discord.gg/ncQkFKU";
                }
                if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                    DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                }
                DiscordPresence.presence.details = details;
                DiscordPresence.presence.state = state;
                DiscordPresence.presence.partySize = intValue;
                DiscordPresence.presence.partyMax = intValue2;
                DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException ex2) {
                ex2.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ void a(final int n, final String s) {
        System.out.println("Discord RPC disconnected, var1: " + String.valueOf(n) + ", var2: " + s);
    }
    
    static {
        rpc = DiscordRPC.INSTANCE;
        DiscordPresence.presence = new DiscordRichPresence();
        DiscordPresence.hasStarted = false;
    }
}
