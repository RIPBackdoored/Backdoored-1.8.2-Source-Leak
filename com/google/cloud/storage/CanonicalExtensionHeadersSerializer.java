package com.google.cloud.storage;

import java.util.*;

public class CanonicalExtensionHeadersSerializer
{
    private static final char HEADER_SEPARATOR = ':';
    private static final char HEADER_NAME_SEPARATOR = ';';
    private final Storage.SignUrlOption.SignatureVersion signatureVersion;
    
    public CanonicalExtensionHeadersSerializer(final Storage.SignUrlOption.SignatureVersion a1) {
        super();
        this.signatureVersion = a1;
    }
    
    public CanonicalExtensionHeadersSerializer() {
        super();
        this.signatureVersion = Storage.SignUrlOption.SignatureVersion.V2;
    }
    
    public StringBuilder serialize(final Map<String, String> v2) {
        final StringBuilder v3 = new StringBuilder();
        if (v2 == null || v2.isEmpty()) {
            return v3;
        }
        final Map<String, String> v4 = this.getLowercaseHeaders(v2);
        final List<String> v5 = new ArrayList<String>(v4.keySet());
        Collections.sort(v5);
        for (final String a1 : v5) {
            v3.append(a1).append(':').append(v4.get(a1).trim().replaceAll("[\\s]{2,}", " ").replaceAll("(\\t|\\r?\\n)+", " ")).append('\n');
        }
        return v3;
    }
    
    public StringBuilder serializeHeaderNames(final Map<String, String> v2) {
        final StringBuilder v3 = new StringBuilder();
        if (v2 == null || v2.isEmpty()) {
            return v3;
        }
        final Map<String, String> v4 = this.getLowercaseHeaders(v2);
        final List<String> v5 = new ArrayList<String>(v4.keySet());
        Collections.sort(v5);
        for (final String a1 : v5) {
            v3.append(a1).append(';');
        }
        v3.setLength(v3.length() - 1);
        return v3;
    }
    
    private Map<String, String> getLowercaseHeaders(final Map<String, String> v-2) {
        final Map<String, String> map = new HashMap<String, String>();
        for (final String v1 : new ArrayList<String>(v-2.keySet())) {
            final String a1 = v1.toLowerCase();
            if (Storage.SignUrlOption.SignatureVersion.V2.equals(this.signatureVersion)) {
                if ("x-goog-encryption-key".equals(a1)) {
                    continue;
                }
                if ("x-goog-encryption-key-sha256".equals(a1)) {
                    continue;
                }
            }
            map.put(a1, v-2.get(v1));
        }
        return map;
    }
}
