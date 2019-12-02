package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class GridRange extends GenericJson
{
    @Key
    private Integer endColumnIndex;
    @Key
    private Integer endRowIndex;
    @Key
    private Integer sheetId;
    @Key
    private Integer startColumnIndex;
    @Key
    private Integer startRowIndex;
    
    public GridRange() {
        super();
    }
    
    public Integer getEndColumnIndex() {
        return this.endColumnIndex;
    }
    
    public GridRange setEndColumnIndex(final Integer endColumnIndex) {
        this.endColumnIndex = endColumnIndex;
        return this;
    }
    
    public Integer getEndRowIndex() {
        return this.endRowIndex;
    }
    
    public GridRange setEndRowIndex(final Integer endRowIndex) {
        this.endRowIndex = endRowIndex;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public GridRange setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    public Integer getStartColumnIndex() {
        return this.startColumnIndex;
    }
    
    public GridRange setStartColumnIndex(final Integer startColumnIndex) {
        this.startColumnIndex = startColumnIndex;
        return this;
    }
    
    public Integer getStartRowIndex() {
        return this.startRowIndex;
    }
    
    public GridRange setStartRowIndex(final Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
        return this;
    }
    
    @Override
    public GridRange set(final String a1, final Object a2) {
        return (GridRange)super.set(a1, a2);
    }
    
    @Override
    public GridRange clone() {
        return (GridRange)super.clone();
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
