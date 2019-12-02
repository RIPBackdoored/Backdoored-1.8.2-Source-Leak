package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class LineStyle extends GenericJson
{
    @Key
    private String type;
    @Key
    private Integer width;
    
    public LineStyle() {
        super();
    }
    
    public String getType() {
        return this.type;
    }
    
    public LineStyle setType(final String type) {
        this.type = type;
        return this;
    }
    
    public Integer getWidth() {
        return this.width;
    }
    
    public LineStyle setWidth(final Integer width) {
        this.width = width;
        return this;
    }
    
    @Override
    public LineStyle set(final String a1, final Object a2) {
        return (LineStyle)super.set(a1, a2);
    }
    
    @Override
    public LineStyle clone() {
        return (LineStyle)super.clone();
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
