package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchGetValuesByDataFilterRequest extends GenericJson
{
    @Key
    private List<DataFilter> dataFilters;
    @Key
    private String dateTimeRenderOption;
    @Key
    private String majorDimension;
    @Key
    private String valueRenderOption;
    
    public BatchGetValuesByDataFilterRequest() {
        super();
    }
    
    public List<DataFilter> getDataFilters() {
        return this.dataFilters;
    }
    
    public BatchGetValuesByDataFilterRequest setDataFilters(final List<DataFilter> dataFilters) {
        this.dataFilters = dataFilters;
        return this;
    }
    
    public String getDateTimeRenderOption() {
        return this.dateTimeRenderOption;
    }
    
    public BatchGetValuesByDataFilterRequest setDateTimeRenderOption(final String dateTimeRenderOption) {
        this.dateTimeRenderOption = dateTimeRenderOption;
        return this;
    }
    
    public String getMajorDimension() {
        return this.majorDimension;
    }
    
    public BatchGetValuesByDataFilterRequest setMajorDimension(final String majorDimension) {
        this.majorDimension = majorDimension;
        return this;
    }
    
    public String getValueRenderOption() {
        return this.valueRenderOption;
    }
    
    public BatchGetValuesByDataFilterRequest setValueRenderOption(final String valueRenderOption) {
        this.valueRenderOption = valueRenderOption;
        return this;
    }
    
    @Override
    public BatchGetValuesByDataFilterRequest set(final String a1, final Object a2) {
        return (BatchGetValuesByDataFilterRequest)super.set(a1, a2);
    }
    
    @Override
    public BatchGetValuesByDataFilterRequest clone() {
        return (BatchGetValuesByDataFilterRequest)super.clone();
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
