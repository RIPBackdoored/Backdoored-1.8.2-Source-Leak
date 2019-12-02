package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public class DoubleSetting extends NumberSetting<Double>
{
    public DoubleSetting(final String s, final BaseHack baseHack, final double n, final double n2, final double n3) {
        super(s, baseHack, b.ye, (Number)n, (Number)n2, (Number)n3);
    }
    
    @Override
    public Class<?> j() {
        return Double.class;
    }
}
