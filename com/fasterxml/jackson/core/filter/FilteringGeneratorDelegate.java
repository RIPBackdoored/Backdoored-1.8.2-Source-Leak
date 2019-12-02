package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.math.*;

public class FilteringGeneratorDelegate extends JsonGeneratorDelegate
{
    protected TokenFilter rootFilter;
    protected boolean _allowMultipleMatches;
    protected boolean _includePath;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected TokenFilterContext _filterContext;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    
    public FilteringGeneratorDelegate(final JsonGenerator a1, final TokenFilter a2, final boolean a3, final boolean a4) {
        super(a1, false);
        this.rootFilter = a2;
        this._itemFilter = a2;
        this._filterContext = TokenFilterContext.createRootContext(a2);
        this._includePath = a3;
        this._allowMultipleMatches = a4;
    }
    
    public TokenFilter getFilter() {
        return this.rootFilter;
    }
    
    public JsonStreamContext getFilterContext() {
        return this._filterContext;
    }
    
    public int getMatchCount() {
        return this._matchCount;
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this._filterContext;
    }
    
    @Override
    public void writeStartArray() throws IOException {
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray();
            return;
        }
        this._itemFilter = this._filterContext.checkValue(this._itemFilter);
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray();
        }
        else {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
        }
    }
    
    @Override
    public void writeStartArray(final int a1) throws IOException {
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(a1);
            return;
        }
        this._itemFilter = this._filterContext.checkValue(this._itemFilter);
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(a1);
        }
        else {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
        }
    }
    
    @Override
    public void writeEndArray() throws IOException {
        this._filterContext = this._filterContext.closeArray(this.delegate);
        if (this._filterContext != null) {
            this._itemFilter = this._filterContext.getFilter();
        }
    }
    
    @Override
    public void writeStartObject() throws IOException {
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
            return;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject();
            return;
        }
        TokenFilter v1 = this._filterContext.checkValue(this._itemFilter);
        if (v1 == null) {
            return;
        }
        if (v1 != TokenFilter.INCLUDE_ALL) {
            v1 = v1.filterStartObject();
        }
        if (v1 == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildObjectContext(v1, true);
            this.delegate.writeStartObject();
        }
        else {
            this._filterContext = this._filterContext.createChildObjectContext(v1, false);
        }
    }
    
    @Override
    public void writeStartObject(final Object a1) throws IOException {
        if (this._itemFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, false);
            return;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject(a1);
            return;
        }
        TokenFilter v1 = this._filterContext.checkValue(this._itemFilter);
        if (v1 == null) {
            return;
        }
        if (v1 != TokenFilter.INCLUDE_ALL) {
            v1 = v1.filterStartObject();
        }
        if (v1 == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildObjectContext(v1, true);
            this.delegate.writeStartObject(a1);
        }
        else {
            this._filterContext = this._filterContext.createChildObjectContext(v1, false);
        }
    }
    
    @Override
    public void writeEndObject() throws IOException {
        this._filterContext = this._filterContext.closeObject(this.delegate);
        if (this._filterContext != null) {
            this._itemFilter = this._filterContext.getFilter();
        }
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
        TokenFilter v1 = this._filterContext.setFieldName(a1);
        if (v1 == null) {
            this._itemFilter = null;
            return;
        }
        if (v1 == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = v1;
            this.delegate.writeFieldName(a1);
            return;
        }
        v1 = v1.includeProperty(a1);
        if ((this._itemFilter = v1) == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
        }
    }
    
    @Override
    public void writeFieldName(final SerializableString a1) throws IOException {
        TokenFilter v1 = this._filterContext.setFieldName(a1.getValue());
        if (v1 == null) {
            this._itemFilter = null;
            return;
        }
        if (v1 == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = v1;
            this.delegate.writeFieldName(a1);
            return;
        }
        v1 = v1.includeProperty(a1.getValue());
        if ((this._itemFilter = v1) == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
        }
    }
    
    @Override
    public void writeString(final String v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeString(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(v2);
    }
    
    @Override
    public void writeString(final char[] v1, final int v2, final int v3) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final String a1 = new String(v1, v2, v3);
            final TokenFilter a2 = this._filterContext.checkValue(this._itemFilter);
            if (a2 == null) {
                return;
            }
            if (a2 != TokenFilter.INCLUDE_ALL && !a2.includeString(a1)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(v1, v2, v3);
    }
    
    @Override
    public void writeString(final SerializableString v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeString(v2.getValue())) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(v2);
    }
    
    @Override
    public void writeRawUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRawUTF8String(a1, a2, a3);
        }
    }
    
    @Override
    public void writeUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeUTF8String(a1, a2, a3);
        }
    }
    
    @Override
    public void writeRaw(final String a1) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1);
        }
    }
    
    @Override
    public void writeRaw(final String a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1);
        }
    }
    
    @Override
    public void writeRaw(final SerializableString a1) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1);
        }
    }
    
    @Override
    public void writeRaw(final char[] a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1, a2, a3);
        }
    }
    
    @Override
    public void writeRaw(final char a1) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1);
        }
    }
    
    @Override
    public void writeRawValue(final String a1) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1);
        }
    }
    
    @Override
    public void writeRawValue(final String a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1, a2, a3);
        }
    }
    
    @Override
    public void writeRawValue(final char[] a1, final int a2, final int a3) throws IOException {
        if (this._checkRawValueWrite()) {
            this.delegate.writeRaw(a1, a2, a3);
        }
    }
    
    @Override
    public void writeBinary(final Base64Variant a1, final byte[] a2, final int a3, final int a4) throws IOException {
        if (this._checkBinaryWrite()) {
            this.delegate.writeBinary(a1, a2, a3, a4);
        }
    }
    
    @Override
    public int writeBinary(final Base64Variant a1, final InputStream a2, final int a3) throws IOException {
        if (this._checkBinaryWrite()) {
            return this.delegate.writeBinary(a1, a2, a3);
        }
        return -1;
    }
    
    @Override
    public void writeNumber(final short v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final int v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final long v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final BigInteger v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final double v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final float v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final BigDecimal v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeNumber(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeNumber(final String v2) throws IOException, UnsupportedOperationException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeRawValue()) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(v2);
    }
    
    @Override
    public void writeBoolean(final boolean v2) throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter a1 = this._filterContext.checkValue(this._itemFilter);
            if (a1 == null) {
                return;
            }
            if (a1 != TokenFilter.INCLUDE_ALL && !a1.includeBoolean(v2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeBoolean(v2);
    }
    
    @Override
    public void writeNull() throws IOException {
        if (this._itemFilter == null) {
            return;
        }
        if (this._itemFilter != TokenFilter.INCLUDE_ALL) {
            final TokenFilter v1 = this._filterContext.checkValue(this._itemFilter);
            if (v1 == null) {
                return;
            }
            if (v1 != TokenFilter.INCLUDE_ALL && !v1.includeNull()) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNull();
    }
    
    @Override
    public void writeOmittedField(final String a1) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeOmittedField(a1);
        }
    }
    
    @Override
    public void writeObjectId(final Object a1) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeObjectId(a1);
        }
    }
    
    @Override
    public void writeObjectRef(final Object a1) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeObjectRef(a1);
        }
    }
    
    @Override
    public void writeTypeId(final Object a1) throws IOException {
        if (this._itemFilter != null) {
            this.delegate.writeTypeId(a1);
        }
    }
    
    protected void _checkParentPath() throws IOException {
        ++this._matchCount;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        }
        if (!this._allowMultipleMatches) {
            this._filterContext.skipParentChecks();
        }
    }
    
    protected void _checkPropertyParentPath() throws IOException {
        ++this._matchCount;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        }
        else if (this._includeImmediateParent) {
            this._filterContext.writeImmediatePath(this.delegate);
        }
        if (!this._allowMultipleMatches) {
            this._filterContext.skipParentChecks();
        }
    }
    
    protected boolean _checkBinaryWrite() throws IOException {
        if (this._itemFilter == null) {
            return false;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (this._itemFilter.includeBinary()) {
            this._checkParentPath();
            return true;
        }
        return false;
    }
    
    protected boolean _checkRawValueWrite() throws IOException {
        if (this._itemFilter == null) {
            return false;
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (this._itemFilter.includeRawValue()) {
            this._checkParentPath();
            return true;
        }
        return false;
    }
}
