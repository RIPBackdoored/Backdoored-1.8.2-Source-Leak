package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class InterpolationPoint extends GenericJson
{
    @Key
    private Color color;
    @Key
    private String type;
    @Key
    private String value;
    
    public InterpolationPoint() {
        super();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public InterpolationPoint setColor(final Color color) {
        this.color = color;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public InterpolationPoint setType(final String type) {
        this.type = type;
        return this;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public InterpolationPoint setValue(final String value) {
        this.value = value;
        return this;
    }
    
    @Override
    public InterpolationPoint set(final String a1, final Object a2) {
        return (InterpolationPoint)super.set(a1, a2);
    }
    
    @Override
    public InterpolationPoint clone() {
        return (InterpolationPoint)super.clone();
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
