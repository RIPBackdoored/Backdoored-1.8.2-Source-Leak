package org.reflections;

import com.google.common.base.*;
import java.util.concurrent.*;
import java.util.*;
import com.google.common.collect.*;

public class Store
{
    private transient boolean concurrent;
    private final Map<String, Multimap<String, String>> storeMap;
    
    protected Store() {
        super();
        this.storeMap = new HashMap<String, Multimap<String, String>>();
        this.concurrent = false;
    }
    
    public Store(final Configuration a1) {
        super();
        this.storeMap = new HashMap<String, Multimap<String, String>>();
        this.concurrent = (a1.getExecutorService() != null);
    }
    
    public Set<String> keySet() {
        return this.storeMap.keySet();
    }
    
    public Multimap<String, String> getOrCreate(final String v2) {
        Multimap<String, String> v3 = this.storeMap.get(v2);
        if (v3 == null) {
            final SetMultimap<String, String> a1 = (SetMultimap<String, String>)Multimaps.newSetMultimap((Map)new HashMap(), (Supplier)new Supplier<Set<String>>() {
                final /* synthetic */ Store this$0;
                
                Store$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Set<String> get() {
                    return (Set<String>)Sets.newSetFromMap((Map)new ConcurrentHashMap());
                }
                
                @Override
                public /* bridge */ Object get() {
                    return this.get();
                }
            });
            v3 = (Multimap<String, String>)(this.concurrent ? Multimaps.synchronizedSetMultimap((SetMultimap)a1) : a1);
            this.storeMap.put(v2, v3);
        }
        return v3;
    }
    
    public Multimap<String, String> get(final String a1) {
        final Multimap<String, String> v1 = this.storeMap.get(a1);
        if (v1 == null) {
            throw new ReflectionsException("Scanner " + a1 + " was not configured");
        }
        return v1;
    }
    
    public Iterable<String> get(final String a1, final String... a2) {
        return this.get(a1, Arrays.asList(a2));
    }
    
    public Iterable<String> get(final String v1, final Iterable<String> v2) {
        final Multimap<String, String> v3 = this.get(v1);
        final IterableChain<String> v4 = new IterableChain<String>();
        for (final String a1 : v2) {
            ((IterableChain<Object>)v4).addAll((Iterable)v3.get(a1));
        }
        return v4;
    }
    
    private Iterable<String> getAllIncluding(final String v1, final Iterable<String> v2, final IterableChain<String> v3) {
        ((IterableChain<Object>)v3).addAll((Iterable)v2);
        for (final String a2 : v2) {
            final Iterable<String> a3 = this.get(v1, a2);
            if (a3.iterator().hasNext()) {
                this.getAllIncluding(v1, a3, v3);
            }
        }
        return v3;
    }
    
    public Iterable<String> getAll(final String a1, final String a2) {
        return this.getAllIncluding(a1, this.get(a1, a2), new IterableChain<String>());
    }
    
    public Iterable<String> getAll(final String a1, final Iterable<String> a2) {
        return this.getAllIncluding(a1, this.get(a1, a2), new IterableChain<String>());
    }
    
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
}
