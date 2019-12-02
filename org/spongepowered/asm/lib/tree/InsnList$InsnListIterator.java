package org.spongepowered.asm.lib.tree;

import java.util.*;

private final class InsnListIterator implements ListIterator
{
    AbstractInsnNode next;
    AbstractInsnNode prev;
    AbstractInsnNode remove;
    final /* synthetic */ InsnList this$0;
    
    InsnListIterator(final InsnList this$0, final int a1) {
        this.this$0 = this$0;
        super();
        if (a1 == this$0.size()) {
            this.next = null;
            this.prev = this$0.getLast();
        }
        else {
            this.next = this$0.get(a1);
            this.prev = this.next.prev;
        }
    }
    
    public boolean hasNext() {
        return this.next != null;
    }
    
    public Object next() {
        if (this.next == null) {
            throw new NoSuchElementException();
        }
        final AbstractInsnNode v1 = this.next;
        this.prev = v1;
        this.next = v1.next;
        return this.remove = v1;
    }
    
    public void remove() {
        if (this.remove != null) {
            if (this.remove == this.next) {
                this.next = this.next.next;
            }
            else {
                this.prev = this.prev.prev;
            }
            this.this$0.remove(this.remove);
            this.remove = null;
            return;
        }
        throw new IllegalStateException();
    }
    
    public boolean hasPrevious() {
        return this.prev != null;
    }
    
    public Object previous() {
        final AbstractInsnNode v1 = this.prev;
        this.next = v1;
        this.prev = v1.prev;
        return this.remove = v1;
    }
    
    public int nextIndex() {
        if (this.next == null) {
            return this.this$0.size();
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.next.index;
    }
    
    public int previousIndex() {
        if (this.prev == null) {
            return -1;
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.prev.index;
    }
    
    public void add(final Object a1) {
        if (this.next != null) {
            this.this$0.insertBefore(this.next, (AbstractInsnNode)a1);
        }
        else if (this.prev != null) {
            this.this$0.insert(this.prev, (AbstractInsnNode)a1);
        }
        else {
            this.this$0.add((AbstractInsnNode)a1);
        }
        this.prev = (AbstractInsnNode)a1;
        this.remove = null;
    }
    
    public void set(final Object a1) {
        if (this.remove != null) {
            this.this$0.set(this.remove, (AbstractInsnNode)a1);
            if (this.remove == this.prev) {
                this.prev = (AbstractInsnNode)a1;
            }
            else {
                this.next = (AbstractInsnNode)a1;
            }
            return;
        }
        throw new IllegalStateException();
    }
}
