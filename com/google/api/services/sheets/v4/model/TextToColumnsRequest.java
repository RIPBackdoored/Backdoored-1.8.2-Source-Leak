package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TextToColumnsRequest extends GenericJson
{
    @Key
    private String delimiter;
    @Key
    private String delimiterType;
    @Key
    private GridRange source;
    
    public TextToColumnsRequest() {
        super();
    }
    
    public String getDelimiter() {
        return this.delimiter;
    }
    
    public TextToColumnsRequest setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
        return this;
    }
    
    public String getDelimiterType() {
        return this.delimiterType;
    }
    
    public TextToColumnsRequest setDelimiterType(final String delimiterType) {
        this.delimiterType = delimiterType;
        return this;
    }
    
    public GridRange getSource() {
        return this.source;
    }
    
    public TextToColumnsRequest setSource(final GridRange source) {
        this.source = source;
        return this;
    }
    
    @Override
    public TextToColumnsRequest set(final String a1, final Object a2) {
        return (TextToColumnsRequest)super.set(a1, a2);
    }
    
    @Override
    public TextToColumnsRequest clone() {
        return (TextToColumnsRequest)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
