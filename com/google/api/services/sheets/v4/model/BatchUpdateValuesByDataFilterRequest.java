package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchUpdateValuesByDataFilterRequest extends GenericJson
{
    @Key
    private List<DataFilterValueRange> data;
    @Key
    private Boolean includeValuesInResponse;
    @Key
    private String responseDateTimeRenderOption;
    @Key
    private String responseValueRenderOption;
    @Key
    private String valueInputOption;
    
    public BatchUpdateValuesByDataFilterRequest() {
        super();
    }
    
    public List<DataFilterValueRange> getData() {
        return this.data;
    }
    
    public BatchUpdateValuesByDataFilterRequest setData(final List<DataFilterValueRange> data) {
        this.data = data;
        return this;
    }
    
    public Boolean getIncludeValuesInResponse() {
        return this.includeValuesInResponse;
    }
    
    public BatchUpdateValuesByDataFilterRequest setIncludeValuesInResponse(final Boolean includeValuesInResponse) {
        this.includeValuesInResponse = includeValuesInResponse;
        return this;
    }
    
    public String getResponseDateTimeRenderOption() {
        return this.responseDateTimeRenderOption;
    }
    
    public BatchUpdateValuesByDataFilterRequest setResponseDateTimeRenderOption(final String responseDateTimeRenderOption) {
        this.responseDateTimeRenderOption = responseDateTimeRenderOption;
        return this;
    }
    
    public String getResponseValueRenderOption() {
        return this.responseValueRenderOption;
    }
    
    public BatchUpdateValuesByDataFilterRequest setResponseValueRenderOption(final String responseValueRenderOption) {
        this.responseValueRenderOption = responseValueRenderOption;
        return this;
    }
    
    public String getValueInputOption() {
        return this.valueInputOption;
    }
    
    public BatchUpdateValuesByDataFilterRequest setValueInputOption(final String valueInputOption) {
        this.valueInputOption = valueInputOption;
        return this;
    }
    
    @Override
    public BatchUpdateValuesByDataFilterRequest set(final String a1, final Object a2) {
        return (BatchUpdateValuesByDataFilterRequest)super.set(a1, a2);
    }
    
    @Override
    public BatchUpdateValuesByDataFilterRequest clone() {
        return (BatchUpdateValuesByDataFilterRequest)super.clone();
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
