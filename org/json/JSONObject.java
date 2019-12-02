package org.json;

import java.util.regex.*;
import java.math.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.io.*;

public class JSONObject
{
    static final Pattern NUMBER_PATTERN;
    private final Map<String, Object> map;
    public static final Object NULL;
    
    public JSONObject() {
        super();
        this.map = new HashMap<String, Object>();
    }
    
    public JSONObject(final JSONObject v1, final String[] v2) {
        this(v2.length);
        for (int a1 = 0; a1 < v2.length; ++a1) {
            try {
                this.putOnce(v2[a1], v1.opt(v2[a1]));
            }
            catch (Exception ex) {}
        }
    }
    
    public JSONObject(final JSONTokener v2) throws JSONException {
        this();
        if (v2.nextClean() != '{') {
            throw v2.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char v3 = v2.nextClean();
            switch (v3) {
                case '\0': {
                    throw v2.syntaxError("A JSONObject text must end with '}'");
                }
                case '}': {}
                default: {
                    v2.back();
                    final String v4 = v2.nextValue().toString();
                    v3 = v2.nextClean();
                    if (v3 != ':') {
                        throw v2.syntaxError("Expected a ':' after a key");
                    }
                    if (v4 != null) {
                        if (this.opt(v4) != null) {
                            throw v2.syntaxError("Duplicate key \"" + v4 + "\"");
                        }
                        final Object a1 = v2.nextValue();
                        if (a1 != null) {
                            this.put(v4, a1);
                        }
                    }
                    switch (v2.nextClean()) {
                        case ',':
                        case ';': {
                            if (v2.nextClean() == '}') {
                                return;
                            }
                            v2.back();
                            continue;
                        }
                        case '}': {
                            return;
                        }
                        default: {
                            throw v2.syntaxError("Expected a ',' or '}'");
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public JSONObject(final Map<?, ?> v-1) {
        super();
        if (v-1 == null) {
            this.map = new HashMap<String, Object>();
        }
        else {
            this.map = new HashMap<String, Object>(v-1.size());
            for (final Map.Entry<?, ?> v1 : v-1.entrySet()) {
                if (v1.getKey() == null) {
                    throw new NullPointerException("Null key.");
                }
                final Object a1 = v1.getValue();
                if (a1 == null) {
                    continue;
                }
                this.map.put(String.valueOf(v1.getKey()), wrap(a1));
            }
        }
    }
    
    public JSONObject(final Object a1) {
        this();
        this.populateMap(a1);
    }
    
    public JSONObject(final Object v2, final String[] v3) {
        this(v3.length);
        final Class<?> v4 = v2.getClass();
        for (int a2 = 0; a2 < v3.length; ++a2) {
            final String a3 = v3[a2];
            try {
                this.putOpt(a3, v4.getField(a3).get(v2));
            }
            catch (Exception ex) {}
        }
    }
    
    public JSONObject(final String a1) throws JSONException {
        this(new JSONTokener(a1));
    }
    
    public JSONObject(final String v-7, final Locale v-6) throws JSONException {
        this();
        final ResourceBundle bundle = ResourceBundle.getBundle(v-7, v-6, Thread.currentThread().getContextClassLoader());
        final Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            final Object nextElement = keys.nextElement();
            if (nextElement != null) {
                final String[] split = ((String)nextElement).split("\\.");
                final int n = split.length - 1;
                JSONObject v0 = this;
                for (final String a1 : split) {
                    JSONObject a2 = v0.optJSONObject(a1);
                    if (a2 == null) {
                        a2 = new JSONObject();
                        v0.put(a1, a2);
                    }
                    v0 = a2;
                }
                v0.put(split[n], bundle.getString((String)nextElement));
            }
        }
    }
    
    protected JSONObject(final int a1) {
        super();
        this.map = new HashMap<String, Object>(a1);
    }
    
    public JSONObject accumulate(final String a1, final Object a2) throws JSONException {
        testValidity(a2);
        final Object v1 = this.opt(a1);
        if (v1 == null) {
            this.put(a1, (a2 instanceof JSONArray) ? new JSONArray().put(a2) : a2);
        }
        else if (v1 instanceof JSONArray) {
            ((JSONArray)v1).put(a2);
        }
        else {
            this.put(a1, new JSONArray().put(v1).put(a2));
        }
        return this;
    }
    
    public JSONObject append(final String a1, final Object a2) throws JSONException {
        testValidity(a2);
        final Object v1 = this.opt(a1);
        if (v1 == null) {
            this.put(a1, new JSONArray().put(a2));
        }
        else {
            if (!(v1 instanceof JSONArray)) {
                throw new JSONException("JSONObject[" + a1 + "] is not a JSONArray.");
            }
            this.put(a1, ((JSONArray)v1).put(a2));
        }
        return this;
    }
    
    public static String doubleToString(final double a1) {
        if (Double.isInfinite(a1) || Double.isNaN(a1)) {
            return "null";
        }
        String v1 = Double.toString(a1);
        if (v1.indexOf(46) > 0 && v1.indexOf(101) < 0 && v1.indexOf(69) < 0) {
            while (v1.endsWith("0")) {
                v1 = v1.substring(0, v1.length() - 1);
            }
            if (v1.endsWith(".")) {
                v1 = v1.substring(0, v1.length() - 1);
            }
        }
        return v1;
    }
    
    public Object get(final String a1) throws JSONException {
        if (a1 == null) {
            throw new JSONException("Null key.");
        }
        final Object v1 = this.opt(a1);
        if (v1 == null) {
            throw new JSONException("JSONObject[" + quote(a1) + "] not found.");
        }
        return v1;
    }
    
    public <E extends Enum<E>> E getEnum(final Class<E> a1, final String a2) throws JSONException {
        final E v1 = (E)this.optEnum((Class<Enum>)a1, a2);
        if (v1 == null) {
            throw new JSONException("JSONObject[" + quote(a2) + "] is not an enum of type " + quote(a1.getSimpleName()) + ".");
        }
        return v1;
    }
    
    public boolean getBoolean(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1.equals(Boolean.FALSE) || (v1 instanceof String && ((String)v1).equalsIgnoreCase("false"))) {
            return false;
        }
        if (v1.equals(Boolean.TRUE) || (v1 instanceof String && ((String)v1).equalsIgnoreCase("true"))) {
            return true;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] is not a Boolean.");
    }
    
    public BigInteger getBigInteger(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        final BigInteger v2 = objectToBigInteger(v1, null);
        if (v2 != null) {
            return v2;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] could not be converted to BigInteger (" + v1 + ").");
    }
    
    public BigDecimal getBigDecimal(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        final BigDecimal v2 = objectToBigDecimal(v1, null);
        if (v2 != null) {
            return v2;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] could not be converted to BigDecimal (" + v1 + ").");
    }
    
    public double getDouble(final String a1) throws JSONException {
        return this.getNumber(a1).doubleValue();
    }
    
    public float getFloat(final String a1) throws JSONException {
        return this.getNumber(a1).floatValue();
    }
    
    public Number getNumber(final String v2) throws JSONException {
        final Object v3 = this.get(v2);
        try {
            if (v3 instanceof Number) {
                return (Number)v3;
            }
            return stringToNumber(v3.toString());
        }
        catch (Exception a1) {
            throw new JSONException("JSONObject[" + quote(v2) + "] is not a number.", a1);
        }
    }
    
    public int getInt(final String a1) throws JSONException {
        return this.getNumber(a1).intValue();
    }
    
    public JSONArray getJSONArray(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof JSONArray) {
            return (JSONArray)v1;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] is not a JSONArray.");
    }
    
    public JSONObject getJSONObject(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof JSONObject) {
            return (JSONObject)v1;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] is not a JSONObject.");
    }
    
    public long getLong(final String a1) throws JSONException {
        return this.getNumber(a1).longValue();
    }
    
    public static String[] getNames(final JSONObject a1) {
        if (a1.isEmpty()) {
            return null;
        }
        return a1.keySet().toArray(new String[a1.length()]);
    }
    
    public static String[] getNames(final Object v1) {
        if (v1 == null) {
            return null;
        }
        final Class<?> v2 = v1.getClass();
        final Field[] v3 = v2.getFields();
        final int v4 = v3.length;
        if (v4 == 0) {
            return null;
        }
        final String[] v5 = new String[v4];
        for (int a1 = 0; a1 < v4; ++a1) {
            v5[a1] = v3[a1].getName();
        }
        return v5;
    }
    
    public String getString(final String a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof String) {
            return (String)v1;
        }
        throw new JSONException("JSONObject[" + quote(a1) + "] not a string.");
    }
    
    public boolean has(final String a1) {
        return this.map.containsKey(a1);
    }
    
    public JSONObject increment(final String a1) throws JSONException {
        final Object v1 = this.opt(a1);
        if (v1 == null) {
            this.put(a1, 1);
        }
        else if (v1 instanceof BigInteger) {
            this.put(a1, ((BigInteger)v1).add(BigInteger.ONE));
        }
        else if (v1 instanceof BigDecimal) {
            this.put(a1, ((BigDecimal)v1).add(BigDecimal.ONE));
        }
        else if (v1 instanceof Integer) {
            this.put(a1, (int)v1 + 1);
        }
        else if (v1 instanceof Long) {
            this.put(a1, (long)v1 + 1L);
        }
        else if (v1 instanceof Double) {
            this.put(a1, (double)v1 + 1.0);
        }
        else {
            if (!(v1 instanceof Float)) {
                throw new JSONException("Unable to increment [" + quote(a1) + "].");
            }
            this.put(a1, (float)v1 + 1.0f);
        }
        return this;
    }
    
    public boolean isNull(final String a1) {
        return JSONObject.NULL.equals(this.opt(a1));
    }
    
    public Iterator<String> keys() {
        return this.keySet().iterator();
    }
    
    public Set<String> keySet() {
        return this.map.keySet();
    }
    
    protected Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }
    
