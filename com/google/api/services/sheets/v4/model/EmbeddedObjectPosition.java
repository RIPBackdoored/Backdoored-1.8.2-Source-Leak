package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class EmbeddedObjectPosition extends GenericJson
{
    @Key
    private Boolean newSheet;
    @Key
    private OverlayPosition overlayPosition;
    @Key
    private Integer sheetId;
    
    public EmbeddedObjectPosition() {
        super();
    }
    
    public Boolean getNewSheet() {
        return this.newSheet;
    }
    
    public EmbeddedObjectPosition setNewSheet(final Boolean newSheet) {
        this.newSheet = newSheet;
        return this;
    }
    
    public OverlayPosition getOverlayPosition() {
        return this.overlayPosition;
    }
    
    public EmbeddedObjectPosition setOverlayPosition(final OverlayPosition overlayPosition) {
        this.overlayPosition = overlayPosition;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public EmbeddedObjectPosition setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public EmbeddedObjectPosition set(final String a1, final Object a2) {
        return (EmbeddedObjectPosition)super.set(a1, a2);
    }
    
    @Override
    public EmbeddedObjectPosition clone() {
        return (EmbeddedObjectPosition)super.clone();
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
