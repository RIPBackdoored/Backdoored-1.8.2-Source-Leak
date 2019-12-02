package com.backdoored.utils;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

public final class ItemNBTUtil
{
    public ItemNBTUtil() {
        super();
    }
    
    public static boolean a(final ItemStack itemStack) {
        return itemStack.hasTagCompound();
    }
    
    public static void b(final ItemStack itemStack) {
        if (!a(itemStack)) {
            a(itemStack, new NBTTagCompound());
        }
    }
    
    public static void a(final ItemStack itemStack, final NBTTagCompound tagCompound) {
        itemStack.setTagCompound(tagCompound);
    }
    
    public static NBTTagCompound c(final ItemStack itemStack) {
        b(itemStack);
        return itemStack.getTagCompound();
    }
    
    public static void a(final ItemStack itemStack, final String s, final boolean b) {
        c(itemStack).setBoolean(s, b);
    }
    
    public static void a(final ItemStack itemStack, final String s, final byte b) {
        c(itemStack).setByte(s, b);
    }
    
    public static void a(final ItemStack itemStack, final String s, final short n) {
        c(itemStack).setShort(s, n);
    }
    
    public static void a(final ItemStack itemStack, final String s, final int n) {
        c(itemStack).setInteger(s, n);
    }
    
    public static void a(final ItemStack itemStack, final String s, final long n) {
        c(itemStack).setLong(s, n);
    }
    
    public static void a(final ItemStack itemStack, final String s, final float n) {
        c(itemStack).setFloat(s, n);
    }
    
    public static void a(final ItemStack itemStack, final String s, final double n) {
        c(itemStack).setDouble(s, n);
    }
    
    public static void a(final ItemStack itemStack, final String s, final NBTTagCompound nbtTagCompound) {
        if (!s.equalsIgnoreCase("ench")) {
            c(itemStack).setTag(s, (NBTBase)nbtTagCompound);
        }
    }
    
    public static void a(final ItemStack itemStack, final String s, final String s2) {
        c(itemStack).setString(s, s2);
    }
    
    public static void a(final ItemStack itemStack, final String s, final NBTTagList list) {
        c(itemStack).setTag(s, (NBTBase)list);
    }
    
    public static boolean a(final ItemStack itemStack, final String s) {
        return !itemStack.isEmpty() && a(itemStack) && c(itemStack).hasKey(s);
    }
    
    @Deprecated
    public static boolean b(final ItemStack itemStack, final String s) {
        return a(itemStack, s);
    }
    
    public static boolean b(final ItemStack itemStack, final String s, final boolean b) {
        return a(itemStack, s) ? c(itemStack).getBoolean(s) : b;
    }
    
    public static byte b(final ItemStack itemStack, final String s, final byte b) {
        return a(itemStack, s) ? c(itemStack).getByte(s) : b;
    }
    
    public static short b(final ItemStack itemStack, final String s, final short n) {
        return a(itemStack, s) ? c(itemStack).getShort(s) : n;
    }
    
    public static int b(final ItemStack itemStack, final String s, final int n) {
        return a(itemStack, s) ? c(itemStack).getInteger(s) : n;
    }
    
    public static long b(final ItemStack itemStack, final String s, final long n) {
        return a(itemStack, s) ? c(itemStack).getLong(s) : n;
    }
    
    public static float b(final ItemStack itemStack, final String s, final float n) {
        return a(itemStack, s) ? c(itemStack).getFloat(s) : n;
    }
    
    public static double b(final ItemStack itemStack, final String s, final double n) {
        return a(itemStack, s) ? c(itemStack).getDouble(s) : n;
    }
    
    public static NBTTagCompound getCompound(final ItemStack itemStack, final String s, final boolean b) {
        return a(itemStack, s) ? c(itemStack).getCompoundTag(s) : (b ? null : new NBTTagCompound());
    }
    
    public static String b(final ItemStack itemStack, final String s, final String s2) {
        return a(itemStack, s) ? c(itemStack).getString(s) : s2;
    }
    
    public static NBTTagList a(final ItemStack itemStack, final String s, final int n, final boolean b) {
        return a(itemStack, s) ? c(itemStack).getTagList(s, n) : (b ? null : new NBTTagList());
    }
}
