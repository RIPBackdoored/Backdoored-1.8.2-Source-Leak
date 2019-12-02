package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DateTimeRule extends GenericJson
{
    @Key
    private String type;
    
    public DateTimeRule() {
        super();
    }
    
    public String getType() {
        return this.type;
    }
    
    public DateTimeRule setType(final String type) {
        this.type = type;
        return this;
    }
    
    @Override
    public DateTimeRule set(final String a1, final Object a2) {
        return (DateTimeRule)super.set(a1, a2);
    }
    
    @Override
    public DateTimeRule clone() {
        return (DateTimeRule)super.clone();
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
