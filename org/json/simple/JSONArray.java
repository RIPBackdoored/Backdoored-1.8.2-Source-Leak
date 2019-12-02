package org.json.simple;

import java.util.*;
import java.io.*;

public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = 3957988303675231981L;
    
    public JSONArray() {
        super();
    }
    
    public static void writeJSONString(final List a2, final Writer v1) throws IOException {
        if (a2 == null) {
            v1.write("null");
            return;
        }
        boolean v2 = true;
        final Iterator v3 = a2.iterator();
        v1.write(91);
        while (v3.hasNext()) {
            if (v2) {
                v2 = false;
            }
            else {
                v1.write(44);
            }
            final Object a3 = v3.next();
            if (a3 == null) {
                v1.write("null");
            }
            else {
                JSONValue.writeJSONString(a3, v1);
            }
        }
        v1.write(93);
    }
    
    public void writeJSONString(final Writer a1) throws IOException {
        writeJSONString(this, a1);
    }
    
    public static String toJSONString(final List v1) {
        if (v1 == null) {
            return "null";
        }
        boolean v2 = true;
        final StringBuffer v3 = new StringBuffer();
        final Iterator v4 = v1.iterator();
        v3.append('[');
        while (v4.hasNext()) {
            if (v2) {
                v2 = false;
            }
            else {
                v3.append(',');
            }
            final Object a1 = v4.next();
            if (a1 == null) {
                v3.append("null");
            }
            else {
                v3.append(JSONValue.toJSONString(a1));
            }
        }
        v3.append(']');
        return v3.toString();
    }
    
    public String toJSONString() {
        return toJSONString(this);
    }
    
    public String toString() {
        return this.toJSONString();
    }
}
