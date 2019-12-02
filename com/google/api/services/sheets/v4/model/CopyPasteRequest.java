package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class CopyPasteRequest extends GenericJson
{
    @Key
    private GridRange destination;
    @Key
    private String pasteOrientation;
    @Key
    private String pasteType;
    @Key
    private GridRange source;
    
    public CopyPasteRequest() {
        super();
    }
    
    public GridRange getDestination() {
        return this.destination;
    }
    
    public CopyPasteRequest setDestination(final GridRange destination) {
        this.destination = destination;
        return this;
    }
    
    public String getPasteOrientation() {
        return this.pasteOrientation;
    }
    
    public CopyPasteRequest setPasteOrientation(final String pasteOrientation) {
        this.pasteOrientation = pasteOrientation;
        return this;
    }
    
    public String getPasteType() {
        return this.pasteType;
    }
    
    public CopyPasteRequest setPasteType(final String pasteType) {
        this.pasteType = pasteType;
        return this;
    }
    
    public GridRange getSource() {
        return this.source;
    }
    
    public CopyPasteRequest setSource(final GridRange source) {
        this.source = source;
        return this;
    }
    
    @Override
    public CopyPasteRequest set(final String a1, final Object a2) {
        return (CopyPasteRequest)super.set(a1, a2);
    }
    
    @Override
    public CopyPasteRequest clone() {
        return (CopyPasteRequest)super.clone();
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
