package org.json.simple;

import org.json.simple.parser.*;
import java.io.*;
import java.util.*;

public class JSONValue
{
    public JSONValue() {
        super();
    }
    
    public static Object parse(final Reader v0) {
        try {
            final JSONParser a1 = new JSONParser();
            return a1.parse(v0);
        }
        catch (Exception v) {
            return null;
        }
    }
    
    public static Object parse(final String a1) {
        final StringReader v1 = new StringReader(a1);
        return parse(v1);
    }
    
    public static Object parseWithException(final Reader a1) throws IOException, ParseException {
        final JSONParser v1 = new JSONParser();
        return v1.parse(a1);
    }
    
    public static Object parseWithException(final String a1) throws ParseException {
        final JSONParser v1 = new JSONParser();
        return v1.parse(a1);
    }
    
    public static void writeJSONString(final Object a1, final Writer a2) throws IOException {
        if (a1 == null) {
            a2.write("null");
            return;
        }
        if (a1 instanceof String) {
            a2.write(34);
            a2.write(escape((String)a1));
            a2.write(34);
            return;
        }
        if (a1 instanceof Double) {
            if (((Double)a1).isInfinite() || ((Double)a1).isNaN()) {
                a2.write("null");
            }
            else {
                a2.write(a1.toString());
            }
            return;
        }
        if (a1 instanceof Float) {
            if (((Float)a1).isInfinite() || ((Float)a1).isNaN()) {
                a2.write("null");
            }
            else {
                a2.write(a1.toString());
            }
            return;
        }
        if (a1 instanceof Number) {
            a2.write(a1.toString());
            return;
        }
        if (a1 instanceof Boolean) {
            a2.write(a1.toString());
            return;
        }
        if (a1 instanceof JSONStreamAware) {
            ((JSONStreamAware)a1).writeJSONString(a2);
            return;
        }
        if (a1 instanceof JSONAware) {
            a2.write(((JSONAware)a1).toJSONString());
            return;
        }
        if (a1 instanceof Map) {
            JSONObject.writeJSONString((Map)a1, a2);
            return;
        }
        if (a1 instanceof List) {
            JSONArray.writeJSONString((List)a1, a2);
            return;
        }
        a2.write(a1.toString());
    }
    
    public static String toJSONString(final Object a1) {
        if (a1 == null) {
            return "null";
        }
        if (a1 instanceof String) {
            return "\"" + escape((String)a1) + "\"";
        }
        if (a1 instanceof Double) {
            if (((Double)a1).isInfinite() || ((Double)a1).isNaN()) {
                return "null";
            }
            return a1.toString();
        }
        else if (a1 instanceof Float) {
            if (((Float)a1).isInfinite() || ((Float)a1).isNaN()) {
                return "null";
            }
            return a1.toString();
        }
        else {
            if (a1 instanceof Number) {
                return a1.toString();
            }
            if (a1 instanceof Boolean) {
                return a1.toString();
            }
            if (a1 instanceof JSONAware) {
                return ((JSONAware)a1).toJSONString();
            }
            if (a1 instanceof Map) {
                return JSONObject.toJSONString((Map)a1);
            }
            if (a1 instanceof List) {
                return JSONArray.toJSONString((List)a1);
            }
            return a1.toString();
        }
    }
    
    public static String escape(final String a1) {
        if (a1 == null) {
            return null;
        }
        final StringBuffer v1 = new StringBuffer();
        escape(a1, v1);
        return v1.toString();
    }
    
    static void escape(final String v-2, final StringBuffer v-1) {
        for (int v0 = 0; v0 < v-2.length(); ++v0) {
            final char v2 = v-2.charAt(v0);
            switch (v2) {
                case '\"': {
                    v-1.append("\\\"");
                    break;
                }
                case '\\': {
                    v-1.append("\\\\");
                    break;
                }
                case '\b': {
                    v-1.append("\\b");
                    break;
                }
                case '\f': {
                    v-1.append("\\f");
                    break;
                }
                case '\n': {
                    v-1.append("\\n");
                    break;
                }
                case '\r': {
                    v-1.append("\\r");
                    break;
                }
                case '\t': {
                    v-1.append("\\t");
                    break;
                }
                case '/': {
                    v-1.append("\\/");
                    break;
                }
                default: {
                    if ((v2 >= '\0' && v2 <= '\u001f') || (v2 >= '\u007f' && v2 <= '\u009f') || (v2 >= '\u2000' && v2 <= '\u20ff')) {
                        final String a2 = Integer.toHexString(v2);
                        v-1.append("\\u");
                        for (int a3 = 0; a3 < 4 - a2.length(); ++a3) {
                            v-1.append('0');
                        }
                        v-1.append(a2.toUpperCase());
                        break;
                    }
                    v-1.append(v2);
                    break;
                }
            }
        }
    }
}
