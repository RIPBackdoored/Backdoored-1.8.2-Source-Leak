package com.google.api.client.json;

import java.io.*;
import java.math.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;
import java.util.concurrent.locks.*;

public abstract class JsonParser
{
    private static WeakHashMap<Class<?>, Field> cachedTypemapFields;
    private static final Lock lock;
    
    public JsonParser() {
        super();
    }
    
    public abstract JsonFactory getFactory();
    
    public abstract void close() throws IOException;
    
    public abstract JsonToken nextToken() throws IOException;
    
    public abstract JsonToken getCurrentToken();
    
    public abstract String getCurrentName() throws IOException;
    
    public abstract JsonParser skipChildren() throws IOException;
    
    public abstract String getText() throws IOException;
    
    public abstract byte getByteValue() throws IOException;
    
    public abstract short getShortValue() throws IOException;
    
    public abstract int getIntValue() throws IOException;
    
    public abstract float getFloatValue() throws IOException;
    
    public abstract long getLongValue() throws IOException;
    
    public abstract double getDoubleValue() throws IOException;
    
    public abstract BigInteger getBigIntegerValue() throws IOException;
    
    public abstract BigDecimal getDecimalValue() throws IOException;
    
    public final <T> T parseAndClose(final Class<T> a1) throws IOException {
        return this.parseAndClose(a1, null);
    }
    
    @Beta
    public final <T> T parseAndClose(final Class<T> a1, final CustomizeJsonParser a2) throws IOException {
        try {
            return (T)this.parse((Class<Object>)a1, a2);
        }
        finally {
            this.close();
        }
    }
    
    public final void skipToKey(final String a1) throws IOException {
        this.skipToKey(Collections.singleton(a1));
    }
    
    public final String skipToKey(final Set<String> v2) throws IOException {
        for (JsonToken v3 = this.startParsingObjectOrArray(); v3 == JsonToken.FIELD_NAME; v3 = this.nextToken()) {
            final String a1 = this.getText();
            this.nextToken();
            if (v2.contains(a1)) {
                return a1;
            }
            this.skipChildren();
        }
        return null;
    }
    
    private JsonToken startParsing() throws IOException {
        JsonToken v1 = this.getCurrentToken();
        if (v1 == null) {
            v1 = this.nextToken();
        }
        Preconditions.checkArgument(v1 != null, (Object)"no JSON input found");
        return v1;
    }
    
    private JsonToken startParsingObjectOrArray() throws IOException {
        JsonToken v1 = this.startParsing();
        switch (v1) {
            case START_OBJECT: {
                v1 = this.nextToken();
                Preconditions.checkArgument(v1 == JsonToken.FIELD_NAME || v1 == JsonToken.END_OBJECT, v1);
                break;
            }
            case START_ARRAY: {
                v1 = this.nextToken();
                break;
            }
        }
        return v1;
    }
    
    public final void parseAndClose(final Object a1) throws IOException {
        this.parseAndClose(a1, null);
    }
    
