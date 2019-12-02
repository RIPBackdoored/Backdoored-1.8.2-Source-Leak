package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.block.material.*;
import com.backdoored.utils.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "BetterHighlightBox", description = "Better Highlight Box", category = CategoriesInit.CLIENT)
public class BetterHighlightBox extends BaseHack
{
    private Setting<Boolean> surround;
    private Setting<Float> width;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Double> alpha;
    
    public BetterHighlightBox() {
        super();
        this.surround = new BooleanSetting("Surround", this, true);
        this.width = new FloatSetting("Width", this, 5.0f, 0.0f, 10.0f);
        this.red = new IntegerSetting("Red", this, 0, 0, 255);
        this.green = new IntegerSetting("Green", this, 0, 0, 255);
        this.blue = new IntegerSetting("Blue", this, 0, 0, 255);
        this.alpha = new DoubleSetting("Alpha", this, 0.4, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void a(final DrawBlockHighlightEvent drawBlockHighlightEvent) {
        if (this.getEnabled()) {
            final float partialTicks = drawBlockHighlightEvent.getPartialTicks();
            final EntityPlayer player = drawBlockHighlightEvent.getPlayer();
            final RayTraceResult target = drawBlockHighlightEvent.getTarget();
            if (target.typeOfHit == RayTraceResult.Type.BLOCK) {
                final BlockPos blockPos = target.getBlockPos();
                final IBlockState blockState = BetterHighlightBox.mc.world.getBlockState(blockPos);
                if (blockState.getMaterial() != Material.AIR && BetterHighlightBox.mc.world.getWorldBorder().contains(blockPos)) {
                    if (this.surround.getValInt()) {
                        RenderUtils.a();
                        GL11.glColor4f((float)this.red.getValInt(), (float)this.green.getValInt(), (float)this.blue.getValInt(), this.alpha.getValInt().floatValue());
                        GL11.glLineWidth((float)this.width.getValInt());
                        RenderUtils.drawOutlinedBox(RenderUtils.getBoundingBox(blockPos));
                        RenderUtils.glEnd();
                    }
                    else {
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        GlStateManager.glLineWidth((float)this.width.getValInt());
                        GlStateManager.disableTexture2D();
                        GlStateManager.depthMask(false);
                        a.a.e.b.drawSelectionBoundingBox(blockState.getSelectedBoundingBox((World)BetterHighlightBox.mc.world, blockPos).grow(0.0020000000949949026).offset(-(player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks), -(player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks), -(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks)), (float)Math.min(Math.abs(this.red.getValInt() - 255), 244), (float)Math.min(Math.abs(this.green.getValInt() - 255), 244), (float)Math.min(Math.abs(this.blue.getValInt() - 255), 244), this.alpha.getValInt().floatValue());
                        GlStateManager.depthMask(true);
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                    }
                }
            }
            drawBlockHighlightEvent.setCanceled(true);
        }
    }
}
