package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

class ReadOnlyInsnList extends InsnList
{
    private InsnList insnList;
    
    public ReadOnlyInsnList(final InsnList a1) {
        super();
        this.insnList = a1;
    }
    
    void dispose() {
        this.insnList = null;
    }
    
    @Override
    public final void set(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void add(final AbstractInsnNode a1) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void add(final InsnList a1) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insert(final AbstractInsnNode a1) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insert(final InsnList a1) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insert(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insert(final AbstractInsnNode a1, final InsnList a2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insertBefore(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void insertBefore(final AbstractInsnNode a1, final InsnList a2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void remove(final AbstractInsnNode a1) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public AbstractInsnNode[] toArray() {
        return this.insnList.toArray();
    }
    
    @Override
    public int size() {
        return this.insnList.size();
    }
    
    @Override
    public AbstractInsnNode getFirst() {
        return this.insnList.getFirst();
    }
    
    @Override
    public AbstractInsnNode getLast() {
        return this.insnList.getLast();
    }
    
    @Override
    public AbstractInsnNode get(final int a1) {
        return this.insnList.get(a1);
    }
    
    @Override
    public boolean contains(final AbstractInsnNode a1) {
        return this.insnList.contains(a1);
    }
    
    @Override
    public int indexOf(final AbstractInsnNode a1) {
        return this.insnList.indexOf(a1);
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator() {
        return this.insnList.iterator();
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator(final int a1) {
        return this.insnList.iterator(a1);
    }
    
    @Override
    public final void resetLabels() {
        this.insnList.resetLabels();
    }
}
