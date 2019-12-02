package org.json;

import java.lang.reflect.*;
import java.math.*;
import java.io.*;
import java.util.*;

public class JSONArray implements Iterable<Object>
{
    private final ArrayList<Object> myArrayList;
    
    public JSONArray() {
        super();
        this.myArrayList = new ArrayList<Object>();
    }
    
    public JSONArray(final JSONTokener a1) throws JSONException {
        this();
        if (a1.nextClean() != '[') {
            throw a1.syntaxError("A JSONArray text must start with '['");
        }
        char v1 = a1.nextClean();
        if (v1 == '\0') {
            throw a1.syntaxError("Expected a ',' or ']'");
        }
        if (v1 == ']') {
            return;
        }
        a1.back();
        while (true) {
            if (a1.nextClean() == ',') {
                a1.back();
                this.myArrayList.add(JSONObject.NULL);
            }
            else {
                a1.back();
                this.myArrayList.add(a1.nextValue());
            }
            switch (a1.nextClean()) {
                case '\0': {
                    throw a1.syntaxError("Expected a ',' or ']'");
                }
                case ',': {
                    v1 = a1.nextClean();
                    if (v1 == '\0') {
                        throw a1.syntaxError("Expected a ',' or ']'");
                    }
                    if (v1 == ']') {
                        return;
                    }
                    a1.back();
                    continue;
                }
                case ']': {}
                default: {
                    throw a1.syntaxError("Expected a ',' or ']'");
                }
            }
        }
    }
    
    public JSONArray(final String a1) throws JSONException {
        this(new JSONTokener(a1));
    }
    
    public JSONArray(final Collection<?> v2) {
        super();
        if (v2 == null) {
            this.myArrayList = new ArrayList<Object>();
        }
        else {
            this.myArrayList = new ArrayList<Object>(v2.size());
            for (final Object a1 : v2) {
                this.myArrayList.add(JSONObject.wrap(a1));
            }
        }
    }
    
    public JSONArray(final Object v0) throws JSONException {
        this();
        if (v0.getClass().isArray()) {
            final int v = Array.getLength(v0);
            this.myArrayList.ensureCapacity(v);
            for (int a1 = 0; a1 < v; ++a1) {
                this.put(JSONObject.wrap(Array.get(v0, a1)));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }
    
    @Override
    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }
    
    public Object get(final int a1) throws JSONException {
        final Object v1 = this.opt(a1);
        if (v1 == null) {
            throw new JSONException("JSONArray[" + a1 + "] not found.");
        }
        return v1;
    }
    
    public boolean getBoolean(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1.equals(Boolean.FALSE) || (v1 instanceof String && ((String)v1).equalsIgnoreCase("false"))) {
            return false;
        }
        if (v1.equals(Boolean.TRUE) || (v1 instanceof String && ((String)v1).equalsIgnoreCase("true"))) {
            return true;
        }
        throw new JSONException("JSONArray[" + a1 + "] is not a boolean.");
    }
    
    public double getDouble(final int a1) throws JSONException {
        return this.getNumber(a1).doubleValue();
    }
    
    public float getFloat(final int a1) throws JSONException {
        return this.getNumber(a1).floatValue();
    }
    
    public Number getNumber(final int v2) throws JSONException {
        final Object v3 = this.get(v2);
        try {
            if (v3 instanceof Number) {
                return (Number)v3;
            }
            return JSONObject.stringToNumber(v3.toString());
        }
        catch (Exception a1) {
            throw new JSONException("JSONArray[" + v2 + "] is not a number.", a1);
        }
    }
    
    public <E extends Enum<E>> E getEnum(final Class<E> a1, final int a2) throws JSONException {
        final E v1 = (E)this.optEnum((Class<Enum>)a1, a2);
        if (v1 == null) {
            throw new JSONException("JSONArray[" + a2 + "] is not an enum of type " + JSONObject.quote(a1.getSimpleName()) + ".");
        }
        return v1;
    }
    
    public BigDecimal getBigDecimal(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        final BigDecimal v2 = JSONObject.objectToBigDecimal(v1, null);
        if (v2 == null) {
            throw new JSONException("JSONArray[" + a1 + "] could not convert to BigDecimal (" + v1 + ").");
        }
        return v2;
    }
    
    public BigInteger getBigInteger(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        final BigInteger v2 = JSONObject.objectToBigInteger(v1, null);
        if (v2 == null) {
            throw new JSONException("JSONArray[" + a1 + "] could not convert to BigDecimal (" + v1 + ").");
        }
        return v2;
    }
    
    public int getInt(final int a1) throws JSONException {
        return this.getNumber(a1).intValue();
    }
    
