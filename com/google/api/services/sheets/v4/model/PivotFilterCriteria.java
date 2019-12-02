package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class PivotFilterCriteria extends GenericJson
{
    @Key
    private List<String> visibleValues;
    
    public PivotFilterCriteria() {
        super();
    }
    
    public List<String> getVisibleValues() {
        return this.visibleValues;
    }
    
    public PivotFilterCriteria setVisibleValues(final List<String> visibleValues) {
        this.visibleValues = visibleValues;
        return this;
    }
    
    @Override
    public PivotFilterCriteria set(final String a1, final Object a2) {
        return (PivotFilterCriteria)super.set(a1, a2);
    }
    
    @Override
    public PivotFilterCriteria clone() {
        return (PivotFilterCriteria)super.clone();
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
