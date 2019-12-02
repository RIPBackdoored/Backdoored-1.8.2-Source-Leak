package com.sun.jna;

public class CallbackThreadInitializer
{
    private boolean daemon;
    private boolean detach;
    private String name;
    private ThreadGroup group;
    
    public CallbackThreadInitializer() {
        this(true);
    }
    
    public CallbackThreadInitializer(final boolean a1) {
        this(a1, false);
    }
    
    public CallbackThreadInitializer(final boolean a1, final boolean a2) {
        this(a1, a2, null);
    }
    
    public CallbackThreadInitializer(final boolean a1, final boolean a2, final String a3) {
        this(a1, a2, a3, null);
    }
    
    public CallbackThreadInitializer(final boolean a1, final boolean a2, final String a3, final ThreadGroup a4) {
        super();
        this.daemon = a1;
        this.detach = a2;
        this.name = a3;
        this.group = a4;
    }
    
    public String getName(final Callback a1) {
        return this.name;
    }
    
    public ThreadGroup getThreadGroup(final Callback a1) {
        return this.group;
    }
    
    public boolean isDaemon(final Callback a1) {
        return this.daemon;
    }
    
    public boolean detach(final Callback a1) {
        return this.detach;
    }
}
