package org.yaml.snakeyaml.util;

import java.util.*;

public class ArrayUtils
{
    private ArrayUtils() {
        super();
    }
    
    public static <E> List<E> toUnmodifiableList(final E... a1) {
        return (a1.length == 0) ? Collections.emptyList() : new UnmodifiableArrayList<E>(a1);
    }
    
    public static <E> List<E> toUnmodifiableCompositeList(final E[] v1, final E[] v2) {
        List<E> v3 = null;
        if (v1.length == 0) {
            final List<E> a1 = toUnmodifiableList(v2);
        }
        else if (v2.length == 0) {
            final List<E> a2 = toUnmodifiableList(v1);
        }
        else {
            v3 = new CompositeUnmodifiableArrayList<E>(v1, v2);
        }
        return v3;
    }
    
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
}
