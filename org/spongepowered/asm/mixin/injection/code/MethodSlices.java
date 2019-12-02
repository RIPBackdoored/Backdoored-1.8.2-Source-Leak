package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public final class MethodSlices
{
    private final InjectionInfo info;
    private final Map<String, MethodSlice> slices;
    
    private MethodSlices(final InjectionInfo a1) {
        super();
        this.slices = new HashMap<String, MethodSlice>(4);
        this.info = a1;
    }
    
    private void add(final MethodSlice a1) {
        final String v1 = this.info.getSliceId(a1.getId());
        if (this.slices.containsKey(v1)) {
            throw new InvalidSliceException((ISliceContext)this.info, a1 + " has a duplicate id, '" + v1 + "' was already defined");
        }
        this.slices.put(v1, a1);
    }
    
    public MethodSlice get(final String a1) {
        return this.slices.get(a1);
    }
    
    @Override
    public String toString() {
        return String.format("MethodSlices%s", this.slices.keySet());
    }
    
    public static MethodSlices parse(final InjectionInfo v-3) {
        final MethodSlices methodSlices = new MethodSlices(v-3);
        final AnnotationNode annotation = v-3.getAnnotation();
        if (annotation != null) {
            for (final AnnotationNode v1 : Annotations.getValue(annotation, "slice", true)) {
                final MethodSlice a1 = MethodSlice.parse(v-3, v1);
                methodSlices.add(a1);
            }
        }
        return methodSlices;
    }
}
