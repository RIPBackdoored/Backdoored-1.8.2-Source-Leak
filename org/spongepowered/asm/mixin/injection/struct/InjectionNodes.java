package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

public class InjectionNodes extends ArrayList<InjectionNode>
{
    private static final long serialVersionUID = 1L;
    
    public InjectionNodes() {
        super();
    }
    
    public InjectionNode add(final AbstractInsnNode a1) {
        InjectionNode v1 = this.get(a1);
        if (v1 == null) {
            v1 = new InjectionNode(a1);
            this.add(v1);
        }
        return v1;
    }
    
    public InjectionNode get(final AbstractInsnNode v2) {
        for (final InjectionNode a1 : this) {
            if (a1.matches(v2)) {
                return a1;
            }
        }
        return null;
    }
    
    public boolean contains(final AbstractInsnNode a1) {
        return this.get(a1) != null;
    }
    
    public void replace(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        final InjectionNode v1 = this.get(a1);
        if (v1 != null) {
            v1.replace(a2);
        }
    }
    
    public void remove(final AbstractInsnNode a1) {
        final InjectionNode v1 = this.get(a1);
        if (v1 != null) {
            v1.remove();
        }
    }
    
    public static class InjectionNode implements Comparable<InjectionNode>
    {
        private static int nextId;
        private final int id;
        private final AbstractInsnNode originalTarget;
        private AbstractInsnNode currentTarget;
        private Map<String, Object> decorations;
        
        public InjectionNode(final AbstractInsnNode a1) {
            super();
            this.originalTarget = a1;
            this.currentTarget = a1;
            this.id = InjectionNode.nextId++;
        }
        
        public int getId() {
            return this.id;
        }
        
        public AbstractInsnNode getOriginalTarget() {
            return this.originalTarget;
        }
        
        public AbstractInsnNode getCurrentTarget() {
            return this.currentTarget;
        }
        
        public InjectionNode replace(final AbstractInsnNode a1) {
            this.currentTarget = a1;
            return this;
        }
        
        public InjectionNode remove() {
            this.currentTarget = null;
            return this;
        }
        
        public boolean matches(final AbstractInsnNode a1) {
            return this.originalTarget == a1 || this.currentTarget == a1;
        }
        
        public boolean isReplaced() {
            return this.originalTarget != this.currentTarget;
        }
        
        public boolean isRemoved() {
            return this.currentTarget == null;
        }
        
        public <V> InjectionNode decorate(final String a1, final V a2) {
            if (this.decorations == null) {
                this.decorations = new HashMap<String, Object>();
            }
            this.decorations.put(a1, a2);
            return this;
        }
        
        public boolean hasDecoration(final String a1) {
            return this.decorations != null && this.decorations.get(a1) != null;
        }
        
        public <V> V getDecoration(final String a1) {
            return (V)((this.decorations == null) ? null : this.decorations.get(a1));
        }
        
        @Override
        public int compareTo(final InjectionNode a1) {
            return (a1 == null) ? 0 : (this.hashCode() - a1.hashCode());
        }
        
        @Override
        public String toString() {
            return String.format("InjectionNode[%s]", Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " "));
        }
        
        @Override
        public /* bridge */ int compareTo(final Object o) {
            return this.compareTo((InjectionNode)o);
        }
        
        static {
            InjectionNode.nextId = 0;
        }
    }
}
