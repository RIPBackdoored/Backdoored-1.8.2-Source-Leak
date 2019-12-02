package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchUpdateValuesByDataFilterResponse extends GenericJson
{
    @Key
    private List<UpdateValuesByDataFilterResponse> responses;
    @Key
    private String spreadsheetId;
    @Key
    private Integer totalUpdatedCells;
    @Key
    private Integer totalUpdatedColumns;
    @Key
    private Integer totalUpdatedRows;
    @Key
    private Integer totalUpdatedSheets;
    
    public BatchUpdateValuesByDataFilterResponse() {
        super();
    }
    
    public List<UpdateValuesByDataFilterResponse> getResponses() {
        return this.responses;
    }
    
    public BatchUpdateValuesByDataFilterResponse setResponses(final List<UpdateValuesByDataFilterResponse> responses) {
        this.responses = responses;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchUpdateValuesByDataFilterResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public Integer getTotalUpdatedCells() {
        return this.totalUpdatedCells;
    }
    
    public BatchUpdateValuesByDataFilterResponse setTotalUpdatedCells(final Integer totalUpdatedCells) {
        this.totalUpdatedCells = totalUpdatedCells;
        return this;
    }
    
    public Integer getTotalUpdatedColumns() {
        return this.totalUpdatedColumns;
    }
    
    public BatchUpdateValuesByDataFilterResponse setTotalUpdatedColumns(final Integer totalUpdatedColumns) {
        this.totalUpdatedColumns = totalUpdatedColumns;
        return this;
    }
    
    public Integer getTotalUpdatedRows() {
        return this.totalUpdatedRows;
    }
    
    public BatchUpdateValuesByDataFilterResponse setTotalUpdatedRows(final Integer totalUpdatedRows) {
        this.totalUpdatedRows = totalUpdatedRows;
        return this;
    }
    
    public Integer getTotalUpdatedSheets() {
        return this.totalUpdatedSheets;
    }
    
    public BatchUpdateValuesByDataFilterResponse setTotalUpdatedSheets(final Integer totalUpdatedSheets) {
        this.totalUpdatedSheets = totalUpdatedSheets;
        return this;
    }
    
    @Override
    public BatchUpdateValuesByDataFilterResponse set(final String a1, final Object a2) {
        return (BatchUpdateValuesByDataFilterResponse)super.set(a1, a2);
    }
    
    @Override
    public BatchUpdateValuesByDataFilterResponse clone() {
        return (BatchUpdateValuesByDataFilterResponse)super.clone();
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
