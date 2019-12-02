package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchUpdateSpreadsheetRequest extends GenericJson
{
    @Key
    private Boolean includeSpreadsheetInResponse;
    @Key
    private List<Request> requests;
    @Key
    private Boolean responseIncludeGridData;
    @Key
    private List<String> responseRanges;
    
    public BatchUpdateSpreadsheetRequest() {
        super();
    }
    
    public Boolean getIncludeSpreadsheetInResponse() {
        return this.includeSpreadsheetInResponse;
    }
    
    public BatchUpdateSpreadsheetRequest setIncludeSpreadsheetInResponse(final Boolean includeSpreadsheetInResponse) {
        this.includeSpreadsheetInResponse = includeSpreadsheetInResponse;
        return this;
    }
    
    public List<Request> getRequests() {
        return this.requests;
    }
    
    public BatchUpdateSpreadsheetRequest setRequests(final List<Request> requests) {
        this.requests = requests;
        return this;
    }
    
    public Boolean getResponseIncludeGridData() {
        return this.responseIncludeGridData;
    }
    
    public BatchUpdateSpreadsheetRequest setResponseIncludeGridData(final Boolean responseIncludeGridData) {
        this.responseIncludeGridData = responseIncludeGridData;
        return this;
    }
    
    public List<String> getResponseRanges() {
        return this.responseRanges;
    }
    
    public BatchUpdateSpreadsheetRequest setResponseRanges(final List<String> responseRanges) {
        this.responseRanges = responseRanges;
        return this;
    }
    
    @Override
    public BatchUpdateSpreadsheetRequest set(final String a1, final Object a2) {
        return (BatchUpdateSpreadsheetRequest)super.set(a1, a2);
    }
    
    @Override
    public BatchUpdateSpreadsheetRequest clone() {
        return (BatchUpdateSpreadsheetRequest)super.clone();
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
        Data.nullOf(Request.class);
    }
}
