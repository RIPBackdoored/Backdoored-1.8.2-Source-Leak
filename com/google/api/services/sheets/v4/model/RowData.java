package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class RowData extends GenericJson
{
    @Key
    private List<CellData> values;
    
    public RowData() {
        super();
    }
    
    public List<CellData> getValues() {
        return this.values;
    }
    
    public RowData setValues(final List<CellData> values) {
        this.values = values;
        return this;
    }
    
    @Override
    public RowData set(final String a1, final Object a2) {
        return (RowData)super.set(a1, a2);
    }
    
    @Override
    public RowData clone() {
        return (RowData)super.clone();
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
