package com.backdoored.setting;

import a.a.g.b.*;
import com.backdoored.hacks.*;

public abstract class NumberSetting<T extends Number> extends Setting<T>
{
    private T yo;
    private T yp;
    
    public NumberSetting(final String s, final b b, final a.a.g.c.b b2, final T t, final T yo, final T yp) {
        super(s, (BaseHack)b, b2, (Object)t);
        this.yo = yo;
        this.yp = yp;
    }
    
    @Override
    public abstract Class<?> j();
    
    @Override
    public T i() {
        return this.yo;
    }
    
    @Override
    public T g() {
        return this.yp;
    }
}
