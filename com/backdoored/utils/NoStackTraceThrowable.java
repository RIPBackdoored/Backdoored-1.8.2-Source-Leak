package com.backdoored.utils;

public class NoStackTraceThrowable extends Error
{
    public NoStackTraceThrowable() {
        this("");
    }
    
    public NoStackTraceThrowable(final String s) {
        super(s);
        this.setStackTrace(new StackTraceElement[0]);
    }
    
    @Override
    public String toString() {
        return "Go away john";
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
