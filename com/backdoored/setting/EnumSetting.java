package com.backdoored.setting;

import a.a.g.b.*;
import com.backdoored.hacks.*;
import java.util.*;

public class EnumSetting<T extends Enum> extends Setting<T>
{
    private Enum[] yn;
    
    public EnumSetting(final String s, final b b, final T t) {
        super(s, (BaseHack)b, a.a.g.c.b.ya, (Object)t);
        this.yn = (Enum[])t.getClass().getEnumConstants();
    }
    
    public void a(final String s) {
        try {
            for (final Enum enum1 : this.yn) {
                if (enum1.toString().equalsIgnoreCase(s)) {
                    this.a(Objects.requireNonNull(enum1));
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public Class<?> j() {
        return String.class;
    }
    
    @Override
    public Enum[] f() {
        return this.yn;
    }
}
