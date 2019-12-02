package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class GridCoordinate extends GenericJson
{
    @Key
    private Integer columnIndex;
    @Key
    private Integer rowIndex;
    @Key
    private Integer sheetId;
    
    public GridCoordinate() {
        super();
    }
    
    public Integer getColumnIndex() {
        return this.columnIndex;
    }
    
    public GridCoordinate setColumnIndex(final Integer columnIndex) {
        this.columnIndex = columnIndex;
        return this;
    }
    
    public Integer getRowIndex() {
        return this.rowIndex;
    }
    
    public GridCoordinate setRowIndex(final Integer rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public GridCoordinate setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public GridCoordinate set(final String a1, final Object a2) {
        return (GridCoordinate)super.set(a1, a2);
    }
    
    @Override
    public GridCoordinate clone() {
        return (GridCoordinate)super.clone();
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
