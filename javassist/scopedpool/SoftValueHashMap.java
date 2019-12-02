package javassist.scopedpool;

import java.util.*;
import java.lang.ref.*;

public class SoftValueHashMap extends AbstractMap implements Map
{
    private Map hash;
    private ReferenceQueue queue;
    
    @Override
    public Set entrySet() {
        this.processQueue();
        return this.hash.entrySet();
    }
    
    private void processQueue() {
        SoftValueRef v1;
        while ((v1 = (SoftValueRef)this.queue.poll()) != null) {
            if (v1 == this.hash.get(v1.key)) {
                this.hash.remove(v1.key);
            }
        }
    }
    
    public SoftValueHashMap(final int a1, final float a2) {
        super();
        this.queue = new ReferenceQueue();
        this.hash = new HashMap(a1, a2);
    }
    
    public SoftValueHashMap(final int a1) {
        super();
        this.queue = new ReferenceQueue();
        this.hash = new HashMap(a1);
    }
    
    public SoftValueHashMap() {
        super();
        this.queue = new ReferenceQueue();
        this.hash = new HashMap();
    }
    
    public SoftValueHashMap(final Map a1) {
        this(Math.max(2 * a1.size(), 11), 0.75f);
        this.putAll(a1);
    }
    
    @Override
    public int size() {
        this.processQueue();
        return this.hash.size();
    }
    
    @Override
    public boolean isEmpty() {
        this.processQueue();
        return this.hash.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object a1) {
        this.processQueue();
        return this.hash.containsKey(a1);
    }
    
    @Override
    public Object get(final Object a1) {
        this.processQueue();
        final SoftReference v1 = this.hash.get(a1);
        if (v1 != null) {
            return v1.get();
        }
        return null;
    }
    
    @Override
    public Object put(final Object a1, final Object a2) {
        this.processQueue();
        Object v1 = this.hash.put(a1, create(a1, a2, this.queue));
        if (v1 != null) {
            v1 = ((SoftReference)v1).get();
        }
        return v1;
    }
    
    @Override
    public Object remove(final Object a1) {
        this.processQueue();
        return this.hash.remove(a1);
    }
    
    @Override
    public void clear() {
        this.processQueue();
        this.hash.clear();
    }
    
    private static class SoftValueRef extends SoftReference
    {
        public Object key;
        
        private SoftValueRef(final Object a1, final Object a2, final ReferenceQueue a3) {
            super(a2, a3);
            this.key = a1;
        }
        
        private static SoftValueRef create(final Object a1, final Object a2, final ReferenceQueue a3) {
            if (a2 == null) {
                return null;
            }
            return new SoftValueRef(a1, a2, a3);
        }
        
        static /* bridge */ SoftValueRef access$000(final Object a1, final Object a2, final ReferenceQueue a3) {
            return create(a1, a2, a3);
        }
    }
}
