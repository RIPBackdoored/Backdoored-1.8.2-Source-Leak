package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static class SliceIterator implements ListIterator<AbstractInsnNode>
{
    private final ListIterator<AbstractInsnNode> iter;
    private int start;
    private int end;
    private int index;
    
    public SliceIterator(final ListIterator<AbstractInsnNode> a1, final int a2, final int a3, final int a4) {
        super();
        this.iter = a1;
        this.start = a2;
        this.end = a3;
        this.index = a4;
    }
    
    @Override
    public boolean hasNext() {
        return this.index <= this.end && this.iter.hasNext();
    }
    
    @Override
    public AbstractInsnNode next() {
        if (this.index > this.end) {
            throw new NoSuchElementException();
        }
        ++this.index;
        return this.iter.next();
    }
    
    @Override
    public boolean hasPrevious() {
        return this.index > this.start;
    }
    
    @Override
    public AbstractInsnNode previous() {
        if (this.index <= this.start) {
            throw new NoSuchElementException();
        }
        --this.index;
        return this.iter.previous();
    }
    
    @Override
    public int nextIndex() {
        return this.index - this.start;
    }
    
    @Override
    public int previousIndex() {
        return this.index - this.start - 1;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove insn from slice");
    }
    
    @Override
    public void set(final AbstractInsnNode a1) {
        throw new UnsupportedOperationException("Cannot set insn using slice");
    }
    
    @Override
    public void add(final AbstractInsnNode a1) {
        throw new UnsupportedOperationException("Cannot add insn using slice");
    }
    
    @Override
    public /* bridge */ void add(final Object o) {
        this.add((AbstractInsnNode)o);
    }
    
    @Override
    public /* bridge */ void set(final Object o) {
        this.set((AbstractInsnNode)o);
    }
    
    @Override
    public /* bridge */ Object previous() {
        return this.previous();
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}
