package com.google.api.client.util;

public final class Throwables
{
    public static RuntimeException propagate(final Throwable a1) {
        return com.google.api.client.repackaged.com.google.common.base.Throwables.propagate(a1);
    }
    
    public static void propagateIfPossible(final Throwable a1) {
        if (a1 != null) {
            com.google.api.client.repackaged.com.google.common.base.Throwables.throwIfUnchecked(a1);
        }
    }
    
    public static <X extends Throwable> void propagateIfPossible(final Throwable a1, final Class<X> a2) throws X, Throwable {
        com.google.api.client.repackaged.com.google.common.base.Throwables.propagateIfPossible(a1, a2);
    }
    
    private Throwables() {
        super();
    }
}
