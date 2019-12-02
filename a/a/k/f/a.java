package a.a.k.f;

import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.util.glu.*;

public final class a
{
    private static a bds;
    private final FloatBuffer bdt;
    private IntBuffer bdu;
    private FloatBuffer bdv;
    private FloatBuffer bdw;
    private d bdx;
    private d[] bdy;
    private d[] bdz;
    private d bea;
    private double beb;
    private double bec;
    private double bed;
    private double bee;
    private double bef;
    private double beg;
    private double beh;
    private double bei;
    private b bej;
    private b bek;
    private b bel;
    private b bem;
    private float ben;
    private float beo;
    private d bep;
    
    private a() {
        super();
        this.bdt = BufferUtils.createFloatBuffer(3);
    }
    
    public static a a() {
        if (a.bds == null) {
            a.bds = new a();
        }
        return a.bds;
    }
    
    public void a(final IntBuffer bdu, final FloatBuffer bdv, final FloatBuffer bdw, final double bed, final double bee) {
        this.bdu = bdu;
        this.bdv = bdv;
        this.bdw = bdw;
        this.bed = bed;
        this.bee = bee;
        final float ben = (float)Math.toDegrees(Math.atan(1.0 / this.bdw.get(5)) * 2.0);
        this.ben = ben;
        this.beb = this.bdu.get(2);
        this.bec = this.bdu.get(3);
        this.beo = (float)Math.toDegrees(2.0 * Math.atan(this.beb / this.bec * Math.tan(Math.toRadians(this.ben) / 2.0)));
        final d d = new d(this.bdv.get(12), this.bdv.get(13), this.bdv.get(14));
        final d d2 = new d(this.bdv.get(0), this.bdv.get(1), this.bdv.get(2));
        final d d3 = new d(this.bdv.get(4), this.bdv.get(5), this.bdv.get(6));
        final d d4 = new d(this.bdv.get(8), this.bdv.get(9), this.bdv.get(10));
        final d d5 = new d(0.0, 1.0, 0.0);
        final d d6 = new d(1.0, 0.0, 0.0);
        final d d7 = new d(0.0, 0.0, 1.0);
        double n = Math.toDegrees(Math.atan2(d6.d(d2).b(), d6.c(d2))) + 180.0;
        if (d4.bdp < 0.0) {
            n = 360.0 - n;
        }
        double degrees;
        if ((-d4.bdq > 0.0 && n >= 90.0 && n < 270.0) || (d4.bdq > 0.0 && (n < 90.0 || n >= 270.0))) {
            degrees = Math.toDegrees(Math.atan2(d5.d(d3).b(), d5.c(d3)));
        }
        else {
            degrees = -Math.toDegrees(Math.atan2(d5.d(d3).b(), d5.c(d3)));
        }
        this.bep = this.a(n, degrees);
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.load(this.bdv.asReadOnlyBuffer());
        matrix4f.invert();
        this.bdx = new d(matrix4f.m30, matrix4f.m31, matrix4f.m32);
        this.bdy = this.a(this.bdx.bdp, this.bdx.bdq, this.bdx.bdr, n, degrees, ben, 1.0, this.beb / this.bec);
        this.bdz = this.a(this.bdx.bdp, this.bdx.bdq, this.bdx.bdr, n - 180.0, -degrees, ben, 1.0, this.beb / this.bec);
        this.bea = this.a(n, degrees).a();
        this.bef = Math.toDegrees(Math.acos(this.bec * bee / Math.sqrt(this.beb * bed * this.beb * bed + this.bec * bee * this.bec * bee)));
        this.beg = 360.0 - this.bef;
        this.beh = this.beg - 180.0;
        this.bei = this.bef + 180.0;
        this.bem = new b(this.beb * this.bed, 0.0, 0.0, 0.0, 1.0, 0.0);
        this.bej = new b(0.0, 0.0, 0.0, 1.0, 0.0, 0.0);
        this.bel = new b(0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        this.bek = new b(0.0, this.bec * this.bee, 0.0, 1.0, 0.0, 0.0);
    }
    
    public c a(final double n, final double n2, final double n3, final a a, final boolean b) {
        if (this.bdu != null && this.bdv != null && this.bdw != null) {
            final d d = new d(n, n2, n3);
            final boolean[] a2 = this.a(this.bdy, this.bdx, n, n2, n3);
            if (a2[0] || a2[1] || a2[2] || a2[3]) {
                final boolean b2 = d.b(this.bdx).c(this.bea) <= 0.0;
                final boolean[] a3 = this.a(this.bdz, this.bdx, n, n2, n3);
                final boolean b3 = a3[0] || a3[1] || a3[2] || a3[3];
                if ((b && !b3) || (b3 && a != a.bdf)) {
                    if ((b && !b3) || (a == a.bde && b3)) {
                        if (GLU.gluProject((float)n, (float)n2, (float)n3, this.bdv, this.bdw, this.bdu, this.bdt)) {
                            double n4;
                            double n5;
                            if (b2) {
                                n4 = this.beb * this.bed - this.bdt.get(0) * this.bed - this.beb * this.bed / 2.0;
                                n5 = this.bec * this.bee - (this.bec - this.bdt.get(1)) * this.bee - this.bec * this.bee / 2.0;
                            }
                            else {
                                n4 = this.bdt.get(0) * this.bed - this.beb * this.bed / 2.0;
                                n5 = (this.bec - this.bdt.get(1)) * this.bee - this.bec * this.bee / 2.0;
                            }
                            final d c = new d(n4, n5, 0.0).c();
                            final double bdp = c.bdp;
                            final b b4 = new b(this.beb * this.bed / 2.0, this.bec * this.bee / 2.0, 0.0, bdp, c.bdq, 0.0);
                            double degrees = Math.toDegrees(Math.acos(c.bdq / Math.sqrt(c.bdp * c.bdp + c.bdq * c.bdq)));
                            if (bdp < 0.0) {
                                degrees = 360.0 - degrees;
                            }
                            final d d2 = new d(0.0, 0.0, 0.0);
                            d d3;
                            if (degrees >= this.bef && degrees < this.beh) {
                                d3 = this.bem.a(b4);
                            }
                            else if (degrees >= this.beh && degrees < this.bei) {
                                d3 = this.bej.a(b4);
                            }
                            else if (degrees >= this.bei && degrees < this.beg) {
                                d3 = this.bel.a(b4);
                            }
                            else {
                                d3 = this.bek.a(b4);
                            }
                            return new c(d3.bdp, d3.bdq, b3 ? a.a.k.f.a.c.a.bdj : a.a.k.f.a.c.a.bdk);
                        }
                        return new c(0.0, 0.0, c.a.bdl);
                    }
                    else if (a == a.bdd && b3) {
                        if (GLU.gluProject((float)n, (float)n2, (float)n3, this.bdv, this.bdw, this.bdu, this.bdt)) {
                            double n6 = this.bdt.get(0) * this.bed;
                            double n7 = (this.bec - this.bdt.get(1)) * this.bee;
                            if (b2) {
                                n6 = this.beb * this.bed - n6;
                                n7 = this.bec * this.bee - n7;
                            }
                            if (n6 < 0.0) {
                                n6 = 0.0;
                            }
                            else if (n6 > this.beb * this.bed) {
                                n6 = this.beb * this.bed;
                            }
                            if (n7 < 0.0) {
                                n7 = 0.0;
                            }
                            else if (n7 > this.bec * this.bee) {
                                n7 = this.bec * this.bee;
                            }
                            return new c(n6, n7, b3 ? c.a.bdj : c.a.bdk);
                        }
                        return new c(0.0, 0.0, c.a.bdl);
                    }
                }
                else {
                    if (GLU.gluProject((float)n, (float)n2, (float)n3, this.bdv, this.bdw, this.bdu, this.bdt)) {
                        double n8 = this.bdt.get(0) * this.bed;
                        double n9 = (this.bec - this.bdt.get(1)) * this.bee;
                        if (b2) {
                            n8 = this.beb * this.bed - n8;
                            n9 = this.bec * this.bee - n9;
                        }
                        return new c(n8, n9, b3 ? c.a.bdj : c.a.bdk);
                    }
                    return new c(0.0, 0.0, c.a.bdl);
                }
            }
            else {
                if (GLU.gluProject((float)n, (float)n2, (float)n3, this.bdv, this.bdw, this.bdu, this.bdt)) {
                    return new c(this.bdt.get(0) * this.bed, (this.bec - this.bdt.get(1)) * this.bee, c.a.bdi);
                }
                return new c(0.0, 0.0, c.a.bdl);
            }
        }
        return new c(0.0, 0.0, c.a.bdl);
    }
    
    public boolean[] a(final d[] array, final d d, final double n, final double n2, final double n3) {
        final d d2 = new d(n, n2, n3);
        return new boolean[] { this.a(new d[] { d, array[3], array[0] }, d2), this.a(new d[] { d, array[0], array[1] }, d2), this.a(new d[] { d, array[1], array[2] }, d2), this.a(new d[] { d, array[2], array[3] }, d2) };
    }
    
    public boolean a(final d[] array, final d d) {
        final d d2 = new d(0.0, 0.0, 0.0);
        final d c = array[1].b(array[0]).d(array[2].b(array[0])).c();
        return c.c(d) + d2.b(c).c(array[2]) >= 0.0;
    }
    
    public d[] a(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8) {
        this.a(n4, n5).c();
        final double n9 = 2.0 * Math.tan(Math.toRadians(n6 / 2.0)) * n7;
        final double n10 = n9 * n8;
        final d c = this.a(n4, n5).c();
        final d c2 = this.a(n4, n5 - 90.0).c();
        final d c3 = this.a(n4 + 90.0, 0.0).c();
        final d a = c.a(new d(n, n2, n3));
        final d d = new d(a.bdp * n7, a.bdq * n7, a.bdr * n7);
        return new d[] { new d(d.bdp + c2.bdp * n9 / 2.0 - c3.bdp * n10 / 2.0, d.bdq + c2.bdq * n9 / 2.0 - c3.bdq * n10 / 2.0, d.bdr + c2.bdr * n9 / 2.0 - c3.bdr * n10 / 2.0), new d(d.bdp - c2.bdp * n9 / 2.0 - c3.bdp * n10 / 2.0, d.bdq - c2.bdq * n9 / 2.0 - c3.bdq * n10 / 2.0, d.bdr - c2.bdr * n9 / 2.0 - c3.bdr * n10 / 2.0), new d(d.bdp - c2.bdp * n9 / 2.0 + c3.bdp * n10 / 2.0, d.bdq - c2.bdq * n9 / 2.0 + c3.bdq * n10 / 2.0, d.bdr - c2.bdr * n9 / 2.0 + c3.bdr * n10 / 2.0), new d(d.bdp + c2.bdp * n9 / 2.0 + c3.bdp * n10 / 2.0, d.bdq + c2.bdq * n9 / 2.0 + c3.bdq * n10 / 2.0, d.bdr + c2.bdr * n9 / 2.0 + c3.bdr * n10 / 2.0) };
    }
    
    public d[] b() {
        return this.bdy;
    }
    
    public float c() {
        return this.beo;
    }
    
    public float d() {
        return this.ben;
    }
    
    public d e() {
        return this.bep;
    }
    
    public d a(final double n, final double n2) {
        final double cos = Math.cos(-n * 0.01745329238474369 - 3.141592653589793);
        final double sin = Math.sin(-n * 0.01745329238474369 - 3.141592653589793);
        final double n3 = -Math.cos(-n2 * 0.01745329238474369);
        return new d(sin * n3, Math.sin(-n2 * 0.01745329238474369), cos * n3);
    }
    
    public static class c
    {
        private final double bdm;
        private final double bdn;
        private final a bdo;
        
        public c(final double bdm, final double bdn, final a bdo) {
            super();
            this.bdm = bdm;
            this.bdn = bdn;
            this.bdo = bdo;
        }
        
        public double a() {
            return this.bdm;
        }
        
        public double b() {
            return this.bdn;
        }
        
        public a c() {
            return this.bdo;
        }
        
        public boolean a(final a a) {
            return this.bdo == a;
        }
        
        public enum a
        {
            bdi("INSIDE", 0), 
            bdj("OUTSIDE", 1), 
            bdk("INVERTED", 2), 
            bdl("FAIL", 3);
            
            private static final /* synthetic */ a[] $VALUES;
            
            public static a[] values() {
                return a.$VALUES.clone();
            }
            
            public static a valueOf(final String s) {
                return Enum.valueOf(a.class, s);
            }
            
            private a(final String s, final int n) {
            }
            
            static {
                $VALUES = new a[] { a.bdi, a.bdj, a.bdk, a.bdl };
            }
        }
    }
    
    public static class d
    {
        public double bdp;
        public double bdq;
        public double bdr;
        
        public d(final double bdp, final double bdq, final double bdr) {
            super();
            this.bdp = bdp;
            this.bdq = bdq;
            this.bdr = bdr;
        }
        
        public d a(final d d) {
            return new d(this.bdp + d.bdp, this.bdq + d.bdq, this.bdr + d.bdr);
        }
        
        public d a(final double n, final double n2, final double n3) {
            return new d(this.bdp + n, this.bdq + n2, this.bdr + n3);
        }
        
        public d b(final d d) {
            return new d(this.bdp - d.bdp, this.bdq - d.bdq, this.bdr - d.bdr);
        }
        
        public d b(final double n, final double n2, final double n3) {
            return new d(this.bdp - n, this.bdq - n2, this.bdr - n3);
        }
        
        public d a() {
            final double sqrt = Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
            return new d(this.bdp / sqrt, this.bdq / sqrt, this.bdr / sqrt);
        }
        
        public double c(final d d) {
            return this.bdp * d.bdp + this.bdq * d.bdq + this.bdr * d.bdr;
        }
        
        public d d(final d d) {
            return new d(this.bdq * d.bdr - this.bdr * d.bdq, this.bdr * d.bdp - this.bdp * d.bdr, this.bdp * d.bdq - this.bdq * d.bdp);
        }
        
        public d a(final double n) {
            return new d(this.bdp * n, this.bdq * n, this.bdr * n);
        }
        
        public d b(final double n) {
            return new d(this.bdp / n, this.bdq / n, this.bdr / n);
        }
        
        public double b() {
            return Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
        }
        
        public d e(final d d) {
            this.bdp += d.bdp;
            this.bdq += d.bdq;
            this.bdr += d.bdr;
            return this;
        }
        
        public d c(final double n, final double n2, final double n3) {
            this.bdp += n;
            this.bdq += n2;
            this.bdr += n3;
            return this;
        }
        
        public d f(final d d) {
            this.bdp -= d.bdp;
            this.bdq -= d.bdq;
            this.bdr -= d.bdr;
            return this;
        }
        
        public d d(final double n, final double n2, final double n3) {
            this.bdp -= n;
            this.bdq -= n2;
            this.bdr -= n3;
            return this;
        }
        
        public d c() {
            final double sqrt = Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
            this.bdp /= sqrt;
            this.bdq /= sqrt;
            this.bdr /= sqrt;
            return this;
        }
        
        public d g(final d d) {
            this.bdp = this.bdq * d.bdr - this.bdr * d.bdq;
            this.bdq = this.bdr * d.bdp - this.bdp * d.bdr;
            this.bdr = this.bdp * d.bdq - this.bdq * d.bdp;
            return this;
        }
        
        public d c(final double n) {
            this.bdp *= n;
            this.bdq *= n;
            this.bdr *= n;
            return this;
        }
        
        public d d(final double n) {
            this.bdp /= n;
            this.bdq /= n;
            this.bdr /= n;
            return this;
        }
        
        @Override
        public String toString() {
            return "(X: " + this.bdp + " Y: " + this.bdq + " Z: " + this.bdr + ")";
        }
    }
    
    public static class b
    {
        public d bdg;
        public d bdh;
        
        public b(final double bdp, final double bdq, final double bdr, final double bdp2, final double bdq2, final double bdr2) {
            super();
            this.bdg = new d(0.0, 0.0, 0.0);
            this.bdh = new d(0.0, 0.0, 0.0);
            this.bdg.bdp = bdp;
            this.bdg.bdq = bdq;
            this.bdg.bdr = bdr;
            this.bdh.bdp = bdp2;
            this.bdh.bdq = bdq2;
            this.bdh.bdr = bdr2;
        }
        
        public d a(final b b) {
            final double bdp = this.bdg.bdp;
            final double bdp2 = this.bdh.bdp;
            final double bdp3 = b.bdg.bdp;
            final double bdp4 = b.bdh.bdp;
            final double bdq = this.bdg.bdq;
            final double bdq2 = this.bdh.bdq;
            final double bdq3 = b.bdg.bdq;
            final double bdq4 = b.bdh.bdq;
            final double n = -(bdp * bdq4 - bdp3 * bdq4 - bdp4 * (bdq - bdq3));
            final double n2 = bdp2 * bdq4 - bdp4 * bdq2;
            if (n2 == 0.0) {
                return this.b(b);
            }
            final double n3 = n / n2;
            final d d = new d(0.0, 0.0, 0.0);
            d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
            d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
            d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
            return d;
        }
        
        private d b(final b b) {
            final double bdp = this.bdg.bdp;
            final double bdp2 = this.bdh.bdp;
            final double bdp3 = b.bdg.bdp;
            final double bdp4 = b.bdh.bdp;
            final double bdr = this.bdg.bdr;
            final double bdr2 = this.bdh.bdr;
            final double bdr3 = b.bdg.bdr;
            final double bdr4 = b.bdh.bdr;
            final double n = -(bdp * bdr4 - bdp3 * bdr4 - bdp4 * (bdr - bdr3));
            final double n2 = bdp2 * bdr4 - bdp4 * bdr2;
            if (n2 == 0.0) {
                return this.c(b);
            }
            final double n3 = n / n2;
            final d d = new d(0.0, 0.0, 0.0);
            d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
            d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
            d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
            return d;
        }
        
        private d c(final b b) {
            final double bdq = this.bdg.bdq;
            final double bdq2 = this.bdh.bdq;
            final double bdq3 = b.bdg.bdq;
            final double bdq4 = b.bdh.bdq;
            final double bdr = this.bdg.bdr;
            final double bdr2 = this.bdh.bdr;
            final double bdr3 = b.bdg.bdr;
            final double bdr4 = b.bdh.bdr;
            final double n = -(bdq * bdr4 - bdq3 * bdr4 - bdq4 * (bdr - bdr3));
            final double n2 = bdq2 * bdr4 - bdq4 * bdr2;
            if (n2 == 0.0) {
                return null;
            }
            final double n3 = n / n2;
            final d d = new d(0.0, 0.0, 0.0);
            d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
            d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
            d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
            return d;
        }
        
        public d a(final d d, final d d2) {
            final d d3 = new d(this.bdg.bdp, this.bdg.bdq, this.bdg.bdr);
            d3.e(this.bdh.a(d.b(this.bdg).c(d2) / this.bdh.c(d2)));
            if (this.bdh.c(d2) == 0.0) {
                return null;
            }
            return d3;
        }
    }
    
    public enum a
    {
        bdd("ORTHOGONAL", 0), 
        bde("DIRECT", 1), 
        bdf("NONE", 2);
        
        private static final /* synthetic */ a[] $VALUES;
        
        public static a[] values() {
            return a.$VALUES.clone();
        }
        
        public static a valueOf(final String s) {
            return Enum.valueOf(a.class, s);
        }
        
        private a(final String s, final int n) {
        }
        
        static {
            $VALUES = new a[] { a.bdd, a.bde, a.bdf };
        }
    }
}
