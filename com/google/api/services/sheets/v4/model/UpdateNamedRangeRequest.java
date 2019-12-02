package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateNamedRangeRequest extends GenericJson
{
    @Key
    private String fields;
    @Key
    private NamedRange namedRange;
    
    public UpdateNamedRangeRequest() {
        super();
    }
    
    public String getFields() {
        return this.fields;
    }
    
    public UpdateNamedRangeRequest setFields(final String fields) {
        this.fields = fields;
        return this;
    }
    
    public NamedRange getNamedRange() {
        return this.namedRange;
    }
    
    public UpdateNamedRangeRequest setNamedRange(final NamedRange namedRange) {
        this.namedRange = namedRange;
        return this;
    }
    
    @Override
    public UpdateNamedRangeRequest set(final String a1, final Object a2) {
        return (UpdateNamedRangeRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateNamedRangeRequest clone() {
        return (UpdateNamedRangeRequest)super.clone();
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
