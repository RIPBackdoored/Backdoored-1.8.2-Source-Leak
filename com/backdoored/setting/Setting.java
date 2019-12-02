package com.backdoored.setting;

import com.backdoored.hacks.*;
import a.a.g.c.*;

public abstract class Setting<T>
{
    private String yh;
    private BaseHack yi;
    private b yj;
    private T yk;
    private final T yl;
    
    public Setting(final String yh, final a.a.g.b.b yi, final b yj, final T t) {
        super();
        this.yh = yh;
        this.yi = (BaseHack)yi;
        this.yj = yj;
        this.yk = t;
        this.yl = t;
        yj.yg.add(this);
        d.ym.add(this);
    }
    
    public String a() {
        return this.yh;
    }
    
    public BaseHack b() {
        return this.yi;
    }
    
    public b c() {
        return this.yj;
    }
    
    public T getValInt() {
        return this.yk;
    }
    
    public void a(final T yk) {
        this.yk = yk;
    }
    
    public T e() {
        return this.yl;
    }
    
    public abstract Class<?> j();
    
    public Number i() {
        return null;
    }
    
    public Number g() {
        return null;
    }
    
    public Enum[] f() {
        return null;
    }
}
