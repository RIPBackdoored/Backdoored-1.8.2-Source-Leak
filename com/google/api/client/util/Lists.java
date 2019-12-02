package com.google.api.client.util;

import java.util.*;

public final class Lists
{
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }
    
    public static <E> ArrayList<E> newArrayListWithCapacity(final int a1) {
        return new ArrayList<E>(a1);
    }
    
    public static <E> ArrayList<E> newArrayList(final Iterable<? extends E> a1) {
        return (a1 instanceof Collection) ? new ArrayList<E>(Collections2.cast(a1)) : newArrayList(a1.iterator());
    }
    
    public static <E> ArrayList<E> newArrayList(final Iterator<? extends E> a1) {
        final ArrayList<E> v1 = newArrayList();
        while (a1.hasNext()) {
            v1.add((E)a1.next());
        }
        return v1;
    }
    
    private Lists() {
        super();
    }
}
