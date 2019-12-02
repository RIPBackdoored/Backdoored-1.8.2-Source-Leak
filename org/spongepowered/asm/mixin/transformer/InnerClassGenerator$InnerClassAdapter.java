package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

static class InnerClassAdapter extends ClassRemapper
{
    private final InnerClassInfo info;
    
    public InnerClassAdapter(final ClassVisitor a1, final InnerClassInfo a2) {
        super(327680, a1, a2);
        this.info = a2;
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        super.visitSource(a1, a2);
        final AnnotationVisitor v1 = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
        v1.visit("mixin", this.info.getOwner().toString());
        v1.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
        v1.visitEnd();
    }
    
    @Override
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        if (a1.startsWith(this.info.getOriginalName() + "$")) {
            throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + a1 + " in " + this.info.getOriginalName());
        }
        super.visitInnerClass(a1, a2, a3, a4);
    }
}
