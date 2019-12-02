package org.json;

public class Cookie
{
    public Cookie() {
        super();
    }
    
    public static String escape(final String v-4) {
        final String trim = v-4.trim();
        final int length = trim.length();
        final StringBuilder v0 = new StringBuilder(length);
        for (int v2 = 0; v2 < length; ++v2) {
            final char a1 = trim.charAt(v2);
            if (a1 < ' ' || a1 == '+' || a1 == '%' || a1 == '=' || a1 == ';') {
                v0.append('%');
                v0.append(Character.forDigit((char)(a1 >>> 4 & 0xF), 16));
                v0.append(Character.forDigit((char)(a1 & '\u000f'), 16));
            }
            else {
                v0.append(a1);
            }
        }
        return v0.toString();
    }
    
    public static JSONObject toJSONObject(final String v0) throws JSONException {
        final JSONObject v = new JSONObject();
        final JSONTokener v2 = new JSONTokener(v0);
        v.put("name", v2.nextTo('='));
        v2.next('=');
        v.put("value", v2.nextTo(';'));
        v2.next();
        while (v2.more()) {
            final String v3 = unescape(v2.nextTo("=;"));
            Object v4 = null;
            if (v2.next() != '=') {
                if (!v3.equals("secure")) {
                    throw v2.syntaxError("Missing '=' in cookie parameter.");
                }
                final Object a1 = Boolean.TRUE;
            }
            else {
                v4 = unescape(v2.nextTo(';'));
                v2.next();
            }
            v.put(v3, v4);
        }
        return v;
    }
    
    public static String toString(final JSONObject a1) throws JSONException {
        final StringBuilder v1 = new StringBuilder();
        v1.append(escape(a1.getString("name")));
        v1.append("=");
        v1.append(escape(a1.getString("value")));
        if (a1.has("expires")) {
            v1.append(";expires=");
            v1.append(a1.getString("expires"));
        }
        if (a1.has("domain")) {
            v1.append(";domain=");
            v1.append(escape(a1.getString("domain")));
        }
        if (a1.has("path")) {
            v1.append(";path=");
            v1.append(escape(a1.getString("path")));
        }
        if (a1.optBoolean("secure")) {
            v1.append(";secure");
        }
        return v1.toString();
    }
    
    public static String unescape(final String v-5) {
        final int length = v-5.length();
        final StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            char char1 = v-5.charAt(i);
            if (char1 == '+') {
                char1 = ' ';
            }
            else if (char1 == '%' && i + 2 < length) {
                final int a1 = JSONTokener.dehexchar(v-5.charAt(i + 1));
                final int v1 = JSONTokener.dehexchar(v-5.charAt(i + 2));
                if (a1 >= 0 && v1 >= 0) {
                    char1 = (char)(a1 * 16 + v1);
                    i += 2;
                }
            }
            sb.append(char1);
        }
        return sb.toString();
    }
}
