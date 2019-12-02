package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchUpdateValuesRequest extends GenericJson
{
    @Key
    private List<ValueRange> data;
    @Key
    private Boolean includeValuesInResponse;
    @Key
    private String responseDateTimeRenderOption;
    @Key
    private String responseValueRenderOption;
    @Key
    private String valueInputOption;
    
    public BatchUpdateValuesRequest() {
        super();
    }
    
    public List<ValueRange> getData() {
        return this.data;
    }
    
    public BatchUpdateValuesRequest setData(final List<ValueRange> data) {
        this.data = data;
        return this;
    }
    
    public Boolean getIncludeValuesInResponse() {
        return this.includeValuesInResponse;
    }
    
    public BatchUpdateValuesRequest setIncludeValuesInResponse(final Boolean includeValuesInResponse) {
        this.includeValuesInResponse = includeValuesInResponse;
        return this;
    }
    
    public String getResponseDateTimeRenderOption() {
        return this.responseDateTimeRenderOption;
    }
    
    public BatchUpdateValuesRequest setResponseDateTimeRenderOption(final String responseDateTimeRenderOption) {
        this.responseDateTimeRenderOption = responseDateTimeRenderOption;
        return this;
    }
    
    public String getResponseValueRenderOption() {
        return this.responseValueRenderOption;
    }
    
    public BatchUpdateValuesRequest setResponseValueRenderOption(final String responseValueRenderOption) {
        this.responseValueRenderOption = responseValueRenderOption;
        return this;
    }
    
    public String getValueInputOption() {
        return this.valueInputOption;
    }
    
    public BatchUpdateValuesRequest setValueInputOption(final String valueInputOption) {
        this.valueInputOption = valueInputOption;
        return this;
    }
    
    @Override
    public BatchUpdateValuesRequest set(final String a1, final Object a2) {
        return (BatchUpdateValuesRequest)super.set(a1, a2);
    }
    
    @Override
    public BatchUpdateValuesRequest clone() {
        return (BatchUpdateValuesRequest)super.clone();
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
