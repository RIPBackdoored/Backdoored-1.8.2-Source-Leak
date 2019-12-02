package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.tree.analysis.*;
import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.*;

public class MixinVerifier extends SimpleVerifier
{
    private Type currentClass;
    private Type currentSuperClass;
    private List<Type> currentClassInterfaces;
    private boolean isInterface;
    
    public MixinVerifier(final Type a1, final Type a2, final List<Type> a3, final boolean a4) {
        super(a1, a2, a3, a4);
        this.currentClass = a1;
        this.currentSuperClass = a2;
        this.currentClassInterfaces = a3;
        this.isInterface = a4;
    }
    
    @Override
    protected boolean isInterface(final Type a1) {
        if (this.currentClass != null && a1.equals(this.currentClass)) {
            return this.isInterface;
        }
        return ClassInfo.forType(a1).isInterface();
    }
    
    @Override
    protected Type getSuperClass(final Type a1) {
        if (this.currentClass != null && a1.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        final ClassInfo v1 = ClassInfo.forType(a1).getSuperClass();
        return (v1 == null) ? null : Type.getType("L" + v1.getName() + ";");
    }
    
    @Override
    protected boolean isAssignableFrom(final Type v2, final Type v3) {
        if (v2.equals(v3)) {
            return true;
        }
        if (this.currentClass != null && v2.equals(this.currentClass)) {
            if (this.getSuperClass(v3) == null) {
                return false;
            }
            if (this.isInterface) {
                return v3.getSort() == 10 || v3.getSort() == 9;
            }
            return this.isAssignableFrom(v2, this.getSuperClass(v3));
        }
        else if (this.currentClass != null && v3.equals(this.currentClass)) {
            if (this.isAssignableFrom(v2, this.currentSuperClass)) {
                return true;
            }
            if (this.currentClassInterfaces != null) {
                for (int a2 = 0; a2 < this.currentClassInterfaces.size(); ++a2) {
                    final Type a3 = this.currentClassInterfaces.get(a2);
                    if (this.isAssignableFrom(v2, a3)) {
                        return true;
                    }
                }
            }
            return false;
        }
        else {
            ClassInfo v4 = ClassInfo.forType(v2);
            if (v4 == null) {
                return false;
            }
            if (v4.isInterface()) {
                v4 = ClassInfo.forName("java/lang/Object");
            }
            return ClassInfo.forType(v3).hasSuperClass(v4);
        }
    }
}
