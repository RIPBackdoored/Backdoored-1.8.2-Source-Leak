package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.g.*;
import java.util.function.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import java.io.*;
import com.backdoored.utils.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import java.util.*;

@b.a(name = "Chunk Logger", description = "Log chunks that contain a specified ammount of chest", category = CategoriesInit.PLAYER)
public class ChunkLogger extends BaseHack
{
    private File sn;
    
    public ChunkLogger() {
        super();
        this.sn = new File("Backdoored/ChunkLogs.txt");
    }
    
    public void onEnabled() {
        this.setEnabled(false);
    }
    
    public void onTick() {
        ChunkLogger.mc.world.loadedTileEntityList.stream().filter(g::a);
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (packetRecieved.packet instanceof SPacketChunkData) {
            final SPacketChunkData sPacketChunkData = (SPacketChunkData)packetRecieved.packet;
        }
    }
    
    @SubscribeEvent
    public void a(final ChunkEvent.Load load) {
        if (!this.getEnabled()) {
            return;
        }
        for (final ChunkPos chunkPos : ChunkLogger.mc.world.getPersistentChunks().keys()) {
            final Chunk chunk = ChunkLogger.mc.world.getChunk(chunkPos.x, chunkPos.z);
            final Collection values = chunk.getTileEntityMap().values();
            System.out.println(chunk.x * 16 + " " + chunk.z * 16);
            System.out.println(chunk.getTileEntityMap().size());
            System.out.println(chunk.getTileEntityMap().values().size());
            if (chunk.getTileEntityMap().size() < 1) {
                return;
            }
            int n = 0;
            System.out.println("tiles: " + chunk.getTileEntityMap().size());
            for (final TileEntity tileEntity : values) {
                System.out.println(tileEntity instanceof TileEntityChest);
                System.out.println(tileEntity.getPos());
                if (tileEntity instanceof TileEntityChest) {
                    ++n;
                }
            }
            int n2 = 0;
            final Iterator<TileEntity> iterator3 = values.iterator();
            while (iterator3.hasNext()) {
                if (iterator3.next() instanceof TileEntityBed) {
                    ++n2;
                }
            }
            int n3 = 0;
            final Iterator<TileEntity> iterator4 = values.iterator();
            while (iterator4.hasNext()) {
                if (iterator4.next() instanceof TileEntityEndPortal) {
                    n3 = 1;
                    break;
                }
            }
            System.out.printf("\nChunk Loaded %d %d %s", n, n2, String.valueOf((boolean)(n3 != 0)));
            if (n <= 0 && n2 <= 0 && n3 == 0) {
                continue;
            }
            final Date date = new Date(System.currentTimeMillis());
            String serverIP = "Singleplayer";
            if (ChunkLogger.mc.getCurrentServerData() != null) {
                serverIP = ChunkLogger.mc.getCurrentServerData().serverIP;
            }
            final String format = String.format("(%s) %s %s: %d chests, %d beds, %d end portals", date, serverIP, "(" + chunk.x * 16 + ", " + chunk.z * 16 + ")", n, n2, n3);
            try {
                final FileWriter fileWriter = new FileWriter(this.sn, true);
                final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(format + "\n");
                bufferedWriter.close();
                fileWriter.close();
                System.out.println("Found Chunk " + format);
                Utils.printMessage("Found Chunk " + format);
            }
            catch (Exception ex) {
                System.out.println(format);
                ex.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ boolean a(final TileEntity tileEntity) {
        return ChunkLogger.mc.player.getDistance((double)tileEntity.getPos().getX(), ChunkLogger.mc.player.posY, (double)tileEntity.getPos().getZ()) < 500.0;
    }
}
