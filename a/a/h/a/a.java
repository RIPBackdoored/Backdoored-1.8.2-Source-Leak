package a.a.h.a;

import a.a.*;
import a.a.k.c.*;

public abstract class a implements e
{
    private String yq;
    private boolean yr;
    private c ys;
    private c yt;
    
    public a() {
        super();
        this.yr = true;
        this.ys = new c(0, 0);
        this.yt = new c(0, 0);
    }
    
    public a(final int n, final int n2, final int n3, final int n4) {
        super();
        this.yr = true;
        this.ys = new c(0, 0);
        this.yt = new c(0, 0);
        this.ys = new c(n, n2);
        this.yt = new c(n3, n4);
    }
    
    public boolean a(final int n, final int n2) {
        return n >= this.d().bco && n2 >= this.d().bcp && n <= this.d().bco + this.e().bco && n2 <= this.d().bcp + this.e().bcp;
    }
    
    public abstract void b(final int p0, final int p1, final float p2);
    
    public abstract void d(final int p0, final int p1, final int p2);
    
    public abstract void a(final int p0, final int p1, final int p2);
    
    public abstract void b(final int p0, final int p1, final int p2);
    
    public String b() {
        return this.yq;
    }
    
    public void a(final String yq) {
        this.yq = yq;
    }
    
    public boolean c() {
        return this.yr;
    }
    
    public void a(final boolean yr) {
        this.yr = yr;
    }
    
    public c d() {
        return this.ys;
    }
    
    public void a(final c ys) {
        this.ys = ys;
    }
    
    public c e() {
        return this.yt;
    }
    
    public void b(final c yt) {
        this.yt = yt;
    }
    
    @Override
    public String toString() {
        return this.b();
    }
}
