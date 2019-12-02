package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public class BooleanSetting extends Setting<Boolean>
{
    public BooleanSetting(final String s, final BaseHack baseHack, final boolean b) {
        super(s, baseHack, b.yc, (Object)b);
    }
    
    @Override
    public Class<?> j() {
        return Boolean.class;
    }
}
