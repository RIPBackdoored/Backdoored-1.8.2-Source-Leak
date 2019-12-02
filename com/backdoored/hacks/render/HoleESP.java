package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.h.*;
import com.backdoored.setting.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import com.backdoored.utils.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;

@b.a(name = "Hole ESP", description = "See holes to camp in during pvp", category = CategoriesInit.RENDER)
public class HoleESP extends BaseHack
{
    private final Setting<Integer> holeRadius;
    private final Setting<Boolean> holeheight3;
    private final Setting<Boolean> renderBottom;
    private final Setting<Integer> maxY;
    private final Setting<Integer> maxHoles;
    private final Setting<Float> vi;
    private HashMap<BlockPos, f.a> vj;
    
    public HoleESP() {
        super();
        this.holeRadius = new IntegerSetting("Hole Radius", this, 10, 1, 50);
        this.holeheight3 = new BooleanSetting("Hole height 3", this, false);
        this.renderBottom = new BooleanSetting("Render Bottom", this, false);
        this.maxY = new IntegerSetting("Max Y", this, 125, 0, 125);
        this.maxHoles = new IntegerSetting("Max Holes", this, 20, 1, 50);
        this.vi = new FloatSetting("Refresh Delay", this, 1.0f, 0.0f, 4.0f);
        this.vj = new HashMap<BlockPos, f.a>();
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        this.vj.clear();
        final Iterable allInBox = BlockPos.getAllInBox(HoleESP.mc.player.getPosition().add(-this.holeRadius.getValInt(), -this.holeRadius.getValInt(), -this.holeRadius.getValInt()), HoleESP.mc.player.getPosition().add((int)this.holeRadius.getValInt(), (int)this.holeRadius.getValInt(), (int)this.holeRadius.getValInt()));
        int n = 0;
        for (final BlockPos blockPos : allInBox) {
            if (n > this.maxHoles.getValInt()) {
                return;
            }
            final f.a a = this.a(blockPos, this.holeheight3.getValInt());
            if (a == f.a.vc) {
                continue;
            }
            this.vj.put(blockPos, a);
            ++n;
        }
    }
    
    public f.a a(final BlockPos blockPos) {
        return this.a(blockPos, false);
    }
    
    public f.a a(final BlockPos blockPos, final boolean b) {
        if (blockPos.getY() > this.maxY.getValInt()) {
            return f.a.vc;
        }
        final IBlockState[] array = { HoleESP.mc.world.getBlockState(blockPos), HoleESP.mc.world.getBlockState(blockPos.add(0, 1, 0)), HoleESP.mc.world.getBlockState(blockPos.add(0, 2, 0)), HoleESP.mc.world.getBlockState(blockPos.add(0, -1, 0)), HoleESP.mc.world.getBlockState(blockPos.add(1, 0, 0)), HoleESP.mc.world.getBlockState(blockPos.add(0, 0, 1)), HoleESP.mc.world.getBlockState(blockPos.add(-1, 0, 0)), HoleESP.mc.world.getBlockState(blockPos.add(0, 0, -1)) };
        if (!array[0].getMaterial().blocksMovement() && !array[1].getMaterial().blocksMovement() && (!array[2].getMaterial().blocksMovement() || !b) && array[3].getBlock().equals(Blocks.BEDROCK) && array[4].getBlock().equals(Blocks.BEDROCK) && array[5].getBlock().equals(Blocks.BEDROCK) && array[6].getBlock().equals(Blocks.BEDROCK) && array[7].getBlock().equals(Blocks.BEDROCK) && (!array[2].getMaterial().blocksMovement() || !b)) {
            return f.a.uz;
        }
        if (!array[0].getMaterial().blocksMovement() && !array[1].getMaterial().blocksMovement() && (!array[2].getMaterial().blocksMovement() || !b) && (array[3].getBlock().equals(Blocks.BEDROCK) || array[3].getBlock().equals(Blocks.OBSIDIAN)) && (array[4].getBlock().equals(Blocks.BEDROCK) || array[4].getBlock().equals(Blocks.OBSIDIAN)) && (array[5].getBlock().equals(Blocks.BEDROCK) || array[5].getBlock().equals(Blocks.OBSIDIAN)) && (array[6].getBlock().equals(Blocks.BEDROCK) || array[6].getBlock().equals(Blocks.OBSIDIAN)) && (array[7].getBlock().equals(Blocks.BEDROCK) || array[7].getBlock().equals(Blocks.OBSIDIAN))) {
            return f.a.va;
        }
        final boolean b2 = !array[0].getMaterial().blocksMovement() && !array[1].getMaterial().blocksMovement() && (!array[2].getMaterial().blocksMovement() || !b) && array[3].getMaterial().isSolid() && array[4].getMaterial().isSolid() && array[5].getMaterial().isSolid() && array[6].getMaterial().isSolid() && array[7].getMaterial().isSolid();
        return f.a.vc;
    }
    
    public void onRender() {
        if (!this.getEnabled()) {
            return;
        }
        RenderUtils.glStart(255.0f, 0.0f, 255.0f, 0.2f);
        if (this.vj != null) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            for (final Map.Entry<BlockPos, f.a> entry : this.vj.entrySet()) {
                final f.a a = entry.getValue();
                GL11.glLineWidth(2.0f);
                AxisAlignedBB boundingBox = RenderUtils.getBoundingBox(entry.getKey());
                if (this.renderBottom.getValInt()) {
                    boundingBox = new AxisAlignedBB(boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY + 0.001, boundingBox.maxZ);
                }
                if (a == f.a.uz) {
                    RenderGlobal.renderFilledBox(boundingBox, 1.0f, 0.0f, 0.0f, 0.2f);
                    RenderGlobal.drawSelectionBoundingBox(boundingBox, 1.0f, 0.0f, 0.0f, 0.2f);
                    ++n;
                }
                if (a == f.a.va) {
                    RenderGlobal.renderFilledBox(boundingBox, 1.0f, 0.498f, 0.3137f, 0.2f);
                    RenderGlobal.drawSelectionBoundingBox(boundingBox, 1.0f, 0.498f, 0.3137f, 0.2f);
                    ++n2;
                }
                if (a == f.a.vb) {
                    RenderGlobal.renderFilledBox(boundingBox, 255.0f, 255.0f, 255.0f, 0.2f);
                    RenderGlobal.drawSelectionBoundingBox(boundingBox, 255.0f, 255.0f, 255.0f, 0.2f);
                    ++n3;
                }
            }
        }
        RenderUtils.glEnd();
    }
}
