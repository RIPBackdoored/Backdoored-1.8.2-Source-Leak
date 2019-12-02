package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeveloperMetadata extends GenericJson
{
    @Key
    private DeveloperMetadataLocation location;
    @Key
    private Integer metadataId;
    @Key
    private String metadataKey;
    @Key
    private String metadataValue;
    @Key
    private String visibility;
    
    public DeveloperMetadata() {
        super();
    }
    
    public DeveloperMetadataLocation getLocation() {
        return this.location;
    }
    
    public DeveloperMetadata setLocation(final DeveloperMetadataLocation location) {
        this.location = location;
        return this;
    }
    
    public Integer getMetadataId() {
        return this.metadataId;
    }
    
    public DeveloperMetadata setMetadataId(final Integer metadataId) {
        this.metadataId = metadataId;
        return this;
    }
    
    public String getMetadataKey() {
        return this.metadataKey;
    }
    
    public DeveloperMetadata setMetadataKey(final String metadataKey) {
        this.metadataKey = metadataKey;
        return this;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public DeveloperMetadata setMetadataValue(final String metadataValue) {
        this.metadataValue = metadataValue;
        return this;
    }
    
    public String getVisibility() {
        return this.visibility;
    }
    
    public DeveloperMetadata setVisibility(final String visibility) {
        this.visibility = visibility;
        return this;
    }
    
    @Override
    public DeveloperMetadata set(final String a1, final Object a2) {
        return (DeveloperMetadata)super.set(a1, a2);
    }
    
    @Override
    public DeveloperMetadata clone() {
        return (DeveloperMetadata)super.clone();
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
