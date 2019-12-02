package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.util.*;

@Mixin({ ItemStack.class })
public class MixinItemStack
{
    @Shadow
    int field_77991_e;
    int actualDamage;
    
    public MixinItemStack() {
        super();
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V" }, at = { @At("RETURN") })
    private void postInit(final Item a1, final int a2, final int a3, final NBTTagCompound a4, final CallbackInfo a5) {
        this.itemDamage = a3;
        this.actualDamage = a3;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/nbt/NBTTagCompound;)V" }, at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    private int max(final int a1, final int a2) {
        return this.actualDamage = a2;
    }
    
    @Redirect(method = { "getTooltip" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemDamage()I"))
    private int getItemDamage(final ItemStack a1) {
        return this.actualDamage;
    }
    
    @Redirect(method = { "getTooltip" }, at = @At(value = "INVOKE", target = "net/minecraft/item/ItemStack.isItemDamaged()Z"))
    private boolean isItemDamaged(final ItemStack a1) {
        return true;
    }
    
    @Redirect(method = { "getTooltip" }, at = @At(value = "INVOKE", target = "net/minecraft/client/util/ITooltipFlag.isAdvanced()Z", ordinal = 2))
    private boolean isAdvanced(final ITooltipFlag a1) {
        return true;
    }
}
