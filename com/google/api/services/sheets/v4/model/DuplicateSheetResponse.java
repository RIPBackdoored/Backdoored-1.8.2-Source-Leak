package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DuplicateSheetResponse extends GenericJson
{
    @Key
    private SheetProperties properties;
    
    public DuplicateSheetResponse() {
        super();
    }
    
    public SheetProperties getProperties() {
        return this.properties;
    }
    
    public DuplicateSheetResponse setProperties(final SheetProperties properties) {
        this.properties = properties;
        return this;
    }
    
    @Override
    public DuplicateSheetResponse set(final String a1, final Object a2) {
        return (DuplicateSheetResponse)super.set(a1, a2);
    }
    
    @Override
    public DuplicateSheetResponse clone() {
        return (DuplicateSheetResponse)super.clone();
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
