package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AutoResizeDimensionsRequest extends GenericJson
{
    @Key
    private DimensionRange dimensions;
    
    public AutoResizeDimensionsRequest() {
        super();
    }
    
    public DimensionRange getDimensions() {
        return this.dimensions;
    }
    
    public AutoResizeDimensionsRequest setDimensions(final DimensionRange dimensions) {
        this.dimensions = dimensions;
        return this;
    }
    
    @Override
    public AutoResizeDimensionsRequest set(final String a1, final Object a2) {
        return (AutoResizeDimensionsRequest)super.set(a1, a2);
    }
    
    @Override
    public AutoResizeDimensionsRequest clone() {
        return (AutoResizeDimensionsRequest)super.clone();
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
