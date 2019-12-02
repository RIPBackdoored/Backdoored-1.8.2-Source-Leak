package com.sun.jna.win32;

import com.sun.jna.*;
import java.lang.reflect.*;

public class W32APIFunctionMapper implements FunctionMapper
{
    public static final FunctionMapper UNICODE;
    public static final FunctionMapper ASCII;
    private final String suffix;
    
    protected W32APIFunctionMapper(final boolean a1) {
        super();
        this.suffix = (a1 ? "W" : "A");
    }
    
    @Override
    public String getFunctionName(final NativeLibrary a1, final Method a2) {
        String v1 = a2.getName();
        if (!v1.endsWith("W") && !v1.endsWith("A")) {
            try {
                v1 = a1.getFunction(v1 + this.suffix, 63).getName();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
        }
        return v1;
    }
    
    static {
        UNICODE = new W32APIFunctionMapper(true);
        ASCII = new W32APIFunctionMapper(false);
    }
}
