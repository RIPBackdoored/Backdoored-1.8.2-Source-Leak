package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

class MixinClassNode extends ClassNode
{
    public final List<MixinMethodNode> mixinMethods;
    final /* synthetic */ MixinInfo this$0;
    
    public MixinClassNode(final MixinInfo a1, final MixinInfo a2) {
        this(a1, 327680);
    }
    
    public MixinClassNode(final MixinInfo a1, final int a2) {
        this.this$0 = a1;
        super(a2);
        this.mixinMethods = (List<MixinMethodNode>)this.methods;
    }
    
    public MixinInfo getMixin() {
        return this.this$0;
    }
    
    @Override
    public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        final MethodNode v1 = this.this$0.new MixinMethodNode(a1, a2, a3, a4, a5);
        this.methods.add(v1);
        return v1;
    }
}
