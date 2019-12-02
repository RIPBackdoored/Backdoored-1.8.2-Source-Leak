package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.mixin.refmap.*;

public abstract class SpecialMethodInfo implements IInjectionPointContext
{
    protected final AnnotationNode annotation;
    protected final ClassNode classNode;
    protected final MethodNode method;
    protected final MixinTargetContext mixin;
    
    public SpecialMethodInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        super();
        this.mixin = a1;
        this.method = a2;
        this.annotation = a3;
        this.classNode = a1.getTargetClassNode();
    }
    
    @Override
    public final IMixinContext getContext() {
        return this.mixin;
    }
    
    @Override
    public final AnnotationNode getAnnotation() {
        return this.annotation;
    }
    
    public final ClassNode getClassNode() {
        return this.classNode;
    }
    
    @Override
    public final MethodNode getMethod() {
        return this.method;
    }
}
