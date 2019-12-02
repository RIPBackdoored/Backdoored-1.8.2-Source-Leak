package org.spongepowered.asm.mixin.gen;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public abstract class AccessorGenerator
{
    protected final AccessorInfo info;
    
    public AccessorGenerator(final AccessorInfo a1) {
        super();
        this.info = a1;
    }
    
    protected final MethodNode createMethod(final int a1, final int a2) {
        final MethodNode v1 = this.info.getMethod();
        final MethodNode v2 = new MethodNode(327680, (v1.access & 0xFFFFFBFF) | 0x1000, v1.name, v1.desc, null, null);
        (v2.visibleAnnotations = new ArrayList<AnnotationNode>()).add(this.info.getAnnotation());
        v2.maxLocals = a1;
        v2.maxStack = a2;
        return v2;
    }
    
    public abstract MethodNode generate();
}
