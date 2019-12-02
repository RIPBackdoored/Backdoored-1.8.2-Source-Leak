package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class DataFilterValueRange extends GenericJson
{
    @Key
    private DataFilter dataFilter;
    @Key
    private String majorDimension;
    @Key
    private List<List<Object>> values;
    
    public DataFilterValueRange() {
        super();
    }
    
    public DataFilter getDataFilter() {
        return this.dataFilter;
    }
    
    public DataFilterValueRange setDataFilter(final DataFilter dataFilter) {
        this.dataFilter = dataFilter;
        return this;
    }
    
    public String getMajorDimension() {
        return this.majorDimension;
    }
    
    public DataFilterValueRange setMajorDimension(final String majorDimension) {
        this.majorDimension = majorDimension;
        return this;
    }
    
    public List<List<Object>> getValues() {
        return this.values;
    }
    
    public DataFilterValueRange setValues(final List<List<Object>> values) {
        this.values = values;
        return this;
    }
    
    @Override
    public DataFilterValueRange set(final String a1, final Object a2) {
        return (DataFilterValueRange)super.set(a1, a2);
    }
    
    @Override
    public DataFilterValueRange clone() {
        return (DataFilterValueRange)super.clone();
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
