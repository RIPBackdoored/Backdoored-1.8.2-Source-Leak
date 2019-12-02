package com.sun.jna;

static final class Native$1 implements Callback.UncaughtExceptionHandler {
    Native$1() {
        super();
    }
    
    @Override
    public void uncaughtException(final Callback a1, final Throwable a2) {
        System.err.println("JNA: Callback " + a1 + " threw the following exception:");
        a2.printStackTrace();
    }
}