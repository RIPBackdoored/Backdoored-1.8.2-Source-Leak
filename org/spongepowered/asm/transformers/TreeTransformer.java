package org.spongepowered.asm.transformers;

import org.spongepowered.asm.service.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

public abstract class TreeTransformer implements ILegacyClassTransformer
{
    private ClassReader classReader;
    private ClassNode classNode;
    
    public TreeTransformer() {
        super();
    }
    
    protected final ClassNode readClass(final byte[] a1) {
        return this.readClass(a1, true);
    }
    
    protected final ClassNode readClass(final byte[] a1, final boolean a2) {
        final ClassReader v1 = new ClassReader(a1);
        if (a2) {
            this.classReader = v1;
        }
        final ClassNode v2 = new ClassNode();
        v1.accept(v2, 8);
        return v2;
    }
    
    protected final byte[] writeClass(final ClassNode v2) {
        if (this.classReader != null && this.classNode == v2) {
            this.classNode = null;
            final ClassWriter a1 = new MixinClassWriter(this.classReader, 3);
            this.classReader = null;
            v2.accept(a1);
            return a1.toByteArray();
        }
        this.classNode = null;
        final ClassWriter v3 = new MixinClassWriter(3);
        v2.accept(v3);
        return v3.toByteArray();
    }
}
