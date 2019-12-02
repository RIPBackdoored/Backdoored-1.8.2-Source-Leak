package com.google.api.client.http;

import java.lang.reflect.*;
import com.google.api.client.util.escape.*;
import java.util.*;
import java.nio.charset.*;
import java.io.*;
import com.google.api.client.util.*;

public class UrlEncodedParser implements ObjectParser
{
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String MEDIA_TYPE;
    
    public UrlEncodedParser() {
        super();
    }
    
    public static void parse(final String a2, final Object v1) {
        if (a2 == null) {
            return;
        }
        try {
            parse(new StringReader(a2), v1);
        }
        catch (IOException a3) {
            throw Throwables.propagate(a3);
        }
    }
    
    public static void parse(final Reader v-16, final Object v-15) throws IOException {
        final Class<?> class1 = v-15.getClass();
        final ClassInfo of = ClassInfo.of(class1);
        final List<Type> list = Arrays.asList(class1);
        final GenericData genericData = GenericData.class.isAssignableFrom(class1) ? ((GenericData)v-15) : null;
        final Map<Object, Object> map = (Map<Object, Object>)(Map.class.isAssignableFrom(class1) ? ((Map)v-15) : null);
        final ArrayValueMap arrayValueMap = new ArrayValueMap(v-15);
        StringWriter stringWriter = new StringWriter();
        StringWriter stringWriter2 = new StringWriter();
        boolean b = true;
    Block_12:
        while (true) {
            final int read = v-16.read();
            switch (read) {
                case -1:
                case 38: {
                    final String decodeUri = CharEscapers.decodeUri(stringWriter.toString());
                    if (decodeUri.length() != 0) {
                        final String decodeUri2 = CharEscapers.decodeUri(stringWriter2.toString());
                        final FieldInfo fieldInfo = of.getFieldInfo(decodeUri);
                        if (fieldInfo != null) {
                            final Type resolveWildcardTypeOrTypeVariable = Data.resolveWildcardTypeOrTypeVariable(list, fieldInfo.getGenericType());
                            if (Types.isArray(resolveWildcardTypeOrTypeVariable)) {
                                final Class<?> a1 = Types.getRawArrayComponentType(list, Types.getArrayComponentType(resolveWildcardTypeOrTypeVariable));
                                arrayValueMap.put(fieldInfo.getField(), a1, parseValue(a1, list, decodeUri2));
                            }
                            else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(list, resolveWildcardTypeOrTypeVariable), Iterable.class)) {
                                Collection<Object> a2 = (Collection<Object>)fieldInfo.getValue(v-15);
                                if (a2 == null) {
                                    a2 = Data.newCollectionInstance(resolveWildcardTypeOrTypeVariable);
                                    fieldInfo.setValue(v-15, a2);
                                }
                                final Type v1 = (resolveWildcardTypeOrTypeVariable == Object.class) ? null : Types.getIterableParameter(resolveWildcardTypeOrTypeVariable);
                                a2.add(parseValue(v1, list, decodeUri2));
                            }
                            else {
                                fieldInfo.setValue(v-15, parseValue(resolveWildcardTypeOrTypeVariable, list, decodeUri2));
                            }
                        }
                        else if (map != null) {
                            ArrayList<String> a3 = map.get(decodeUri);
                            if (a3 == null) {
                                a3 = new ArrayList<String>();
                                if (genericData != null) {
                                    genericData.set(decodeUri, a3);
                                }
                                else {
                                    map.put(decodeUri, a3);
                                }
                            }
                            a3.add(decodeUri2);
                        }
                    }
                    b = true;
                    stringWriter = new StringWriter();
                    stringWriter2 = new StringWriter();
                    if (read == -1) {
                        break Block_12;
                    }
                    continue;
                }
                case 61: {
                    if (b) {
                        b = false;
                        continue;
                    }
                    stringWriter2.write(read);
                    continue;
                }
                default: {
                    if (b) {
                        stringWriter.write(read);
                        continue;
                    }
                    stringWriter2.write(read);
                    continue;
                }
            }
        }
        arrayValueMap.setValues();
    }
    
    private static Object parseValue(final Type a1, final List<Type> a2, final String a3) {
        final Type v1 = Data.resolveWildcardTypeOrTypeVariable(a2, a1);
        return Data.parsePrimitiveValue(v1, a3);
    }
    
    @Override
    public <T> T parseAndClose(final InputStream a1, final Charset a2, final Class<T> a3) throws IOException {
        final InputStreamReader v1 = new InputStreamReader(a1, a2);
        return this.parseAndClose(v1, a3);
    }
    
    @Override
    public Object parseAndClose(final InputStream a1, final Charset a2, final Type a3) throws IOException {
        final InputStreamReader v1 = new InputStreamReader(a1, a2);
        return this.parseAndClose(v1, a3);
    }
    
    @Override
    public <T> T parseAndClose(final Reader a1, final Class<T> a2) throws IOException {
        return (T)this.parseAndClose(a1, (Type)a2);
    }
    
    @Override
    public Object parseAndClose(final Reader a1, final Type a2) throws IOException {
        Preconditions.checkArgument(a2 instanceof Class, (Object)"dataType has to be of type Class<?>");
        final Object v1 = Types.newInstance((Class<Object>)a2);
        parse(new BufferedReader(a1), v1);
        return v1;
    }
    
    static {
        MEDIA_TYPE = new HttpMediaType("application/x-www-form-urlencoded").setCharsetParameter(Charsets.UTF_8).build();
    }
}
