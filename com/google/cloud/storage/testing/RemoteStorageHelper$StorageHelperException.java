package com.google.cloud.storage.testing;

public static class StorageHelperException extends RuntimeException
{
    private static final long serialVersionUID = -7756074894502258736L;
    
    public StorageHelperException(final String a1) {
        super(a1);
    }
    
    public StorageHelperException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public static StorageHelperException translate(final Exception a1) {
        return new StorageHelperException(a1.getMessage(), a1);
    }
}
