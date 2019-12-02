package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.math.*;

public class TokenFilter
{
    public static final TokenFilter INCLUDE_ALL;
    
    protected TokenFilter() {
        super();
    }
    
    public TokenFilter filterStartObject() {
        return this;
    }
    
    public TokenFilter filterStartArray() {
        return this;
    }
    
    public void filterFinishObject() {
    }
    
    public void filterFinishArray() {
    }
    
    public TokenFilter includeProperty(final String a1) {
        return this;
    }
    
    public TokenFilter includeElement(final int a1) {
        return this;
    }
    
    public TokenFilter includeRootValue(final int a1) {
        return this;
    }
    
    public boolean includeValue(final JsonParser a1) throws IOException {
        return this._includeScalar();
    }
    
    public boolean includeBoolean(final boolean a1) {
        return this._includeScalar();
    }
    
    public boolean includeNull() {
        return this._includeScalar();
    }
    
    public boolean includeString(final String a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final int a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final long a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final float a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final double a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigDecimal a1) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigInteger a1) {
        return this._includeScalar();
    }
    
    public boolean includeBinary() {
        return this._includeScalar();
    }
    
    public boolean includeRawValue() {
        return this._includeScalar();
    }
    
    public boolean includeEmbeddedValue(final Object a1) {
        return this._includeScalar();
    }
    
    @Override
    public String toString() {
        if (this == TokenFilter.INCLUDE_ALL) {
            return "TokenFilter.INCLUDE_ALL";
        }
        return super.toString();
    }
    
    protected boolean _includeScalar() {
        return true;
    }
    
    static {
        INCLUDE_ALL = new TokenFilter();
    }
}
