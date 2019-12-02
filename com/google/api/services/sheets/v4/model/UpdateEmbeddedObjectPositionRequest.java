package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateEmbeddedObjectPositionRequest extends GenericJson
{
    @Key
    private String fields;
    @Key
    private EmbeddedObjectPosition newPosition;
    @Key
    private Integer objectId;
    
    public UpdateEmbeddedObjectPositionRequest() {
        super();
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public UpdateEmbeddedObjectPositionRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    public EmbeddedObjectPosition getNewPosition() {
        return this.newPosition;
    }
    
    public UpdateEmbeddedObjectPositionRequest setNewPosition(final EmbeddedObjectPosition newPosition) {
        this.newPosition = newPosition;
        return this;
    }
    
    public Integer getObjectId() {
        return this.objectId;
    }
    
    public UpdateEmbeddedObjectPositionRequest setObjectId(final Integer objectId) {
        this.objectId = objectId;
        return this;
    }
    
    @Override
    public UpdateEmbeddedObjectPositionRequest set(final String a1, final Object a2) {
        return (UpdateEmbeddedObjectPositionRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateEmbeddedObjectPositionRequest clone() {
        return (UpdateEmbeddedObjectPositionRequest)super.clone();
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
