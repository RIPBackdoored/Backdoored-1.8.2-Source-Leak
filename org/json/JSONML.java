package org.json;

import java.util.*;

public class JSONML
{
    public JSONML() {
        super();
    }
    
    private static Object parse(final XMLTokener a4, final boolean v1, final JSONArray v2, final boolean v3) throws JSONException {
        String v4 = null;
        JSONArray v5 = null;
        JSONObject v6 = null;
        String v7 = null;
        while (a4.more()) {
            Object v8 = a4.nextContent();
            if (v8 == XML.LT) {
                v8 = a4.nextToken();
                if (v8 instanceof Character) {
                    if (v8 == XML.SLASH) {
                        v8 = a4.nextToken();
                        if (!(v8 instanceof String)) {
                            throw new JSONException("Expected a closing name instead of '" + v8 + "'.");
                        }
                        if (a4.nextToken() != XML.GT) {
                            throw a4.syntaxError("Misshaped close tag");
                        }
                        return v8;
                    }
                    else if (v8 == XML.BANG) {
                        final char a5 = a4.next();
                        if (a5 == '-') {
                            if (a4.next() == '-') {
                                a4.skipPast("-->");
                            }
                            else {
                                a4.back();
                            }
                        }
                        else if (a5 == '[') {
                            v8 = a4.nextToken();
                            if (!v8.equals("CDATA") || a4.next() != '[') {
                                throw a4.syntaxError("Expected 'CDATA['");
                            }
                            if (v2 == null) {
                                continue;
                            }
                            v2.put(a4.nextCDATA());
                        }
                        else {
                            int a6 = 1;
                            do {
                                v8 = a4.nextMeta();
                                if (v8 == null) {
                                    throw a4.syntaxError("Missing '>' after '<!'.");
                                }
                                if (v8 == XML.LT) {
                                    ++a6;
                                }
                                else {
                                    if (v8 != XML.GT) {
                                        continue;
                                    }
                                    --a6;
                                }
                            } while (a6 > 0);
                        }
                    }
                    else {
                        if (v8 != XML.QUEST) {
                            throw a4.syntaxError("Misshaped tag");
                        }
                        a4.skipPast("?>");
                    }
                }
                else {
                    if (!(v8 instanceof String)) {
                        throw a4.syntaxError("Bad tagName '" + v8 + "'.");
                    }
                    v7 = (String)v8;
                    v5 = new JSONArray();
                    v6 = new JSONObject();
                    if (v1) {
                        v5.put(v7);
                        if (v2 != null) {
                            v2.put(v5);
                        }
                    }
                    else {
                        v6.put("tagName", v7);
                        if (v2 != null) {
                            v2.put(v6);
                        }
                    }
                    v8 = null;
                    while (true) {
                        if (v8 == null) {
                            v8 = a4.nextToken();
                        }
                        if (v8 == null) {
                            throw a4.syntaxError("Misshaped tag");
                        }
                        if (!(v8 instanceof String)) {
                            if (v1 && v6.length() > 0) {
                                v5.put(v6);
                            }
                            if (v8 == XML.SLASH) {
                                if (a4.nextToken() != XML.GT) {
                                    throw a4.syntaxError("Misshaped tag");
                                }
                                if (v2 != null) {
                                    break;
                                }
                                if (v1) {
                                    return v5;
                                }
                                return v6;
                            }
                            else {
                                if (v8 != XML.GT) {
                                    throw a4.syntaxError("Misshaped tag");
                                }
                                v4 = (String)parse(a4, v1, v5, v3);
                                if (v4 == null) {
                                    break;
                                }
                                if (!v4.equals(v7)) {
                                    throw a4.syntaxError("Mismatched '" + v7 + "' and '" + v4 + "'");
                                }
                                v7 = null;
                                if (!v1 && v5.length() > 0) {
                                    v6.put("childNodes", v5);
                                }
                                if (v2 != null) {
                                    break;
                                }
                                if (v1) {
                                    return v5;
                                }
                                return v6;
                            }
                        }
                        else {
                            final String a7 = (String)v8;
                            if (!v1 && ("tagName".equals(a7) || "childNode".equals(a7))) {
                                throw a4.syntaxError("Reserved attribute.");
                            }
                            v8 = a4.nextToken();
                            if (v8 == XML.EQ) {
                                v8 = a4.nextToken();
                                if (!(v8 instanceof String)) {
                                    throw a4.syntaxError("Missing value");
                                }
                                v6.accumulate(a7, v3 ? ((String)v8) : XML.stringToValue((String)v8));
                                v8 = null;
                            }
                            else {
                                v6.accumulate(a7, "");
                            }
                        }
                    }
                }
            }
            else {
                if (v2 == null) {
                    continue;
                }
                v2.put((v8 instanceof String) ? (v3 ? XML.unescape((String)v8) : XML.stringToValue((String)v8)) : v8);
            }
        }
        throw a4.syntaxError("Bad XML");
    }
    
