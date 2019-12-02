package org.json;

import java.util.*;

public class HTTP
{
    public static final String CRLF = "\r\n";
    
    public HTTP() {
        super();
    }
    
    public static JSONObject toJSONObject(final String v1) throws JSONException {
        final JSONObject v2 = new JSONObject();
        final HTTPTokener v3 = new HTTPTokener(v1);
        final String v4 = v3.nextToken();
        if (v4.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
            v2.put("HTTP-Version", v4);
            v2.put("Status-Code", v3.nextToken());
            v2.put("Reason-Phrase", v3.nextTo('\0'));
            v3.next();
        }
        else {
            v2.put("Method", v4);
            v2.put("Request-URI", v3.nextToken());
            v2.put("HTTP-Version", v3.nextToken());
        }
        while (v3.more()) {
            final String a1 = v3.nextTo(':');
            v3.next(':');
            v2.put(a1, v3.nextTo('\0'));
            v3.next();
        }
        return v2;
    }
    
    public static String toString(final JSONObject v-2) throws JSONException {
        final StringBuilder sb = new StringBuilder();
        if (v-2.has("Status-Code") && v-2.has("Reason-Phrase")) {
            sb.append(v-2.getString("HTTP-Version"));
            sb.append(' ');
            sb.append(v-2.getString("Status-Code"));
            sb.append(' ');
            sb.append(v-2.getString("Reason-Phrase"));
        }
        else {
            if (!v-2.has("Method") || !v-2.has("Request-URI")) {
                throw new JSONException("Not enough material for an HTTP header.");
            }
            sb.append(v-2.getString("Method"));
            sb.append(' ');
            sb.append('\"');
            sb.append(v-2.getString("Request-URI"));
            sb.append('\"');
            sb.append(' ');
            sb.append(v-2.getString("HTTP-Version"));
        }
        sb.append("\r\n");
        for (final String v1 : v-2.keySet()) {
            final String a1 = v-2.optString(v1);
            if (!"HTTP-Version".equals(v1) && !"Status-Code".equals(v1) && !"Reason-Phrase".equals(v1) && !"Method".equals(v1) && !"Request-URI".equals(v1) && !JSONObject.NULL.equals(a1)) {
                sb.append(v1);
                sb.append(": ");
                sb.append(v-2.optString(v1));
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
