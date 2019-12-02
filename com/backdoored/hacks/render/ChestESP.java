package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.utils.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.util.math.*;

@b.a(name = "Chest ESP", description = "yes", category = CategoriesInit.RENDER)
public class ChestESP extends BaseHack
{
    private Setting<Boolean> noNether;
    private Setting<Boolean> chams;
    private Setting<Boolean> outlines;
    private Setting<Boolean> chests;
    private Setting<Boolean> enderChests;
    private Setting<Boolean> beds;
    private Setting<Double> chestsR;
    private Setting<Double> chestsG;
    private Setting<Double> chestsB;
    private Setting<Double> chestsA;
    private Setting<Double> bedsR;
    private Setting<Double> bedsG;
    private Setting<Double> bedsB;
    private Setting<Double> bedsA;
    private Setting<Double> eChestsR;
    private Setting<Double> eChestsG;
    private Setting<Double> eChestsB;
    private Setting<Double> eChestsA;
    
    public ChestESP() {
        super();
        this.noNether = new BooleanSetting("No Nether", this, false);
        this.chams = new BooleanSetting("Chams", this, true);
        this.outlines = new BooleanSetting("Outlines", this, true);
        this.chests = new BooleanSetting("Chests", this, true);
        this.enderChests = new BooleanSetting("Ender Chests", this, true);
        this.beds = new BooleanSetting("Beds", this, true);
        this.chestsR = new DoubleSetting("Chests R", this, 1.0, 0.0, 1.0);
        this.chestsG = new DoubleSetting("Chests G", this, 1.0, 0.0, 1.0);
        this.chestsB = new DoubleSetting("Chests B", this, 0.0, 0.0, 1.0);
        this.chestsA = new DoubleSetting("Chests A", this, 1.0, 0.0, 1.0);
        this.bedsR = new DoubleSetting("Beds R", this, 1.0, 0.0, 1.0);
        this.bedsG = new DoubleSetting("Beds G", this, 1.0, 0.0, 1.0);
        this.bedsB = new DoubleSetting("Beds B", this, 0.0, 0.0, 1.0);
        this.bedsA = new DoubleSetting("Beds A", this, 1.0, 0.0, 1.0);
        this.eChestsR = new DoubleSetting("E Chests R", this, 0.0, 0.0, 1.0);
        this.eChestsG = new DoubleSetting("E Chests G", this, 1.0, 0.0, 1.0);
        this.eChestsB = new DoubleSetting("E Chests B", this, 0.0, 0.0, 1.0);
        this.eChestsA = new DoubleSetting("E Chests A", this, 1.0, 0.0, 1.0);
    }
    
    public void onRender() {
        if (!this.getEnabled()) {
            return;
        }
        if (this.noNether.getValInt() && ChestESP.mc.player.dimension == -1) {
            return;
        }
        RenderUtils.a();
        for (final TileEntity tileEntity : ChestESP.mc.world.loadedTileEntityList) {
            final boolean b = this.chests.getValInt() && tileEntity instanceof TileEntityChest;
            final boolean b2 = this.beds.getValInt() && tileEntity instanceof TileEntityBed;
            final boolean b3 = this.enderChests.getValInt() && tileEntity instanceof TileEntityEnderChest;
            if (!b && !b2 && !b3) {
                continue;
            }
            float n = 0.0f;
            float n2 = 0.0f;
            float n3 = 0.0f;
            float n4 = 0.0f;
            if (b) {
                n = this.chestsR.getValInt().floatValue();
                n2 = this.chestsG.getValInt().floatValue();
                n3 = this.chestsB.getValInt().floatValue();
                n4 = this.chestsA.getValInt().floatValue();
            }
            if (b2) {
                n = this.bedsR.getValInt().floatValue();
                n2 = this.bedsG.getValInt().floatValue();
                n3 = this.bedsB.getValInt().floatValue();
                n4 = this.bedsA.getValInt().floatValue();
            }
            if (b3) {
                n = this.eChestsR.getValInt().floatValue();
                n2 = this.eChestsG.getValInt().floatValue();
                n3 = this.eChestsB.getValInt().floatValue();
                n4 = this.eChestsA.getValInt().floatValue();
            }
            AxisAlignedBB axisAlignedBB = RenderUtils.getBoundingBox(tileEntity.getPos());
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                if (tileEntityChest.adjacentChestXPos != null) {
                    continue;
                }
                if (tileEntityChest.adjacentChestZPos != null) {
                    continue;
                }
                if (tileEntityChest.adjacentChestXNeg != null) {
                    axisAlignedBB = axisAlignedBB.union(RenderUtils.getBoundingBox(tileEntityChest.adjacentChestXNeg.getPos()));
                }
                else if (tileEntityChest.adjacentChestZNeg != null) {
                    axisAlignedBB = axisAlignedBB.union(RenderUtils.getBoundingBox(tileEntityChest.adjacentChestZNeg.getPos()));
                }
            }
            GL11.glColor4f(n, n2, n3, n4);
            if (this.chams.getValInt()) {
                RenderUtils.b(axisAlignedBB);
            }
            if (this.outlines.getValInt()) {
                RenderUtils.drawOutlinedBox(axisAlignedBB);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RenderUtils.glEnd();
    }
}
