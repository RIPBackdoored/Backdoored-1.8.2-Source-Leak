package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class UpdateDeveloperMetadataRequest extends GenericJson
{
    @Key
    private List<DataFilter> dataFilters;
    @Key
    private DeveloperMetadata developerMetadata;
    @Key
    private String fields;
    
    public UpdateDeveloperMetadataRequest() {
        super();
    }
    
    public List<DataFilter> getDataFilters() {
        return this.dataFilters;
    }
    
    public UpdateDeveloperMetadataRequest setDataFilters(final List<DataFilter> dataFilters) {
        this.dataFilters = dataFilters;
        return this;
    }
    
    public DeveloperMetadata getDeveloperMetadata() {
        return this.developerMetadata;
    }
    
    public UpdateDeveloperMetadataRequest setDeveloperMetadata(final DeveloperMetadata developerMetadata) {
        this.developerMetadata = developerMetadata;
        return this;
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public UpdateDeveloperMetadataRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    @Override
    public UpdateDeveloperMetadataRequest set(final String a1, final Object a2) {
        return (UpdateDeveloperMetadataRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateDeveloperMetadataRequest clone() {
        return (UpdateDeveloperMetadataRequest)super.clone();
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
