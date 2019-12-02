package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class PivotGroupValueMetadata extends GenericJson
{
    @Key
    private Boolean collapsed;
    @Key
    private ExtendedValue value;
    
    public PivotGroupValueMetadata() {
        super();
    }
    
    public Boolean getCollapsed() {
        return this.collapsed;
    }
    
    public PivotGroupValueMetadata setCollapsed(final Boolean collapsed) {
        this.collapsed = collapsed;
        return this;
    }
    
    public ExtendedValue getValue() {
        return this.value;
    }
    
    public PivotGroupValueMetadata setValue(final ExtendedValue value) {
        this.value = value;
        return this;
    }
    
    @Override
    public PivotGroupValueMetadata set(final String a1, final Object a2) {
        return (PivotGroupValueMetadata)super.set(a1, a2);
    }
    
    @Override
    public PivotGroupValueMetadata clone() {
        return (PivotGroupValueMetadata)super.clone();
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
