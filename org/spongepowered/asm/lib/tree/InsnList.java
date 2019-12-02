package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class InsnList
{
    private int size;
    private AbstractInsnNode first;
    private AbstractInsnNode last;
    AbstractInsnNode[] cache;
    
    public InsnList() {
        super();
    }
    
    public int size() {
        return this.size;
    }
    
    public AbstractInsnNode getFirst() {
        return this.first;
    }
    
    public AbstractInsnNode getLast() {
        return this.last;
    }
    
    public AbstractInsnNode get(final int a1) {
        if (a1 < 0 || a1 >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (this.cache == null) {
            this.cache = this.toArray();
        }
        return this.cache[a1];
    }
    
    public boolean contains(final AbstractInsnNode a1) {
        AbstractInsnNode v1;
        for (v1 = this.first; v1 != null && v1 != a1; v1 = v1.next) {}
        return v1 != null;
    }
    
    public int indexOf(final AbstractInsnNode a1) {
        if (this.cache == null) {
            this.cache = this.toArray();
        }
        return a1.index;
    }
    
    public void accept(final MethodVisitor a1) {
        for (AbstractInsnNode v1 = this.first; v1 != null; v1 = v1.next) {
            v1.accept(a1);
        }
    }
    
    public ListIterator<AbstractInsnNode> iterator() {
        return this.iterator(0);
    }
    
    public ListIterator<AbstractInsnNode> iterator(final int a1) {
        return (ListIterator<AbstractInsnNode>)new InsnListIterator(a1);
    }
    
    public AbstractInsnNode[] toArray() {
        int v1 = 0;
        AbstractInsnNode v2 = this.first;
        final AbstractInsnNode[] v3 = new AbstractInsnNode[this.size];
        while (v2 != null) {
            v3[v1] = v2;
            v2.index = v1++;
            v2 = v2.next;
        }
        return v3;
    }
    
    public void set(final AbstractInsnNode v1, final AbstractInsnNode v2) {
        final AbstractInsnNode v3 = v1.next;
        v2.next = v3;
        if (v3 != null) {
            v3.prev = v2;
        }
        else {
            this.last = v2;
        }
        final AbstractInsnNode v4 = v1.prev;
        v2.prev = v4;
        if (v4 != null) {
            v4.next = v2;
        }
        else {
            this.first = v2;
        }
        if (this.cache != null) {
            final int a1 = v1.index;
            this.cache[a1] = v2;
            v2.index = a1;
        }
        else {
            v2.index = 0;
        }
        v1.index = -1;
        v1.prev = null;
        v1.next = null;
    }
    
    public void add(final AbstractInsnNode a1) {
        ++this.size;
        if (this.last == null) {
            this.first = a1;
            this.last = a1;
        }
        else {
            this.last.next = a1;
            a1.prev = this.last;
        }
        this.last = a1;
        this.cache = null;
        a1.index = 0;
    }
    
    public void add(final InsnList v2) {
        if (v2.size == 0) {
            return;
        }
        this.size += v2.size;
        if (this.last == null) {
            this.first = v2.first;
            this.last = v2.last;
        }
        else {
            final AbstractInsnNode a1 = v2.first;
            this.last.next = a1;
            a1.prev = this.last;
            this.last = v2.last;
        }
        this.cache = null;
        v2.removeAll(false);
    }
    
    public void insert(final AbstractInsnNode a1) {
        ++this.size;
        if (this.first == null) {
            this.first = a1;
            this.last = a1;
        }
        else {
            this.first.prev = a1;
            a1.next = this.first;
        }
        this.first = a1;
        this.cache = null;
        a1.index = 0;
    }
    
    public void insert(final InsnList v2) {
        if (v2.size == 0) {
            return;
        }
        this.size += v2.size;
        if (this.first == null) {
            this.first = v2.first;
            this.last = v2.last;
        }
        else {
            final AbstractInsnNode a1 = v2.last;
            this.first.prev = a1;
            a1.next = this.first;
            this.first = v2.first;
        }
        this.cache = null;
        v2.removeAll(false);
    }
    
    public void insert(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        ++this.size;
        final AbstractInsnNode v1 = a1.next;
        if (v1 == null) {
            this.last = a2;
        }
        else {
            v1.prev = a2;
        }
        a1.next = a2;
        a2.next = v1;
        a2.prev = a1;
        this.cache = null;
        a2.index = 0;
    }
    
    public void insert(final AbstractInsnNode a1, final InsnList a2) {
        if (a2.size == 0) {
            return;
        }
        this.size += a2.size;
        final AbstractInsnNode v1 = a2.first;
        final AbstractInsnNode v2 = a2.last;
        final AbstractInsnNode v3 = a1.next;
        if (v3 == null) {
            this.last = v2;
        }
        else {
            v3.prev = v2;
        }
        a1.next = v1;
        v2.next = v3;
        v1.prev = a1;
        this.cache = null;
        a2.removeAll(false);
    }
    
    public void insertBefore(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        ++this.size;
        final AbstractInsnNode v1 = a1.prev;
        if (v1 == null) {
            this.first = a2;
        }
        else {
            v1.next = a2;
        }
        a1.prev = a2;
        a2.next = a1;
        a2.prev = v1;
        this.cache = null;
        a2.index = 0;
    }
    
    public void insertBefore(final AbstractInsnNode a1, final InsnList a2) {
        if (a2.size == 0) {
            return;
        }
        this.size += a2.size;
        final AbstractInsnNode v1 = a2.first;
        final AbstractInsnNode v2 = a2.last;
        final AbstractInsnNode v3 = a1.prev;
        if (v3 == null) {
            this.first = v1;
        }
        else {
            v3.next = v1;
        }
        a1.prev = v2;
        v2.next = a1;
        v1.prev = v3;
        this.cache = null;
        a2.removeAll(false);
    }
    
    public void remove(final AbstractInsnNode a1) {
        --this.size;
        final AbstractInsnNode v1 = a1.next;
        final AbstractInsnNode v2 = a1.prev;
        if (v1 == null) {
            if (v2 == null) {
                this.first = null;
                this.last = null;
            }
            else {
                v2.next = null;
                this.last = v2;
            }
        }
        else if (v2 == null) {
            this.first = v1;
            v1.prev = null;
        }
        else {
            v2.next = v1;
            v1.prev = v2;
        }
        this.cache = null;
        a1.index = -1;
        a1.prev = null;
        a1.next = null;
    }
    
    void removeAll(final boolean v0) {
        if (v0) {
            AbstractInsnNode a1;
            for (AbstractInsnNode v = this.first; v != null; v = a1) {
                a1 = v.next;
                v.index = -1;
                v.prev = null;
                v.next = null;
            }
        }
        this.size = 0;
        this.first = null;
        this.last = null;
        this.cache = null;
    }
    
    public void clear() {
        this.removeAll(false);
    }
    
    public void resetLabels() {
        for (AbstractInsnNode v1 = this.first; v1 != null; v1 = v1.next) {
            if (v1 instanceof LabelNode) {
                ((LabelNode)v1).resetLabel();
            }
        }
    }
    
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
}
