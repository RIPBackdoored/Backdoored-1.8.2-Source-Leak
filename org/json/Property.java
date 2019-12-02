package org.json;

import java.util.*;

public class Property
{
    public Property() {
        super();
    }
    
    public static JSONObject toJSONObject(final Properties v-1) throws JSONException {
        final JSONObject v0 = new JSONObject();
        if (v-1 != null && !v-1.isEmpty()) {
            final Enumeration<?> v2 = v-1.propertyNames();
            while (v2.hasMoreElements()) {
                final String a1 = (String)v2.nextElement();
                v0.put(a1, v-1.getProperty(a1));
            }
        }
        return v0;
    }
    
    public static Properties toProperties(final JSONObject v-2) throws JSONException {
        final Properties properties = new Properties();
        if (v-2 != null) {
            for (final String v1 : v-2.keySet()) {
                final Object a1 = v-2.opt(v1);
                if (!JSONObject.NULL.equals(a1)) {
                    ((Hashtable<String, String>)properties).put(v1, a1.toString());
                }
            }
        }
        return properties;
    }
}
