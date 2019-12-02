package a.a.k.d;

import net.minecraft.util.math.*;

public class b
{
    public static final long bcw = 1000000000L;
    public static final double bcx = 1.0E-9;
    public static final double bcy = 6.283185307179586;
    public static final double bcz = 1.5707963267948966;
    public static final double bda = 0.7853981633974483;
    
    public b() {
        super();
    }
    
    public static double a(final double n, final long n2) {
        return Math.round(n * n2) / (double)n2;
    }
    
    public static double a(final double n) {
        return a(n, 1000000000L);
    }
    
    public static boolean a(final double n, final double n2, final double n3) {
        return Double.compare(n, n2) == 0 || Math.abs(n - n2) < n3;
    }
    
    public static boolean a(final double n, final double n2) {
        return a(n, n2, 1.0E-4);
    }
    
    public static boolean a(final a a, final a a2) {
        final a b = a.b();
        final a b2 = a2.d(b).b();
        return a(b.e(), b2.e()) && a(b.f(), b2.f()) && a(b.g(), b2.g());
    }
    
    public static double b(double n) {
        while (n > 3.141592653589793) {
            n -= 6.283185307179586;
        }
        while (n < -3.141592653589793) {
            n += 6.283185307179586;
        }
        return n;
    }
    
    public static float a(float n) {
        while (n > 3.141592653589793) {
            n -= (float)6.283185307179586;
        }
        while (n < -3.141592653589793) {
            n += (float)6.283185307179586;
        }
        return n;
    }
    
    public static double c(final double n) {
        return MathHelper.wrapDegrees(n);
    }
    
    public static float b(final float n) {
        return MathHelper.wrapDegrees(n);
    }
    
    public static a a(final Vec3d vec3d) {
        double n;
        double n2;
        if (vec3d.x == 0.0 && vec3d.z == 0.0) {
            n = 0.0;
            n2 = 1.5707963267948966;
        }
        else {
            n = Math.atan2(vec3d.z, vec3d.x) - 1.5707963267948966;
            n2 = -Math.atan2(vec3d.y, Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z));
        }
        return a.a((float)n2, (float)n);
    }
    
    public static a b(final Vec3d vec3d) {
        return a(vec3d).d();
    }
}
