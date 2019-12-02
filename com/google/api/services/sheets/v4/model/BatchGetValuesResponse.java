package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchGetValuesResponse extends GenericJson
{
    @Key
    private String spreadsheetId;
    @Key
    private List<ValueRange> valueRanges;
    
    public BatchGetValuesResponse() {
        super();
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchGetValuesResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public List<ValueRange> getValueRanges() {
        return this.valueRanges;
    }
    
    public BatchGetValuesResponse setValueRanges(final List<ValueRange> valueRanges) {
        this.valueRanges = valueRanges;
        return this;
    }
    
    @Override
    public BatchGetValuesResponse set(final String a1, final Object a2) {
        return (BatchGetValuesResponse)super.set(a1, a2);
    }
    
    @Override
    public BatchGetValuesResponse clone() {
        return (BatchGetValuesResponse)super.clone();
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
