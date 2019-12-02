package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateEmbeddedObjectPositionResponse extends GenericJson
{
    @Key
    private EmbeddedObjectPosition position;
    
    public UpdateEmbeddedObjectPositionResponse() {
        super();
    }
    
    public EmbeddedObjectPosition getPosition() {
        return this.position;
    }
    
    public UpdateEmbeddedObjectPositionResponse setPosition(final EmbeddedObjectPosition position) {
        this.position = position;
        return this;
    }
    
    @Override
    public UpdateEmbeddedObjectPositionResponse set(final String a1, final Object a2) {
        return (UpdateEmbeddedObjectPositionResponse)super.set(a1, a2);
    }
    
    @Override
    public UpdateEmbeddedObjectPositionResponse clone() {
        return (UpdateEmbeddedObjectPositionResponse)super.clone();
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
