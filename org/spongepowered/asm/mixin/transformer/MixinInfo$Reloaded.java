package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

class Reloaded extends State
{
    private final State previous;
    final /* synthetic */ MixinInfo this$0;
    
    Reloaded(final MixinInfo a1, final State a2, final byte[] a3) {
        this.this$0 = a1;
        a1.super(a3, a2.getClassInfo());
        this.previous = a2;
    }
    
    @Override
    protected void validateChanges(final SubType a1, final List<ClassInfo> a2) {
        if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
            throw new MixinReloadException(this.this$0, "Cannot change inner classes");
        }
        if (!this.interfaces.equals(this.previous.interfaces)) {
            throw new MixinReloadException(this.this$0, "Cannot change interfaces");
        }
        if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
            throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
        }
        final List<ClassInfo> v1 = this.this$0.readTargetClasses(this.classNode, true);
        if (!new HashSet(v1).equals(new HashSet(a2))) {
            throw new MixinReloadException(this.this$0, "Cannot change target classes");
        }
        final int v2 = this.this$0.readPriority(this.classNode);
        if (v2 != this.this$0.getPriority()) {
            throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
        }
    }
}
