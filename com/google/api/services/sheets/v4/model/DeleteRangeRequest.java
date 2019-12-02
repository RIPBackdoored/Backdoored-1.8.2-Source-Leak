package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeleteRangeRequest extends GenericJson
{
    @Key
    private GridRange range;
    @Key
    private String shiftDimension;
    
    public DeleteRangeRequest() {
        super();
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public DeleteRangeRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public String getShiftDimension() {
        return this.shiftDimension;
    }
    
    public DeleteRangeRequest setShiftDimension(final String shiftDimension) {
        this.shiftDimension = shiftDimension;
        return this;
    }
    
    @Override
    public DeleteRangeRequest set(final String a1, final Object a2) {
        return (DeleteRangeRequest)super.set(a1, a2);
    }
    
    @Override
    public DeleteRangeRequest clone() {
        return (DeleteRangeRequest)super.clone();
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
