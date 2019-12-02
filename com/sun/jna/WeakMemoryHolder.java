package com.sun.jna;

import java.util.*;
import java.lang.ref.*;

public class WeakMemoryHolder
{
    ReferenceQueue<Object> referenceQueue;
    IdentityHashMap<Reference<Object>, Memory> backingMap;
    
    public WeakMemoryHolder() {
        super();
        this.referenceQueue = new ReferenceQueue<Object>();
        this.backingMap = new IdentityHashMap<Reference<Object>, Memory>();
    }
    
    public synchronized void put(final Object a1, final Memory a2) {
        this.clean();
        final Reference<Object> v1 = new WeakReference<Object>(a1, this.referenceQueue);
        this.backingMap.put(v1, a2);
    }
    
    public synchronized void clean() {
        for (Reference v1 = this.referenceQueue.poll(); v1 != null; v1 = this.referenceQueue.poll()) {
            this.backingMap.remove(v1);
        }
    }
}
