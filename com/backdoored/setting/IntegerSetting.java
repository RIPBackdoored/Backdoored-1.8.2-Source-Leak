package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public class IntegerSetting extends NumberSetting<Integer>
{
    public IntegerSetting(final String s, final BaseHack baseHack, final int n, final int n2, final int n3) {
        super(s, baseHack, b.yd, (Number)n, (Number)n2, (Number)n3);
    }
    
    @Override
    public Class<?> j() {
        return Integer.class;
    }
}
