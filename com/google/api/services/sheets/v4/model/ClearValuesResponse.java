package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class ClearValuesResponse extends GenericJson
{
    @Key
    private String clearedRange;
    @Key
    private String spreadsheetId;
    
    public ClearValuesResponse() {
        super();
    }
    
    public String getClearedRange() {
        return this.clearedRange;
    }
    
    public ClearValuesResponse setClearedRange(final String clearedRange) {
        this.clearedRange = clearedRange;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public ClearValuesResponse setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    @Override
    public ClearValuesResponse set(final String a1, final Object a2) {
        return (ClearValuesResponse)super.set(a1, a2);
    }
    
    @Override
    public ClearValuesResponse clone() {
        return (ClearValuesResponse)super.clone();
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
