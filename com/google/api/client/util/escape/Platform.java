package com.google.api.client.util.escape;

final class Platform
{
    private static final ThreadLocal<char[]> DEST_TL;
    
    private Platform() {
        super();
    }
    
    static char[] charBufferFromThreadLocal() {
        return Platform.DEST_TL.get();
    }
    
    static {
        DEST_TL = new ThreadLocal<char[]>() {
            Platform$1() {
                super();
            }
            
            @Override
            protected char[] initialValue() {
                return new char[1024];
            }
            
            @Override
            protected /* bridge */ Object initialValue() {
                return this.initialValue();
            }
        };
    }
}
