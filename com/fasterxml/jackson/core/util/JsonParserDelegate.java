package com.fasterxml.jackson.core.util;

import java.math.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class JsonParserDelegate extends JsonParser
{
    protected JsonParser delegate;
    
    public JsonParserDelegate(final JsonParser a1) {
        super();
        this.delegate = a1;
    }
    
    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
        this.delegate.setCurrentValue(a1);
    }
    
    @Override
    public void setCodec(final ObjectCodec a1) {
        this.delegate.setCodec(a1);
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }
    
    @Override
    public JsonParser enable(final Feature a1) {
        this.delegate.enable(a1);
        return this;
    }
    
    @Override
    public JsonParser disable(final Feature a1) {
        this.delegate.disable(a1);
        return this;
    }
    
    @Override
    public boolean isEnabled(final Feature a1) {
        return this.delegate.isEnabled(a1);
    }
    
    @Override
    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }
    
    @Deprecated
    @Override
    public JsonParser setFeatureMask(final int a1) {
        this.delegate.setFeatureMask(a1);
        return this;
    }
    
    @Override
    public JsonParser overrideStdFeatures(final int a1, final int a2) {
        this.delegate.overrideStdFeatures(a1, a2);
        return this;
    }
    
    @Override
    public JsonParser overrideFormatFeatures(final int a1, final int a2) {
        this.delegate.overrideFormatFeatures(a1, a2);
        return this;
    }
    
    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }
    
    @Override
    public void setSchema(final FormatSchema a1) {
        this.delegate.setSchema(a1);
    }
    
    @Override
    public boolean canUseSchema(final FormatSchema a1) {
        return this.delegate.canUseSchema(a1);
    }
    
    @Override
    public Version version() {
        return this.delegate.version();
    }
    
    @Override
    public Object getInputSource() {
        return this.delegate.getInputSource();
    }
    
    @Override
    public boolean requiresCustomCodec() {
        return this.delegate.requiresCustomCodec();
    }
    
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
    
    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }
    
    @Override
    public JsonToken currentToken() {
        return this.delegate.currentToken();
    }
    
    @Override
    public int currentTokenId() {
        return this.delegate.currentTokenId();
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this.delegate.getCurrentToken();
    }
    
    @Override
    public int getCurrentTokenId() {
        return this.delegate.getCurrentTokenId();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this.delegate.hasCurrentToken();
    }
    
    @Override
    public boolean hasTokenId(final int a1) {
        return this.delegate.hasTokenId(a1);
    }
    
    @Override
    public boolean hasToken(final JsonToken a1) {
        return this.delegate.hasToken(a1);
    }
    
    @Override
    public String getCurrentName() throws IOException {
        return this.delegate.getCurrentName();
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }
    
    @Override
    public JsonStreamContext getParsingContext() {
        return this.delegate.getParsingContext();
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this.delegate.isExpectedStartArrayToken();
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this.delegate.isExpectedStartObjectToken();
    }
    
    @Override
    public boolean isNaN() throws IOException {
        return this.delegate.isNaN();
    }
    
    @Override
    public void clearCurrentToken() {
        this.delegate.clearCurrentToken();
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this.delegate.getLastClearedToken();
    }
    
    @Override
    public void overrideCurrentName(final String a1) {
        this.delegate.overrideCurrentName(a1);
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
    public int getText(final Writer a1) throws IOException, UnsupportedOperationException {
        return this.delegate.getText(a1);
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
    
    @Override
    public JsonToken nextToken() throws IOException {
        return this.delegate.nextToken();
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        return this.delegate.nextValue();
    }
    
    @Override
    public void finishToken() throws IOException {
        this.delegate.finishToken();
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        this.delegate.skipChildren();
        return this;
    }
    
    @Override
    public boolean canReadObjectId() {
        return this.delegate.canReadObjectId();
    }
    
    @Override
    public boolean canReadTypeId() {
        return this.delegate.canReadTypeId();
    }
    
    @Override
    public Object getObjectId() throws IOException {
        return this.delegate.getObjectId();
    }
    
    @Override
    public Object getTypeId() throws IOException {
        return this.delegate.getTypeId();
    }
}
