package org.spongepowered.asm.mixin.gen;

import java.util.*;
import com.google.common.collect.*;

public enum AccessorType
{
    FIELD_GETTER((Set)ImmutableSet.of((Object)"get", (Object)"is")) {
        AccessorInfo$AccessorType$1(final String a2, final int a3, final Set a1) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo a1) {
            return new AccessorGeneratorFieldGetter(a1);
        }
    }, 
    FIELD_SETTER((Set)ImmutableSet.of("set")) {
        AccessorInfo$AccessorType$2(final String a2, final int a3, final Set a1) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo a1) {
            return new AccessorGeneratorFieldSetter(a1);
        }
    }, 
    METHOD_PROXY((Set)ImmutableSet.of((Object)"call", (Object)"invoke")) {
        AccessorInfo$AccessorType$3(final String a2, final int a3, final Set a1) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo a1) {
            return new AccessorGeneratorMethodProxy(a1);
        }
    };
    
    private final Set<String> expectedPrefixes;
    private static final /* synthetic */ AccessorType[] $VALUES;
    
    public static AccessorType[] values() {
        return AccessorType.$VALUES.clone();
    }
    
    public static AccessorType valueOf(final String a1) {
        return Enum.valueOf(AccessorType.class, a1);
    }
    
    private AccessorType(final Set<String> a1) {
        this.expectedPrefixes = a1;
    }
    
    public boolean isExpectedPrefix(final String a1) {
        return this.expectedPrefixes.contains(a1);
    }
    
    public String getExpectedPrefixes() {
        return this.expectedPrefixes.toString();
    }
    
    abstract AccessorGenerator getGenerator(final AccessorInfo p0);
    
    AccessorType(final String a1, final int a2, final Set a3, final AccessorInfo$1 a4) {
        this(a3);
    }
    
    static {
        $VALUES = new AccessorType[] { AccessorType.FIELD_GETTER, AccessorType.FIELD_SETTER, AccessorType.METHOD_PROXY };
    }
}
