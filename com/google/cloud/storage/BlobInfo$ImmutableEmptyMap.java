package com.google.cloud.storage;

import java.util.*;
import com.google.common.collect.*;

public static final class ImmutableEmptyMap<K, V> extends AbstractMap<K, V>
{
    public ImmutableEmptyMap() {
        super();
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return (Set<Map.Entry<K, V>>)ImmutableSet.of();
    }
}
