package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DimensionRange extends GenericJson
{
    @Key
    private String dimension;
    @Key
    private Integer endIndex;
    @Key
    private Integer sheetId;
    @Key
    private Integer startIndex;
    
    public DimensionRange() {
        super();
    }
    
    public String getDimension() {
        return this.dimension;
    }
    
    public DimensionRange setDimension(final String dimension) {
        this.dimension = dimension;
        return this;
    }
    
    public Integer getEndIndex() {
        return this.endIndex;
    }
    
    public DimensionRange setEndIndex(final Integer endIndex) {
        this.endIndex = endIndex;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public DimensionRange setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    public Integer getStartIndex() {
        return this.startIndex;
    }
    
    public DimensionRange setStartIndex(final Integer startIndex) {
        this.startIndex = startIndex;
        return this;
    }
    
    @Override
    public DimensionRange set(final String a1, final Object a2) {
        return (DimensionRange)super.set(a1, a2);
    }
    
    @Override
    public DimensionRange clone() {
        return (DimensionRange)super.clone();
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
