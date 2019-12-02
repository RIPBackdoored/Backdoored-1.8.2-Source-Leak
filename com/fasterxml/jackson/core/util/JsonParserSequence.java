package com.fasterxml.jackson.core.util;

import java.util.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public class JsonParserSequence extends JsonParserDelegate
{
    protected final JsonParser[] _parsers;
    protected final boolean _checkForExistingToken;
    protected int _nextParserIndex;
    protected boolean _hasToken;
    
    @Deprecated
    protected JsonParserSequence(final JsonParser[] a1) {
        this(false, a1);
    }
    
    protected JsonParserSequence(final boolean a1, final JsonParser[] a2) {
        super(a2[0]);
        this._checkForExistingToken = a1;
        this._hasToken = (a1 && this.delegate.hasCurrentToken());
        this._parsers = a2;
        this._nextParserIndex = 1;
    }
    
    public static JsonParserSequence createFlattened(final boolean a1, final JsonParser a2, final JsonParser a3) {
        if (!(a2 instanceof JsonParserSequence) && !(a3 instanceof JsonParserSequence)) {
            return new JsonParserSequence(a1, new JsonParser[] { a2, a3 });
        }
        final ArrayList<JsonParser> v1 = new ArrayList<JsonParser>();
        if (a2 instanceof JsonParserSequence) {
            ((JsonParserSequence)a2).addFlattenedActiveParsers(v1);
        }
        else {
            v1.add(a2);
        }
        if (a3 instanceof JsonParserSequence) {
            ((JsonParserSequence)a3).addFlattenedActiveParsers(v1);
        }
        else {
            v1.add(a3);
        }
        return new JsonParserSequence(a1, v1.toArray(new JsonParser[v1.size()]));
    }
    
    @Deprecated
    public static JsonParserSequence createFlattened(final JsonParser a1, final JsonParser a2) {
        return createFlattened(false, a1, a2);
    }
    
    protected void addFlattenedActiveParsers(final List<JsonParser> v0) {
        for (int v = this._nextParserIndex - 1, v2 = this._parsers.length; v < v2; ++v) {
            final JsonParser a1 = this._parsers[v];
            if (a1 instanceof JsonParserSequence) {
                ((JsonParserSequence)a1).addFlattenedActiveParsers(v0);
            }
            else {
                v0.add(a1);
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (this.delegate == null) {
            return null;
        }
        if (this._hasToken) {
            this._hasToken = false;
            return this.delegate.currentToken();
        }
        final JsonToken v1 = this.delegate.nextToken();
        if (v1 == null) {
            return this.switchAndReturnNext();
        }
        return v1;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this.delegate.currentToken() != JsonToken.START_OBJECT && this.delegate.currentToken() != JsonToken.START_ARRAY) {
            return this;
        }
        int v0 = 1;
        while (true) {
            final JsonToken v2 = this.nextToken();
            if (v2 == null) {
                return this;
            }
            if (v2.isStructStart()) {
                ++v0;
            }
            else {
                if (v2.isStructEnd() && --v0 == 0) {
                    return this;
                }
                continue;
            }
        }
    }
    
    public int containedParsersCount() {
        return this._parsers.length;
    }
    
    protected boolean switchToNext() {
        if (this._nextParserIndex < this._parsers.length) {
            this.delegate = this._parsers[this._nextParserIndex++];
            return true;
        }
        return false;
    }
    
    protected JsonToken switchAndReturnNext() throws IOException {
        while (this._nextParserIndex < this._parsers.length) {
            this.delegate = this._parsers[this._nextParserIndex++];
            if (this._checkForExistingToken && this.delegate.hasCurrentToken()) {
                return this.delegate.getCurrentToken();
            }
            final JsonToken v1 = this.delegate.nextToken();
            if (v1 != null) {
                return v1;
            }
        }
        return null;
    }
}
