package com.fasterxml.jackson.core.util;

import java.lang.ref.*;
import java.util.concurrent.*;
import java.util.*;

class ThreadLocalBufferManager
{
    private final Object RELEASE_LOCK;
    private final Map<SoftReference<BufferRecycler>, Boolean> _trackedRecyclers;
    private final ReferenceQueue<BufferRecycler> _refQueue;
    
    ThreadLocalBufferManager() {
        super();
        this.RELEASE_LOCK = new Object();
        this._trackedRecyclers = new ConcurrentHashMap<SoftReference<BufferRecycler>, Boolean>();
        this._refQueue = new ReferenceQueue<BufferRecycler>();
    }
    
    public static ThreadLocalBufferManager instance() {
        return ThreadLocalBufferManagerHolder.manager;
    }
    
    public int releaseBuffers() {
        synchronized (this.RELEASE_LOCK) {
            int n = 0;
            this.removeSoftRefsClearedByGc();
            for (final SoftReference<BufferRecycler> v2 : this._trackedRecyclers.keySet()) {
                v2.clear();
                ++n;
            }
            this._trackedRecyclers.clear();
            return n;
        }
    }
    
    public SoftReference<BufferRecycler> wrapAndTrack(final BufferRecycler a1) {
        final SoftReference<BufferRecycler> v1 = new SoftReference<BufferRecycler>(a1, this._refQueue);
        this._trackedRecyclers.put(v1, true);
        this.removeSoftRefsClearedByGc();
        return v1;
    }
    
    private void removeSoftRefsClearedByGc() {
        SoftReference<?> v1;
        while ((v1 = (SoftReference<?>)(SoftReference)this._refQueue.poll()) != null) {
            this._trackedRecyclers.remove(v1);
        }
    }
    
    private static final class ThreadLocalBufferManagerHolder
    {
        static final ThreadLocalBufferManager manager;
        
        private ThreadLocalBufferManagerHolder() {
            super();
        }
        
        static {
            manager = new ThreadLocalBufferManager();
        }
    }
}
