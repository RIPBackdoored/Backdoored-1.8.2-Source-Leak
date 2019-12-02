package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class GridData extends GenericJson
{
    @Key
    private List<DimensionProperties> columnMetadata;
    @Key
    private List<RowData> rowData;
    @Key
    private List<DimensionProperties> rowMetadata;
    @Key
    private Integer startColumn;
    @Key
    private Integer startRow;
    
    public GridData() {
        super();
    }
    
    public List<DimensionProperties> getColumnMetadata() {
        return this.columnMetadata;
    }
    
    public GridData setColumnMetadata(final List<DimensionProperties> columnMetadata) {
        this.columnMetadata = columnMetadata;
        return this;
    }
    
    public List<RowData> getRowData() {
        return this.rowData;
    }
    
    public GridData setRowData(final List<RowData> rowData) {
        this.rowData = rowData;
        return this;
    }
    
    public List<DimensionProperties> getRowMetadata() {
        return this.rowMetadata;
    }
    
    public GridData setRowMetadata(final List<DimensionProperties> rowMetadata) {
        this.rowMetadata = rowMetadata;
        return this;
    }
    
    public Integer getStartColumn() {
        return this.startColumn;
    }
    
    public GridData setStartColumn(final Integer startColumn) {
        this.startColumn = startColumn;
        return this;
    }
    
    public Integer getStartRow() {
        return this.startRow;
    }
    
    public GridData setStartRow(final Integer startRow) {
        this.startRow = startRow;
        return this;
    }
    
    @Override
    public GridData set(final String a1, final Object a2) {
        return (GridData)super.set(a1, a2);
    }
    
    @Override
    public GridData clone() {
        return (GridData)super.clone();
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
        Data.nullOf(RowData.class);
    }
}
