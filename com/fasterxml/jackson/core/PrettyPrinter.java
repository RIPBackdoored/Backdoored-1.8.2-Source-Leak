package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import java.io.*;

public interface PrettyPrinter
{
    public static final Separators DEFAULT_SEPARATORS = Separators.createDefaultInstance();
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    
    void writeRootValueSeparator(final JsonGenerator p0) throws IOException;
    
    void writeStartObject(final JsonGenerator p0) throws IOException;
    
    void writeEndObject(final JsonGenerator p0, final int p1) throws IOException;
    
    void writeObjectEntrySeparator(final JsonGenerator p0) throws IOException;
    
    void writeObjectFieldValueSeparator(final JsonGenerator p0) throws IOException;
    
    void writeStartArray(final JsonGenerator p0) throws IOException;
    
    void writeEndArray(final JsonGenerator p0, final int p1) throws IOException;
    
    void writeArrayValueSeparator(final JsonGenerator p0) throws IOException;
    
    void beforeArrayValues(final JsonGenerator p0) throws IOException;
    
    void beforeObjectEntries(final JsonGenerator p0) throws IOException;
}
