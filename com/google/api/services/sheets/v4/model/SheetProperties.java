package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class SheetProperties extends GenericJson
{
    @Key
    private GridProperties gridProperties;
    @Key
    private Boolean hidden;
    @Key
    private Integer index;
    @Key
    private Boolean rightToLeft;
    @Key
    private Integer sheetId;
    @Key
    private String sheetType;
    @Key
    private Color tabColor;
    @Key
    private String title;
    
    public SheetProperties() {
        super();
    }
    
    public GridProperties getGridProperties() {
        return this.gridProperties;
    }
    
    public SheetProperties setGridProperties(final GridProperties gridProperties) {
        this.gridProperties = gridProperties;
        return this;
    }
    
    public Boolean getHidden() {
        return this.hidden;
    }
    
    public SheetProperties setHidden(final Boolean hidden) {
        this.hidden = hidden;
        return this;
    }
    
    public Integer getIndex() {
        return this.index;
    }
    
    public SheetProperties setIndex(final Integer index) {
        this.index = index;
        return this;
    }
    
    public Boolean getRightToLeft() {
        return this.rightToLeft;
    }
    
    public SheetProperties setRightToLeft(final Boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public SheetProperties setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    public String getSheetType() {
        return this.sheetType;
    }
    
    public SheetProperties setSheetType(final String sheetType) {
        this.sheetType = sheetType;
        return this;
    }
    
    public Color getTabColor() {
        return this.tabColor;
    }
    
    public SheetProperties setTabColor(final Color tabColor) {
        this.tabColor = tabColor;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public SheetProperties setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    @Override
    public SheetProperties set(final String a1, final Object a2) {
        return (SheetProperties)super.set(a1, a2);
    }
    
    @Override
    public SheetProperties clone() {
        return (SheetProperties)super.clone();
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
