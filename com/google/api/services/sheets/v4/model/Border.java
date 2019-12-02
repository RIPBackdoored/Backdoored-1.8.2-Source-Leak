package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Border extends GenericJson
{
    @Key
    private Color color;
    @Key
    private String style;
    @Key
    private Integer width;
    
    public Border() {
        super();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public Border setColor(final Color color) {
        this.color = color;
        return this;
    }
    
    public String getStyle() {
        return this.style;
    }
    
    public Border setStyle(final String style) {
        this.style = style;
        return this;
    }
    
    public Integer getWidth() {
        return this.width;
    }
    
    public Border setWidth(final Integer width) {
        this.width = width;
        return this;
    }
    
    @Override
    public Border set(final String a1, final Object a2) {
        return (Border)super.set(a1, a2);
    }
    
    @Override
    public Border clone() {
        return (Border)super.clone();
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
