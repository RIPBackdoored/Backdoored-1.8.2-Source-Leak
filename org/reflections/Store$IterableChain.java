package org.reflections;

import java.util.*;
import com.google.common.collect.*;

private static class IterableChain<T> implements Iterable<T>
{
    private final List<Iterable<T>> chain;
    
    private IterableChain() {
        super();
        this.chain = (List<Iterable<T>>)Lists.newArrayList();
    }
    
    private void addAll(final Iterable<T> a1) {
        this.chain.add(a1);
    }
    
    @Override
    public Iterator<T> iterator() {
        return Iterables.concat((Iterable<? extends Iterable<? extends T>>)this.chain).iterator();
    }
    
    IterableChain(final Store$1 a1) {
        this();
    }
    
    static /* bridge */ void access$100(final IterableChain a1, final Iterable a2) {
        a1.addAll(a2);
    }
}
