package com.google.api.client.json.webtoken;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public static class Header extends GenericJson
{
    @Key("typ")
    private String type;
    @Key("cty")
    private String contentType;
    
    public Header() {
        super();
    }
    
    public final String getType() {
        return this.type;
    }
    
    public Header setType(final String a1) {
        this.type = a1;
        return this;
    }
    
    public final String getContentType() {
        return this.contentType;
    }
    
    public Header setContentType(final String a1) {
        this.contentType = a1;
        return this;
    }
    
    @Override
    public Header set(final String a1, final Object a2) {
        return (Header)super.set(a1, a2);
    }
    
    @Override
    public Header clone() {
        return (Header)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String a1, final Object a2) {
        return this.set(a1, a2);
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
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
