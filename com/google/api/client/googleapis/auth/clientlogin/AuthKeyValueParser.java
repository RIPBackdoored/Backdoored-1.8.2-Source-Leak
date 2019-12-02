package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;
import java.nio.charset.*;
import java.lang.reflect.*;

@Beta
final class AuthKeyValueParser implements ObjectParser
{
    public static final AuthKeyValueParser INSTANCE;
    
    public String getContentType() {
        return "text/plain";
    }
    
    public <T> T parse(final HttpResponse a1, final Class<T> a2) throws IOException {
        a1.setContentLoggingLimit(0);
        final InputStream v1 = a1.getContent();
        try {
            return this.parse(v1, a2);
        }
        finally {
            v1.close();
        }
    }
    
    public <T> T parse(final InputStream v-10, final Class<T> v-9) throws IOException {
        final ClassInfo of = ClassInfo.of(v-9);
        final T instance = Types.newInstance(v-9);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(v-10));
        while (true) {
            final String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            final int index = line.indexOf(61);
            final String substring = line.substring(0, index);
            final String substring2 = line.substring(index + 1);
            final Field field = of.getField(substring);
            if (field != null) {
                final Class<?> a2 = field.getType();
                Object v1 = null;
                if (a2 == Boolean.TYPE || a2 == Boolean.class) {
                    final Object a3 = Boolean.valueOf(substring2);
                }
                else {
                    v1 = substring2;
                }
                FieldInfo.setFieldValue(field, instance, v1);
            }
            else if (GenericData.class.isAssignableFrom(v-9)) {
                final GenericData v2 = (GenericData)instance;
                v2.set(substring, substring2);
            }
            else {
                if (!Map.class.isAssignableFrom(v-9)) {
                    continue;
                }
                final Map<Object, Object> v3 = (Map<Object, Object>)instance;
                v3.put(substring, substring2);
            }
        }
        return instance;
    }
    
    private AuthKeyValueParser() {
        super();
    }
    
    public <T> T parseAndClose(final InputStream a1, final Charset a2, final Class<T> a3) throws IOException {
        final Reader v1 = new InputStreamReader(a1, a2);
        return this.parseAndClose(v1, a3);
    }
    
    public Object parseAndClose(final InputStream a1, final Charset a2, final Type a3) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }
    
    public <T> T parseAndClose(final Reader v-10, final Class<T> v-9) throws IOException {
        try {
            final ClassInfo of = ClassInfo.of(v-9);
            final T instance = Types.newInstance(v-9);
            final BufferedReader bufferedReader = new BufferedReader(v-10);
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                final int index = line.indexOf(61);
                final String substring = line.substring(0, index);
                final String substring2 = line.substring(index + 1);
                final Field field = of.getField(substring);
                if (field != null) {
                    final Class<?> a2 = field.getType();
                    Object v1 = null;
                    if (a2 == Boolean.TYPE || a2 == Boolean.class) {
                        final Object a3 = Boolean.valueOf(substring2);
                    }
                    else {
                        v1 = substring2;
                    }
                    FieldInfo.setFieldValue(field, instance, v1);
                }
                else if (GenericData.class.isAssignableFrom(v-9)) {
                    final GenericData v2 = (GenericData)instance;
                    v2.set(substring, substring2);
                }
                else {
                    if (!Map.class.isAssignableFrom(v-9)) {
                        continue;
                    }
                    final Map<Object, Object> v3 = (Map<Object, Object>)instance;
                    v3.put(substring, substring2);
                }
            }
            return instance;
        }
        finally {
            v-10.close();
        }
    }
    
    public Object parseAndClose(final Reader a1, final Type a2) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }
    
    static {
        INSTANCE = new AuthKeyValueParser();
    }
}
