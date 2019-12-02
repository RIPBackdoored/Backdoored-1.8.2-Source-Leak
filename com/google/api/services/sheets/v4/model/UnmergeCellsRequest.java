package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UnmergeCellsRequest extends GenericJson
{
    @Key
    private GridRange range;
    
    public UnmergeCellsRequest() {
        super();
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public UnmergeCellsRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public UnmergeCellsRequest set(final String a1, final Object a2) {
        return (UnmergeCellsRequest)super.set(a1, a2);
    }
    
    @Override
    public UnmergeCellsRequest clone() {
        return (UnmergeCellsRequest)super.clone();
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
