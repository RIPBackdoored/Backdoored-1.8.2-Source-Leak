package com.backdoored.utils;

import a.a.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import a.a.d.b.d.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Utils implements e
{
    public Utils() {
        super();
    }
    
    public static void printMessage(final String s) {
        a(s, true);
    }
    
    public static void a(final String s, final boolean b) {
        a(s, "white", b);
    }
    
    public static void printMessage(final String s, final String s2) {
        a(s, s2, true);
    }
    
    public static void a(final String s, final String s2, final boolean b) {
        try {
            a(Objects.requireNonNull(ITextComponent.Serializer.jsonToComponent("{\"text\":\"" + s + "\",\"color\":\"" + s2 + "\"}")), b);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void a(final ITextComponent textComponent) {
        a(textComponent, true);
    }
    
    public static void a(final ITextComponent textComponent, final boolean b) {
        try {
            ITextComponent appendSibling;
            if (b) {
                appendSibling = new TextComponentString("§a[§cBD§a]§r ").appendSibling(textComponent);
            }
            else {
                appendSibling = textComponent;
            }
            Utils.mc.ingameGUI.addChatMessage(ChatType.SYSTEM, appendSibling);
            MinecraftForge.EVENT_BUS.post((Event)new a(textComponent));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
