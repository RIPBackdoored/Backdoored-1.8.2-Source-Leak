package org.spongepowered.asm.lib.tree;

import java.util.*;

class MethodNode$1 extends ArrayList<Object> {
    final /* synthetic */ MethodNode this$0;
    
    MethodNode$1(final MethodNode a1, final int a2) {
        this.this$0 = a1;
        super(a2);
    }
    
    @Override
    public boolean add(final Object a1) {
        this.this$0.annotationDefault = a1;
        return super.add(a1);
    }
}