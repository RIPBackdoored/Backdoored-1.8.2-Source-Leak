package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class DimensionProperties extends GenericJson
{
    @Key
    private List<DeveloperMetadata> developerMetadata;
    @Key
    private Boolean hiddenByFilter;
    @Key
    private Boolean hiddenByUser;
    @Key
    private Integer pixelSize;
    
    public DimensionProperties() {
        super();
    }
    
    public List<DeveloperMetadata> getDeveloperMetadata() {
        return this.developerMetadata;
    }
    
    public DimensionProperties setDeveloperMetadata(final List<DeveloperMetadata> developerMetadata) {
        this.developerMetadata = developerMetadata;
        return this;
    }
    
    public Boolean getHiddenByFilter() {
        return this.hiddenByFilter;
    }
    
    public DimensionProperties setHiddenByFilter(final Boolean hiddenByFilter) {
        this.hiddenByFilter = hiddenByFilter;
        return this;
    }
    
    public Boolean getHiddenByUser() {
        return this.hiddenByUser;
    }
    
    public DimensionProperties setHiddenByUser(final Boolean hiddenByUser) {
        this.hiddenByUser = hiddenByUser;
        return this;
    }
    
    public Integer getPixelSize() {
        return this.pixelSize;
    }
    
    public DimensionProperties setPixelSize(final Integer pixelSize) {
        this.pixelSize = pixelSize;
        return this;
    }
    
    @Override
    public DimensionProperties set(final String a1, final Object a2) {
        return (DimensionProperties)super.set(a1, a2);
    }
    
    @Override
    public DimensionProperties clone() {
        return (DimensionProperties)super.clone();
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
        Data.nullOf(DeveloperMetadata.class);
    }
}
