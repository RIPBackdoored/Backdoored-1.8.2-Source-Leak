package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.event.entity.player.*;
import com.backdoored.utils.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.items.*;
import a.a.e.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraftforge.registries.*;
import a.a.g.b.b.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

@b.a(name = "Shulker Preview", description = "Preview Shulkers via tooltip", category = CategoriesInit.CLIENT)
public class ShulkerPreview extends BaseHack
{
    public static String[] kd;
    private static final ResourceLocation SHULKER_ICON;
    private static List<ResourceLocation> shulkerBoxes;
    private static final int[][] TARGET_RATIOS;
    private static final int CORNER = 5;
    private static final int BUFFER = 1;
    private static final int EDGE = 18;
    public static final /* synthetic */ boolean assertionsDisabled;
    
    public ShulkerPreview() {
        super();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
        if (isShulkerBox(event.getItemStack(), ShulkerPreview.shulkerBoxes) && event.getItemStack().hasTagCompound()) {
            NBTTagCompound nbtTagCompound = ItemNBTUtil.getCompound(event.getItemStack(), "BlockEntityTag", true);
            if (nbtTagCompound != null) {
                if (!nbtTagCompound.hasKey("id", 8)) {
                    nbtTagCompound = nbtTagCompound.copy();
                    nbtTagCompound.setString("id", "minecraft:shulker_box");
                }
                final TileEntity create = TileEntity.create((World)ShulkerPreview.mc.world, nbtTagCompound);
                if (create != null && create.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final List toolTip = event.getToolTip();
                    final ArrayList list = new ArrayList<Object>(toolTip);
                    for (int i = 1; i < list.size(); ++i) {
                        final String s = list.get(i);
                        if (!s.startsWith("§") || s.startsWith("§o")) {
                            toolTip.remove(s);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void renderTooltip(final RenderTooltipEvent.PostText event) {
        if (!this.getEnabled()) {
            return;
        }
        if (isShulkerBox(event.getStack(), ShulkerPreview.shulkerBoxes) && event.getStack().hasTagCompound()) {
            NBTTagCompound nbtTagCompound = ItemNBTUtil.getCompound(event.getStack(), "BlockEntityTag", true);
            if (nbtTagCompound != null) {
                if (!nbtTagCompound.hasKey("id", 8)) {
                    nbtTagCompound = nbtTagCompound.copy();
                    nbtTagCompound.setString("id", "minecraft:shulker_box");
                }
                final TileEntity create = TileEntity.create((World)ShulkerPreview.mc.world, nbtTagCompound);
                if (create != null && create.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final ItemStack stack = event.getStack();
                    int n = event.getX() - 5;
                    int n2 = event.getY() - 70;
                    final IItemHandler itemHandler = (IItemHandler)create.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
                    if (!ShulkerPreview.assertionsDisabled && itemHandler == null) {
                        throw new AssertionError();
                    }
                    final int slots = itemHandler.getSlots();
                    int[] array = { Math.min(slots, 9), Math.max(slots / 9, 1) };
                    for (final int[] array2 : ShulkerPreview.TARGET_RATIOS) {
                        if (array2[0] * array2[1] == slots) {
                            array = array2;
                            break;
                        }
                    }
                    final int n3 = 10 + 18 * array[0];
                    if (n2 < 0) {
                        n2 = event.getY() + event.getLines().size() * 10 + 5;
                    }
                    final ScaledResolution scaledResolution = new ScaledResolution(ShulkerPreview.mc);
                    final int n4 = n + n3;
                    if (n4 > scaledResolution.getScaledWidth()) {
                        n -= n4 - scaledResolution.getScaledWidth();
                    }
                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.color(1.0f, 1.0f, 1.0f);
                    GlStateManager.translate(0.0f, 0.0f, 700.0f);
                    ShulkerPreview.mc.getTextureManager().bindTexture(ShulkerPreview.SHULKER_ICON);
                    RenderHelper.disableStandardItemLighting();
                    int n5 = -1;
                    if (((ItemBlock)stack.getItem()).getBlock() instanceof BlockShulkerBox) {
                        n5 = ItemDye.DYE_COLORS[((BlockShulkerBox)((ItemBlock)stack.getItem()).getBlock()).getColor().getDyeDamage()];
                    }
                    renderTooltipBackground(n, n2, array[0], array[1], n5);
                    final RenderItem renderItem = ShulkerPreview.mc.getRenderItem();
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.enableDepth();
                    for (int j = 0; j < slots; ++j) {
                        final ItemStack stackInSlot = itemHandler.getStackInSlot(j);
                        final int n6 = n + 6 + j % 9 * 18;
                        final int n7 = n2 + 6 + j / 9 * 18;
                        if (!stackInSlot.isEmpty()) {
                            renderItem.renderItemAndEffectIntoGUI(stackInSlot, n6, n7);
                            renderItem.renderItemOverlays(c.fontRenderer, stackInSlot, n6, n7);
                        }
                    }
                    GlStateManager.disableDepth();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    private static void renderTooltipBackground(final int n, final int n2, final int n3, final int n4, final int n5) {
        ShulkerPreview.mc.getTextureManager().bindTexture(ShulkerPreview.SHULKER_ICON);
        GlStateManager.color(((n5 & 0xFF0000) >> 16) / 255.0f, ((n5 & 0xFF00) >> 8) / 255.0f, (n5 & 0xFF) / 255.0f);
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2 + 5 + 18 * n4, 25.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2, 25.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(n, n2 + 5 + 18 * n4, 0.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        for (int i = 0; i < n4; ++i) {
            Gui.drawModalRectWithCustomSizedTexture(n, n2 + 5 + 18 * i, 0.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * n3, n2 + 5 + 18 * i, 25.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            for (int j = 0; j < n3; ++j) {
                if (i == 0) {
                    Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2, 6.0f, 0.0f, 18, 5, 256.0f, 256.0f);
                    Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2 + 5 + 18 * n4, 6.0f, 25.0f, 18, 5, 256.0f, 256.0f);
                }
                Gui.drawModalRectWithCustomSizedTexture(n + 5 + 18 * j, n2 + 5 + 18 * i, 6.0f, 6.0f, 18, 18, 256.0f, 256.0f);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    private static boolean isShulkerBox(final ItemStack stack, final List<ResourceLocation> boxes) {
        return !stack.isEmpty() && isShulkerBox(stack.getItem().getRegistryName(), boxes);
    }
    
    private static boolean isShulkerBox(final ResourceLocation loc, final List<ResourceLocation> boxes) {
        return loc != null && boxes.contains(loc);
    }
    
    private static /* synthetic */ String[] a(final int n) {
        return new String[n];
    }
    
    static {
        assertionsDisabled = !ShulkerPreview.class.desiredAssertionStatus();
        ShulkerPreview.kd = (String[])ImmutableSet.of((Object)Blocks.WHITE_SHULKER_BOX, (Object)Blocks.ORANGE_SHULKER_BOX, (Object)Blocks.MAGENTA_SHULKER_BOX, (Object)Blocks.LIGHT_BLUE_SHULKER_BOX, (Object)Blocks.YELLOW_SHULKER_BOX, (Object)Blocks.LIME_SHULKER_BOX, (Object[])new Block[] { Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX }).stream().map(IForgeRegistryEntry.Impl::getRegistryName).map(Objects::toString).toArray(p::a);
        SHULKER_ICON = new ResourceLocation("backdoored", "textures/inv_slot.png");
        ShulkerPreview.shulkerBoxes = Arrays.stream(ShulkerPreview.kd).map((Function<? super String, ?>)ResourceLocation::new).collect((Collector<? super Object, ?, List<ResourceLocation>>)Collectors.toList());
        TARGET_RATIOS = new int[][] { { 1, 1 }, { 9, 3 }, { 9, 5 }, { 9, 6 }, { 9, 8 }, { 9, 9 }, { 12, 9 } };
    }
}
