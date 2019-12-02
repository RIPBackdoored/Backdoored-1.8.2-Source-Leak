package com.google.api.client.util;

import java.util.*;

public final class Maps
{
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
    
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }
    
    public static <K extends java.lang.Object, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }
    
    private Maps() {
        super();
    }
}
