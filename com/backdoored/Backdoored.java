package com.backdoored;

import a.a.h.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import a.a.c.*;
import com.backdoored.config.*;
import com.backdoored.utils.*;
import org.lwjgl.opengl.*;
import a.a.f.*;
import a.a.f.b.*;
import a.a.d.b.a.*;
import com.backdoored.hacks.chatbot.*;
import com.backdoored.hacks.render.*;
import com.backdoored.hacks.client.*;
import com.backdoored.hacks.player.*;
import com.backdoored.hacks.movement.*;
import java.sql.*;
import com.backdoored.hacks.exploit.*;
import com.backdoored.hacks.misc.*;
import com.backdoored.hacks.combat.*;
import net.minecraft.world.border.*;
import com.backdoored.hacks.ui.*;
import com.backdoored.api.classloading.*;
import java.util.*;
import com.backdoored.commands.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.server.*;
import com.backdoored.event.*;

@Mod(modid = "backdoored", version = "1.8.2", clientSideOnly = true)
public class Backdoored
{
    public static final String MODID = "backdoored";
    public static final String VERSION = "1.8.2";
    public static String providedLicense;
    private static Boolean j;
    public static a k;
    public static String lastChat;
    
    public Backdoored() {
        super();
    }
    
    public static boolean getDevMode() {
        if (Backdoored.j != null) {
            return Backdoored.j;
        }
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader("Backdoored/options.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("dev.enable.debugger")) {
                    return true;
                }
            }
            bufferedReader.close();
            return false;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        FMLLog.log.info("\n$$$$$$$\\                      $$\\             $$\\                                               $$\\ \n$$  __$$\\                     $$ |            $$ |                                              $$ |\n$$ |  $$ | $$$$$$\\   $$$$$$$\\ $$ |  $$\\  $$$$$$$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$$ |\n$$$$$$$\\ | \\____$$\\ $$  _____|$$ | $$  |$$  __$$ |$$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$ |\n$$  __$$\\  $$$$$$$ |$$ /      $$$$$$  / $$ /  $$ |$$ /  $$ |$$ /  $$ |$$ |  \\__|$$$$$$$$ |$$ /  $$ |\n$$ |  $$ |$$  __$$ |$$ |      $$  _$$<  $$ |  $$ |$$ |  $$ |$$ |  $$ |$$ |      $$   ____|$$ |  $$ |\n$$$$$$$  |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\ \\$$$$$$$ |\\$$$$$$  |\\$$$$$$  |$$ |      \\$$$$$$$\\ \\$$$$$$$ |\n\\_______/  \\_______| \\_______|\\__|  \\__| \\_______| \\______/  \\______/ \\__|       \\_______| \\_______|\n");
        FMLLog.log.info("Loading backdoored...");
        DiscordPresence.start();
        final File file = new File("Backdoored");
        if (!file.exists()) {
            file.mkdir();
        }
        if (!DrmManager.loadLicense()) {
            throw new NoStackTraceThrowable("Couldnt load license, invalid drm");
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLInitializationEvent event) {
        FriendUtils.read();
        MinecraftForge.EVENT_BUS.register((Object)new e());
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.initHacks();
        this.onChatRecieved();
        this.initCommands();
        this.c();
        Backdoored.k = new a();
        System.out.println("Reading config");
        ConfigJsonManager.read();
        Runtime.getRuntime().addShutdownHook(new Thread(a.a.a::g));
        new CapeUtils();
        setTitle();
    }
    
    public static void setTitle() {
        Display.setTitle("Backdoored 1.8.2");
    }
    
    private void c() {
        b.a();
        MinecraftForge.EVENT_BUS.register((Object)new a.a.f.b.a());
        MinecraftForge.EVENT_BUS.register((Object)new a.a.f.b.b());
        MinecraftForge.EVENT_BUS.register((Object)new d());
    }
    
