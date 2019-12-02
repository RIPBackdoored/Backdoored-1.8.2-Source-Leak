package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.injection.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.extensibility.*;

class MixinMethodNode extends MethodNode
{
    private final String originalName;
    final /* synthetic */ MixinInfo this$0;
    
    public MixinMethodNode(final MixinInfo a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        this.this$0 = a1;
        super(327680, a2, a3, a4, a5, a6);
        this.originalName = a3;
    }
    
    @Override
    public String toString() {
        return String.format("%s%s", this.originalName, this.desc);
    }
    
    public String getOriginalName() {
        return this.originalName;
    }
    
    public boolean isInjector() {
        return this.getInjectorAnnotation() != null || this.isSurrogate();
    }
    
    public boolean isSurrogate() {
        return this.getVisibleAnnotation(Surrogate.class) != null;
    }
    
    public boolean isSynthetic() {
        return Bytecode.hasFlag(this, 4096);
    }
    
    public AnnotationNode getVisibleAnnotation(final Class<? extends Annotation> a1) {
        return Annotations.getVisible(this, a1);
    }
    
    public AnnotationNode getInjectorAnnotation() {
        return InjectionInfo.getInjectorAnnotation(this.this$0, this);
    }
    
    public IMixinInfo getOwner() {
        return this.this$0;
    }
}