    @Beta
    public final void parseAndClose(final Object a1, final CustomizeJsonParser a2) throws IOException {
        try {
            this.parse(a1, a2);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> T parse(final Class<T> a1) throws IOException {
        return this.parse(a1, null);
    }
    
    @Beta
    public final <T> T parse(final Class<T> a1, final CustomizeJsonParser a2) throws IOException {
        final T v1 = (T)this.parse(a1, false, a2);
        return v1;
    }
    
    public Object parse(final Type a1, final boolean a2) throws IOException {
        return this.parse(a1, a2, null);
    }
    
    @Beta
    public Object parse(final Type a1, final boolean a2, final CustomizeJsonParser a3) throws IOException {
        try {
            if (!Void.class.equals(a1)) {
                this.startParsing();
            }
            return this.parseValue(null, a1, new ArrayList<Type>(), null, a3, true);
        }
        finally {
            if (a2) {
                this.close();
            }
        }
    }
    
    public final void parse(final Object a1) throws IOException {
        this.parse(a1, null);
    }
    
    @Beta
    public final void parse(final Object a1, final CustomizeJsonParser a2) throws IOException {
        final ArrayList<Type> v1 = new ArrayList<Type>();
        v1.add(a1.getClass());
        this.parse(v1, a1, a2);
    }
    
    private void parse(final ArrayList<Type> v-10, final Object v-9, final CustomizeJsonParser v-8) throws IOException {
        if (v-9 instanceof GenericJson) {
            ((GenericJson)v-9).setFactory(this.getFactory());
        }
        JsonToken jsonToken = this.startParsingObjectOrArray();
        final Class<?> class1 = v-9.getClass();
        final ClassInfo of = ClassInfo.of(class1);
        final boolean assignable = GenericData.class.isAssignableFrom(class1);
        if (!assignable && Map.class.isAssignableFrom(class1)) {
            final Map<String, Object> a1 = (Map<String, Object>)v-9;
            this.parseMap(null, a1, Types.getMapValueParameter(class1), v-10, v-8);
            return;
        }
        while (jsonToken == JsonToken.FIELD_NAME) {
            final String text = this.getText();
            this.nextToken();
            if (v-8 != null && v-8.stopAt(v-9, text)) {
                return;
            }
            final FieldInfo fieldInfo = of.getFieldInfo(text);
            if (fieldInfo != null) {
                if (fieldInfo.isFinal() && !fieldInfo.isPrimitive()) {
                    throw new IllegalArgumentException("final array/object fields are not supported");
                }
                final Field a2 = fieldInfo.getField();
                final int a3 = v-10.size();
                v-10.add(a2.getGenericType());
                final Object v1 = this.parseValue(a2, fieldInfo.getGenericType(), v-10, v-9, v-8, true);
                v-10.remove(a3);
                fieldInfo.setValue(v-9, v1);
            }
            else if (assignable) {
                final GenericData genericData = (GenericData)v-9;
                genericData.set(text, this.parseValue(null, null, v-10, v-9, v-8, true));
            }
            else {
                if (v-8 != null) {
                    v-8.handleUnrecognizedKey(v-9, text);
                }
                this.skipChildren();
            }
            jsonToken = this.nextToken();
        }
    }
    
    public final <T> Collection<T> parseArrayAndClose(final Class<?> a1, final Class<T> a2) throws IOException {
        return this.parseArrayAndClose(a1, a2, null);
    }
    
    @Beta
    public final <T> Collection<T> parseArrayAndClose(final Class<?> a1, final Class<T> a2, final CustomizeJsonParser a3) throws IOException {
        try {
            return (Collection<T>)this.parseArray(a1, (Class<Object>)a2, a3);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> void parseArrayAndClose(final Collection<? super T> a1, final Class<T> a2) throws IOException {
        this.parseArrayAndClose(a1, a2, null);
    }
    
    @Beta
    public final <T> void parseArrayAndClose(final Collection<? super T> a1, final Class<T> a2, final CustomizeJsonParser a3) throws IOException {
        try {
            this.parseArray((Collection<? super Object>)a1, (Class<Object>)a2, a3);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> Collection<T> parseArray(final Class<?> a1, final Class<T> a2) throws IOException {
        return this.parseArray(a1, a2, null);
    }
    
    @Beta
    public final <T> Collection<T> parseArray(final Class<?> a1, final Class<T> a2, final CustomizeJsonParser a3) throws IOException {
        final Collection<T> v1 = (Collection<T>)Data.newCollectionInstance(a1);
        this.parseArray(v1, a2, a3);
        return v1;
    }
    
    public final <T> void parseArray(final Collection<? super T> a1, final Class<T> a2) throws IOException {
        this.parseArray(a1, a2, null);
    }
    
    @Beta
    public final <T> void parseArray(final Collection<? super T> a1, final Class<T> a2, final CustomizeJsonParser a3) throws IOException {
        this.parseArray(null, a1, a2, new ArrayList<Type>(), a3);
    }
    
    private <T> void parseArray(final Field a3, final Collection<T> a4, final Type a5, final ArrayList<Type> v1, final CustomizeJsonParser v2) throws IOException {
        for (JsonToken v3 = this.startParsingObjectOrArray(); v3 != JsonToken.END_ARRAY; v3 = this.nextToken()) {
            final T a6 = (T)this.parseValue(a3, a5, v1, a4, v2, true);
            a4.add(a6);
        }
    }
    
    private void parseMap(final Field a4, final Map<String, Object> a5, final Type v1, final ArrayList<Type> v2, final CustomizeJsonParser v3) throws IOException {
        for (JsonToken v4 = this.startParsingObjectOrArray(); v4 == JsonToken.FIELD_NAME; v4 = this.nextToken()) {
            final String a6 = this.getText();
            this.nextToken();
            if (v3 != null && v3.stopAt(a5, a6)) {
                return;
            }
            final Object a7 = this.parseValue(a4, v1, v2, a5, v3, true);
            a5.put(a6, a7);
        }
    }
    
    private final Object parseValue(final Field v-10, Type v-9, final ArrayList<Type> v-8, final Object v-7, final CustomizeJsonParser v-6, final boolean v-5) throws IOException {
        v-9 = Data.resolveWildcardTypeOrTypeVariable(v-8, v-9);
        Class<?> rawClass = (Class<?>)((v-9 instanceof Class) ? ((Class)v-9) : null);
        if (v-9 instanceof ParameterizedType) {
            rawClass = Types.getRawClass((ParameterizedType)v-9);
        }
        if (rawClass == Void.class) {
            this.skipChildren();
            return null;
        }
        final JsonToken currentToken = this.getCurrentToken();
        try {
            switch (this.getCurrentToken()) {
                case START_ARRAY:
                case END_ARRAY: {
                    final boolean a1 = Types.isArray(v-9);
                    Preconditions.checkArgument(v-9 == null || a1 || (rawClass != null && Types.isAssignableToOrFrom(rawClass, Collection.class)), "expected collection or array type but got %s", v-9);
                    Collection<Object> a2 = null;
                    if (v-6 != null && v-10 != null) {
                        a2 = v-6.newInstanceForArray(v-7, v-10);
                    }
                    if (a2 == null) {
                        a2 = Data.newCollectionInstance(v-9);
                    }
                    Type a3 = null;
                    if (a1) {
                        a3 = Types.getArrayComponentType(v-9);
                    }
                    else if (rawClass != null && Iterable.class.isAssignableFrom(rawClass)) {
                        a3 = Types.getIterableParameter(v-9);
                    }
                    a3 = Data.resolveWildcardTypeOrTypeVariable(v-8, a3);
                    this.parseArray(v-10, a2, a3, v-8, v-6);
                    if (a1) {
                        return Types.toArray(a2, Types.getRawArrayComponentType(v-8, a3));
                    }
                    return a2;
                }
                case START_OBJECT:
                case FIELD_NAME:
                case END_OBJECT: {
                    Preconditions.checkArgument(!Types.isArray(v-9), "expected object or map type but got %s", v-9);
                    final Field v1 = v-5 ? getCachedTypemapFieldFor(rawClass) : null;
                    Object v2 = null;
                    if (rawClass != null && v-6 != null) {
                        v2 = v-6.newInstanceForObject(v-7, rawClass);
                    }
                    final boolean v3 = rawClass != null && Types.isAssignableToOrFrom(rawClass, Map.class);
                    if (v1 != null) {
                        v2 = new GenericJson();
                    }
                    else if (v2 == null) {
                        if (v3 || rawClass == null) {
                            v2 = Data.newMapInstance(rawClass);
                        }
                        else {
                            v2 = Types.newInstance(rawClass);
                        }
                    }
                    final int v4 = v-8.size();
                    if (v-9 != null) {
                        v-8.add(v-9);
                    }
                    if (v3 && !GenericData.class.isAssignableFrom(rawClass)) {
                        final Type a4 = Map.class.isAssignableFrom(rawClass) ? Types.getMapValueParameter(v-9) : null;
                        if (a4 != null) {
                            final Map<String, Object> a5 = (Map<String, Object>)v2;
                            this.parseMap(v-10, a5, a4, v-8, v-6);
                            return v2;
                        }
                    }
                    this.parse(v-8, v2, v-6);
                    if (v-9 != null) {
                        v-8.remove(v4);
                    }
                    if (v1 == null) {
                        return v2;
                    }
                    final Object v5 = ((GenericJson)v2).get(v1.getName());
                    Preconditions.checkArgument(v5 != null, (Object)"No value specified for @JsonPolymorphicTypeMap field");
                    final String v6 = v5.toString();
                    final JsonPolymorphicTypeMap v7 = v1.getAnnotation(JsonPolymorphicTypeMap.class);
                    Class<?> v8 = null;
                    for (final JsonPolymorphicTypeMap.TypeDef a6 : v7.typeDefinitions()) {
                        if (a6.key().equals(v6)) {
                            v8 = a6.ref();
                            break;
                        }
                    }
                    Preconditions.checkArgument(v8 != null, (Object)("No TypeDef annotation found with key: " + v6));
                    final JsonFactory v9 = this.getFactory();
                    final JsonParser v10 = v9.createJsonParser(v9.toString(v2));
                    v10.startParsing();
                    return v10.parseValue(v-10, v8, v-8, null, null, false);
                }
                case VALUE_TRUE:
                case VALUE_FALSE: {
                    Preconditions.checkArgument(v-9 == null || rawClass == Boolean.TYPE || (rawClass != null && rawClass.isAssignableFrom(Boolean.class)), "expected type Boolean or boolean but got %s", v-9);
                    return (currentToken == JsonToken.VALUE_TRUE) ? Boolean.TRUE : Boolean.FALSE;
                }
                case VALUE_NUMBER_FLOAT:
                case VALUE_NUMBER_INT: {
                    Preconditions.checkArgument(v-10 == null || v-10.getAnnotation(JsonString.class) == null, (Object)"number type formatted as a JSON number cannot use @JsonString annotation");
                    if (rawClass == null || rawClass.isAssignableFrom(BigDecimal.class)) {
                        return this.getDecimalValue();
                    }
                    if (rawClass == BigInteger.class) {
                        return this.getBigIntegerValue();
                    }
                    if (rawClass == Double.class || rawClass == Double.TYPE) {
                        return this.getDoubleValue();
                    }
                    if (rawClass == Long.class || rawClass == Long.TYPE) {
                        return this.getLongValue();
                    }
                    if (rawClass == Float.class || rawClass == Float.TYPE) {
                        return this.getFloatValue();
                    }
                    if (rawClass == Integer.class || rawClass == Integer.TYPE) {
                        return this.getIntValue();
                    }
                    if (rawClass == Short.class || rawClass == Short.TYPE) {
                        return this.getShortValue();
                    }
                    if (rawClass == Byte.class || rawClass == Byte.TYPE) {
                        return this.getByteValue();
                    }
                    throw new IllegalArgumentException("expected numeric type but got " + v-9);
                }
                case VALUE_STRING: {
                    final String v11 = this.getText().trim().toLowerCase(Locale.US);
                    if ((rawClass != Float.TYPE && rawClass != Float.class && rawClass != Double.TYPE && rawClass != Double.class) || (!v11.equals("nan") && !v11.equals("infinity") && !v11.equals("-infinity"))) {
                        Preconditions.checkArgument(rawClass == null || !Number.class.isAssignableFrom(rawClass) || (v-10 != null && v-10.getAnnotation(JsonString.class) != null), (Object)"number field formatted as a JSON string must use the @JsonString annotation");
                    }
                    return Data.parsePrimitiveValue(v-9, this.getText());
                }
                case VALUE_NULL: {
                    Preconditions.checkArgument(rawClass == null || !rawClass.isPrimitive(), (Object)"primitive number field but found a JSON null");
                    if (rawClass != null && 0x0 != (rawClass.getModifiers() & 0x600)) {
                        if (Types.isAssignableToOrFrom(rawClass, Collection.class)) {
                            return Data.nullOf(Data.newCollectionInstance(v-9).getClass());
                        }
                        if (Types.isAssignableToOrFrom(rawClass, Map.class)) {
                            return Data.nullOf(Data.newMapInstance(rawClass).getClass());
                        }
                    }
                    return Data.nullOf(Types.getRawArrayComponentType(v-8, v-9));
                }
                default: {
                    throw new IllegalArgumentException("unexpected JSON node type: " + currentToken);
                }
            }
        }
        catch (IllegalArgumentException ex) {
            final StringBuilder sb = new StringBuilder();
            final String v12 = this.getCurrentName();
            if (v12 != null) {
                sb.append("key ").append(v12);
            }
            if (v-10 != null) {
                if (v12 != null) {
                    sb.append(", ");
                }
                sb.append("field ").append(v-10);
            }
            throw new IllegalArgumentException(sb.toString(), ex);
        }
    }
    
    private static Field getCachedTypemapFieldFor(final Class<?> v-6) {
        if (v-6 == null) {
            return null;
        }
        JsonParser.lock.lock();
        try {
            if (JsonParser.cachedTypemapFields.containsKey(v-6)) {
                return JsonParser.cachedTypemapFields.get(v-6);
            }
            Field field = null;
            final Collection<FieldInfo> fieldInfos = ClassInfo.of(v-6).getFieldInfos();
            for (final FieldInfo fieldInfo : fieldInfos) {
                final Field field2 = fieldInfo.getField();
                final JsonPolymorphicTypeMap v0 = field2.getAnnotation(JsonPolymorphicTypeMap.class);
                if (v0 != null) {
                    Preconditions.checkArgument(field == null, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", v-6);
                    Preconditions.checkArgument(Data.isPrimitive(field2.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", v-6, field2.getType());
                    field = field2;
                    final JsonPolymorphicTypeMap.TypeDef[] v2 = v0.typeDefinitions();
                    final HashSet<String> v3 = Sets.newHashSet();
                    Preconditions.checkArgument(v2.length > 0, (Object)"@JsonPolymorphicTypeMap must have at least one @TypeDef");
                    for (final JsonPolymorphicTypeMap.TypeDef a1 : v2) {
                        Preconditions.checkArgument(v3.add(a1.key()), "Class contains two @TypeDef annotations with identical key: %s", a1.key());
                    }
                }
            }
            JsonParser.cachedTypemapFields.put(v-6, field);
            return field;
        }
        finally {
            JsonParser.lock.unlock();
        }
    }
    
    static {
        JsonParser.cachedTypemapFields = new WeakHashMap<Class<?>, Field>();
        lock = new ReentrantLock();
    }
}
