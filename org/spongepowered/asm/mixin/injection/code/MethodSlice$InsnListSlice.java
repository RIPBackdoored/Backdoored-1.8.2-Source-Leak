package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class InsnListSlice extends ReadOnlyInsnList
{
    private final int start;
    private final int end;
    
    protected InsnListSlice(final InsnList a1, final int a2, final int a3) {
        super(a1);
        this.start = a2;
        this.end = a3;
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator() {
        return this.iterator(0);
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator(final int a1) {
        return new SliceIterator(super.iterator(this.start + a1), this.start, this.end, this.start + a1);
    }
    
    @Override
    public AbstractInsnNode[] toArray() {
        final AbstractInsnNode[] v1 = super.toArray();
        final AbstractInsnNode[] v2 = new AbstractInsnNode[this.size()];
        System.arraycopy(v1, this.start, v2, 0, v2.length);
        return v2;
    }
    
    @Override
    public int size() {
        return this.end - this.start + 1;
    }
    
    @Override
    public AbstractInsnNode getFirst() {
        return super.get(this.start);
    }
    
    @Override
    public AbstractInsnNode getLast() {
        return super.get(this.end);
    }
    
    @Override
    public AbstractInsnNode get(final int a1) {
        return super.get(this.start + a1);
    }
    
    @Override
    public boolean contains(final AbstractInsnNode v2) {
        for (final AbstractInsnNode a1 : this.toArray()) {
            if (a1 == v2) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int indexOf(final AbstractInsnNode a1) {
        final int v1 = super.indexOf(a1);
        return (v1 >= this.start && v1 <= this.end) ? (v1 - this.start) : -1;
    }
    
    public int realIndexOf(final AbstractInsnNode a1) {
        return super.indexOf(a1);
    }
    
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
}
