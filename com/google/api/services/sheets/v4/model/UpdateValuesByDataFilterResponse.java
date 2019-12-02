package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateValuesByDataFilterResponse extends GenericJson
{
    @Key
    private DataFilter dataFilter;
    @Key
    private Integer updatedCells;
    @Key
    private Integer updatedColumns;
    @Key
    private ValueRange updatedData;
    @Key
    private String updatedRange;
    @Key
    private Integer updatedRows;
    
    public UpdateValuesByDataFilterResponse() {
        super();
    }
    
    public DataFilter getDataFilter() {
        return this.dataFilter;
    }
    
    public UpdateValuesByDataFilterResponse setDataFilter(final DataFilter dataFilter) {
        this.dataFilter = dataFilter;
        return this;
    }
    
    public Integer getUpdatedCells() {
        return this.updatedCells;
    }
    
    public UpdateValuesByDataFilterResponse setUpdatedCells(final Integer updatedCells) {
        this.updatedCells = updatedCells;
        return this;
    }
    
    public Integer getUpdatedColumns() {
        return this.updatedColumns;
    }
    
    public UpdateValuesByDataFilterResponse setUpdatedColumns(final Integer updatedColumns) {
        this.updatedColumns = updatedColumns;
        return this;
    }
    
    public ValueRange getUpdatedData() {
        return this.updatedData;
    }
    
    public UpdateValuesByDataFilterResponse setUpdatedData(final ValueRange updatedData) {
        this.updatedData = updatedData;
        return this;
    }
    
    public String getUpdatedRange() {
        return this.updatedRange;
    }
    
    public UpdateValuesByDataFilterResponse setUpdatedRange(final String updatedRange) {
        this.updatedRange = updatedRange;
        return this;
    }
    
    public Integer getUpdatedRows() {
        return this.updatedRows;
    }
    
    public UpdateValuesByDataFilterResponse setUpdatedRows(final Integer updatedRows) {
        this.updatedRows = updatedRows;
        return this;
    }
    
    @Override
    public UpdateValuesByDataFilterResponse set(final String a1, final Object a2) {
        return (UpdateValuesByDataFilterResponse)super.set(a1, a2);
    }
    
    @Override
    public UpdateValuesByDataFilterResponse clone() {
        return (UpdateValuesByDataFilterResponse)super.clone();
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