    public int length() {
        return this.map.size();
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    public JSONArray names() {
        if (this.map.isEmpty()) {
            return null;
        }
        return new JSONArray(this.map.keySet());
    }
    
    public static String numberToString(final Number a1) throws JSONException {
        if (a1 == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(a1);
        String v1 = a1.toString();
        if (v1.indexOf(46) > 0 && v1.indexOf(101) < 0 && v1.indexOf(69) < 0) {
            while (v1.endsWith("0")) {
                v1 = v1.substring(0, v1.length() - 1);
            }
            if (v1.endsWith(".")) {
                v1 = v1.substring(0, v1.length() - 1);
            }
        }
        return v1;
    }
    
    public Object opt(final String a1) {
        return (a1 == null) ? null : this.map.get(a1);
    }
    
    public <E extends Enum<E>> E optEnum(final Class<E> a1, final String a2) {
        return this.optEnum(a1, a2, (E)null);
    }
    
    public <E extends Enum<E>> E optEnum(final Class<E> v-2, final String v-1, final E v0) {
        try {
            final Object a2 = this.opt(v-1);
            if (JSONObject.NULL.equals(a2)) {
                return v0;
            }
            if (v-2.isAssignableFrom(a2.getClass())) {
                final E a3 = (E)a2;
                return a3;
            }
            return Enum.valueOf(v-2, a2.toString());
        }
        catch (IllegalArgumentException a4) {
            return v0;
        }
        catch (NullPointerException v) {
            return v0;
        }
    }
    
    public boolean optBoolean(final String a1) {
        return this.optBoolean(a1, false);
    }
    
    public boolean optBoolean(final String v1, final boolean v2) {
        final Object v3 = this.opt(v1);
        if (JSONObject.NULL.equals(v3)) {
            return v2;
        }
        if (v3 instanceof Boolean) {
            return (boolean)v3;
        }
        try {
            return this.getBoolean(v1);
        }
        catch (Exception a1) {
            return v2;
        }
    }
    
    public BigDecimal optBigDecimal(final String a1, final BigDecimal a2) {
        final Object v1 = this.opt(a1);
        return objectToBigDecimal(v1, a2);
    }
    
    static BigDecimal objectToBigDecimal(final Object v1, final BigDecimal v2) {
        if (JSONObject.NULL.equals(v1)) {
            return v2;
        }
        if (v1 instanceof BigDecimal) {
            return (BigDecimal)v1;
        }
        if (v1 instanceof BigInteger) {
            return new BigDecimal((BigInteger)v1);
        }
        if (v1 instanceof Double || v1 instanceof Float) {
            final double a1 = ((Number)v1).doubleValue();
            if (Double.isNaN(a1)) {
                return v2;
            }
            return new BigDecimal(((Number)v1).doubleValue());
        }
        else {
            if (v1 instanceof Long || v1 instanceof Integer || v1 instanceof Short || v1 instanceof Byte) {
                return new BigDecimal(((Number)v1).longValue());
            }
            try {
                return new BigDecimal(v1.toString());
            }
            catch (Exception a2) {
                return v2;
            }
        }
    }
    
    public BigInteger optBigInteger(final String a1, final BigInteger a2) {
        final Object v1 = this.opt(a1);
        return objectToBigInteger(v1, a2);
    }
    
    static BigInteger objectToBigInteger(final Object v-1, final BigInteger v0) {
        if (JSONObject.NULL.equals(v-1)) {
            return v0;
        }
        if (v-1 instanceof BigInteger) {
            return (BigInteger)v-1;
        }
        if (v-1 instanceof BigDecimal) {
            return ((BigDecimal)v-1).toBigInteger();
        }
        if (v-1 instanceof Double || v-1 instanceof Float) {
            final double a1 = ((Number)v-1).doubleValue();
            if (Double.isNaN(a1)) {
                return v0;
            }
            return new BigDecimal(a1).toBigInteger();
        }
        else {
            if (v-1 instanceof Long || v-1 instanceof Integer || v-1 instanceof Short || v-1 instanceof Byte) {
                return BigInteger.valueOf(((Number)v-1).longValue());
            }
            try {
                final String a2 = v-1.toString();
                if (isDecimalNotation(a2)) {
                    return new BigDecimal(a2).toBigInteger();
                }
                return new BigInteger(a2);
            }
            catch (Exception v) {
                return v0;
            }
        }
    }
    
    public double optDouble(final String a1) {
        return this.optDouble(a1, Double.NaN);
    }
    
    public double optDouble(final String a1, final double a2) {
        final Number v1 = this.optNumber(a1);
        if (v1 == null) {
            return a2;
        }
        final double v2 = v1.doubleValue();
        return v2;
    }
    
    public float optFloat(final String a1) {
        return this.optFloat(a1, Float.NaN);
    }
    
    public float optFloat(final String a1, final float a2) {
        final Number v1 = this.optNumber(a1);
        if (v1 == null) {
            return a2;
        }
        final float v2 = v1.floatValue();
        return v2;
    }
    
    public int optInt(final String a1) {
        return this.optInt(a1, 0);
    }
    
    public int optInt(final String a1, final int a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        return v1.intValue();
    }
    
    public JSONArray optJSONArray(final String a1) {
        final Object v1 = this.opt(a1);
        return (v1 instanceof JSONArray) ? ((JSONArray)v1) : null;
    }
    
    public JSONObject optJSONObject(final String a1) {
        final Object v1 = this.opt(a1);
        return (v1 instanceof JSONObject) ? ((JSONObject)v1) : null;
    }
    
    public long optLong(final String a1) {
        return this.optLong(a1, 0L);
    }
    
    public long optLong(final String a1, final long a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        return v1.longValue();
    }
    
    public Number optNumber(final String a1) {
        return this.optNumber(a1, null);
    }
    
    public Number optNumber(final String v1, final Number v2) {
        final Object v3 = this.opt(v1);
        if (JSONObject.NULL.equals(v3)) {
            return v2;
        }
        if (v3 instanceof Number) {
            return (Number)v3;
        }
        try {
            return stringToNumber(v3.toString());
        }
        catch (Exception a1) {
            return v2;
        }
    }
    
    public String optString(final String a1) {
        return this.optString(a1, "");
    }
    
    public String optString(final String a1, final String a2) {
        final Object v1 = this.opt(a1);
        return JSONObject.NULL.equals(v1) ? a2 : v1.toString();
    }
    
    private void populateMap(final Object v-8) {
        final Class<?> class1 = v-8.getClass();
        final boolean b = class1.getClassLoader() != null;
        final Method[] array2;
        final Method[] array = array2 = (b ? class1.getMethods() : class1.getDeclaredMethods());
        for (final Method v-9 : array2) {
            final int v0 = v-9.getModifiers();
            if (Modifier.isPublic(v0) && !Modifier.isStatic(v0) && v-9.getParameterTypes().length == 0 && !v-9.isBridge() && v-9.getReturnType() != Void.TYPE && this.isValidMethodName(v-9.getName())) {
                final String v2 = this.getKeyNameFromMethod(v-9);
                if (v2 != null && !v2.isEmpty()) {
                    try {
                        final Object a1 = v-9.invoke(v-8, new Object[0]);
                        if (a1 != null) {
                            this.map.put(v2, wrap(a1));
                            if (a1 instanceof Closeable) {
                                try {
                                    ((Closeable)a1).close();
                                }
                                catch (IOException ex) {}
                            }
                        }
                    }
                    catch (IllegalAccessException ex2) {}
                    catch (IllegalArgumentException ex3) {}
                    catch (InvocationTargetException ex4) {}
                }
            }
        }
    }
    
    private boolean isValidMethodName(final String a1) {
        return !"getClass".equals(a1) && !"getDeclaringClass".equals(a1);
    }
    
    private String getKeyNameFromMethod(final Method v-2) {
        final int annotationDepth = getAnnotationDepth(v-2, JSONPropertyIgnore.class);
        if (annotationDepth > 0) {
            final int a1 = getAnnotationDepth(v-2, JSONPropertyName.class);
            if (a1 < 0 || annotationDepth <= a1) {
                return null;
            }
        }
        final JSONPropertyName v0 = (JSONPropertyName)getAnnotation(v-2, (Class)JSONPropertyName.class);
        if (v0 != null && v0.value() != null && !v0.value().isEmpty()) {
            return v0.value();
        }
        final String v2 = v-2.getName();
        String v3;
        if (v2.startsWith("get") && v2.length() > 3) {
            v3 = v2.substring(3);
        }
        else {
            if (!v2.startsWith("is") || v2.length() <= 2) {
                return null;
            }
            v3 = v2.substring(2);
        }
        if (Character.isLowerCase(v3.charAt(0))) {
            return null;
        }
        if (v3.length() == 1) {
            v3 = v3.toLowerCase(Locale.ROOT);
        }
        else if (!Character.isUpperCase(v3.charAt(1))) {
            v3 = v3.substring(0, 1).toLowerCase(Locale.ROOT) + v3.substring(1);
        }
        return v3;
    }
    
    private static <A extends java.lang.Object> A getAnnotation(final Method v-6, final Class<A> v-5) {
        if (v-6 == null || v-5 == null) {
            return null;
        }
        if (v-6.isAnnotationPresent((Class<? extends Annotation>)v-5)) {
            return v-6.getAnnotation(v-5);
        }
        final Class<?> declaringClass = v-6.getDeclaringClass();
        if (declaringClass.getSuperclass() == null) {
            return null;
        }
        for (final Class<?> v0 : declaringClass.getInterfaces()) {
            try {
                final Method a1 = v0.getMethod(v-6.getName(), v-6.getParameterTypes());
                return (A)getAnnotation(a1, (Class)v-5);
            }
            catch (SecurityException a2) {}
            catch (NoSuchMethodException v2) {}
        }
        try {
            return (A)getAnnotation(declaringClass.getSuperclass().getMethod(v-6.getName(), v-6.getParameterTypes()), (Class)v-5);
        }
        catch (SecurityException ex) {
            return null;
        }
        catch (NoSuchMethodException ex2) {
            return null;
        }
    }
    
    private static int getAnnotationDepth(final Method v-6, final Class<? extends Annotation> v-5) {
        if (v-6 == null || v-5 == null) {
            return -1;
        }
        if (v-6.isAnnotationPresent(v-5)) {
            return 1;
        }
        final Class<?> declaringClass = v-6.getDeclaringClass();
        if (declaringClass.getSuperclass() == null) {
            return -1;
        }
        for (final Class<?> v0 : declaringClass.getInterfaces()) {
            try {
                final Method a1 = v0.getMethod(v-6.getName(), v-6.getParameterTypes());
                final int a2 = getAnnotationDepth(a1, v-5);
                if (a2 > 0) {
                    return a2 + 1;
                }
            }
            catch (SecurityException v2) {}
            catch (NoSuchMethodException v3) {}
        }
        try {
            final int annotationDepth = getAnnotationDepth(declaringClass.getSuperclass().getMethod(v-6.getName(), v-6.getParameterTypes()), v-5);
            if (annotationDepth > 0) {
                return annotationDepth + 1;
            }
            return -1;
        }
        catch (SecurityException ex) {
            return -1;
        }
        catch (NoSuchMethodException ex2) {
            return -1;
        }
    }
    
    public JSONObject put(final String a1, final boolean a2) throws JSONException {
        return this.put(a1, a2 ? Boolean.TRUE : Boolean.FALSE);
    }
    
    public JSONObject put(final String a1, final Collection<?> a2) throws JSONException {
        return this.put(a1, new JSONArray(a2));
    }
    
    public JSONObject put(final String a1, final double a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONObject put(final String a1, final float a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONObject put(final String a1, final int a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONObject put(final String a1, final long a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONObject put(final String a1, final Map<?, ?> a2) throws JSONException {
        return this.put(a1, new JSONObject(a2));
    }
    
    public JSONObject put(final String a1, final Object a2) throws JSONException {
        if (a1 == null) {
            throw new NullPointerException("Null key.");
        }
        if (a2 != null) {
            testValidity(a2);
            this.map.put(a1, a2);
        }
        else {
            this.remove(a1);
        }
        return this;
    }
    
    public JSONObject putOnce(final String a1, final Object a2) throws JSONException {
        if (a1 == null || a2 == null) {
            return this;
        }
        if (this.opt(a1) != null) {
            throw new JSONException("Duplicate key \"" + a1 + "\"");
        }
        return this.put(a1, a2);
    }
    
    public JSONObject putOpt(final String a1, final Object a2) throws JSONException {
        if (a1 != null && a2 != null) {
            return this.put(a1, a2);
        }
        return this;
    }
    
    public Object query(final String a1) {
        return this.query(new JSONPointer(a1));
    }
    
    public Object query(final JSONPointer a1) {
        return a1.queryFrom(this);
    }
    
    public Object optQuery(final String a1) {
        return this.optQuery(new JSONPointer(a1));
    }
    
    public Object optQuery(final JSONPointer v2) {
        try {
            return v2.queryFrom(this);
        }
        catch (JSONPointerException a1) {
            return null;
        }
    }
    
    public static String quote(final String v1) {
        final StringWriter v2 = new StringWriter();
        synchronized (v2.getBuffer()) {
            try {
                return quote(v1, v2).toString();
            }
            catch (IOException a1) {
                return "";
            }
        }
    }
    
    public static Writer quote(final String v1, final Writer v2) throws IOException {
        if (v1 == null || v1.isEmpty()) {
            v2.write("\"\"");
            return v2;
        }
        char v3 = '\0';
        final int v4 = v1.length();
        v2.write(34);
        for (int v5 = 0; v5 < v4; ++v5) {
            final char a2 = v3;
            v3 = v1.charAt(v5);
            switch (v3) {
                case '\"':
                case '\\': {
                    v2.write(92);
                    v2.write(v3);
                    break;
                }
                case '/': {
                    if (a2 == '<') {
                        v2.write(92);
                    }
                    v2.write(v3);
                    break;
                }
                case '\b': {
                    v2.write("\\b");
                    break;
                }
                case '\t': {
                    v2.write("\\t");
                    break;
                }
                case '\n': {
                    v2.write("\\n");
                    break;
                }
                case '\f': {
                    v2.write("\\f");
                    break;
                }
                case '\r': {
                    v2.write("\\r");
                    break;
                }
                default: {
                    if (v3 < ' ' || (v3 >= '\u0080' && v3 < ' ') || (v3 >= '\u2000' && v3 < '\u2100')) {
                        v2.write("\\u");
                        final String a3 = Integer.toHexString(v3);
                        v2.write("0000", 0, 4 - a3.length());
                        v2.write(a3);
                        break;
                    }
                    v2.write(v3);
                    break;
                }
            }
        }
        v2.write(34);
        return v2;
    }
    
    public Object remove(final String a1) {
        return this.map.remove(a1);
    }
    
    public boolean similar(final Object v-3) {
        try {
            if (!(v-3 instanceof JSONObject)) {
                return false;
            }
            if (!this.keySet().equals(((JSONObject)v-3).keySet())) {
                return false;
            }
            for (final Map.Entry<String, ?> entry : this.entrySet()) {
                final String a1 = entry.getKey();
                final Object v1 = entry.getValue();
                final Object v2 = ((JSONObject)v-3).get(a1);
                if (v1 == v2) {
                    continue;
                }
                if (v1 == null) {
                    return false;
                }
                if (v1 instanceof JSONObject) {
                    if (!((JSONObject)v1).similar(v2)) {
                        return false;
                    }
                    continue;
                }
                else if (v1 instanceof JSONArray) {
                    if (!((JSONArray)v1).similar(v2)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (!v1.equals(v2)) {
                        return false;
                    }
                    continue;
                }
            }
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    protected static boolean isDecimalNotation(final String a1) {
        return a1.indexOf(46) > -1 || a1.indexOf(101) > -1 || a1.indexOf(69) > -1 || "-0".equals(a1);
    }
    
    protected static Number stringToNumber(final String v-1) throws NumberFormatException {
        final char v0 = v-1.charAt(0);
        if ((v0 < '0' || v0 > '9') && v0 != '-') {
            throw new NumberFormatException("val [" + v-1 + "] is not a valid number.");
        }
        if (isDecimalNotation(v-1)) {
            if (v-1.length() > 14) {
                return new BigDecimal(v-1);
            }
            final Double a1 = Double.valueOf(v-1);
            if (a1.isInfinite() || a1.isNaN()) {
                return new BigDecimal(v-1);
            }
            return a1;
        }
        else {
            final BigInteger v2 = new BigInteger(v-1);
            if (v2.bitLength() <= 31) {
                return v2.intValue();
            }
            if (v2.bitLength() <= 63) {
                return v2.longValue();
            }
            return v2;
        }
    }
    
    public static Object stringToValue(final String v-1) {
        if ("".equals(v-1)) {
            return v-1;
        }
        if ("true".equalsIgnoreCase(v-1)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(v-1)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(v-1)) {
            return JSONObject.NULL;
        }
        final char v0 = v-1.charAt(0);
        if (v0 < '0' || v0 > '9') {
            if (v0 != '-') {
                return v-1;
            }
        }
        try {
            if (isDecimalNotation(v-1)) {
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
    
    public static void testValidity(final Object a1) throws JSONException {
        if (a1 != null) {
            if (a1 instanceof Double) {
                if (((Double)a1).isInfinite() || ((Double)a1).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            }
            else if (a1 instanceof Float && (((Float)a1).isInfinite() || ((Float)a1).isNaN())) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }
    
    public JSONArray toJSONArray(final JSONArray v2) throws JSONException {
        if (v2 == null || v2.isEmpty()) {
            return null;
        }
        final JSONArray v3 = new JSONArray();
        for (int a1 = 0; a1 < v2.length(); ++a1) {
            v3.put(this.opt(v2.getString(a1)));
        }
        return v3;
    }
    
    @Override
    public String toString() {
        try {
            return this.toString(0);
        }
        catch (Exception v1) {
            return null;
        }
    }
    
    public String toString(final int a1) throws JSONException {
        final StringWriter v1 = new StringWriter();
        synchronized (v1.getBuffer()) {
            return this.write(v1, a1, 0).toString();
        }
    }
    
    public static String valueToString(final Object a1) throws JSONException {
        return JSONWriter.valueToString(a1);
    }
    
    public static Object wrap(final Object v0) {
        try {
            if (v0 == null) {
                return JSONObject.NULL;
            }
            if (v0 instanceof JSONObject || v0 instanceof JSONArray || JSONObject.NULL.equals(v0) || v0 instanceof JSONString || v0 instanceof Byte || v0 instanceof Character || v0 instanceof Short || v0 instanceof Integer || v0 instanceof Long || v0 instanceof Boolean || v0 instanceof Float || v0 instanceof Double || v0 instanceof String || v0 instanceof BigInteger || v0 instanceof BigDecimal || v0 instanceof Enum) {
                return v0;
            }
            if (v0 instanceof Collection) {
                final Collection<?> a1 = (Collection<?>)v0;
                return new JSONArray(a1);
            }
            if (v0.getClass().isArray()) {
                return new JSONArray(v0);
            }
            if (v0 instanceof Map) {
                final Map<?, ?> v = (Map<?, ?>)v0;
                return new JSONObject(v);
            }
            final Package v2 = v0.getClass().getPackage();
            final String v3 = (v2 != null) ? v2.getName() : "";
            if (v3.startsWith("java.") || v3.startsWith("javax.") || v0.getClass().getClassLoader() == null) {
                return v0.toString();
            }
            return new JSONObject(v0);
        }
        catch (Exception v4) {
            return null;
        }
    }
    
    public Writer write(final Writer a1) throws JSONException {
        return this.write(a1, 0, 0);
    }
    
    static final Writer writeValue(final Writer v-3, final Object v-2, final int v-1, final int v0) throws JSONException, IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          12
        //     4: aload_1         /* v-2 */
        //     5: aconst_null    
        //     6: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //     9: ifeq            22
        //    12: aload_0         /* v-3 */
        //    13: ldc_w           "null"
        //    16: invokevirtual   java/io/Writer.write:(Ljava/lang/String;)V
        //    19: goto            316
        //    22: aload_1         /* v-2 */
        //    23: instanceof      Lorg/json/JSONString;
        //    26: ifeq            82
        //    29: aload_1         /* v-2 */
        //    30: checkcast       Lorg/json/JSONString;
        //    33: invokeinterface org/json/JSONString.toJSONString:()Ljava/lang/String;
        //    38: astore          a1
        //    40: goto            55
        //    43: astore          a2
        //    45: new             Lorg/json/JSONException;
        //    48: dup            
        //    49: aload           a2
        //    51: invokespecial   org/json/JSONException.<init>:(Ljava/lang/Throwable;)V
        //    54: athrow         
        //    55: aload_0         /* v-3 */
        //    56: aload           a3
        //    58: ifnull          69
        //    61: aload           a3
        //    63: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //    66: goto            76
        //    69: aload_1         /* v-2 */
        //    70: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //    73: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;)Ljava/lang/String;
        //    76: invokevirtual   java/io/Writer.write:(Ljava/lang/String;)V
        //    79: goto            316
        //    82: aload_1         /* v-2 */
        //    83: instanceof      Ljava/lang/Number;
        //    86: ifeq            131
        //    89: aload_1         /* v-2 */
        //    90: checkcast       Ljava/lang/Number;
        //    93: invokestatic    org/json/JSONObject.numberToString:(Ljava/lang/Number;)Ljava/lang/String;
        //    96: astore          a4
        //    98: getstatic       org/json/JSONObject.NUMBER_PATTERN:Ljava/util/regex/Pattern;
        //   101: aload           a4
        //   103: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //   106: invokevirtual   java/util/regex/Matcher.matches:()Z
        //   109: ifeq            121
        //   112: aload_0         /* v-3 */
        //   113: aload           a4
        //   115: invokevirtual   java/io/Writer.write:(Ljava/lang/String;)V
        //   118: goto            128
        //   121: aload           a4
        //   123: aload_0         /* v-3 */
        //   124: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;Ljava/io/Writer;)Ljava/io/Writer;
        //   127: pop            
        //   128: goto            316
        //   131: aload_1         /* v-2 */
        //   132: instanceof      Ljava/lang/Boolean;
        //   135: ifeq            149
        //   138: aload_0         /* v-3 */
        //   139: aload_1         /* v-2 */
        //   140: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   143: invokevirtual   java/io/Writer.write:(Ljava/lang/String;)V
        //   146: goto            316
        //   149: aload_1         /* v-2 */
        //   150: instanceof      Ljava/lang/Enum;
        //   153: ifeq            173
        //   156: aload_0         /* v-3 */
        //   157: aload_1         /* v-2 */
        //   158: checkcast       Ljava/lang/Enum;
        //   161: invokevirtual   java/lang/Enum.name:()Ljava/lang/String;
        //   164: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;)Ljava/lang/String;
        //   167: invokevirtual   java/io/Writer.write:(Ljava/lang/String;)V
        //   170: goto            316
        //   173: aload_1         /* v-2 */
        //   174: instanceof      Lorg/json/JSONObject;
        //   177: ifeq            194
        //   180: aload_1         /* v-2 */
        //   181: checkcast       Lorg/json/JSONObject;
        //   184: aload_0         /* v-3 */
        //   185: iload_2         /* v-1 */
        //   186: iload_3         /* v0 */
        //   187: invokevirtual   org/json/JSONObject.write:(Ljava/io/Writer;II)Ljava/io/Writer;
        //   190: pop            
        //   191: goto            316
        //   194: aload_1         /* v-2 */
        //   195: instanceof      Lorg/json/JSONArray;
        //   198: ifeq            215
        //   201: aload_1         /* v-2 */
        //   202: checkcast       Lorg/json/JSONArray;
        //   205: aload_0         /* v-3 */
        //   206: iload_2         /* v-1 */
        //   207: iload_3         /* v0 */
        //   208: invokevirtual   org/json/JSONArray.write:(Ljava/io/Writer;II)Ljava/io/Writer;
        //   211: pop            
        //   212: goto            316
        //   215: aload_1         /* v-2 */
        //   216: instanceof      Ljava/util/Map;
        //   219: ifeq            247
        //   222: aload_1         /* v-2 */
        //   223: checkcast       Ljava/util/Map;
        //   226: astore          v1
        //   228: new             Lorg/json/JSONObject;
        //   231: dup            
        //   232: aload           v1
        //   234: invokespecial   org/json/JSONObject.<init>:(Ljava/util/Map;)V
        //   237: aload_0         /* v-3 */
        //   238: iload_2         /* v-1 */
        //   239: iload_3         /* v0 */
        //   240: invokevirtual   org/json/JSONObject.write:(Ljava/io/Writer;II)Ljava/io/Writer;
        //   243: pop            
        //   244: goto            316
        //   247: aload_1         /* v-2 */
        //   248: instanceof      Ljava/util/Collection;
        //   251: ifeq            279
        //   254: aload_1         /* v-2 */
        //   255: checkcast       Ljava/util/Collection;
        //   258: astore          v1
        //   260: new             Lorg/json/JSONArray;
        //   263: dup            
        //   264: aload           v1
        //   266: invokespecial   org/json/JSONArray.<init>:(Ljava/util/Collection;)V
        //   269: aload_0         /* v-3 */
        //   270: iload_2         /* v-1 */
        //   271: iload_3         /* v0 */
        //   272: invokevirtual   org/json/JSONArray.write:(Ljava/io/Writer;II)Ljava/io/Writer;
        //   275: pop            
        //   276: goto            316
        //   279: aload_1         /* v-2 */
        //   280: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   283: invokevirtual   java/lang/Class.isArray:()Z
        //   286: ifeq            307
        //   289: new             Lorg/json/JSONArray;
        //   292: dup            
        //   293: aload_1         /* v-2 */
        //   294: invokespecial   org/json/JSONArray.<init>:(Ljava/lang/Object;)V
        //   297: aload_0         /* v-3 */
        //   298: iload_2         /* v-1 */
        //   299: iload_3         /* v0 */
        //   300: invokevirtual   org/json/JSONArray.write:(Ljava/io/Writer;II)Ljava/io/Writer;
        //   303: pop            
        //   304: goto            316
        //   307: aload_1         /* v-2 */
        //   308: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   311: aload_0         /* v-3 */
        //   312: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;Ljava/io/Writer;)Ljava/io/Writer;
        //   315: pop            
        //   316: aload_0         /* v-3 */
        //   317: areturn        
        //    Exceptions:
        //  throws org.json.JSONException
        //  throws java.io.IOException
        //    StackMapTable: 00 12 0C 09 54 07 00 24 FC 00 0B 07 00 04 4D 07 03 1C FF 00 06 00 05 07 03 1C 07 00 04 01 01 07 00 04 00 02 07 03 1C 07 00 57 FA 00 05 FC 00 26 07 00 57 FA 00 06 02 11 17 14 14 1F 1F 1B 08
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  29     40     43     55     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static final void indent(final Writer a2, final int v1) throws IOException {
        for (int a3 = 0; a3 < v1; ++a3) {
            a2.write(32);
        }
    }
    
    public Writer write(final Writer v-8, final int v-7, final int v-6) throws JSONException {
        try {
            boolean b = false;
            final int length = this.length();
            v-8.write(123);
            if (length == 1) {
                final Map.Entry<String, ?> a2 = this.entrySet().iterator().next();
                final String a3 = a2.getKey();
                v-8.write(quote(a3));
                v-8.write(58);
                if (v-7 > 0) {
                    v-8.write(32);
                }
                try {
                    writeValue(v-8, a2.getValue(), v-7, v-6);
                }
                catch (Exception a4) {
                    throw new JSONException("Unable to write JSONObject value for key: " + a3, a4);
                }
            }
            else if (length != 0) {
                final int n = v-6 + v-7;
                for (final Map.Entry<String, ?> entry : this.entrySet()) {
                    if (b) {
                        v-8.write(44);
                    }
                    if (v-7 > 0) {
                        v-8.write(10);
                    }
                    indent(v-8, n);
                    final String v0 = entry.getKey();
                    v-8.write(quote(v0));
                    v-8.write(58);
                    if (v-7 > 0) {
                        v-8.write(32);
                    }
                    try {
                        writeValue(v-8, entry.getValue(), v-7, n);
                    }
                    catch (Exception v2) {
                        throw new JSONException("Unable to write JSONObject value for key: " + v0, v2);
                    }
                    b = true;
                }
                if (v-7 > 0) {
                    v-8.write(10);
                }
                indent(v-8, v-6);
            }
            v-8.write(125);
            return v-8;
        }
        catch (IOException a5) {
            throw new JSONException(a5);
        }
    }
    
    public Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        for (final Map.Entry<String, Object> v0 : this.entrySet()) {
            Object v2;
            if (v0.getValue() == null || JSONObject.NULL.equals(v0.getValue())) {
                v2 = null;
            }
            else if (v0.getValue() instanceof JSONObject) {
                v2 = v0.getValue().toMap();
            }
            else if (v0.getValue() instanceof JSONArray) {
                v2 = v0.getValue().toList();
            }
            else {
                v2 = v0.getValue();
            }
            map.put(v0.getKey(), v2);
        }
        return map;
    }
    
    static {
        NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");
        NULL = new Null();
    }
    
    private static final class Null
    {
        private Null() {
            super();
        }
        
        @Override
        protected final Object clone() {
            return this;
        }
        
        @Override
        public boolean equals(final Object a1) {
            return a1 == null || a1 == this;
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public String toString() {
            return "null";
        }
        
        Null(final JSONObject$1 a1) {
            this();
        }
    }
}
