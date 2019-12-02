package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeveloperMetadataLocation extends GenericJson
{
    @Key
    private DimensionRange dimensionRange;
    @Key
    private String locationType;
    @Key
    private Integer sheetId;
    @Key
    private Boolean spreadsheet;
    
    public DeveloperMetadataLocation() {
        super();
    }
    
    public DimensionRange getDimensionRange() {
        return this.dimensionRange;
    }
    
    public DeveloperMetadataLocation setDimensionRange(final DimensionRange dimensionRange) {
        this.dimensionRange = dimensionRange;
        return this;
    }
    
    public String getLocationType() {
        return this.locationType;
    }
    
    public DeveloperMetadataLocation setLocationType(final String locationType) {
        this.locationType = locationType;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public DeveloperMetadataLocation setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    public Boolean getSpreadsheet() {
        return this.spreadsheet;
    }
    
    public DeveloperMetadataLocation setSpreadsheet(final Boolean spreadsheet) {
        this.spreadsheet = spreadsheet;
        return this;
    }
    
    @Override
    public DeveloperMetadataLocation set(final String a1, final Object a2) {
        return (DeveloperMetadataLocation)super.set(a1, a2);
    }
    
    @Override
    public DeveloperMetadataLocation clone() {
        return (DeveloperMetadataLocation)super.clone();
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
