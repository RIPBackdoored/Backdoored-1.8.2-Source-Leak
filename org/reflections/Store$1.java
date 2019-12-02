package org.reflections;

import com.google.common.base.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import java.util.*;

class Store$1 implements Supplier<Set<String>> {
    final /* synthetic */ Store this$0;
    
    Store$1(final Store a1) {
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
}