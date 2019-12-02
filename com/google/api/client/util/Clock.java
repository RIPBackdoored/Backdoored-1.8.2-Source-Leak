package com.google.api.client.util;

public interface Clock
{
    public static final Clock SYSTEM = new Clock() {
        Clock$1() {
            super();
        }
        
        @Override
        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    };
    
    long currentTimeMillis();
}
