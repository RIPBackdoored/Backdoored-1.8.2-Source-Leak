package org.spongepowered.asm.util;

import java.util.regex.*;

public abstract class JavaVersion
{
    private static double current;
    
    private JavaVersion() {
        super();
    }
    
    public static double current() {
        if (JavaVersion.current == 0.0) {
            JavaVersion.current = resolveCurrentVersion();
        }
        return JavaVersion.current;
    }
    
    private static double resolveCurrentVersion() {
        final String v1 = System.getProperty("java.version");
        final Matcher v2 = Pattern.compile("[0-9]+\\.[0-9]+").matcher(v1);
        if (v2.find()) {
            return Double.parseDouble(v2.group());
        }
        return 1.6;
    }
    
    static {
        JavaVersion.current = 0.0;
    }
}
