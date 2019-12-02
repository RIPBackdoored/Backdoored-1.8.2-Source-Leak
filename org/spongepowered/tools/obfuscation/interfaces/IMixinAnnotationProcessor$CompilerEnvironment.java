package org.spongepowered.tools.obfuscation.interfaces;

public enum CompilerEnvironment
{
    JAVAC, 
    JDT;
    
    private static final /* synthetic */ CompilerEnvironment[] $VALUES;
    
    public static CompilerEnvironment[] values() {
        return CompilerEnvironment.$VALUES.clone();
    }
    
    public static CompilerEnvironment valueOf(final String a1) {
        return Enum.valueOf(CompilerEnvironment.class, a1);
    }
    
    static {
        $VALUES = new CompilerEnvironment[] { CompilerEnvironment.JAVAC, CompilerEnvironment.JDT };
    }
}
