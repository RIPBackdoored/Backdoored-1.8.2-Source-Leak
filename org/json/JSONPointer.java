package org.json;

import java.io.*;
import java.util.*;
import java.net.*;

public class JSONPointer
{
    private static final String ENCODING = "utf-8";
    private final List<String> refTokens;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public JSONPointer(final String v0) {
        super();
        if (v0 == null) {
            throw new NullPointerException("pointer cannot be null");
        }
        if (v0.isEmpty() || v0.equals("#")) {
            this.refTokens = Collections.emptyList();
            return;
        }
        String v = null;
        Label_0105: {
            if (v0.startsWith("#/")) {
                v = v0.substring(2);
                try {
                    v = URLDecoder.decode(v, "utf-8");
                    break Label_0105;
                }
                catch (UnsupportedEncodingException a1) {
                    throw new RuntimeException(a1);
                }
            }
            if (!v0.startsWith("/")) {
                throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
            }
            v = v0.substring(1);
        }
        this.refTokens = new ArrayList<String>();
        int v2 = -1;
        int v3 = 0;
        do {
            v3 = v2 + 1;
            v2 = v.indexOf(47, v3);
            if (v3 == v2 || v3 == v.length()) {
                this.refTokens.add("");
            }
            else if (v2 >= 0) {
                final String v4 = v.substring(v3, v2);
                this.refTokens.add(this.unescape(v4));
            }
            else {
                final String v4 = v.substring(v3);
                this.refTokens.add(this.unescape(v4));
            }
        } while (v2 >= 0);
    }
    
    public JSONPointer(final List<String> a1) {
        super();
        this.refTokens = new ArrayList<String>(a1);
    }
    
    private String unescape(final String a1) {
        return a1.replace("~1", "/").replace("~0", "~").replace("\\\"", "\"").replace("\\\\", "\\");
    }
    
    public Object queryFrom(final Object v2) throws JSONPointerException {
        if (this.refTokens.isEmpty()) {
            return v2;
        }
        Object v3 = v2;
        for (final String a1 : this.refTokens) {
            if (v3 instanceof JSONObject) {
                v3 = ((JSONObject)v3).opt(this.unescape(a1));
            }
            else {
                if (!(v3 instanceof JSONArray)) {
                    throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", v3, a1));
                }
                v3 = this.readByIndexToken(v3, a1);
            }
        }
        return v3;
    }
    
    private Object readByIndexToken(final Object v-2, final String v-1) throws JSONPointerException {
        try {
            final int a2 = Integer.parseInt(v-1);
            final JSONArray v1 = (JSONArray)v-2;
            if (a2 >= v1.length()) {
                throw new JSONPointerException(String.format("index %s is out of bounds - the array has %d elements", v-1, v1.length()));
            }
            try {
                return v1.get(a2);
            }
            catch (JSONException a3) {
                throw new JSONPointerException("Error reading value at index position " + a2, a3);
            }
        }
        catch (NumberFormatException v2) {
            throw new JSONPointerException(String.format("%s is not an array index", v-1), v2);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (final String v1 : this.refTokens) {
            sb.append('/').append(this.escape(v1));
        }
        return sb.toString();
    }
    
    private String escape(final String a1) {
        return a1.replace("~", "~0").replace("/", "~1").replace("\\", "\\\\").replace("\"", "\\\"");
    }
    
    public String toURIFragment() {
        try {
            final StringBuilder sb = new StringBuilder("#");
            for (final String v1 : this.refTokens) {
                sb.append('/').append(URLEncoder.encode(v1, "utf-8"));
            }
            return sb.toString();
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static class Builder
    {
        private final List<String> refTokens;
        
        public Builder() {
            super();
            this.refTokens = new ArrayList<String>();
        }
        
        public JSONPointer build() {
            return new JSONPointer(this.refTokens);
        }
        
        public Builder append(final String a1) {
            if (a1 == null) {
                throw new NullPointerException("token cannot be null");
            }
            this.refTokens.add(a1);
            return this;
        }
        
        public Builder append(final int a1) {
            this.refTokens.add(String.valueOf(a1));
            return this;
        }
    }
}
