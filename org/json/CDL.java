package org.json;

public class CDL
{
    public CDL() {
        super();
    }
    
    private static String getValue(final JSONTokener v-1) throws JSONException {
        char v0;
        do {
            v0 = v-1.next();
        } while (v0 == ' ' || v0 == '\t');
        switch (v0) {
            case '\0': {
                return null;
            }
            case '\"':
            case '\'': {
                final char v2 = v0;
                final StringBuffer v3 = new StringBuffer();
                while (true) {
                    v0 = v-1.next();
                    if (v0 == v2) {
                        final char a1 = v-1.next();
                        if (a1 != '\"') {
                            if (a1 > '\0') {
                                v-1.back();
                            }
                            return v3.toString();
                        }
                    }
                    if (v0 == '\0' || v0 == '\n' || v0 == '\r') {
                        throw v-1.syntaxError("Missing close quote '" + v2 + "'.");
                    }
                    v3.append(v0);
                }
                break;
            }
            case ',': {
                v-1.back();
                return "";
            }
            default: {
                v-1.back();
                return v-1.nextTo(',');
            }
        }
    }
    
    public static JSONArray rowToJSONArray(final JSONTokener v-2) throws JSONException {
        final JSONArray jsonArray = new JSONArray();
        while (true) {
            final String a1 = getValue(v-2);
            char v1 = v-2.next();
            if (a1 == null || (jsonArray.length() == 0 && a1.length() == 0 && v1 != ',')) {
                return null;
            }
            jsonArray.put(a1);
            while (v1 != ',') {
                if (v1 != ' ') {
                    if (v1 == '\n' || v1 == '\r' || v1 == '\0') {
                        return jsonArray;
                    }
                    throw v-2.syntaxError("Bad character '" + v1 + "' (" + (int)v1 + ").");
                }
                else {
                    v1 = v-2.next();
                }
            }
        }
    }
    
    public static JSONObject rowToJSONObject(final JSONArray a1, final JSONTokener a2) throws JSONException {
        final JSONArray v1 = rowToJSONArray(a2);
        return (v1 != null) ? v1.toJSONObject(a1) : null;
    }
    
    public static String rowToString(final JSONArray v-5) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v-5.length(); ++i) {
            if (i > 0) {
                sb.append(',');
            }
            final Object opt = v-5.opt(i);
            if (opt != null) {
                final String string = opt.toString();
                if (string.length() > 0 && (string.indexOf(44) >= 0 || string.indexOf(10) >= 0 || string.indexOf(13) >= 0 || string.indexOf(0) >= 0 || string.charAt(0) == '\"')) {
                    sb.append('\"');
                    for (int v0 = string.length(), v2 = 0; v2 < v0; ++v2) {
                        final char a1 = string.charAt(v2);
                        if (a1 >= ' ' && a1 != '\"') {
                            sb.append(a1);
                        }
                    }
                    sb.append('\"');
                }
                else {
                    sb.append(string);
                }
            }
        }
        sb.append('\n');
        return sb.toString();
    }
    
    public static JSONArray toJSONArray(final String a1) throws JSONException {
        return toJSONArray(new JSONTokener(a1));
    }
    
    public static JSONArray toJSONArray(final JSONTokener a1) throws JSONException {
        return toJSONArray(rowToJSONArray(a1), a1);
    }
    
    public static JSONArray toJSONArray(final JSONArray a1, final String a2) throws JSONException {
        return toJSONArray(a1, new JSONTokener(a2));
    }
    
    public static JSONArray toJSONArray(final JSONArray a2, final JSONTokener v1) throws JSONException {
        if (a2 == null || a2.length() == 0) {
            return null;
        }
        final JSONArray v2 = new JSONArray();
        while (true) {
            final JSONObject a3 = rowToJSONObject(a2, v1);
            if (a3 == null) {
                break;
            }
            v2.put(a3);
        }
        if (v2.length() == 0) {
            return null;
        }
        return v2;
    }
    
    public static String toString(final JSONArray v1) throws JSONException {
        final JSONObject v2 = v1.optJSONObject(0);
        if (v2 != null) {
            final JSONArray a1 = v2.names();
            if (a1 != null) {
                return rowToString(a1) + toString(a1, v1);
            }
        }
        return null;
    }
    
    public static String toString(final JSONArray v1, final JSONArray v2) throws JSONException {
        if (v1 == null || v1.length() == 0) {
            return null;
        }
        final StringBuffer v3 = new StringBuffer();
        for (int a2 = 0; a2 < v2.length(); ++a2) {
            final JSONObject a3 = v2.optJSONObject(a2);
            if (a3 != null) {
                v3.append(rowToString(a3.toJSONArray(v1)));
            }
        }
        return v3.toString();
    }
}
