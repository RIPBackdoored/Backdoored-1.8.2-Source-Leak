package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TextPosition extends GenericJson
{
    @Key
    private String horizontalAlignment;
    
    public TextPosition() {
        super();
    }
    
    public String getHorizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    public TextPosition setHorizontalAlignment(final String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }
    
    @Override
    public TextPosition set(final String a1, final Object a2) {
        return (TextPosition)super.set(a1, a2);
    }
    
    @Override
    public TextPosition clone() {
        return (TextPosition)super.clone();
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
