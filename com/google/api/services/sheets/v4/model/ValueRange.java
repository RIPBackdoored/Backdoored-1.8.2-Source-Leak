package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class ValueRange extends GenericJson
{
    @Key
    private String majorDimension;
    @Key
    private String range;
    @Key
    private List<List<Object>> values;
    
    public ValueRange() {
        super();
    }
    
    public String getMajorDimension() {
        return this.majorDimension;
    }
    
    public ValueRange setMajorDimension(final String majorDimension) {
        this.majorDimension = majorDimension;
        return this;
    }
    
    public String getRange() {
        return this.range;
    }
    
    public ValueRange setRange(final String range) {
        this.range = range;
        return this;
    }
    
    public List<List<Object>> getValues() {
        return this.values;
    }
    
    public ValueRange setValues(final List<List<Object>> values) {
        this.values = values;
        return this;
    }
    
    @Override
    public ValueRange set(final String a1, final Object a2) {
        return (ValueRange)super.set(a1, a2);
    }
    
    @Override
    public ValueRange clone() {
        return (ValueRange)super.clone();
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
