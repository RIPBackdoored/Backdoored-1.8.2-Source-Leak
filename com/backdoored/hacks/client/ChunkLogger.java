package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import a.a.g.b.b.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.utils.*;
import java.nio.file.*;
import net.minecraft.nbt.*;

@b.a(name = "Chunk Logger", description = "Save chunks with chests in them to a text file", category = CategoriesInit.CLIENT)
public class ChunkLogger extends BaseHack
{
    private final Setting<Integer> minChests;
    private final Setting<Boolean> savetoFile;
    private final Setting<Boolean> notifications;
    
    public ChunkLogger() {
        super();
        this.minChests = new IntegerSetting("Min Chests", this, 10, 1, 50);
        this.savetoFile = new BooleanSetting("Save to File", this, true);
        this.notifications = new BooleanSetting("Notifications", this, true);
    }
    
    @SubscribeEvent
    public void onPacketReceived(final PacketRecieved event) {
        if (this.getEnabled() && event.packet instanceof SPacketChunkData) {
            final SPacketChunkData data = (SPacketChunkData)event.packet;
            final long count = data.getTileEntityTags().stream().map(h::a).filter(h::d).count();
            if (count >= this.minChests.getValInt()) {
                this.a(data, count);
            }
        }
    }
    
    private void a(final SPacketChunkData data, final long n) {
        final String string = "(" + data.getChunkX() * 16 + ", " + data.getChunkZ() * 16 + "):" + n + " chests";
        Utils.printMessage(string);
        if (this.notifications.getValInt()) {
            TrayUtils.sendMessage("Visual Range", string);
        }
        new Thread(this::c, "Backdoored Chunk Logger File Saving Thread").start();
    }
    
    private /* synthetic */ void c(final String s) {
        if (this.savetoFile.getValInt()) {
            try {
                Files.write(Paths.get("Backdoored/loggedchunks.txt", new String[0]), s.getBytes(), StandardOpenOption.APPEND);
            }
            catch (Exception ex) {
                ChunkLogger.mc.addScheduledTask(h::a);
                ex.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ void a(final Exception ex) {
        Utils.printMessage("Could not save world to log file: " + ex.getMessage());
    }
    
    private static /* bridge */ boolean d(final String s) {
        return s.equals("minecraft:chest");
    }
    
    private static /* synthetic */ String a(final NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.getString("id");
    }
}
