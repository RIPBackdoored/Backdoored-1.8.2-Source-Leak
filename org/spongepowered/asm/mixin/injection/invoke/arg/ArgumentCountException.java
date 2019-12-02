package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentCountException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public ArgumentCountException(final int a1, final int a2, final String a3) {
        super("Invalid number of arguments for setAll, received " + a1 + " but expected " + a2 + ": " + a3);
    }
}
