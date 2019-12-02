package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import com.backdoored.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;

@b.a(name = "Debug Crosshair", description = "Show f3 crosshair", category = CategoriesInit.CLIENT)
public class DebugCrosshair extends BaseHack
{
    public DebugCrosshair() {
        super();
    }
    
    @SubscribeEvent
    public void stopRegularCrosshair(final RenderGameOverlayEvent event) {
        if (this.getEnabled() && event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            event.setCanceled(true);
            renderCrosshair(event.getPartialTicks(), new ScaledResolution(DebugCrosshair.mc).getScaledWidth(), new ScaledResolution(DebugCrosshair.mc).getScaledHeight(), (float)ObfuscationReflectionHelper.getPrivateValue((Class)Gui.class, (Object)Globals.mc.ingameGUI, new String[] { "zLevel", "field_73735_i" }));
        }
    }
    
    private static void renderCrosshair(final float n, final int n2, final int n3, final float n4) {
        final GameSettings gameSettings = DebugCrosshair.mc.gameSettings;
        if (gameSettings.thirdPersonView == 0) {
            if (DebugCrosshair.mc.playerController.isSpectator() && DebugCrosshair.mc.pointedEntity == null) {
                final RayTraceResult objectMouseOver = DebugCrosshair.mc.objectMouseOver;
                if (objectMouseOver == null || objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
                    return;
                }
                final BlockPos blockPos = objectMouseOver.getBlockPos();
                final IBlockState blockState = DebugCrosshair.mc.world.getBlockState(blockPos);
                if (!blockState.getBlock().hasTileEntity(blockState) || !(DebugCrosshair.mc.world.getTileEntity(blockPos) instanceof IInventory)) {
                    return;
                }
            }
            if (!gameSettings.hideGUI) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(n2 / 2), (float)(n3 / 2), n4);
                final Entity renderViewEntity = DebugCrosshair.mc.getRenderViewEntity();
                if (renderViewEntity != null) {
                    GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, -1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n, 0.0f, 1.0f, 0.0f);
                    GlStateManager.scale(-1.0f, -1.0f, -1.0f);
                    OpenGlHelper.renderDirections(10);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}
