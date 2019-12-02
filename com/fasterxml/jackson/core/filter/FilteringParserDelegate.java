package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.util.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class FilteringParserDelegate extends JsonParserDelegate
{
    protected TokenFilter rootFilter;
    protected boolean _allowMultipleMatches;
    protected boolean _includePath;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;
    protected TokenFilterContext _headContext;
    protected TokenFilterContext _exposedContext;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    
    public FilteringParserDelegate(final JsonParser a1, final TokenFilter a2, final boolean a3, final boolean a4) {
        super(a1);
        this.rootFilter = a2;
        this._itemFilter = a2;
        this._headContext = TokenFilterContext.createRootContext(a2);
        this._includePath = a3;
        this._allowMultipleMatches = a4;
    }
    
    public TokenFilter getFilter() {
        return this.rootFilter;
    }
    
    public int getMatchCount() {
        return this._matchCount;
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }
    
    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }
    
    @Override
    public final int getCurrentTokenId() {
        final JsonToken v1 = this._currToken;
        return (v1 == null) ? 0 : v1.id();
    }
    
    @Override
    public final int currentTokenId() {
        final JsonToken v1 = this._currToken;
        return (v1 == null) ? 0 : v1.id();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }
    
    @Override
    public boolean hasTokenId(final int a1) {
        final JsonToken v1 = this._currToken;
        if (v1 == null) {
            return 0 == a1;
        }
        return v1.id() == a1;
    }
    
    @Override
    public final boolean hasToken(final JsonToken a1) {
        return this._currToken == a1;
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this._currToken == JsonToken.START_ARRAY;
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this._currToken == JsonToken.START_OBJECT;
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }
    
    @Override
    public JsonStreamContext getParsingContext() {
        return this._filterContext();
    }
    
    @Override
    public String getCurrentName() throws IOException {
        final JsonStreamContext v0 = this._filterContext();
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            final JsonStreamContext v2 = v0.getParent();
            return (v2 == null) ? null : v2.getCurrentName();
        }
        return v0.getCurrentName();
    }
    
    @Override
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }
    
    @Override
    public void overrideCurrentName(final String a1) {
        throw new UnsupportedOperationException("Can not currently override name during filtering read");
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (!this._allowMultipleMatches && this._currToken != null && this._exposedContext == null && this._currToken.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL) {
            return this._currToken = null;
        }
        TokenFilterContext v0 = this._exposedContext;
        if (v0 != null) {
            while (true) {
                JsonToken v2 = v0.nextTokenToRead();
                if (v2 != null) {
                    return this._currToken = v2;
                }
                if (v0 == this._headContext) {
                    this._exposedContext = null;
                    if (v0.inArray()) {
                        v2 = this.delegate.getCurrentToken();
                        return this._currToken = v2;
                    }
                    break;
                }
                else {
                    v0 = this._headContext.findChildOf(v0);
                    if ((this._exposedContext = v0) == null) {
                        throw this._constructError("Unexpected problem: chain of filtered context broken");
                    }
                    continue;
                }
            }
        }
        JsonToken v2 = this.delegate.nextToken();
        if (v2 == null) {
            return this._currToken = v2;
        }
        switch (v2.id()) {
            case 3: {
                TokenFilter v3 = this._itemFilter;
                if (v3 == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(v3, true);
                    return this._currToken = v2;
                }
                if (v3 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                v3 = this._headContext.checkValue(v3);
                if (v3 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (v3 != TokenFilter.INCLUDE_ALL) {
                    v3 = v3.filterStartArray();
                }
                if ((this._itemFilter = v3) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(v3, true);
                    return this._currToken = v2;
                }
                this._headContext = this._headContext.createChildArrayContext(v3, false);
                if (!this._includePath) {
                    break;
                }
                v2 = this._nextTokenWithBuffering(this._headContext);
                if (v2 != null) {
                    return this._currToken = v2;
                }
                break;
            }
            case 1: {
                TokenFilter v3 = this._itemFilter;
                if (v3 == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(v3, true);
                    return this._currToken = v2;
                }
                if (v3 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                v3 = this._headContext.checkValue(v3);
                if (v3 == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (v3 != TokenFilter.INCLUDE_ALL) {
                    v3 = v3.filterStartObject();
                }
                if ((this._itemFilter = v3) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(v3, true);
                    return this._currToken = v2;
                }
                this._headContext = this._headContext.createChildObjectContext(v3, false);
                if (!this._includePath) {
                    break;
                }
                v2 = this._nextTokenWithBuffering(this._headContext);
                if (v2 != null) {
                    return this._currToken = v2;
                }
                break;
            }
            case 2:
            case 4: {
                final boolean v4 = this._headContext.isStartHandled();
                final TokenFilter v3 = this._headContext.getFilter();
                if (v3 != null && v3 != TokenFilter.INCLUDE_ALL) {
                    v3.filterFinishArray();
                }
                this._headContext = this._headContext.getParent();
                this._itemFilter = this._headContext.getFilter();
                if (v4) {
                    return this._currToken = v2;
                }
                break;
            }
            case 5: {
                final String v5 = this.delegate.getCurrentName();
                TokenFilter v3 = this._headContext.setFieldName(v5);
                if (v3 == TokenFilter.INCLUDE_ALL) {
                    this._itemFilter = v3;
                    if (!this._includePath && this._includeImmediateParent && !this._headContext.isStartHandled()) {
                        v2 = this._headContext.nextTokenToRead();
                        this._exposedContext = this._headContext;
                    }
                    return this._currToken = v2;
                }
                if (v3 == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                v3 = v3.includeProperty(v5);
                if (v3 == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                if ((this._itemFilter = v3) == TokenFilter.INCLUDE_ALL) {
                    if (this._verifyAllowedMatches()) {
                        if (this._includePath) {
                            return this._currToken = v2;
                        }
                    }
                    else {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                    }
                }
                if (!this._includePath) {
                    break;
                }
                v2 = this._nextTokenWithBuffering(this._headContext);
                if (v2 != null) {
                    return this._currToken = v2;
                }
                break;
            }
            default: {
                TokenFilter v3 = this._itemFilter;
                if (v3 == TokenFilter.INCLUDE_ALL) {
                    return this._currToken = v2;
                }
                if (v3 == null) {
                    break;
                }
                v3 = this._headContext.checkValue(v3);
                if ((v3 == TokenFilter.INCLUDE_ALL || (v3 != null && v3.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                    return this._currToken = v2;
                }
                break;
            }
        }
        return this._nextToken2();
    }
    
    protected final JsonToken _nextToken2() throws IOException {
        while (true) {
            JsonToken currToken = this.delegate.nextToken();
            if (currToken == null) {
                return this._currToken = currToken;
            }
            switch (currToken.id()) {
                case 3: {
                    TokenFilter v0 = this._itemFilter;
                    if (v0 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(v0, true);
                        return this._currToken = currToken;
                    }
                    if (v0 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    v0 = this._headContext.checkValue(v0);
                    if (v0 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (v0 != TokenFilter.INCLUDE_ALL) {
                        v0 = v0.filterStartArray();
                    }
                    if ((this._itemFilter = v0) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(v0, true);
                        return this._currToken = currToken;
                    }
                    this._headContext = this._headContext.createChildArrayContext(v0, false);
                    if (!this._includePath) {
                        continue;
                    }
                    currToken = this._nextTokenWithBuffering(this._headContext);
                    if (currToken != null) {
                        return this._currToken = currToken;
                    }
                    continue;
                }
                case 1: {
                    TokenFilter v0 = this._itemFilter;
                    if (v0 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(v0, true);
                        return this._currToken = currToken;
                    }
                    if (v0 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    v0 = this._headContext.checkValue(v0);
                    if (v0 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (v0 != TokenFilter.INCLUDE_ALL) {
                        v0 = v0.filterStartObject();
                    }
                    if ((this._itemFilter = v0) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(v0, true);
                        return this._currToken = currToken;
                    }
                    this._headContext = this._headContext.createChildObjectContext(v0, false);
                    if (!this._includePath) {
                        continue;
                    }
                    currToken = this._nextTokenWithBuffering(this._headContext);
                    if (currToken != null) {
                        return this._currToken = currToken;
                    }
                    continue;
                }
                case 2:
                case 4: {
                    final boolean v2 = this._headContext.isStartHandled();
                    final TokenFilter v0 = this._headContext.getFilter();
                    if (v0 != null && v0 != TokenFilter.INCLUDE_ALL) {
                        v0.filterFinishArray();
                    }
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (v2) {
                        return this._currToken = currToken;
                    }
                    continue;
                }
                case 5: {
                    final String v3 = this.delegate.getCurrentName();
                    TokenFilter v0 = this._headContext.setFieldName(v3);
                    if (v0 == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = v0;
                        return this._currToken = currToken;
                    }
                    if (v0 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    v0 = v0.includeProperty(v3);
                    if (v0 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = v0) != TokenFilter.INCLUDE_ALL) {
                        if (!this._includePath) {
                            continue;
                        }
                        currToken = this._nextTokenWithBuffering(this._headContext);
                        if (currToken != null) {
                            return this._currToken = currToken;
                        }
                        continue;
                        continue;
                    }
                    if (this._verifyAllowedMatches() && this._includePath) {
                        return this._currToken = currToken;
                    }
                    continue;
                }
                default: {
                    TokenFilter v0 = this._itemFilter;
                    if (v0 == TokenFilter.INCLUDE_ALL) {
                        return this._currToken = currToken;
                    }
                    if (v0 == null) {
                        continue;
                    }
                    v0 = this._headContext.checkValue(v0);
                    if ((v0 == TokenFilter.INCLUDE_ALL || (v0 != null && v0.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._currToken = currToken;
                    }
                    continue;
                    continue;
                }
            }
        }
    }
    
    protected final JsonToken _nextTokenWithBuffering(final TokenFilterContext v-3) throws IOException {
        while (true) {
            final JsonToken nextToken = this.delegate.nextToken();
            if (nextToken == null) {
                return nextToken;
            }
            switch (nextToken.id()) {
                case 3: {
                    TokenFilter a2 = this._headContext.checkValue(this._itemFilter);
                    if (a2 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (a2 != TokenFilter.INCLUDE_ALL) {
                        a2 = a2.filterStartArray();
                    }
                    if ((this._itemFilter = a2) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(a2, true);
                        return this._nextBuffered(v-3);
                    }
                    this._headContext = this._headContext.createChildArrayContext(a2, false);
                    continue;
                }
                case 1: {
                    TokenFilter a2 = this._itemFilter;
                    if (a2 == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(a2, true);
                        return nextToken;
                    }
                    if (a2 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    a2 = this._headContext.checkValue(a2);
                    if (a2 == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (a2 != TokenFilter.INCLUDE_ALL) {
                        a2 = a2.filterStartObject();
                    }
                    if ((this._itemFilter = a2) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(a2, true);
                        return this._nextBuffered(v-3);
                    }
                    this._headContext = this._headContext.createChildObjectContext(a2, false);
                    continue;
                }
                case 2:
                case 4: {
                    final TokenFilter a2 = this._headContext.getFilter();
                    if (a2 != null && a2 != TokenFilter.INCLUDE_ALL) {
                        a2.filterFinishArray();
                    }
                    final boolean a1 = this._headContext == v-3;
                    final boolean v1 = a1 && this._headContext.isStartHandled();
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (v1) {
                        return nextToken;
                    }
                    continue;
                }
                case 5: {
                    final String v2 = this.delegate.getCurrentName();
                    TokenFilter a2 = this._headContext.setFieldName(v2);
                    if (a2 == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = a2;
                        return this._nextBuffered(v-3);
                    }
                    if (a2 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    a2 = a2.includeProperty(v2);
                    if (a2 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = a2) != TokenFilter.INCLUDE_ALL) {
                        continue;
                    }
                    if (this._verifyAllowedMatches()) {
                        return this._nextBuffered(v-3);
                    }
                    this._itemFilter = this._headContext.setFieldName(v2);
                    continue;
                }
                default: {
                    TokenFilter a2 = this._itemFilter;
                    if (a2 == TokenFilter.INCLUDE_ALL) {
                        return this._nextBuffered(v-3);
                    }
                    if (a2 == null) {
                        continue;
                    }
                    a2 = this._headContext.checkValue(a2);
                    if ((a2 == TokenFilter.INCLUDE_ALL || (a2 != null && a2.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._nextBuffered(v-3);
                    }
                    continue;
                }
            }
        }
    }
    
    private JsonToken _nextBuffered(final TokenFilterContext a1) throws IOException {
        this._exposedContext = a1;
        TokenFilterContext v1 = a1;
        JsonToken v2 = v1.nextTokenToRead();
        if (v2 != null) {
            return v2;
        }
        while (v1 != this._headContext) {
            v1 = this._exposedContext.findChildOf(v1);
            if ((this._exposedContext = v1) == null) {
                throw this._constructError("Unexpected problem: chain of filtered context broken");
            }
            v2 = this._exposedContext.nextTokenToRead();
            if (v2 != null) {
                return v2;
            }
        }
        throw this._constructError("Internal error: failed to locate expected buffered tokens");
    }
    
    private final boolean _verifyAllowedMatches() throws IOException {
        if (this._matchCount == 0 || this._allowMultipleMatches) {
            ++this._matchCount;
            return true;
        }
        return false;
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken v1 = this.nextToken();
        if (v1 == JsonToken.FIELD_NAME) {
            v1 = this.nextToken();
        }
        return v1;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
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
    
    @Override
    public String getText() throws IOException {
        return this.delegate.getText();
    }
    
    @Override
    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }
    
    @Override
    public char[] getTextCharacters() throws IOException {
        return this.delegate.getTextCharacters();
    }
    
    @Override
    public int getTextLength() throws IOException {
        return this.delegate.getTextLength();
    }
    
    @Override
    public int getTextOffset() throws IOException {
        return this.delegate.getTextOffset();
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }
    
    @Override
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }
    
    @Override
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }
    
    @Override
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }
    
    @Override
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }
    
    @Override
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }
    
    @Override
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }
    
    @Override
    public NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }
    
    @Override
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }
    
    @Override
    public int getValueAsInt(final int a1) throws IOException {
        return this.delegate.getValueAsInt(a1);
    }
    
    @Override
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }
    
    @Override
    public long getValueAsLong(final long a1) throws IOException {
        return this.delegate.getValueAsLong(a1);
    }
    
    @Override
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }
    
    @Override
    public double getValueAsDouble(final double a1) throws IOException {
        return this.delegate.getValueAsDouble(a1);
    }
    
    @Override
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }
    
    @Override
    public boolean getValueAsBoolean(final boolean a1) throws IOException {
        return this.delegate.getValueAsBoolean(a1);
    }
    
    @Override
    public String getValueAsString() throws IOException {
        return this.delegate.getValueAsString();
    }
    
    @Override
    public String getValueAsString(final String a1) throws IOException {
        return this.delegate.getValueAsString(a1);
    }
    
    @Override
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant a1) throws IOException {
        return this.delegate.getBinaryValue(a1);
    }
    
    @Override
    public int readBinaryValue(final Base64Variant a1, final OutputStream a2) throws IOException {
        return this.delegate.readBinaryValue(a1, a2);
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }
    
    protected JsonStreamContext _filterContext() {
        if (this._exposedContext != null) {
            return this._exposedContext;
        }
        return this._headContext;
    }
}
