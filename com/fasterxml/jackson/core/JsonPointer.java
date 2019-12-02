package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.*;

public class JsonPointer
{
    public static final char SEPARATOR = '/';
    protected static final JsonPointer EMPTY;
    protected final JsonPointer _nextSegment;
    protected volatile JsonPointer _head;
    protected final String _asString;
    protected final String _matchingPropertyName;
    protected final int _matchingElementIndex;
    
    protected JsonPointer() {
        super();
        this._nextSegment = null;
        this._matchingPropertyName = "";
        this._matchingElementIndex = -1;
        this._asString = "";
    }
    
    protected JsonPointer(final String a1, final String a2, final JsonPointer a3) {
        super();
        this._asString = a1;
        this._nextSegment = a3;
        this._matchingPropertyName = a2;
        this._matchingElementIndex = _parseIndex(a2);
    }
    
    protected JsonPointer(final String a1, final String a2, final int a3, final JsonPointer a4) {
        super();
        this._asString = a1;
        this._nextSegment = a4;
        this._matchingPropertyName = a2;
        this._matchingElementIndex = a3;
    }
    
    public static JsonPointer compile(final String a1) throws IllegalArgumentException {
        if (a1 == null || a1.length() == 0) {
            return JsonPointer.EMPTY;
        }
        if (a1.charAt(0) != '/') {
            throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + a1 + "\"");
        }
        return _parseTail(a1);
    }
    
    public static JsonPointer valueOf(final String a1) {
        return compile(a1);
    }
    
    public static JsonPointer forPath(JsonStreamContext v-3, final boolean v-2) {
        if (v-3 == null) {
            return JsonPointer.EMPTY;
        }
        if (!v-3.hasPathSegment() && (!v-2 || !v-3.inRoot() || !v-3.hasCurrentIndex())) {
            v-3 = v-3.getParent();
        }
        JsonPointer jsonPointer = null;
        while (v-3 != null) {
            if (v-3.inObject()) {
                String a1 = v-3.getCurrentName();
                if (a1 == null) {
                    a1 = "";
                }
                jsonPointer = new JsonPointer(_fullPath(jsonPointer, a1), a1, jsonPointer);
            }
            else if (v-3.inArray() || v-2) {
                final int a2 = v-3.getCurrentIndex();
                final String v1 = String.valueOf(a2);
                jsonPointer = new JsonPointer(_fullPath(jsonPointer, v1), v1, a2, jsonPointer);
            }
            v-3 = v-3.getParent();
        }
        if (jsonPointer == null) {
            return JsonPointer.EMPTY;
        }
        return jsonPointer;
    }
    
    private static String _fullPath(final JsonPointer a2, final String v1) {
        if (a2 == null) {
            final StringBuilder a3 = new StringBuilder(v1.length() + 1);
            a3.append('/');
            _appendEscaped(a3, v1);
            return a3.toString();
        }
        final String v2 = a2._asString;
        final StringBuilder v3 = new StringBuilder(v1.length() + 1 + v2.length());
        v3.append('/');
        _appendEscaped(v3, v1);
        v3.append(v2);
        return v3.toString();
    }
    
    private static void _appendEscaped(final StringBuilder v-2, final String v-1) {
        for (int a2 = 0, v1 = v-1.length(); a2 < v1; ++a2) {
            final char a3 = v-1.charAt(a2);
            if (a3 == '/') {
                v-2.append("~1");
            }
            else if (a3 == '~') {
                v-2.append("~0");
            }
            else {
                v-2.append(a3);
            }
        }
    }
    
    public boolean matches() {
        return this._nextSegment == null;
    }
    
    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }
    
    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }
    
    public boolean mayMatchProperty() {
        return this._matchingPropertyName != null;
    }
    
    public boolean mayMatchElement() {
        return this._matchingElementIndex >= 0;
    }
    
    public JsonPointer last() {
        JsonPointer v1 = this;
        if (v1 == JsonPointer.EMPTY) {
            return null;
        }
        JsonPointer v2;
        while ((v2 = v1._nextSegment) != JsonPointer.EMPTY) {
            v1 = v2;
        }
        return v1;
    }
    
    public JsonPointer append(final JsonPointer a1) {
        if (this == JsonPointer.EMPTY) {
            return a1;
        }
        if (a1 == JsonPointer.EMPTY) {
            return this;
        }
        String v1 = this._asString;
        if (v1.endsWith("/")) {
            v1 = v1.substring(0, v1.length() - 1);
        }
        return compile(v1 + a1._asString);
    }
    
    public boolean matchesProperty(final String a1) {
        return this._nextSegment != null && this._matchingPropertyName.equals(a1);
    }
    
    public JsonPointer matchProperty(final String a1) {
        if (this._nextSegment != null && this._matchingPropertyName.equals(a1)) {
            return this._nextSegment;
        }
        return null;
    }
    
    public boolean matchesElement(final int a1) {
        return a1 == this._matchingElementIndex && a1 >= 0;
    }
    
    public JsonPointer matchElement(final int a1) {
        if (a1 != this._matchingElementIndex || a1 < 0) {
            return null;
        }
        return this._nextSegment;
    }
    
    public JsonPointer tail() {
        return this._nextSegment;
    }
    
    public JsonPointer head() {
        JsonPointer v1 = this._head;
        if (v1 == null) {
            if (this != JsonPointer.EMPTY) {
                v1 = this._constructHead();
            }
            this._head = v1;
        }
        return v1;
    }
    
    @Override
    public String toString() {
        return this._asString;
    }
    
    @Override
    public int hashCode() {
        return this._asString.hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this || (a1 != null && a1 instanceof JsonPointer && this._asString.equals(((JsonPointer)a1)._asString));
    }
    
    private static final int _parseIndex(final String v-2) {
        final int length = v-2.length();
        if (length == 0 || length > 10) {
            return -1;
        }
        char v0 = v-2.charAt(0);
        if (v0 <= '0') {
            return (length == 1 && v0 == '0') ? 0 : -1;
        }
        if (v0 > '9') {
            return -1;
        }
        for (int a1 = 1; a1 < length; ++a1) {
            v0 = v-2.charAt(a1);
            if (v0 > '9' || v0 < '0') {
                return -1;
            }
        }
        if (length == 10) {
            final long v2 = NumberInput.parseLong(v-2);
            if (v2 > 0L) {
                return -1;
            }
        }
        return NumberInput.parseInt(v-2);
    }
    
    protected static JsonPointer _parseTail(final String v-1) {
        final int v0 = v-1.length();
        int v2 = 1;
        while (v2 < v0) {
            final char a1 = v-1.charAt(v2);
            if (a1 == '/') {
                return new JsonPointer(v-1, v-1.substring(1, v2), _parseTail(v-1.substring(v2)));
            }
            ++v2;
            if (a1 == '~' && v2 < v0) {
                return _parseQuotedTail(v-1, v2);
            }
        }
        return new JsonPointer(v-1, v-1.substring(1), JsonPointer.EMPTY);
    }
    
    protected static JsonPointer _parseQuotedTail(final String a2, int v1) {
        final int v2 = a2.length();
        final StringBuilder v3 = new StringBuilder(Math.max(16, v2));
        if (v1 > 2) {
            v3.append(a2, 1, v1 - 1);
        }
        _appendEscape(v3, a2.charAt(v1++));
        while (v1 < v2) {
            final char a3 = a2.charAt(v1);
            if (a3 == '/') {
                return new JsonPointer(a2, v3.toString(), _parseTail(a2.substring(v1)));
            }
            ++v1;
            if (a3 == '~' && v1 < v2) {
                _appendEscape(v3, a2.charAt(v1++));
            }
            else {
                v3.append(a3);
            }
        }
        return new JsonPointer(a2, v3.toString(), JsonPointer.EMPTY);
    }
    
    protected JsonPointer _constructHead() {
        final JsonPointer v1 = this.last();
        if (v1 == this) {
            return JsonPointer.EMPTY;
        }
        final int v2 = v1._asString.length();
        final JsonPointer v3 = this._nextSegment;
        return new JsonPointer(this._asString.substring(0, this._asString.length() - v2), this._matchingPropertyName, this._matchingElementIndex, v3._constructHead(v2, v1));
    }
    
    protected JsonPointer _constructHead(final int a1, final JsonPointer a2) {
        if (this == a2) {
            return JsonPointer.EMPTY;
        }
        final JsonPointer v1 = this._nextSegment;
        final String v2 = this._asString;
        return new JsonPointer(v2.substring(0, v2.length() - a1), this._matchingPropertyName, this._matchingElementIndex, v1._constructHead(a1, a2));
    }
    
    private static void _appendEscape(final StringBuilder a1, char a2) {
        if (a2 == '0') {
            a2 = '~';
        }
        else if (a2 == '1') {
            a2 = '/';
        }
        else {
            a1.append('~');
        }
        a1.append(a2);
    }
    
    static {
        EMPTY = new JsonPointer();
    }
}
