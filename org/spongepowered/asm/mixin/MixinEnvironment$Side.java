package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

public enum Side
{
    UNKNOWN {
        MixinEnvironment$Side$1(final String a1, final int a2) {
        }
        
        @Override
        protected boolean detect() {
            return false;
        }
    }, 
    CLIENT {
        MixinEnvironment$Side$2(final String a1, final int a2) {
        }
        
        @Override
        protected boolean detect() {
            final String v1 = MixinService.getService().getSideName();
            return "CLIENT".equals(v1);
        }
    }, 
    SERVER {
        MixinEnvironment$Side$3(final String a1, final int a2) {
        }
        
        @Override
        protected boolean detect() {
            final String v1 = MixinService.getService().getSideName();
            return "SERVER".equals(v1) || "DEDICATEDSERVER".equals(v1);
        }
    };
    
    private static final /* synthetic */ Side[] $VALUES;
    
    public static Side[] values() {
        return Side.$VALUES.clone();
    }
    
    public static Side valueOf(final String a1) {
        return Enum.valueOf(Side.class, a1);
    }
    
    protected abstract boolean detect();
    
    Side(final String a1, final int a2, final MixinEnvironment$1 a3) {
        this();
    }
    
    static {
        $VALUES = new Side[] { Side.UNKNOWN, Side.CLIENT, Side.SERVER };
    }
}
