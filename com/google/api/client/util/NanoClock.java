package com.google.api.client.util;

public interface NanoClock
{
    public static final NanoClock SYSTEM = new NanoClock() {
        NanoClock$1() {
            super();
        }
        
        @Override
        public long nanoTime() {
            return System.nanoTime();
        }
    };
    
    long nanoTime();
}
