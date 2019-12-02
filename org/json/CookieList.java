package org.json;

import java.util.*;

public class CookieList
{
    public CookieList() {
        super();
    }
    
    public static JSONObject toJSONObject(final String v1) throws JSONException {
        final JSONObject v2 = new JSONObject();
        final JSONTokener v3 = new JSONTokener(v1);
        while (v3.more()) {
            final String a1 = Cookie.unescape(v3.nextTo('='));
            v3.next('=');
            v2.put(a1, Cookie.unescape(v3.nextTo(';')));
            v3.next();
        }
        return v2;
    }
    
    public static String toString(final JSONObject v-3) throws JSONException {
        boolean b = false;
        final StringBuilder sb = new StringBuilder();
        for (final String v1 : v-3.keySet()) {
            final Object a1 = v-3.opt(v1);
            if (!JSONObject.NULL.equals(a1)) {
                if (b) {
                    sb.append(';');
                }
                sb.append(Cookie.escape(v1));
                sb.append("=");
                sb.append(Cookie.escape(a1.toString()));
                b = true;
            }
        }
        return sb.toString();
    }
}
