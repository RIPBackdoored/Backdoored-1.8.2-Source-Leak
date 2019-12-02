package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchClearValuesResponse extends GenericJson
{
    @Key
    private List<String> clearedRanges;
    @Key
    private String spreadsheetId;
    
    public BatchClearValuesResponse() {
        super();
    }
    
    public List<String> getClearedRanges() {
        return this.clearedRanges;
    }
    
    public BatchClearValuesResponse setClearedRanges(final List<String> clearedRanges) {
        this.clearedRanges = clearedRanges;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchClearValuesResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchClearValuesResponse set(final String a1, final Object a2) {
        return (BatchClearValuesResponse)super.set(a1, a2);
    }
    
    @Override
    public BatchClearValuesResponse clone() {
        return (BatchClearValuesResponse)super.clone();
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
