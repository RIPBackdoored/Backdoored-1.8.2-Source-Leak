package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public class StringSetting extends Setting<String>
{
    public StringSetting(final String s, final BaseHack baseHack, final String s2) {
        super(s, baseHack, b.xz, (Object)s2);
    }
    
    @Override
    public Class<?> j() {
        return String.class;
    }
}
