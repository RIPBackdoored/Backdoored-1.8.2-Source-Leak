package org.yaml.snakeyaml.util;

import java.util.*;

private static class UnmodifiableArrayList<E> extends AbstractList<E>
{
    private final E[] array;
    
    UnmodifiableArrayList(final E[] a1) {
        super();
        this.array = a1;
    }
    
    @Override
    public E get(final int a1) {
        if (a1 >= this.array.length) {
            throw new IndexOutOfBoundsException("Index: " + a1 + ", Size: " + this.size());
        }
        return this.array[a1];
    }
    
    @Override
    public int size() {
        return this.array.length;
    }
}
