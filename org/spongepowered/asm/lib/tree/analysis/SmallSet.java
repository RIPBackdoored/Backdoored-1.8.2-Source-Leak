package org.spongepowered.asm.lib.tree.analysis;

import java.util.*;

class SmallSet<E> extends AbstractSet<E> implements Iterator<E>
{
    E e1;
    E e2;
    
    static final <T> Set<T> emptySet() {
        return new SmallSet<T>(null, null);
    }
    
    SmallSet(final E a1, final E a2) {
        super();
        this.e1 = a1;
        this.e2 = a2;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new SmallSet(this.e1, this.e2);
    }
    
    @Override
    public int size() {
        return (this.e1 == null) ? 0 : ((this.e2 == null) ? 1 : 2);
    }
    
    public boolean hasNext() {
        return this.e1 != null;
    }
    
    public E next() {
        if (this.e1 == null) {
            throw new NoSuchElementException();
        }
        final E v1 = this.e1;
        this.e1 = this.e2;
        this.e2 = null;
        return v1;
    }
    
    public void remove() {
    }
    
    Set<E> union(final SmallSet<E> a1) {
        if ((a1.e1 == this.e1 && a1.e2 == this.e2) || (a1.e1 == this.e2 && a1.e2 == this.e1)) {
            return this;
        }
        if (a1.e1 == null) {
            return this;
        }
        if (this.e1 == null) {
            return a1;
        }
        if (a1.e2 == null) {
            if (this.e2 == null) {
                return new SmallSet(this.e1, a1.e1);
            }
            if (a1.e1 == this.e1 || a1.e1 == this.e2) {
                return this;
            }
        }
        if (this.e2 == null && (this.e1 == a1.e1 || this.e1 == a1.e2)) {
            return a1;
        }
        final HashSet<E> v1 = new HashSet<E>(4);
        v1.add(this.e1);
        if (this.e2 != null) {
            v1.add(this.e2);
        }
        v1.add(a1.e1);
        if (a1.e2 != null) {
            v1.add(a1.e2);
        }
        return v1;
    }
}
