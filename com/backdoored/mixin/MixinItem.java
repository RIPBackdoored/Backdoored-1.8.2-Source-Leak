package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.fml.common.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Item.class })
public class MixinItem
{
    public MixinItem() {
        super();
    }
    
    @Inject(method = { "Lnet/minecraft/item/Item;setDamage(Lnet/minecraft/item/ItemStack;I)V" }, at = { @At("RETURN") }, remap = false)
    private void setDamageWrap(final ItemStack a1, final int a2, final CallbackInfo a3) {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)ItemStack.class, (Object)a1, (Object)a2, new String[] { "itemDamage", "field_77991_e" });
        }
        catch (Exception ex) {}
    }
}
