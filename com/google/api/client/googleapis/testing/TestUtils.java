package com.google.api.client.googleapis.testing;

import com.google.common.base.*;
import com.google.common.collect.*;
import java.io.*;
import java.net.*;
import java.util.*;

public final class TestUtils
{
    private static final String UTF_8 = "UTF-8";
    
    public static Map<String, String> parseQuery(final String v-5) throws IOException {
        final Map<String, String> map = new HashMap<String, String>();
        final Iterable<String> split = (Iterable<String>)Splitter.on('&').split((CharSequence)v-5);
        for (final String s : split) {
            final List<String> a1 = (List<String>)Lists.newArrayList((Iterable<?>)Splitter.on('=').split((CharSequence)s));
            if (a1.size() != 2) {
                throw new IOException("Invalid Query String");
            }
            final String v1 = URLDecoder.decode(a1.get(0), "UTF-8");
            final String v2 = URLDecoder.decode(a1.get(1), "UTF-8");
            map.put(v1, v2);
        }
        return map;
    }
    
    private TestUtils() {
        super();
    }
}