    public static JSONArray toJSONArray(final String a1) throws JSONException {
        return (JSONArray)parse(new XMLTokener(a1), true, null, false);
    }
    
    public static JSONArray toJSONArray(final String a1, final boolean a2) throws JSONException {
        return (JSONArray)parse(new XMLTokener(a1), true, null, a2);
    }
    
    public static JSONArray toJSONArray(final XMLTokener a1, final boolean a2) throws JSONException {
        return (JSONArray)parse(a1, true, null, a2);
    }
    
    public static JSONArray toJSONArray(final XMLTokener a1) throws JSONException {
        return (JSONArray)parse(a1, true, null, false);
    }
    
    public static JSONObject toJSONObject(final String a1) throws JSONException {
        return (JSONObject)parse(new XMLTokener(a1), false, null, false);
    }
    
    public static JSONObject toJSONObject(final String a1, final boolean a2) throws JSONException {
        return (JSONObject)parse(new XMLTokener(a1), false, null, a2);
    }
    
    public static JSONObject toJSONObject(final XMLTokener a1) throws JSONException {
        return (JSONObject)parse(a1, false, null, false);
    }
    
    public static JSONObject toJSONObject(final XMLTokener a1, final boolean a2) throws JSONException {
        return (JSONObject)parse(a1, false, null, a2);
    }
    
    public static String toString(final JSONArray v-7) throws JSONException {
        final StringBuilder sb = new StringBuilder();
        String s = v-7.getString(0);
        XML.noSpace(s);
        s = XML.escape(s);
        sb.append('<');
        sb.append(s);
        Object o = v-7.opt(1);
        int i;
        if (o instanceof JSONObject) {
            i = 2;
            final JSONObject jsonObject = (JSONObject)o;
            for (final String v1 : jsonObject.keySet()) {
                final Object a1 = jsonObject.opt(v1);
                XML.noSpace(v1);
                if (a1 != null) {
                    sb.append(' ');
                    sb.append(XML.escape(v1));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(a1.toString()));
                    sb.append('\"');
                }
            }
        }
        else {
            i = 1;
        }
        final int length = v-7.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        }
        else {
            sb.append('>');
            do {
                o = v-7.get(i);
                ++i;
                if (o != null) {
                    if (o instanceof String) {
                        sb.append(XML.escape(o.toString()));
                    }
                    else if (o instanceof JSONObject) {
                        sb.append(toString((JSONObject)o));
                    }
                    else if (o instanceof JSONArray) {
                        sb.append(toString((JSONArray)o));
                    }
                    else {
                        sb.append(o.toString());
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(s);
            sb.append('>');
        }
        return sb.toString();
    }
    
    public static String toString(final JSONObject v-8) throws JSONException {
        final StringBuilder sb = new StringBuilder();
        String s = v-8.optString("tagName");
        if (s == null) {
            return XML.escape(v-8.toString());
        }
        XML.noSpace(s);
        s = XML.escape(s);
        sb.append('<');
        sb.append(s);
        for (final String v1 : v-8.keySet()) {
            if (!"tagName".equals(v1) && !"childNodes".equals(v1)) {
                XML.noSpace(v1);
                final Object a1 = v-8.opt(v1);
                if (a1 == null) {
                    continue;
                }
                sb.append(' ');
                sb.append(XML.escape(v1));
                sb.append('=');
                sb.append('\"');
                sb.append(XML.escape(a1.toString()));
                sb.append('\"');
            }
        }
        final JSONArray optJSONArray = v-8.optJSONArray("childNodes");
        if (optJSONArray == null) {
            sb.append('/');
            sb.append('>');
        }
        else {
            sb.append('>');
            for (int length = optJSONArray.length(), i = 0; i < length; ++i) {
                final Object value = optJSONArray.get(i);
                if (value != null) {
                    if (value instanceof String) {
                        sb.append(XML.escape(value.toString()));
                    }
                    else if (value instanceof JSONObject) {
                        sb.append(toString((JSONObject)value));
                    }
                    else if (value instanceof JSONArray) {
                        sb.append(toString((JSONArray)value));
                    }
                    else {
                        sb.append(value.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(s);
            sb.append('>');
        }
        return sb.toString();
    }
}