    public JSONArray getJSONArray(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof JSONArray) {
            return (JSONArray)v1;
        }
        throw new JSONException("JSONArray[" + a1 + "] is not a JSONArray.");
    }
    
    public JSONObject getJSONObject(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof JSONObject) {
            return (JSONObject)v1;
        }
        throw new JSONException("JSONArray[" + a1 + "] is not a JSONObject.");
    }
    
    public long getLong(final int a1) throws JSONException {
        return this.getNumber(a1).longValue();
    }
    
    public String getString(final int a1) throws JSONException {
        final Object v1 = this.get(a1);
        if (v1 instanceof String) {
            return (String)v1;
        }
        throw new JSONException("JSONArray[" + a1 + "] not a string.");
    }
    
    public boolean isNull(final int a1) {
        return JSONObject.NULL.equals(this.opt(a1));
    }
    
    public String join(final String v2) throws JSONException {
        final int v3 = this.length();
        if (v3 == 0) {
            return "";
        }
        final StringBuilder v4 = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
        for (int a1 = 1; a1 < v3; ++a1) {
            v4.append(v2).append(JSONObject.valueToString(this.myArrayList.get(a1)));
        }
        return v4.toString();
    }
    
    public int length() {
        return this.myArrayList.size();
    }
    
    public Object opt(final int a1) {
        return (a1 < 0 || a1 >= this.length()) ? null : this.myArrayList.get(a1);
    }
    
    public boolean optBoolean(final int a1) {
        return this.optBoolean(a1, false);
    }
    
    public boolean optBoolean(final int v1, final boolean v2) {
        try {
            return this.getBoolean(v1);
        }
        catch (Exception a1) {
            return v2;
        }
    }
    
    public double optDouble(final int a1) {
        return this.optDouble(a1, Double.NaN);
    }
    
    public double optDouble(final int a1, final double a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        final double v2 = v1.doubleValue();
        return v2;
    }
    
    public float optFloat(final int a1) {
        return this.optFloat(a1, Float.NaN);
    }
    
    public float optFloat(final int a1, final float a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        final float v2 = v1.floatValue();
        return v2;
    }
    
    public int optInt(final int a1) {
        return this.optInt(a1, 0);
    }
    
    public int optInt(final int a1, final int a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        return v1.intValue();
    }
    
    public <E extends Enum<E>> E optEnum(final Class<E> a1, final int a2) {
        return this.optEnum(a1, a2, (E)null);
    }
    
    public <E extends Enum<E>> E optEnum(final Class<E> v-2, final int v-1, final E v0) {
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
    
    public BigInteger optBigInteger(final int a1, final BigInteger a2) {
        final Object v1 = this.opt(a1);
        return JSONObject.objectToBigInteger(v1, a2);
    }
    
    public BigDecimal optBigDecimal(final int a1, final BigDecimal a2) {
        final Object v1 = this.opt(a1);
        return JSONObject.objectToBigDecimal(v1, a2);
    }
    
    public JSONArray optJSONArray(final int a1) {
        final Object v1 = this.opt(a1);
        return (v1 instanceof JSONArray) ? ((JSONArray)v1) : null;
    }
    
    public JSONObject optJSONObject(final int a1) {
        final Object v1 = this.opt(a1);
        return (v1 instanceof JSONObject) ? ((JSONObject)v1) : null;
    }
    
    public long optLong(final int a1) {
        return this.optLong(a1, 0L);
    }
    
    public long optLong(final int a1, final long a2) {
        final Number v1 = this.optNumber(a1, null);
        if (v1 == null) {
            return a2;
        }
        return v1.longValue();
    }
    
    public Number optNumber(final int a1) {
        return this.optNumber(a1, null);
    }
    
    public Number optNumber(final int v1, final Number v2) {
        final Object v3 = this.opt(v1);
        if (JSONObject.NULL.equals(v3)) {
            return v2;
        }
        if (v3 instanceof Number) {
            return (Number)v3;
        }
        if (v3 instanceof String) {
            try {
                return JSONObject.stringToNumber((String)v3);
            }
            catch (Exception a1) {
                return v2;
            }
        }
        return v2;
    }
    
    public String optString(final int a1) {
        return this.optString(a1, "");
    }
    
    public String optString(final int a1, final String a2) {
        final Object v1 = this.opt(a1);
        return JSONObject.NULL.equals(v1) ? a2 : v1.toString();
    }
    
    public JSONArray put(final boolean a1) {
        return this.put(a1 ? Boolean.TRUE : Boolean.FALSE);
    }
    
    public JSONArray put(final Collection<?> a1) {
        return this.put(new JSONArray(a1));
    }
    
    public JSONArray put(final double a1) throws JSONException {
        return this.put((Object)a1);
    }
    
    public JSONArray put(final float a1) throws JSONException {
        return this.put((Object)a1);
    }
    
    public JSONArray put(final int a1) {
        return this.put((Object)a1);
    }
    
    public JSONArray put(final long a1) {
        return this.put((Object)a1);
    }
    
    public JSONArray put(final Map<?, ?> a1) {
        return this.put(new JSONObject(a1));
    }
    
    public JSONArray put(final Object a1) {
        JSONObject.testValidity(a1);
        this.myArrayList.add(a1);
        return this;
    }
    
    public JSONArray put(final int a1, final boolean a2) throws JSONException {
        return this.put(a1, a2 ? Boolean.TRUE : Boolean.FALSE);
    }
    
    public JSONArray put(final int a1, final Collection<?> a2) throws JSONException {
        return this.put(a1, new JSONArray(a2));
    }
    
    public JSONArray put(final int a1, final double a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONArray put(final int a1, final float a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONArray put(final int a1, final int a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONArray put(final int a1, final long a2) throws JSONException {
        return this.put(a1, (Object)a2);
    }
    
    public JSONArray put(final int a1, final Map<?, ?> a2) throws JSONException {
        this.put(a1, new JSONObject(a2));
        return this;
    }
    
    public JSONArray put(final int a1, final Object a2) throws JSONException {
        if (a1 < 0) {
            throw new JSONException("JSONArray[" + a1 + "] not found.");
        }
        if (a1 < this.length()) {
            JSONObject.testValidity(a2);
            this.myArrayList.set(a1, a2);
            return this;
        }
        if (a1 == this.length()) {
            return this.put(a2);
        }
        this.myArrayList.ensureCapacity(a1 + 1);
        while (a1 != this.length()) {
            this.myArrayList.add(JSONObject.NULL);
        }
        return this.put(a2);
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
    
    public Object remove(final int a1) {
        return (a1 >= 0 && a1 < this.length()) ? this.myArrayList.remove(a1) : null;
    }
    
    public boolean similar(final Object v-3) {
        if (!(v-3 instanceof JSONArray)) {
            return false;
        }
        final int length = this.length();
        if (length != ((JSONArray)v-3).length()) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final Object a1 = this.myArrayList.get(i);
            final Object v1 = ((JSONArray)v-3).myArrayList.get(i);
            if (a1 != v1) {
                if (a1 == null) {
                    return false;
                }
                if (a1 instanceof JSONObject) {
                    if (!((JSONObject)a1).similar(v1)) {
                        return false;
                    }
                }
                else if (a1 instanceof JSONArray) {
                    if (!((JSONArray)a1).similar(v1)) {
                        return false;
                    }
                }
                else if (!a1.equals(v1)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public JSONObject toJSONObject(final JSONArray v2) throws JSONException {
        if (v2 == null || v2.isEmpty() || this.isEmpty()) {
            return null;
        }
        final JSONObject v3 = new JSONObject(v2.length());
        for (int a1 = 0; a1 < v2.length(); ++a1) {
            v3.put(v2.getString(a1), this.opt(a1));
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
    
    public Writer write(final Writer a1) throws JSONException {
        return this.write(a1, 0, 0);
    }
    
    public Writer write(final Writer v-4, final int v-3, final int v-2) throws JSONException {
        try {
            boolean b = false;
            final int v0 = this.length();
            v-4.write(91);
            Label_0178: {
                if (v0 == 1) {
                    try {
                        JSONObject.writeValue(v-4, this.myArrayList.get(0), v-3, v-2);
                        break Label_0178;
                    }
                    catch (Exception a1) {
                        throw new JSONException("Unable to write JSONArray value at index: 0", a1);
                    }
                }
                if (v0 != 0) {
                    final int v2 = v-2 + v-3;
                    for (int a2 = 0; a2 < v0; ++a2) {
                        if (b) {
                            v-4.write(44);
                        }
                        if (v-3 > 0) {
                            v-4.write(10);
                        }
                        JSONObject.indent(v-4, v2);
                        try {
                            JSONObject.writeValue(v-4, this.myArrayList.get(a2), v-3, v2);
                        }
                        catch (Exception a3) {
                            throw new JSONException("Unable to write JSONArray value at index: " + a2, a3);
                        }
                        b = true;
                    }
                    if (v-3 > 0) {
                        v-4.write(10);
                    }
                    JSONObject.indent(v-4, v-2);
                }
            }
            v-4.write(93);
            return v-4;
        }
        catch (IOException a4) {
            throw new JSONException(a4);
        }
    }
    
    public List<Object> toList() {
        final List<Object> list = new ArrayList<Object>(this.myArrayList.size());
        for (final Object v1 : this.myArrayList) {
            if (v1 == null || JSONObject.NULL.equals(v1)) {
                list.add(null);
            }
            else if (v1 instanceof JSONArray) {
                list.add(((JSONArray)v1).toList());
            }
            else if (v1 instanceof JSONObject) {
                list.add(((JSONObject)v1).toMap());
            }
            else {
                list.add(v1);
            }
        }
        return list;
    }
    
    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }
}
