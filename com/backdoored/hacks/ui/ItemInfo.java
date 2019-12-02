package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.client.util.*;
import com.backdoored.setting.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import net.minecraft.item.*;
import java.util.*;

@b.a(name = "Item info", description = "Show extra info about the item your holding", category = CategoriesInit.UI)
public class ItemInfo extends BaseHack
{
    private final Setting<ITooltipFlag.TooltipFlags> type;
    private final Setting<Integer> xf;
    private final Setting<Integer> xg;
    
    public ItemInfo() {
        super();
        this.type = new EnumSetting<ITooltipFlag.TooltipFlags>("Type", (BaseHack)this, (Enum)ITooltipFlag.TooltipFlags.ADVANCED);
        this.xf = new IntegerSetting("x", this, 0, 0, (int)Math.round(ItemInfo.mc.displayWidth * 1.2));
        this.xg = new IntegerSetting("y", this, 0, 0, (int)Math.round(ItemInfo.mc.displayHeight * 1.2));
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            final ItemStack currentItem = ItemInfo.mc.player.inventory.getCurrentItem();
            if (!currentItem.isEmpty()) {
                final List tooltip = currentItem.getTooltip((EntityPlayer)ItemInfo.mc.player, (ITooltipFlag)this.type.getValInt());
                if (currentItem.getTagCompound() != null) {
                    for (final String s : currentItem.getTagCompound().getKeySet()) {
                        tooltip.add(s + ":" + currentItem.getTagCompound().getTag(s).toString());
                    }
                }
                int n = 0;
                int n2 = 1;
                for (final String s2 : tooltip) {
                    if (s2.isEmpty()) {
                        continue;
                    }
                    ItemInfo.mc.fontRenderer.drawString(s2, (int)this.xf.getValInt(), this.xg.getValInt() + n, (n2 != 0) ? Color.WHITE.getRGB() : Color.LIGHT_GRAY.getRGB());
                    n2 = 0;
                    n += ItemInfo.mc.fontRenderer.FONT_HEIGHT + 2;
                }
            }
        }
    }
}
