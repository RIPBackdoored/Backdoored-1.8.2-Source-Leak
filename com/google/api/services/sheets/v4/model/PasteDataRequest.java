package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class PasteDataRequest extends GenericJson
{
    @Key
    private GridCoordinate coordinate;
    @Key
    private String data;
    @Key
    private String delimiter;
    @Key
    private Boolean html;
    @Key
    private String type;
    
    public PasteDataRequest() {
        super();
    }
    
    public GridCoordinate getCoordinate() {
        return this.coordinate;
    }
    
    public PasteDataRequest setCoordinate(final GridCoordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }
    
    public String getData() {
        return this.data;
    }
    
    public PasteDataRequest setData(final String data) {
        this.data = data;
        return this;
    }
    
    public String getDelimiter() {
        return this.delimiter;
    }
    
    public PasteDataRequest setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
        return this;
    }
    
    public Boolean getHtml() {
        return this.html;
    }
    
    public PasteDataRequest setHtml(final Boolean html) {
        this.html = html;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public PasteDataRequest setType(final String type) {
        this.type = type;
        return this;
    }
    
    @Override
    public PasteDataRequest set(final String a1, final Object a2) {
        return (PasteDataRequest)super.set(a1, a2);
    }
    
    @Override
    public PasteDataRequest clone() {
        return (PasteDataRequest)super.clone();
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
