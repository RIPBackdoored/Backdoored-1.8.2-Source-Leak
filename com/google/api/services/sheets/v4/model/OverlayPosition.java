package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class OverlayPosition extends GenericJson
{
    @Key
    private GridCoordinate anchorCell;
    @Key
    private Integer heightPixels;
    @Key
    private Integer offsetXPixels;
    @Key
    private Integer offsetYPixels;
    @Key
    private Integer widthPixels;
    
    public OverlayPosition() {
        super();
    }
    
    public GridCoordinate getAnchorCell() {
        return this.anchorCell;
    }
    
    public OverlayPosition setAnchorCell(final GridCoordinate anchorCell) {
        this.anchorCell = anchorCell;
        return this;
    }
    
    public Integer getHeightPixels() {
        return this.heightPixels;
    }
    
    public OverlayPosition setHeightPixels(final Integer heightPixels) {
        this.heightPixels = heightPixels;
        return this;
    }
    
    public Integer getOffsetXPixels() {
        return this.offsetXPixels;
    }
    
    public OverlayPosition setOffsetXPixels(final Integer offsetXPixels) {
        this.offsetXPixels = offsetXPixels;
        return this;
    }
    
    public Integer getOffsetYPixels() {
        return this.offsetYPixels;
    }
    
    public OverlayPosition setOffsetYPixels(final Integer offsetYPixels) {
        this.offsetYPixels = offsetYPixels;
        return this;
    }
    
    public Integer getWidthPixels() {
        return this.widthPixels;
    }
    
    public OverlayPosition setWidthPixels(final Integer widthPixels) {
        this.widthPixels = widthPixels;
        return this;
    }
    
    @Override
    public OverlayPosition set(final String a1, final Object a2) {
        return (OverlayPosition)super.set(a1, a2);
    }
    
    @Override
    public OverlayPosition clone() {
        return (OverlayPosition)super.clone();
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
