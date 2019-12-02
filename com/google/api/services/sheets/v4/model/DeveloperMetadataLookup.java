package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeveloperMetadataLookup extends GenericJson
{
    @Key
    private String locationMatchingStrategy;
    @Key
    private String locationType;
    @Key
    private Integer metadataId;
    @Key
    private String metadataKey;
    @Key
    private DeveloperMetadataLocation metadataLocation;
    @Key
    private String metadataValue;
    @Key
    private String visibility;
    
    public DeveloperMetadataLookup() {
        super();
    }
    
    public String getLocationMatchingStrategy() {
        return this.locationMatchingStrategy;
    }
    
    public DeveloperMetadataLookup setLocationMatchingStrategy(final String locationMatchingStrategy) {
        this.locationMatchingStrategy = locationMatchingStrategy;
        return this;
    }
    
    public String getLocationType() {
        return this.locationType;
    }
    
    public DeveloperMetadataLookup setLocationType(final String locationType) {
        this.locationType = locationType;
        return this;
    }
    
    public Integer getMetadataId() {
        return this.metadataId;
    }
    
    public DeveloperMetadataLookup setMetadataId(final Integer metadataId) {
        this.metadataId = metadataId;
        return this;
    }
    
    public String getMetadataKey() {
        return this.metadataKey;
    }
    
    public DeveloperMetadataLookup setMetadataKey(final String metadataKey) {
        this.metadataKey = metadataKey;
        return this;
    }
    
    public DeveloperMetadataLocation getMetadataLocation() {
        return this.metadataLocation;
    }
    
    public DeveloperMetadataLookup setMetadataLocation(final DeveloperMetadataLocation metadataLocation) {
        this.metadataLocation = metadataLocation;
        return this;
    }
    
    public String getMetadataValue() {
        return this.metadataValue;
    }
    
    public DeveloperMetadataLookup setMetadataValue(final String metadataValue) {
        this.metadataValue = metadataValue;
        return this;
    }
    
    public String getVisibility() {
        return this.visibility;
    }
    
    public DeveloperMetadataLookup setVisibility(final String visibility) {
        this.visibility = visibility;
        return this;
    }
    
    @Override
    public DeveloperMetadataLookup set(final String a1, final Object a2) {
        return (DeveloperMetadataLookup)super.set(a1, a2);
    }
    
    @Override
    public DeveloperMetadataLookup clone() {
        return (DeveloperMetadataLookup)super.clone();
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
