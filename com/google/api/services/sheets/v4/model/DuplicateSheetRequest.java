package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DuplicateSheetRequest extends GenericJson
{
    @Key
    private Integer insertSheetIndex;
    @Key
    private Integer newSheetId;
    @Key
    private String newSheetName;
    @Key
    private Integer sourceSheetId;
    
    public DuplicateSheetRequest() {
        super();
    }
    
    public Integer getInsertSheetIndex() {
        return this.insertSheetIndex;
    }
    
    public DuplicateSheetRequest setInsertSheetIndex(final Integer insertSheetIndex) {
        this.insertSheetIndex = insertSheetIndex;
        return this;
    }
    
    public Integer getNewSheetId() {
        return this.newSheetId;
    }
    
    public DuplicateSheetRequest setNewSheetId(final Integer newSheetId) {
        this.newSheetId = newSheetId;
        return this;
    }
    
    public String getNewSheetName() {
        return this.newSheetName;
    }
    
    public DuplicateSheetRequest setNewSheetName(final String newSheetName) {
        this.newSheetName = newSheetName;
        return this;
    }
    
    public Integer getSourceSheetId() {
        return this.sourceSheetId;
    }
    
    public DuplicateSheetRequest setSourceSheetId(final Integer sourceSheetId) {
        this.sourceSheetId = sourceSheetId;
        return this;
    }
    
    @Override
    public DuplicateSheetRequest set(final String a1, final Object a2) {
        return (DuplicateSheetRequest)super.set(a1, a2);
    }
    
    @Override
    public DuplicateSheetRequest clone() {
        return (DuplicateSheetRequest)super.clone();
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
