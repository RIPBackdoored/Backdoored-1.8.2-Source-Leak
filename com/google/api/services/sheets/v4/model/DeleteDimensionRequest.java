package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeleteDimensionRequest extends GenericJson
{
    @Key
    private DimensionRange range;
    
    public DeleteDimensionRequest() {
        super();
    }
    
    public DimensionRange getRange() {
        return this.range;
    }
    
    public DeleteDimensionRequest setRange(final DimensionRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public DeleteDimensionRequest set(final String a1, final Object a2) {
        return (DeleteDimensionRequest)super.set(a1, a2);
    }
    
    @Override
    public DeleteDimensionRequest clone() {
        return (DeleteDimensionRequest)super.clone();
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
