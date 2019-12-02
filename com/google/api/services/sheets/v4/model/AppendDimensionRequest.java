package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AppendDimensionRequest extends GenericJson
{
    @Key
    private String dimension;
    @Key
    private Integer length;
    @Key
    private Integer sheetId;
    
    public AppendDimensionRequest() {
        super();
    }
    
    public String getDimension() {
        return this.dimension;
    }
    
    public AppendDimensionRequest setDimension(final String dimension) {
        this.dimension = dimension;
        return this;
    }
    
    public Integer getLength() {
        return this.length;
    }
    
    public AppendDimensionRequest setLength(final Integer length) {
        this.length = length;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public AppendDimensionRequest setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public AppendDimensionRequest set(final String a1, final Object a2) {
        return (AppendDimensionRequest)super.set(a1, a2);
    }
    
    @Override
    public AppendDimensionRequest clone() {
        return (AppendDimensionRequest)super.clone();
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
