package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class PivotTable extends GenericJson
{
    @Key
    private List<PivotGroup> columns;
    @Key
    private Map<String, PivotFilterCriteria> criteria;
    @Key
    private List<PivotGroup> rows;
    @Key
    private GridRange source;
    @Key
    private String valueLayout;
    @Key
    private List<PivotValue> values;
    
    public PivotTable() {
        super();
    }
    
    public List<PivotGroup> getColumns() {
        return this.columns;
    }
    
    public PivotTable setColumns(final List<PivotGroup> columns) {
        this.columns = columns;
        return this;
    }
    
    public Map<String, PivotFilterCriteria> getCriteria() {
        return this.criteria;
    }
    
    public PivotTable setCriteria(final Map<String, PivotFilterCriteria> criteria) {
        this.criteria = criteria;
        return this;
    }
    
    public List<PivotGroup> getRows() {
        return this.rows;
    }
    
    public PivotTable setRows(final List<PivotGroup> rows) {
        this.rows = rows;
        return this;
    }
    
    public GridRange getSource() {
        return this.source;
    }
    
    public PivotTable setSource(final GridRange source) {
        this.source = source;
        return this;
    }
    
    public String getValueLayout() {
        return this.valueLayout;
    }
    
    public PivotTable setValueLayout(final String valueLayout) {
        this.valueLayout = valueLayout;
        return this;
    }
    
    public List<PivotValue> getValues() {
        return this.values;
    }
    
    public PivotTable setValues(final List<PivotValue> values) {
        this.values = values;
        return this;
    }
    
    @Override
    public PivotTable set(final String a1, final Object a2) {
        return (PivotTable)super.set(a1, a2);
    }
    
    @Override
    public PivotTable clone() {
        return (PivotTable)super.clone();
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
    
    static {
        Data.nullOf(PivotGroup.class);
        Data.nullOf(PivotFilterCriteria.class);
        Data.nullOf(PivotGroup.class);
        Data.nullOf(PivotValue.class);
    }
}
