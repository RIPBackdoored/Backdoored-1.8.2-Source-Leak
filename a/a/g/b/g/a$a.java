package a.a.g.b.g;

import net.minecraft.util.math.*;

static class a$a {
    public static final /* synthetic */ int[] rq;
    
    static {
        a$a.rq = new int[RayTraceResult.Type.values().length];
        try {
            a$a.rq[RayTraceResult.Type.ENTITY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            a$a.rq[RayTraceResult.Type.BLOCK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            a$a.rq[RayTraceResult.Type.MISS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}