    private void initHacks() {
        MinecraftForge.EVENT_BUS.post((Event)new c.b());
        final Class[] array = { Announcer.class, AntiAFK.class, AntiBedTrap.class, AntiDeathScreen.class, AntiChunkBan.class, AntiFall.class, AntiFOV.class, AntiOverlay.class, AntiProfanity.class, AntiLag.class, Auto32k.class, AutoBedBomb.class, AutoCrystal.class, AutoEz.class, AutoReply.class, AutoSign.class, AutoTotem.class, AutoTrap.class, AutoWither.class, BetterChat.class, BetterHighlightBox.class, BetterScreenshot.class, BetterSign.class, BoatFly.class, BoatAura.class, ChatAppend.class, ChatBot.class, ChatFilter.class, ClearGUI.class, ChatModifier.class, ChestESP.class, ConstantQMain.class, Coordinates.class, CoordTpExploit.class, DebugCrosshair.class, ElytraFlight.class, ExtraTab.class, FakeCreative.class, FakeItem.class, FakeRotation.class, FastPlace.class, FullBright.class, GrabCoords.class, GuiMove.class, GodMode.class, GodmodeCrystalRemover.class, HausemasterFinder.class, HitboxESP.class, HoleFiller.class, HoleESP.class, HopperAura.class, InvisDetect.class, InstantPortal.class, ItemInfo.class, Jesus.class, KillAura.class, LagExploit.class, LogoutSpots.class, MapTooltip.class, MapSpam.class, MobOwner.class, MsgOnToggle.class, NoBreakDelay.class, NoHands.class, NoRender.class, NoSwing.class, Notifications.class, PlayersPotions.class, PopbobS3xDupe.class, PullDown.class, PingSpoof.class, QueueExploit.class, RainbowEnchant.class, ReloadSoundSystem.class, RotationLock.class, SafeWalk.class, Scaffold.class, SecretClose.class, ServerCrasher.class, ServerNotResponding.class, ShulkerPreview.class, SoundCoordLogger.class, Spammer.class, Speed.class, StrengthPotDetect.class, SmoothMovement.class, Surround.class, Swing.class, Time.class, VanishDetector.class, VisualRange.class, WebAura.class, WorldBorder.class, Hud.class, ClickGui.class };
        final Set<Class> erroredClasses = new SimpleClassLoader().build(array).initialise().getErroredClasses();
        FMLLog.log.info("Backdoored tried to load " + array.length + " hacks, out of which " + erroredClasses.size() + " failed");
        FMLLog.log.info("Failed hack: " + erroredClasses.toString());
        MinecraftForge.EVENT_BUS.post((Event)new c.a(a.a.g.b.c.hacks()));
        FMLLog.log.info("Backdoored startup finished ");
    }
    
    private void initCommands() {
        final Class[] array = { Fakemsg.class, Friend.class, Help.class, Prefix.class, Spectate.class, Toggle.class, Save.class, Nomadbase.class, Load.class, Bind.class, Fakeplayer.class, Import.class, Viewinv.class, Unbind.class, Drawn.class, Build.class, Hudeditor.class };
        final Set<Class> erroredClasses = new SimpleClassLoader().build(array).initialise().getErroredClasses();
        FMLLog.log.info("Backdoored tried to load " + array.length + " commands, out of which " + erroredClasses.size() + " failed");
        FMLLog.log.info("Failed commands: " + erroredClasses.toString());
        MinecraftForge.EVENT_BUS.register((Object)new Command());
    }
    
    private void onChatRecieved() {
        MinecraftForge.EVENT_BUS.register((Object)new a.a.g.c.a());
    }
    
    @SubscribeEvent
    public void a(final ClientChatReceivedEvent clientChatReceivedEvent) {
        Backdoored.lastChat = clientChatReceivedEvent.getMessage().getUnformattedText();
    }
    
    @SubscribeEvent
    public void onGuiOpened(final GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() instanceof GuiMainMenu) {
            guiOpenEvent.setGui((GuiScreen)new a.a.j.d());
        }
        if (guiOpenEvent.getGui() instanceof GuiIngameMenu) {}
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (event.packet instanceof SPacketTimeUpdate) {
            MinecraftForge.EVENT_BUS.post((Event)new ServerTick());
        }
    }
    
    private static /* synthetic */ void g() {
        System.out.println("System shutdown, saving configs");
        a.a.g.b.c.hacks().forEach(a.a.g.b.b::h);
        FriendUtils.a();
        ConfigJsonManager.a();
    }
    
    static {
        Backdoored.providedLicense = "";
        Backdoored.j = null;
        Backdoored.lastChat = "";
    }
}
