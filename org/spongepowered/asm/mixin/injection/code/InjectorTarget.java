package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public class InjectorTarget
{
    private final ISliceContext context;
    private final Map<String, ReadOnlyInsnList> cache;
    private final Target target;
    private final String mergedBy;
    private final int mergedPriority;
    
    public InjectorTarget(final ISliceContext a1, final Target a2) {
        super();
        this.cache = new HashMap<String, ReadOnlyInsnList>();
        this.context = a1;
        this.target = a2;
        final AnnotationNode v1 = Annotations.getVisible(a2.method, MixinMerged.class);
        this.mergedBy = Annotations.getValue(v1, "mixin");
        this.mergedPriority = Annotations.getValue(v1, "priority", 1000);
    }
    
    @Override
    public String toString() {
        return this.target.toString();
    }
    
    public Target getTarget() {
        return this.target;
    }
    
    public MethodNode getMethod() {
        return this.target.method;
    }
    
    public boolean isMerged() {
        return this.mergedBy != null;
    }
    
    public String getMergedBy() {
        return this.mergedBy;
    }
    
    public int getMergedPriority() {
        return this.mergedPriority;
    }
    
    public InsnList getSlice(final String v2) {
        ReadOnlyInsnList v3 = this.cache.get(v2);
        if (v3 == null) {
            final MethodSlice a1 = this.context.getSlice(v2);
            if (a1 != null) {
                v3 = a1.getSlice(this.target.method);
            }
            else {
                v3 = new ReadOnlyInsnList(this.target.method.instructions);
            }
            this.cache.put(v2, v3);
        }
        return v3;
    }
    
    public InsnList getSlice(final InjectionPoint a1) {
        return this.getSlice(a1.getSlice());
    }
    
    public void dispose() {
        for (final ReadOnlyInsnList v1 : this.cache.values()) {
            v1.dispose();
        }
        this.cache.clear();
    }
}
