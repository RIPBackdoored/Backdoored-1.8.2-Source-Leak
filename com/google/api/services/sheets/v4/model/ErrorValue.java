package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class ErrorValue extends GenericJson
{
    @Key
    private String message;
    @Key
    private String type;
    
    public ErrorValue() {
        super();
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public ErrorValue setMessage(final String message) {
        this.message = message;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public ErrorValue setType(final String type) {
        this.type = type;
        return this;
    }
    
    @Override
    public ErrorValue set(final String a1, final Object a2) {
        return (ErrorValue)super.set(a1, a2);
    }
    
    @Override
    public ErrorValue clone() {
        return (ErrorValue)super.clone();
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
