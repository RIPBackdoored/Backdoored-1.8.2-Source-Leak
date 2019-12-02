package com.backdoored.mixin;

import net.minecraft.launchwrapper.*;

public class ClassTransformer implements IClassTransformer
{
    public ClassTransformer() {
        super();
    }
    
    public byte[] transform(final String a1, final String a2, final byte[] a3) {
        return a3;
    }
}
