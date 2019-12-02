package com.backdoored.commands;

import net.minecraftforge.client.event.*;
import a.a.g.c.*;
import com.backdoored.setting.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.d.*;
import net.minecraft.network.play.client.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

public class Command
{
    public static String x;
    private static Command y;
    
    public Command() {
        super();
    }
    
    public void a() {
        Command.y = this;
    }
    
    public static void a(final String s) {
        if (s.startsWith(Command.x)) {
            if (Command.y == null) {
                Command.y = new Command();
            }
            Command.y.a(new ClientChatEvent(s));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void a(final ClientChatEvent clientChatEvent) {
        final String[] split = clientChatEvent.getMessage().split(" ");
        if (!split[0].startsWith(Command.x)) {
            return;
        }
        c();
        if (split[0].startsWith(Command.x)) {
            split[0] = split[0].replace(Command.x, "");
        }
        final String[] array = new ArrayList(Arrays.asList(split).subList(1, split.length)).toArray(new String[split.length - 1]);
        for (final CommandBase commandBase : CommandBase.z) {
            if (commandBase.ba.contains(split[0].toLowerCase())) {
                if (array.length == 0) {
                    commandBase.a(new String[] { "", "", "", "", "", "" });
                    return;
                }
                commandBase.a(array);
                return;
            }
        }
        if (array.length >= 2) {
            for (final Setting<Integer> setting : d.a()) {
                if (setting.b().name.equalsIgnoreCase(array[0])) {
                    try {
                        if (setting.getValInt() instanceof Integer) {
                            setting.a(Integer.parseInt(array[1]));
                        }
                        else if (setting.getValInt() instanceof Double) {
                            setting.a((Integer)(Object)Double.valueOf(Double.parseDouble(array[1])));
                        }
                        else if (setting.getValInt() instanceof Float) {
                            setting.a((Integer)(Object)Float.valueOf(Float.parseFloat(array[1])));
                        }
                        else if (setting.getValInt() instanceof Long) {
                            setting.a((Integer)(Object)Long.valueOf(Long.parseLong(array[1])));
                        }
                        else if (setting.getValInt() instanceof Enum) {
                            ((EnumSetting<Integer>)setting).a(array[1]);
                        }
                        else if (setting.getValInt() instanceof Boolean) {
                            setting.a((String)Boolean.valueOf(array[1]));
                        }
                        else if (setting.getValInt() instanceof String) {
                            setting.a((Integer)array[1]);
                        }
                        else {
                            System.out.println("Settings of that type not supported yet");
                        }
                    }
                    catch (Exception ex) {
                        Utils.printMessage(ex.getMessage(), "red");
                    }
                }
            }
        }
        Utils.printMessage("Command not found! Type " + Command.x + "help for a list of commands", "red");
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (c.packet instanceof CPacketChatMessage && ((CPacketChatMessage)c.packet).getMessage().startsWith(Command.x)) {
            c.setCanceled(true);
        }
    }
    
    private void a(final CommandBase commandBase, final String[] array) {
        if (!commandBase.a(array)) {
            Utils.printMessage("Usage:\n" + commandBase.a(), "red");
        }
    }
    
    private static String b() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean b(final String s) {
        final String b = b();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)b, StandardCharsets.UTF_8).toString() + b + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void c() {
        if (!b(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + b());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    static {
        Command.x = "-";
    }
}
