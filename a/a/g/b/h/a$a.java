package a.a.g.b.h;

import net.minecraftforge.client.event.*;

static class a$a {
    public static final /* synthetic */ int[] ub;
    
    static {
        a$a.ub = new int[RenderBlockOverlayEvent.OverlayType.values().length];
        try {
            a$a.ub[RenderBlockOverlayEvent.OverlayType.FIRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            a$a.ub[RenderBlockOverlayEvent.OverlayType.BLOCK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            a$a.ub[RenderBlockOverlayEvent.OverlayType.WATER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}