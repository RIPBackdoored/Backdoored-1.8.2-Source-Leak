package com.fasterxml.jackson.core.util;

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
