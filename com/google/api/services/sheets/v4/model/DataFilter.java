package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DataFilter extends GenericJson
{
    @Key
    private String a1Range;
    @Key
    private DeveloperMetadataLookup developerMetadataLookup;
    @Key
    private GridRange gridRange;
    
    public DataFilter() {
        super();
    }
    
    public String getA1Range() {
        return this.a1Range;
    }
    
    public DataFilter setA1Range(final String a1Range) {
        this.a1Range = a1Range;
        return this;
    }
    
    public DeveloperMetadataLookup getDeveloperMetadataLookup() {
        return this.developerMetadataLookup;
    }
    
    public DataFilter setDeveloperMetadataLookup(final DeveloperMetadataLookup developerMetadataLookup) {
        this.developerMetadataLookup = developerMetadataLookup;
        return this;
    }
    
    public GridRange getGridRange() {
        return this.gridRange;
    }
    
    public DataFilter setGridRange(final GridRange gridRange) {
        this.gridRange = gridRange;
        return this;
    }
    
    @Override
    public DataFilter set(final String a1, final Object a2) {
        return (DataFilter)super.set(a1, a2);
    }
    
    @Override
    public DataFilter clone() {
        return (DataFilter)super.clone();
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
