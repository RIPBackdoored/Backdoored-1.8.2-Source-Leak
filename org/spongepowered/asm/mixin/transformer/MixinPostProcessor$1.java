package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;

class MixinPostProcessor$1 extends ClassVisitor {
    final /* synthetic */ MixinPostProcessor this$0;
    
    MixinPostProcessor$1(final MixinPostProcessor a1, final int a2, final ClassVisitor a3) {
        this.this$0 = a1;
        super(a2, a3);
    }
    
    @Override
    public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        super.visit(a1, a2 | 0x1, a3, a4, a5, a6);
    }
    
    @Override
    public FieldVisitor visitField(int a1, final String a2, final String a3, final String a4, final Object a5) {
        if ((a1 & 0x6) == 0x0) {
            a1 |= 0x1;
        }
        return super.visitField(a1, a2, a3, a4, a5);
    }
    
    @Override
    public MethodVisitor visitMethod(int a1, final String a2, final String a3, final String a4, final String[] a5) {
        if ((a1 & 0x6) == 0x0) {
            a1 |= 0x1;
        }
        return super.visitMethod(a1, a2, a3, a4, a5);
    }
}