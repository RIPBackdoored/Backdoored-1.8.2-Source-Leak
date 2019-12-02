package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.struct.*;

abstract class ClassContext
{
    private final Set<ClassInfo.Method> upgradedMethods;
    
    ClassContext() {
        super();
        this.upgradedMethods = new HashSet<ClassInfo.Method>();
    }
    
    abstract String getClassRef();
    
    abstract ClassNode getClassNode();
    
    abstract ClassInfo getClassInfo();
    
    void addUpgradedMethod(final MethodNode a1) {
        final ClassInfo.Method v1 = this.getClassInfo().findMethod(a1);
        if (v1 == null) {
            throw new IllegalStateException("Meta method for " + a1.name + " not located in " + this);
        }
        this.upgradedMethods.add(v1);
    }
    
    protected void upgradeMethods() {
        for (final MethodNode v1 : this.getClassNode().methods) {
            this.upgradeMethod(v1);
        }
    }
    
    private void upgradeMethod(final MethodNode v-1) {
        for (final AbstractInsnNode v2 : v-1.instructions) {
            if (!(v2 instanceof MethodInsnNode)) {
                continue;
            }
            final MemberRef v3 = new MemberRef.Method((MethodInsnNode)v2);
            if (!v3.getOwner().equals(this.getClassRef())) {
                continue;
            }
            final ClassInfo.Method a1 = this.getClassInfo().findMethod(v3.getName(), v3.getDesc(), 10);
            this.upgradeMethodRef(v-1, v3, a1);
        }
    }
    
    protected void upgradeMethodRef(final MethodNode a1, final MemberRef a2, final ClassInfo.Method a3) {
        if (a2.getOpcode() != 183) {
            return;
        }
        if (this.upgradedMethods.contains(a3)) {
            a2.setOpcode(182);
        }
    }
}
