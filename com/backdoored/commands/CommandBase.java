package com.backdoored.commands;

import a.a.b.*;
import net.minecraft.client.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraftforge.common.*;
import com.backdoored.utils.*;

public abstract class CommandBase implements l
{
    public static ArrayList<CommandBase> z;
    protected List<String> ba;
    public Minecraft mc;
    
    private CommandBase() {
        super();
        this.mc = Minecraft.getMinecraft();
    }
    
    public CommandBase(final String s) {
        this(new String[] { s });
    }
    
    public CommandBase(final String... array) {
        this(Arrays.asList(array));
    }
    
    public CommandBase(final List<String> list) {
        super();
        this.mc = Minecraft.getMinecraft();
        this.ba = list.stream().map((Function<? super Object, ?>)String::toLowerCase).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        CommandBase.z.add(this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public abstract boolean a(final String[] p0);
    
    @Override
    public void a(final String[] array, final String s) {
        Utils.printMessage("Usage: " + this.a(), "yellow");
    }
    
    @Override
    public boolean a(final String[] array, final String[] array2, final String s) {
        if (array[0].equals("")) {
            this.a(array2, s);
            return false;
        }
        for (int i = 0; i <= array2.length; ++i) {
            if (array2[i].equals(array[0])) {
                return true;
            }
            if (!array2[i].equals(array[0]) && i == array2.length - 1) {
                this.a(array2, s);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public abstract String a();
    
    static {
        CommandBase.z = new ArrayList<CommandBase>();
    }
}
