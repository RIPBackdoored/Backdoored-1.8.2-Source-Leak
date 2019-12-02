package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TextRotation extends GenericJson
{
    @Key
    private Integer angle;
    @Key
    private Boolean vertical;
    
    public TextRotation() {
        super();
    }
    
    public Integer getAngle() {
        return this.angle;
    }
    
    public TextRotation setAngle(final Integer angle) {
        this.angle = angle;
        return this;
    }
    
    public Boolean getVertical() {
        return this.vertical;
    }
    
    public TextRotation setVertical(final Boolean vertical) {
        this.vertical = vertical;
        return this;
    }
    
    @Override
    public TextRotation set(final String a1, final Object a2) {
        return (TextRotation)super.set(a1, a2);
    }
    
    @Override
    public TextRotation clone() {
        return (TextRotation)super.clone();
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
