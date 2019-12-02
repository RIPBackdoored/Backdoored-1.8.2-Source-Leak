package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateSpreadsheetPropertiesRequest extends GenericJson
{
    @Key
    private String fields;
    @Key
    private SpreadsheetProperties properties;
    
    public UpdateSpreadsheetPropertiesRequest() {
        super();
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public UpdateSpreadsheetPropertiesRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    public SpreadsheetProperties getProperties() {
        return this.properties;
    }
    
    public UpdateSpreadsheetPropertiesRequest setProperties(final SpreadsheetProperties properties) {
        this.properties = properties;
        return this;
    }
    
    @Override
    public UpdateSpreadsheetPropertiesRequest set(final String a1, final Object a2) {
        return (UpdateSpreadsheetPropertiesRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateSpreadsheetPropertiesRequest clone() {
        return (UpdateSpreadsheetPropertiesRequest)super.clone();
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
