package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.*;

@b.a(name = "Map Tooltip", description = "Tooltips to preview maps", category = CategoriesInit.CLIENT)
public class MapTooltip extends BaseHack
{
    private static final ResourceLocation MAP;
    
    public MapTooltip() {
        super();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
    }
    
    @SubscribeEvent
    public void renderTooltip(final RenderTooltipEvent.PostText event) {
        if (!this.getEnabled()) {
            return;
        }
        if (!event.getStack().isEmpty() && event.getStack().getItem() instanceof ItemMap) {
            final MapData mapData = Items.FILLED_MAP.getMapData(event.getStack(), (World)MapTooltip.mc.world);
            if (mapData != null) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderHelper.disableStandardItemLighting();
                MapTooltip.mc.getTextureManager().bindTexture(MapTooltip.MAP);
                final Tessellator instance = Tessellator.getInstance();
                final BufferBuilder buffer = instance.getBuffer();
                final int n = 7;
                final float n2 = 135.0f;
                final float n3 = 0.5f;
                GlStateManager.translate((float)event.getX(), event.getY() - n2 * n3 - 5.0f, 0.0f);
                GlStateManager.scale(n3, n3, n3);
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos((double)(-n), (double)n2, 0.0).tex(0.0, 1.0).endVertex();
                buffer.pos((double)n2, (double)n2, 0.0).tex(1.0, 1.0).endVertex();
                buffer.pos((double)n2, (double)(-n), 0.0).tex(1.0, 0.0).endVertex();
                buffer.pos((double)(-n), (double)(-n), 0.0).tex(0.0, 0.0).endVertex();
                instance.draw();
                MapTooltip.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
    
    static {
        MAP = new ResourceLocation("textures/map/map_background.png");
    }
}
