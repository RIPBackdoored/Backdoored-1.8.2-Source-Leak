package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Color extends GenericJson
{
    @Key
    private Float alpha;
    @Key
    private Float blue;
    @Key
    private Float green;
    @Key
    private Float red;
    
    public Color() {
        super();
    }
    
    public Float getAlpha() {
        return this.alpha;
    }
    
    public Color setAlpha(final Float alpha) {
        this.alpha = alpha;
        return this;
    }
    
    public Float getBlue() {
        return this.blue;
    }
    
    public Color setBlue(final Float blue) {
        this.blue = blue;
        return this;
    }
    
    public Float getGreen() {
        return this.green;
    }
    
    public Color setGreen(final Float green) {
        this.green = green;
        return this;
    }
    
    public Float getRed() {
        return this.red;
    }
    
    public Color setRed(final Float red) {
        this.red = red;
        return this;
    }
    
    @Override
    public Color set(final String a1, final Object a2) {
        return (Color)super.set(a1, a2);
    }
    
    @Override
    public Color clone() {
        return (Color)super.clone();
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
