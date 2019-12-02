package com.google.api.client.util;

public final class Preconditions
{
    public static void checkArgument(final boolean a1) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkArgument(a1);
    }
    
    public static void checkArgument(final boolean a1, final Object a2) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkArgument(a1, a2);
    }
    
    public static void checkArgument(final boolean a1, final String a2, final Object... a3) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkArgument(a1, a2, a3);
    }
    
    public static void checkState(final boolean a1) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkState(a1);
    }
    
    public static void checkState(final boolean a1, final Object a2) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkState(a1, a2);
    }
    
    public static void checkState(final boolean a1, final String a2, final Object... a3) {
        com.google.api.client.repackaged.com.google.common.base.Preconditions.checkState(a1, a2, a3);
    }
    
    public static <T> T checkNotNull(final T a1) {
        return com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull(a1);
    }
    
    public static <T> T checkNotNull(final T a1, final Object a2) {
        return com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull(a1, a2);
    }
    
    public static <T> T checkNotNull(final T a1, final String a2, final Object... a3) {
        return com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull(a1, a2, a3);
    }
    
    private Preconditions() {
        super();
    }
}
