package org.json.simple;

import java.util.*;
import java.io.*;

public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = -503443796854799292L;
    
    public JSONObject() {
        super();
    }
    
    public JSONObject(final Map a1) {
        super(a1);
    }
    
    public static void writeJSONString(final Map a2, final Writer v1) throws IOException {
        if (a2 == null) {
            v1.write("null");
            return;
        }
        boolean v2 = true;
        final Iterator v3 = a2.entrySet().iterator();
        v1.write(123);
        while (v3.hasNext()) {
            if (v2) {
                v2 = false;
            }
            else {
                v1.write(44);
            }
            final Entry a3 = v3.next();
            v1.write(34);
            v1.write(escape(String.valueOf(a3.getKey())));
            v1.write(34);
            v1.write(58);
            JSONValue.writeJSONString(a3.getValue(), v1);
        }
        v1.write(125);
    }
    
    public void writeJSONString(final Writer a1) throws IOException {
        writeJSONString(this, a1);
    }
    
    public static String toJSONString(final Map v1) {
        if (v1 == null) {
            return "null";
        }
        final StringBuffer v2 = new StringBuffer();
        boolean v3 = true;
        final Iterator v4 = v1.entrySet().iterator();
        v2.append('{');
        while (v4.hasNext()) {
            if (v3) {
                v3 = false;
            }
            else {
                v2.append(',');
            }
            final Entry a1 = v4.next();
            toJSONString(String.valueOf(a1.getKey()), a1.getValue(), v2);
        }
        v2.append('}');
        return v2.toString();
    }
    
    public String toJSONString() {
        return toJSONString(this);
    }
    
    private static String toJSONString(final String a1, final Object a2, final StringBuffer a3) {
        a3.append('\"');
        if (a1 == null) {
            a3.append("null");
        }
        else {
            JSONValue.escape(a1, a3);
        }
        a3.append('\"').append(':');
        a3.append(JSONValue.toJSONString(a2));
        return a3.toString();
    }
    
    public String toString() {
        return this.toJSONString();
    }
    
    public static String toString(final String a1, final Object a2) {
        final StringBuffer v1 = new StringBuffer();
        toJSONString(a1, a2, v1);
        return v1.toString();
    }
    
    public static String escape(final String a1) {
        return JSONValue.escape(a1);
    }
}
