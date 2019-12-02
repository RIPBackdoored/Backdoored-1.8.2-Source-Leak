package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

public enum CompatibilityLevel
{
    JAVA_6(6, 50, false), 
    JAVA_7(7, 51, false) {
        MixinEnvironment$CompatibilityLevel$1(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.7;
        }
    }, 
    JAVA_8(8, 52, true) {
        MixinEnvironment$CompatibilityLevel$2(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.8;
        }
    }, 
    JAVA_9(9, 53, true) {
        MixinEnvironment$CompatibilityLevel$3(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
        }
        
        @Override
        boolean isSupported() {
            return false;
        }
    };
    
    private static final int CLASS_V1_9 = 53;
    private final int ver;
    private final int classVersion;
    private final boolean supportsMethodsInInterfaces;
    private CompatibilityLevel maxCompatibleLevel;
    private static final /* synthetic */ CompatibilityLevel[] $VALUES;
    
    public static CompatibilityLevel[] values() {
        return CompatibilityLevel.$VALUES.clone();
    }
    
    public static CompatibilityLevel valueOf(final String a1) {
        return Enum.valueOf(CompatibilityLevel.class, a1);
    }
    
    private CompatibilityLevel(final int a1, final int a2, final boolean a3) {
        this.ver = a1;
        this.classVersion = a2;
        this.supportsMethodsInInterfaces = a3;
    }
    
    private void setMaxCompatibleLevel(final CompatibilityLevel a1) {
        this.maxCompatibleLevel = a1;
    }
    
    boolean isSupported() {
        return true;
    }
    
    public int classVersion() {
        return this.classVersion;
    }
    
    public boolean supportsMethodsInInterfaces() {
        return this.supportsMethodsInInterfaces;
    }
    
    public boolean isAtLeast(final CompatibilityLevel a1) {
        return a1 == null || this.ver >= a1.ver;
    }
    
    public boolean canElevateTo(final CompatibilityLevel a1) {
        return a1 == null || this.maxCompatibleLevel == null || a1.ver <= this.maxCompatibleLevel.ver;
    }
    
    public boolean canSupport(final CompatibilityLevel a1) {
        return a1 == null || a1.canElevateTo(this);
    }
    
    CompatibilityLevel(final String a1, final int a2, final int a3, final int a4, final boolean a5, final MixinEnvironment$1 a6) {
        this(a3, a4, a5);
    }
    
    static {
        $VALUES = new CompatibilityLevel[] { CompatibilityLevel.JAVA_6, CompatibilityLevel.JAVA_7, CompatibilityLevel.JAVA_8, CompatibilityLevel.JAVA_9 };
    }
}
