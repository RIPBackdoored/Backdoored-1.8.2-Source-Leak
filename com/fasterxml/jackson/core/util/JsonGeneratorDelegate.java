package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.*;
import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;

public class JsonGeneratorDelegate extends JsonGenerator
{
    protected JsonGenerator delegate;
    protected boolean delegateCopyMethods;
    
    public JsonGeneratorDelegate(final JsonGenerator a1) {
        this(a1, true);
    }
    
    public JsonGeneratorDelegate(final JsonGenerator a1, final boolean a2) {
        super();
        this.delegate = a1;
        this.delegateCopyMethods = a2;
    }
    
    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
        this.delegate.setCurrentValue(a1);
    }
    
    public JsonGenerator getDelegate() {
        return this.delegate;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec a1) {
        this.delegate.setCodec(a1);
        return this;
    }
    
    @Override
    public void setSchema(final FormatSchema a1) {
        this.delegate.setSchema(a1);
    }
    
    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }
    
    @Override
    public Version version() {
        return this.delegate.version();
    }
    
    @Override
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }
    
    @Override
    public int getOutputBuffered() {
        return this.delegate.getOutputBuffered();
    }
    
    @Override
    public boolean canUseSchema(final FormatSchema a1) {
        return this.delegate.canUseSchema(a1);
    }
    
    @Override
    public boolean canWriteTypeId() {
        return this.delegate.canWriteTypeId();
    }
    
    @Override
    public boolean canWriteObjectId() {
        return this.delegate.canWriteObjectId();
    }
    
    @Override
    public boolean canWriteBinaryNatively() {
        return this.delegate.canWriteBinaryNatively();
    }
    
    @Override
    public boolean canOmitFields() {
        return this.delegate.canOmitFields();
    }
    
    @Override
    public JsonGenerator enable(final Feature a1) {
        this.delegate.enable(a1);
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature a1) {
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
    public JsonGenerator setFeatureMask(final int a1) {
        this.delegate.setFeatureMask(a1);
        return this;
    }
    
    @Override
    public JsonGenerator overrideStdFeatures(final int a1, final int a2) {
        this.delegate.overrideStdFeatures(a1, a2);
        return this;
    }
    
    @Override
    public JsonGenerator overrideFormatFeatures(final int a1, final int a2) {
        this.delegate.overrideFormatFeatures(a1, a2);
        return this;
    }
    
    @Override
    public JsonGenerator setPrettyPrinter(final PrettyPrinter a1) {
        this.delegate.setPrettyPrinter(a1);
        return this;
    }
    
    @Override
    public PrettyPrinter getPrettyPrinter() {
        return this.delegate.getPrettyPrinter();
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }
    
    @Override
    public JsonGenerator setHighestNonEscapedChar(final int a1) {
        this.delegate.setHighestNonEscapedChar(a1);
        return this;
    }
    
    @Override
    public int getHighestEscapedChar() {
        return this.delegate.getHighestEscapedChar();
    }
    
    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this.delegate.getCharacterEscapes();
    }
    
    @Override
    public JsonGenerator setCharacterEscapes(final CharacterEscapes a1) {
        this.delegate.setCharacterEscapes(a1);
        return this;
    }
    
    @Override
    public JsonGenerator setRootValueSeparator(final SerializableString a1) {
        this.delegate.setRootValueSeparator(a1);
        return this;
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this.delegate.writeStartArray();
    }
    
    @Override
    public void writeStartArray(final int a1) throws IOException {
        this.delegate.writeStartArray(a1);
    }
    
    @Override
    public void writeEndArray() throws IOException {
        this.delegate.writeEndArray();
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this.delegate.writeStartObject();
    }
    
    @Override
    public void writeStartObject(final Object a1) throws IOException {
        this.delegate.writeStartObject(a1);
    }
    
    @Override
    public void writeEndObject() throws IOException {
        this.delegate.writeEndObject();
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
        this.delegate.writeFieldName(a1);
    }
    
    @Override
    public void writeFieldName(final SerializableString a1) throws IOException {
        this.delegate.writeFieldName(a1);
    }
    
    @Override
    public void writeFieldId(final long a1) throws IOException {
        this.delegate.writeFieldId(a1);
    }
    
    @Override
    public void writeArray(final int[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeArray(a1, a2, a3);
    }
    
    @Override
    public void writeArray(final long[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeArray(a1, a2, a3);
    }
    
    @Override
    public void writeArray(final double[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeArray(a1, a2, a3);
    }
    
    @Override
    public void writeString(final String a1) throws IOException {
        this.delegate.writeString(a1);
    }
    
    @Override
    public void writeString(final Reader a1, final int a2) throws IOException {
        this.delegate.writeString(a1, a2);
    }
    
    @Override
    public void writeString(final char[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeString(a1, a2, a3);
    }
    
    @Override
    public void writeString(final SerializableString a1) throws IOException {
        this.delegate.writeString(a1);
    }
    
    @Override
    public void writeRawUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeRawUTF8String(a1, a2, a3);
    }
    
    @Override
    public void writeUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeUTF8String(a1, a2, a3);
    }
    
    @Override
    public void writeRaw(final String a1) throws IOException {
        this.delegate.writeRaw(a1);
    }
    
    @Override
    public void writeRaw(final String a1, final int a2, final int a3) throws IOException {
        this.delegate.writeRaw(a1, a2, a3);
    }
    
    @Override
    public void writeRaw(final SerializableString a1) throws IOException {
        this.delegate.writeRaw(a1);
    }
    
    @Override
    public void writeRaw(final char[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeRaw(a1, a2, a3);
    }
    
    @Override
    public void writeRaw(final char a1) throws IOException {
        this.delegate.writeRaw(a1);
    }
    
    @Override
    public void writeRawValue(final String a1) throws IOException {
        this.delegate.writeRawValue(a1);
    }
    
    @Override
    public void writeRawValue(final String a1, final int a2, final int a3) throws IOException {
        this.delegate.writeRawValue(a1, a2, a3);
    }
    
    @Override
    public void writeRawValue(final char[] a1, final int a2, final int a3) throws IOException {
        this.delegate.writeRawValue(a1, a2, a3);
    }
    
    @Override
    public void writeBinary(final Base64Variant a1, final byte[] a2, final int a3, final int a4) throws IOException {
        this.delegate.writeBinary(a1, a2, a3, a4);
    }
    
    @Override
    public int writeBinary(final Base64Variant a1, final InputStream a2, final int a3) throws IOException {
        return this.delegate.writeBinary(a1, a2, a3);
    }
    
    @Override
    public void writeNumber(final short a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final int a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final long a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final BigInteger a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final double a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final float a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final BigDecimal a1) throws IOException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final String a1) throws IOException, UnsupportedOperationException {
        this.delegate.writeNumber(a1);
    }
    
    @Override
    public void writeBoolean(final boolean a1) throws IOException {
        this.delegate.writeBoolean(a1);
    }
    
    @Override
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }
    
    @Override
    public void writeOmittedField(final String a1) throws IOException {
        this.delegate.writeOmittedField(a1);
    }
    
    @Override
    public void writeObjectId(final Object a1) throws IOException {
        this.delegate.writeObjectId(a1);
    }
    
    @Override
    public void writeObjectRef(final Object a1) throws IOException {
        this.delegate.writeObjectRef(a1);
    }
    
    @Override
    public void writeTypeId(final Object a1) throws IOException {
        this.delegate.writeTypeId(a1);
    }
    
    @Override
    public void writeEmbeddedObject(final Object a1) throws IOException {
        this.delegate.writeEmbeddedObject(a1);
    }
    
    @Override
    public void writeObject(final Object v2) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeObject(v2);
            return;
        }
        if (v2 == null) {
            this.writeNull();
        }
        else {
            final ObjectCodec a1 = this.getCodec();
            if (a1 != null) {
                a1.writeValue(this, v2);
                return;
            }
            this._writeSimpleObject(v2);
        }
    }
    
    @Override
    public void writeTree(final TreeNode v2) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeTree(v2);
            return;
        }
        if (v2 == null) {
            this.writeNull();
        }
        else {
            final ObjectCodec a1 = this.getCodec();
            if (a1 == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            a1.writeTree(this, v2);
        }
    }
    
    @Override
    public void copyCurrentEvent(final JsonParser a1) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentEvent(a1);
        }
        else {
            super.copyCurrentEvent(a1);
        }
    }
    
    @Override
    public void copyCurrentStructure(final JsonParser a1) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentStructure(a1);
        }
        else {
            super.copyCurrentStructure(a1);
        }
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }
    
    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
    
    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }
}
