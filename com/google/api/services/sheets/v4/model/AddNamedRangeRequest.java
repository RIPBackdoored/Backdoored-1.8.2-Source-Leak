package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddNamedRangeRequest extends GenericJson
{
    @Key
    private NamedRange namedRange;
    
    public AddNamedRangeRequest() {
        super();
    }
    
    public NamedRange getNamedRange() {
        return this.namedRange;
    }
    
    public AddNamedRangeRequest setNamedRange(final NamedRange namedRange) {
        this.namedRange = namedRange;
        return this;
    }
    
    @Override
    public AddNamedRangeRequest set(final String a1, final Object a2) {
        return (AddNamedRangeRequest)super.set(a1, a2);
    }
    
    @Override
    public AddNamedRangeRequest clone() {
        return (AddNamedRangeRequest)super.clone();
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
