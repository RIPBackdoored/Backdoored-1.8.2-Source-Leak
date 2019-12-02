package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class RepeatCellRequest extends GenericJson
{
    @Key
    private CellData cell;
    @Key
    private String fields;
    @Key
    private GridRange range;
    
    public RepeatCellRequest() {
        super();
    }
    
    public CellData getCell() {
        return this.cell;
    }
    
    public RepeatCellRequest setCell(final CellData cell) {
        this.cell = cell;
        return this;
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public RepeatCellRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public RepeatCellRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public RepeatCellRequest set(final String a1, final Object a2) {
        return (RepeatCellRequest)super.set(a1, a2);
    }
    
    @Override
    public RepeatCellRequest clone() {
        return (RepeatCellRequest)super.clone();
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
