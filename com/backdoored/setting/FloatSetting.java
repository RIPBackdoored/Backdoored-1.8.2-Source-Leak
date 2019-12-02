package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public class FloatSetting extends NumberSetting<Float>
{
    public FloatSetting(final String s, final BaseHack baseHack, final float n, final float n2, final float n3) {
        super(s, baseHack, b.yf, (Number)n, (Number)n2, (Number)n3);
    }
    
    @Override
    public Class<?> j() {
        return Float.class;
    }
}
