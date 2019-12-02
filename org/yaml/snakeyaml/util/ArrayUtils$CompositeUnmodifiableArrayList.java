package org.yaml.snakeyaml.util;

import java.util.*;

private static class CompositeUnmodifiableArrayList<E> extends AbstractList<E>
{
    private final E[] array1;
    private final E[] array2;
    
    CompositeUnmodifiableArrayList(final E[] a1, final E[] a2) {
        super();
        this.array1 = a1;
        this.array2 = a2;
    }
    
    @Override
    public E get(final int v0) {
        E v = null;
        if (v0 < this.array1.length) {
            final E a1 = this.array1[v0];
        }
        else {
            if (v0 - this.array1.length >= this.array2.length) {
                throw new IndexOutOfBoundsException("Index: " + v0 + ", Size: " + this.size());
            }
            v = this.array2[v0 - this.array1.length];
        }
        return v;
    }
    
    @Override
    public int size() {
        return this.array1.length + this.array2.length;
    }
}
