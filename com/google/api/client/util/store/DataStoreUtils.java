package com.google.api.client.util.store;

import java.io.*;
import java.util.*;

public final class DataStoreUtils
{
    public static String toString(final DataStore<?> v0) {
        try {
            final StringBuilder v = new StringBuilder();
            v.append('{');
            boolean v2 = true;
            for (final String a1 : v0.keySet()) {
                if (v2) {
                    v2 = false;
                }
                else {
                    v.append(", ");
                }
                v.append(a1).append('=').append(v0.get(a1));
            }
            return v.append('}').toString();
        }
        catch (IOException v3) {
            throw new RuntimeException(v3);
        }
    }
    
    private DataStoreUtils() {
        super();
    }
}
