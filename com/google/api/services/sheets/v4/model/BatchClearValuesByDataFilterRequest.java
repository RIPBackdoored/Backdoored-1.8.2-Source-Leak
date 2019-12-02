package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BatchClearValuesByDataFilterRequest extends GenericJson
{
    @Key
    private List<DataFilter> dataFilters;
    
    public BatchClearValuesByDataFilterRequest() {
        super();
    }
    
    public List<DataFilter> getDataFilters() {
        return this.dataFilters;
    }
    
    public BatchClearValuesByDataFilterRequest setDataFilters(final List<DataFilter> dataFilters) {
        this.dataFilters = dataFilters;
        return this;
    }
    
    @Override
    public BatchClearValuesByDataFilterRequest set(final String a1, final Object a2) {
        return (BatchClearValuesByDataFilterRequest)super.set(a1, a2);
    }
    
    @Override
    public BatchClearValuesByDataFilterRequest clone() {
        return (BatchClearValuesByDataFilterRequest)super.clone();
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
