package a.a.k.d;

import net.minecraft.util.math.*;
import java.util.*;

public abstract class a
{
    public static final a bcs;
    private final float bct;
    private final float bcu;
    private final float bcv;
    
    public static a b(final float n, final float n2, final float n3) {
        return new c(n, n2, n3, null);
    }
    
    public static a a(final float n, final float n2) {
        return b(n, n2, 0.0f);
    }
    
    public static a a(final double n, final double n2, final double n3) {
        return b((float)a.a.k.d.b.a(n), (float)a.a.k.d.b.a(n2), (float)a.a.k.d.b.a(n3));
    }
    
    public static a a(final double n, final double n2) {
        return a(n, n2, 0.0);
    }
    
    public static a c(final float n, final float n2, final float n3) {
        return new b(n, n2, n3, null);
    }
    
    public static a b(final float n, final float n2) {
        return c(n, n2, 0.0f);
    }
    
    public static a b(final double n, final double n2, final double n3) {
        return c((float)a.a.k.d.b.a(n), (float)a.a.k.d.b.a(n2), (float)a.a.k.d.b.a(n3));
    }
    
    public static a b(final double n, final double n2) {
        return b(n, n2, 0.0);
    }
    
    public static a a(final a a) {
        return a.a(a.e(), a.f(), a.g());
    }
    
    private a(final float bct, final float bcu, final float bcv) {
        super();
        this.bct = bct;
        this.bcu = bcu;
        this.bcv = bcv;
    }
    
    public float e() {
        return this.bct;
    }
    
    public float f() {
        return this.bcu;
    }
    
    public float g() {
        return this.bcv;
    }
    
    public a a(final float n) {
        return this.a(n, this.f(), this.g());
    }
    
    public a b(final float n) {
        return this.a(this.e(), n, this.g());
    }
    
    public a c(final float n) {
        return this.a(this.e(), this.f(), n);
    }
    
    public abstract boolean a();
    
    public boolean h() {
        return !this.a();
    }
    
    public a b(final a a) {
        return this.a(this.e() + a.d(this).e(), this.f() + a.d(this).f(), this.g() + a.d(this).g());
    }
    
    public a d(final float n, final float n2, final float n3) {
        return this.b(this.a(n, n2, n3));
    }
    
    public a c(final float n, final float n2) {
        return this.d(n, n2, 0.0f);
    }
    
    public a c(final a a) {
        return this.b(a.d(-1.0f));
    }
    
    public a e(final float n, final float n2, final float n3) {
        return this.d(-n, -n2, -n3);
    }
    
    public a d(final float n, final float n2) {
        return this.e(n, n2, 0.0f);
    }
    
    public a d(final float n) {
        return this.a(this.e() * n, this.f() * n, this.g() * n);
    }
    
    public abstract a b();
    
    public double[] i() {
        final double sin = Math.sin(this.c().e());
        final double cos = Math.cos(this.c().e());
        return new double[] { cos * Math.cos(this.c().f()), sin, cos * Math.sin(this.c().f()) };
    }
    
    public Vec3d j() {
        final float cos = MathHelper.cos(-this.d().f() * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-this.d().f() * 0.017453292f - 3.1415927f);
        final float n = -MathHelper.cos(-this.d().e() * 0.017453292f);
        return new Vec3d((double)(sin * n), (double)MathHelper.sin(-this.d().e() * 0.017453292f), (double)(cos * n));
    }
    
    public float[] k() {
        return new float[] { this.e(), this.f(), this.g() };
    }
    
    public abstract a c();
    
    public abstract a d();
    
    protected a d(final a a) {
        return a.a() ? this.d() : this.c();
    }
    
    protected abstract a a(final float p0, final float p1, final float p2);
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof a && a.a.k.d.b.a(this, (a)o));
    }
    
    @Override
    public int hashCode() {
        final a d = this.b().d();
        return Objects.hash(d.e(), d.f(), d.g());
    }
    
    @Override
    public String toString() {
        return String.format("(%.15f, %.15f, %.15f)[%s]", this.e(), this.f(), this.g(), this.h() ? "rad" : "deg");
    }
    
    public a(final float n, final float n2, final float n3, final a$a object) {
        this(n, n2, n3);
    }
    
    static {
        bcs = c(0.0f, 0.0f, 0.0f);
    }
    
    static class c extends a
    {
        private b bcr;
        
        private c(final float n, final float n2, final float n3) {
            super(n, n2, n3, null);
            this.bcr = null;
        }
        
        @Override
        public boolean a() {
            return false;
        }
        
        @Override
        public a b() {
            return this.a(a.a.k.d.b.a(this.e()), a.a.k.d.b.a(this.f()), a.a.k.d.b.a(this.g()));
        }
        
        @Override
        public a c() {
            return this;
        }
        
        @Override
        public a d() {
            return (this.bcr == null) ? (this.bcr = (b)a.b(Math.toDegrees(this.e()), Math.toDegrees(this.f()), Math.toDegrees(this.g()))) : this.bcr;
        }
        
        @Override
        protected a a(final float n, final float n2, final float n3) {
            return new c(n, n2, n3);
        }
        
        public c(final float n, final float n2, final float n3, final a$a object) {
            this(n, n2, n3);
        }
    }
    
    static class b extends a
    {
        private c bcq;
        
        private b(final float n, final float n2, final float n3) {
            super(n, n2, n3, null);
            this.bcq = null;
        }
        
        @Override
        public boolean a() {
            return true;
        }
        
        @Override
        public a b() {
            return this.a(a.a.k.d.b.b(this.e()), a.a.k.d.b.b(this.f()), a.a.k.d.b.b(this.g()));
        }
        
        @Override
        public a c() {
            return (this.bcq == null) ? (this.bcq = (c)a.a(Math.toRadians(this.e()), Math.toRadians(this.f()), Math.toRadians(this.g()))) : this.bcq;
        }
        
        @Override
        public a d() {
            return this;
        }
        
        @Override
        protected a a(final float n, final float n2, final float n3) {
            return new b(n, n2, n3);
        }
        
        public b(final float n, final float n2, final float n3, final a$a object) {
            this(n, n2, n3);
        }
    }
}
