package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchUpdateValuesResponse extends GenericJson
{
    @Key
    private List<UpdateValuesResponse> responses;
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
    
    public BatchUpdateValuesResponse() {
        super();
    }
    
    public List<UpdateValuesResponse> getResponses() {
        return this.responses;
    }
    
    public BatchUpdateValuesResponse setResponses(final List<UpdateValuesResponse> responses) {
        this.responses = responses;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchUpdateValuesResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public Integer getTotalUpdatedCells() {
        return this.totalUpdatedCells;
    }
    
    public BatchUpdateValuesResponse setTotalUpdatedCells(final Integer totalUpdatedCells) {
        this.totalUpdatedCells = totalUpdatedCells;
        return this;
    }
    
    public Integer getTotalUpdatedColumns() {
        return this.totalUpdatedColumns;
    }
    
    public BatchUpdateValuesResponse setTotalUpdatedColumns(final Integer totalUpdatedColumns) {
        this.totalUpdatedColumns = totalUpdatedColumns;
        return this;
    }
    
    public Integer getTotalUpdatedRows() {
        return this.totalUpdatedRows;
    }
    
    public BatchUpdateValuesResponse setTotalUpdatedRows(final Integer totalUpdatedRows) {
        this.totalUpdatedRows = totalUpdatedRows;
        return this;
    }
    
    public Integer getTotalUpdatedSheets() {
        return this.totalUpdatedSheets;
    }
    
    public BatchUpdateValuesResponse setTotalUpdatedSheets(final Integer totalUpdatedSheets) {
        this.totalUpdatedSheets = totalUpdatedSheets;
        return this;
    }
    
    @Override
    public BatchUpdateValuesResponse set(final String a1, final Object a2) {
        return (BatchUpdateValuesResponse)super.set(a1, a2);
    }
    
    @Override
    public BatchUpdateValuesResponse clone() {
        return (BatchUpdateValuesResponse)super.clone();
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
