package com.google.api.client.util;

import java.util.*;

public final class Collections2
{
    static <T> Collection<T> cast(final Iterable<T> a1) {
        return (Collection<T>)(Collection)a1;
    }
    
    private Collections2() {
        super();
    }
}
