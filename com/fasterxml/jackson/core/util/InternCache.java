package com.fasterxml.jackson.core.util;

import java.util.concurrent.*;

public final class InternCache extends ConcurrentHashMap<String, String>
{
    private static final long serialVersionUID = 1L;
    private static final int MAX_ENTRIES = 180;
    public static final InternCache instance;
    private final Object lock;
    
    private InternCache() {
        super(180, 0.8f, 4);
        this.lock = new Object();
    }
    
    public String intern(final String a1) {
        String v1 = ((ConcurrentHashMap<K, String>)this).get(a1);
        if (v1 != null) {
            return v1;
        }
        if (this.size() >= 180) {
            synchronized (this.lock) {
                if (this.size() >= 180) {
                    this.clear();
                }
            }
        }
        v1 = a1.intern();
        this.put(v1, v1);
        return v1;
    }
    
    static {
        instance = new InternCache();
    }
}
