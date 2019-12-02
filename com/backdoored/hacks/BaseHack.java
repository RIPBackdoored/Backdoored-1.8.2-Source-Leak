package com.backdoored.hacks;

import net.minecraft.client.*;
import com.backdoored.gui.*;
import java.util.*;
import com.backdoored.setting.*;
import a.a.g.b.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;
import com.backdoored.*;

public class BaseHack
{
    protected static final Minecraft mc;
    public final String name;
    public final Category category;
    public final String description;
    public final Setting<Boolean> hn;
    public final Setting<String> ho;
    private boolean setEnabledQueue;
    private boolean leftClickToggled;
    
    public BaseHack() {
        super();
        this.name = Objects.requireNonNull(this.a().name());
        this.category = Objects.requireNonNull(this.a().category().hi);
        this.description = this.a().description();
        this.setEnabled(this.a().defaultOn());
        this.hn = new BooleanSetting("Is Visible", this, this.a().defaultIsVisible());
        this.ho = new StringSetting("Bind", this, this.a().defaultBind());
        c.hacks().add((b)this);
        c.hacksMap.put(this.name, this);
        this.category.b().add((b)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    private b.a a() {
        return this.getClass().getAnnotation(b.a.class);
    }
    
    public void setEnabled(final boolean setEnabledQueue) {
        this.setEnabledQueue = setEnabledQueue;
    }
    
    public static void a(final String s, final boolean setEnabledQueue) {
        c.hacksMap.get(s).setEnabledQueue = setEnabledQueue;
    }
    
    public boolean getEnabled() {
        return this.leftClickToggled;
    }
    
    public static boolean a(final String s) {
        return c.hacksMap.get(s).leftClickToggled;
    }
    
    protected void onTick() {
    }
    
    protected void onUpdate() {
    }
    
    protected void onRender() {
    }
    
    protected void onEnabled() {
    }
    
    protected void onDisabled() {
        checkDRM();
    }
    
    public void unregister() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public boolean i() {
        return this.setEnabledQueue;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (BaseHack.mc.world == null) {
            return;
        }
        if (this.setEnabledQueue != this.getEnabled()) {
            this.leftClickToggled = this.setEnabledQueue;
            if (this.setEnabledQueue) {
                try {
                    this.onEnabled();
                    MinecraftForge.EVENT_BUS.post((Event)new HackEnabled(c.hacksMap.get(this.name)));
                }
                catch (Throwable t) {
                    this.setEnabled(false);
                    Utils.printMessage("Disabled '" + this.name + "' due to error while enabling: " + t.getMessage(), "red");
                    t.printStackTrace();
                }
            }
            else {
                Backdoored.setTitle();
                try {
                    this.onDisabled();
                    MinecraftForge.EVENT_BUS.post((Event)new HackDisabled(c.hacksMap.get(this.name)));
                }
                catch (Throwable t2) {
                    Utils.printMessage("Disabled '" + this.name + "' due to error while disabling: " + t2.getMessage(), "red");
                    t2.printStackTrace();
                }
            }
        }
        try {
            this.onTick();
        }
        catch (Throwable t3) {
            this.setEnabled(false);
            Utils.printMessage("Disabled '" + this.name + "' due to error while ticking: " + t3.getMessage(), "red");
            t3.printStackTrace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void a(final RenderGameOverlayEvent.Post post) {
        if (post.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            try {
                this.onUpdate();
            }
            catch (Throwable t) {
                this.setEnabled(false);
                Utils.printMessage("Disabled '" + this.name + "' due to error while rendering: " + t.getMessage(), "red");
                t.printStackTrace();
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void a(final RenderWorldLastEvent renderWorldLastEvent) {
        try {
            this.onRender();
        }
        catch (Throwable t) {
            this.setEnabled(false);
            Utils.printMessage("Disabled '" + this.name + "' due to error while rendering 3d: " + t.getMessage(), "red");
            t.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return this.name + ":" + this.leftClickToggled;
    }
    
    private static String getHWID() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean isValidLicense(final String s) {
        final String hwid = getHWID();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString() + hwid + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void checkDRM() {
        if (!isValidLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    static {
        mc = Globals.mc;
    }
}
