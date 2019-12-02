package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DimensionGroup extends GenericJson
{
    @Key
    private Boolean collapsed;
    @Key
    private Integer depth;
    @Key
    private DimensionRange range;
    
    public DimensionGroup() {
        super();
    }
    
    public Boolean getCollapsed() {
        return this.collapsed;
    }
    
    public DimensionGroup setCollapsed(final Boolean collapsed) {
        this.collapsed = collapsed;
        return this;
    }
    
    public Integer getDepth() {
        return this.depth;
    }
    
    public DimensionGroup setDepth(final Integer depth) {
        this.depth = depth;
        return this;
    }
    
    public DimensionRange getRange() {
        return this.range;
    }
    
    public DimensionGroup setRange(final DimensionRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public DimensionGroup set(final String a1, final Object a2) {
        return (DimensionGroup)super.set(a1, a2);
    }
    
    @Override
    public DimensionGroup clone() {
        return (DimensionGroup)super.clone();
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
