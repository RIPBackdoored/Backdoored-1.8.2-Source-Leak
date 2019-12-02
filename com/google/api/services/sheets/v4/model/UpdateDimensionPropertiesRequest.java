package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateDimensionPropertiesRequest extends GenericJson
{
    @Key
    private String fields;
    @Key
    private DimensionProperties properties;
    @Key
    private DimensionRange range;
    
    public UpdateDimensionPropertiesRequest() {
        super();
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public UpdateDimensionPropertiesRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    public DimensionProperties getProperties() {
        return this.properties;
    }
    
    public UpdateDimensionPropertiesRequest setProperties(final DimensionProperties properties) {
        this.properties = properties;
        return this;
    }
    
    public DimensionRange getRange() {
        return this.range;
    }
    
    public UpdateDimensionPropertiesRequest setRange(final DimensionRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public UpdateDimensionPropertiesRequest set(final String a1, final Object a2) {
        return (UpdateDimensionPropertiesRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateDimensionPropertiesRequest clone() {
        return (UpdateDimensionPropertiesRequest)super.clone();
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
