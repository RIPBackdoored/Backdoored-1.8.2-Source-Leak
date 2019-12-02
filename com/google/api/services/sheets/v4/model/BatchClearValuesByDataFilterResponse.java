package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchClearValuesByDataFilterResponse extends GenericJson
{
    @Key
    private List<String> clearedRanges;
    @Key
    private String spreadsheetId;
    
    public BatchClearValuesByDataFilterResponse() {
        super();
    }
    
    public List<String> getClearedRanges() {
        return this.clearedRanges;
    }
    
    public BatchClearValuesByDataFilterResponse setClearedRanges(final List<String> clearedRanges) {
        this.clearedRanges = clearedRanges;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public BatchClearValuesByDataFilterResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public BatchClearValuesByDataFilterResponse set(final String a1, final Object a2) {
        return (BatchClearValuesByDataFilterResponse)super.set(a1, a2);
    }
    
    @Override
    public BatchClearValuesByDataFilterResponse clone() {
        return (BatchClearValuesByDataFilterResponse)super.clone();
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
