package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class CutPasteRequest extends GenericJson
{
    @Key
    private GridCoordinate destination;
    @Key
    private String pasteType;
    @Key
    private GridRange source;
    
    public CutPasteRequest() {
        super();
    }
    
    public GridCoordinate getDestination() {
        return this.destination;
    }
    
    public CutPasteRequest setDestination(final GridCoordinate destination) {
        this.destination = destination;
        return this;
    }
    
    public String getPasteType() {
        return this.pasteType;
    }
    
    public CutPasteRequest setPasteType(final String pasteType) {
        this.pasteType = pasteType;
        return this;
    }
    
    public GridRange getSource() {
        return this.source;
    }
    
    public CutPasteRequest setSource(final GridRange source) {
        this.source = source;
        return this;
    }
    
    @Override
    public CutPasteRequest set(final String a1, final Object a2) {
        return (CutPasteRequest)super.set(a1, a2);
    }
    
    @Override
    public CutPasteRequest clone() {
        return (CutPasteRequest)super.clone();
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
