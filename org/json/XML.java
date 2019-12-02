package org.json;

import java.util.*;
import java.io.*;

public class XML
{
    public static final Character AMP;
    public static final Character APOS;
    public static final Character BANG;
    public static final Character EQ;
    public static final Character GT;
    public static final Character LT;
    public static final Character QUEST;
    public static final Character QUOT;
    public static final Character SLASH;
    public static final String NULL_ATTR = "xsi:nil";
    
    public XML() {
        super();
    }
    
    private static Iterable<Integer> codePointIterator(final String a1) {
        return new Iterable<Integer>() {
            final /* synthetic */ String val$string;
            
            XML$1() {
                super();
            }
            
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int nextIndex = 0;
                    private int length = a1.length();
                    final /* synthetic */ XML$1 this$0;
                    
                    XML$1$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }
                    
                    @Override
                    public Integer next() {
                        final int v1 = a1.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(v1);
                        return v1;
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                    @Override
                    public /* bridge */ Object next() {
                        return this.next();
                    }
                };
            }
        };
    }
    
    public static String escape(final String v1) {
        final StringBuilder v2 = new StringBuilder(v1.length());
        for (final int a1 : codePointIterator(v1)) {
            switch (a1) {
                case 38: {
                    v2.append("&amp;");
                    continue;
                }
                case 60: {
                    v2.append("&lt;");
                    continue;
                }
                case 62: {
                    v2.append("&gt;");
                    continue;
                }
                case 34: {
                    v2.append("&quot;");
                    continue;
                }
                case 39: {
                    v2.append("&apos;");
                    continue;
                }
                default: {
                    if (mustEscape(a1)) {
                        v2.append("&#x");
                        v2.append(Integer.toHexString(a1));
                        v2.append(';');
                        continue;
                    }
                    v2.appendCodePoint(a1);
                    continue;
                }
            }
        }
        return v2.toString();
    }
    
    private static boolean mustEscape(final int a1) {
        return (Character.isISOControl(a1) && a1 != 9 && a1 != 10 && a1 != 13) || ((a1 < 32 || a1 > 55295) && (a1 < 57344 || a1 > 65533) && (a1 < 65536 || a1 > 1114111));
    }
    
    public static String unescape(final String v-4) {
        final StringBuilder sb = new StringBuilder(v-4.length());
        for (int i = 0, length = v-4.length(); i < length; ++i) {
            final char v0 = v-4.charAt(i);
            if (v0 == '&') {
                final int v2 = v-4.indexOf(59, i);
                if (v2 > i) {
                    final String a1 = v-4.substring(i + 1, v2);
                    sb.append(XMLTokener.unescapeEntity(a1));
                    i += a1.length() + 1;
                }
                else {
                    sb.append(v0);
                }
            }
            else {
                sb.append(v0);
            }
        }
        return sb.toString();
    }
    
    public static void noSpace(final String a1) throws JSONException {
        final int v2 = a1.length();
        if (v2 == 0) {
            throw new JSONException("Empty string.");
        }
        for (int v3 = 0; v3 < v2; ++v3) {
            if (Character.isWhitespace(a1.charAt(v3))) {
                throw new JSONException("'" + a1 + "' contains a space character.");
            }
        }
    }
    
    private static boolean parse(final XMLTokener v-6, final JSONObject v-5, final String v-4, final XMLParserConfiguration v-3) throws JSONException {
        JSONObject v0 = null;
        Object v2 = v-6.nextToken();
        if (v2 == XML.BANG) {
            final char a2 = v-6.next();
            if (a2 == '-') {
                if (v-6.next() == '-') {
                    v-6.skipPast("-->");
                    return false;
                }
                v-6.back();
            }
            else if (a2 == '[') {
                v2 = v-6.nextToken();
                if ("CDATA".equals(v2) && v-6.next() == '[') {
                    final String a3 = v-6.nextCDATA();
                    if (a3.length() > 0) {
                        v-5.accumulate(v-3.cDataTagName, a3);
                    }
                    return false;
                }
                throw v-6.syntaxError("Expected 'CDATA['");
            }
            int a4 = 1;
            do {
                v2 = v-6.nextMeta();
                if (v2 == null) {
                    throw v-6.syntaxError("Missing '>' after '<!'.");
                }
                if (v2 == XML.LT) {
                    ++a4;
                }
                else {
                    if (v2 != XML.GT) {
                        continue;
                    }
                    --a4;
                }
            } while (a4 > 0);
            return false;
        }
        if (v2 == XML.QUEST) {
            v-6.skipPast("?>");
            return false;
        }
        if (v2 == XML.SLASH) {
            v2 = v-6.nextToken();
            if (v-4 == null) {
                throw v-6.syntaxError("Mismatched close tag " + v2);
            }
            if (!v2.equals(v-4)) {
                throw v-6.syntaxError("Mismatched " + v-4 + " and " + v2);
            }
            if (v-6.nextToken() != XML.GT) {
                throw v-6.syntaxError("Misshaped close tag");
            }
            return true;
        }
        else {
            if (v2 instanceof Character) {
                throw v-6.syntaxError("Misshaped tag");
            }
            final String v3 = (String)v2;
            v2 = null;
            v0 = new JSONObject();
            boolean v4 = false;
            while (true) {
                if (v2 == null) {
                    v2 = v-6.nextToken();
                }
                if (v2 instanceof String) {
                    final String a5 = (String)v2;
                    v2 = v-6.nextToken();
                    if (v2 == XML.EQ) {
                        v2 = v-6.nextToken();
                        if (!(v2 instanceof String)) {
                            throw v-6.syntaxError("Missing value");
                        }
                        if (v-3.convertNilAttributeToNull && "xsi:nil".equals(a5) && Boolean.parseBoolean((String)v2)) {
                            v4 = true;
                        }
                        else if (!v4) {
                            v0.accumulate(a5, v-3.keepStrings ? ((String)v2) : stringToValue((String)v2));
                        }
                        v2 = null;
                    }
                    else {
                        v0.accumulate(a5, "");
                    }
                }
                else if (v2 == XML.SLASH) {
                    if (v-6.nextToken() != XML.GT) {
                        throw v-6.syntaxError("Misshaped tag");
                    }
                    if (v4) {
                        v-5.accumulate(v3, JSONObject.NULL);
                    }
                    else if (v0.length() > 0) {
                        v-5.accumulate(v3, v0);
                    }
                    else {
                        v-5.accumulate(v3, "");
                    }
                    return false;
                }
                else {
                    if (v2 != XML.GT) {
                        throw v-6.syntaxError("Misshaped tag");
                    }
                    while (true) {
                        v2 = v-6.nextContent();
                        if (v2 == null) {
                            if (v3 != null) {
                                throw v-6.syntaxError("Unclosed tag " + v3);
                            }
                            return false;
                        }
                        else if (v2 instanceof String) {
                            final String v5 = (String)v2;
                            if (v5.length() <= 0) {
                                continue;
                            }
                            v0.accumulate(v-3.cDataTagName, v-3.keepStrings ? v5 : stringToValue(v5));
                        }
                        else {
                            if (v2 == XML.LT && parse(v-6, v0, v3, v-3)) {
                                if (v0.length() == 0) {
                                    v-5.accumulate(v3, "");
                                }
                                else if (v0.length() == 1 && v0.opt(v-3.cDataTagName) != null) {
                                    v-5.accumulate(v3, v0.opt(v-3.cDataTagName));
                                }
                                else {
                                    v-5.accumulate(v3, v0);
                                }
                                return false;
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }
    
    public static Object stringToValue(final String v-1) {
        if (v-1.equals("")) {
            return v-1;
        }
        if (v-1.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (v-1.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (v-1.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        }
        final char v0 = v-1.charAt(0);
        if (v0 < '0' || v0 > '9') {
            if (v0 != '-') {
                return v-1;
            }
        }
        try {
            if (v-1.indexOf(46) > -1 || v-1.indexOf(101) > -1 || v-1.indexOf(69) > -1 || "-0".equals(v-1)) {
                final Double a1 = Double.valueOf(v-1);
                if (!a1.isInfinite() && !a1.isNaN()) {
                    return a1;
                }
            }
            else {
                final Long v2 = Long.valueOf(v-1);
                if (v-1.equals(v2.toString())) {
                    if (v2 == v2.intValue()) {
                        return v2.intValue();
                    }
                    return v2;
                }
            }
        }
        catch (Exception ex) {}
        return v-1;
    }
    
    public static JSONObject toJSONObject(final String a1) throws JSONException {
        return toJSONObject(a1, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader a1) throws JSONException {
        return toJSONObject(a1, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader a1, final boolean a2) throws JSONException {
        if (a2) {
            return toJSONObject(a1, XMLParserConfiguration.KEEP_STRINGS);
        }
        return toJSONObject(a1, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader a1, final XMLParserConfiguration a2) throws JSONException {
        final JSONObject v1 = new JSONObject();
        final XMLTokener v2 = new XMLTokener(a1);
        while (v2.more()) {
            v2.skipPast("<");
            if (v2.more()) {
                parse(v2, v1, null, a2);
            }
        }
        return v1;
    }
    
    public static JSONObject toJSONObject(final String a1, final boolean a2) throws JSONException {
        return toJSONObject(new StringReader(a1), a2);
    }
    
    public static JSONObject toJSONObject(final String a1, final XMLParserConfiguration a2) throws JSONException {
        return toJSONObject(new StringReader(a1), a2);
    }
    
    public static String toString(final Object a1) throws JSONException {
        return toString(a1, null, XMLParserConfiguration.ORIGINAL);
    }
    
    public static String toString(final Object a1, final String a2) {
        return toString(a1, a2, XMLParserConfiguration.ORIGINAL);
    }
    
    public static String toString(final Object v-3, final String v-2, final XMLParserConfiguration v-1) throws JSONException {
        final StringBuilder v0 = new StringBuilder();
        if (v-3 instanceof JSONObject) {
            if (v-2 != null) {
                v0.append('<');
                v0.append(v-2);
                v0.append('>');
            }
            final JSONObject v2 = (JSONObject)v-3;
            for (final String v3 : v2.keySet()) {
                Object v4 = v2.opt(v3);
                if (v4 == null) {
                    v4 = "";
                }
                else if (v4.getClass().isArray()) {
                    v4 = new JSONArray(v4);
                }
                if (v3.equals(v-1.cDataTagName)) {
                    if (v4 instanceof JSONArray) {
                        final JSONArray v5 = (JSONArray)v4;
                        for (int a3 = v5.length(), a4 = 0; a4 < a3; ++a4) {
                            if (a4 > 0) {
                                v0.append('\n');
                            }
                            final Object a5 = v5.opt(a4);
                            v0.append(escape(a5.toString()));
                        }
                    }
                    else {
                        v0.append(escape(v4.toString()));
                    }
                }
                else if (v4 instanceof JSONArray) {
                    final JSONArray v5 = (JSONArray)v4;
                    for (int v6 = v5.length(), v7 = 0; v7 < v6; ++v7) {
                        final Object v8 = v5.opt(v7);
                        if (v8 instanceof JSONArray) {
                            v0.append('<');
                            v0.append(v3);
                            v0.append('>');
                            v0.append(toString(v8, null, v-1));
                            v0.append("</");
                            v0.append(v3);
                            v0.append('>');
                        }
                        else {
                            v0.append(toString(v8, v3, v-1));
                        }
                    }
                }
                else if ("".equals(v4)) {
                    v0.append('<');
                    v0.append(v3);
                    v0.append("/>");
                }
                else {
                    v0.append(toString(v4, v3, v-1));
                }
            }
            if (v-2 != null) {
                v0.append("</");
                v0.append(v-2);
                v0.append('>');
            }
            return v0.toString();
        }
        if (v-3 != null && (v-3 instanceof JSONArray || v-3.getClass().isArray())) {
            JSONArray v5;
            if (v-3.getClass().isArray()) {
                v5 = new JSONArray(v-3);
            }
            else {
                v5 = (JSONArray)v-3;
            }
            for (int v9 = v5.length(), v10 = 0; v10 < v9; ++v10) {
                final Object v4 = v5.opt(v10);
                v0.append(toString(v4, (v-2 == null) ? "array" : v-2, v-1));
            }
            return v0.toString();
        }
        final String v11 = (v-3 == null) ? "null" : escape(v-3.toString());
        return (v-2 == null) ? ("\"" + v11 + "\"") : ((v11.length() == 0) ? ("<" + v-2 + "/>") : ("<" + v-2 + ">" + v11 + "</" + v-2 + ">"));
    }
    
    static {
        AMP = '&';
        APOS = '\'';
        BANG = '!';
        EQ = '=';
        GT = '>';
        LT = '<';
        QUEST = '?';
        QUOT = '\"';
        SLASH = '/';
    }
}
