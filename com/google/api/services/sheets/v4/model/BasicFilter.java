package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BasicFilter extends GenericJson
{
    @Key
    private Map<String, FilterCriteria> criteria;
    @Key
    private GridRange range;
    @Key
    private List<SortSpec> sortSpecs;
    
    public BasicFilter() {
        super();
    }
    
    public Map<String, FilterCriteria> getCriteria() {
        return this.criteria;
    }
    
    public BasicFilter setCriteria(final Map<String, FilterCriteria> criteria) {
        this.criteria = criteria;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public BasicFilter setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public List<SortSpec> getSortSpecs() {
        return this.sortSpecs;
    }
    
    public BasicFilter setSortSpecs(final List<SortSpec> sortSpecs) {
        this.sortSpecs = sortSpecs;
        return this;
    }
    
    @Override
    public BasicFilter set(final String a1, final Object a2) {
        return (BasicFilter)super.set(a1, a2);
    }
    
    @Override
    public BasicFilter clone() {
        return (BasicFilter)super.clone();
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
    
    static {
        Data.nullOf(FilterCriteria.class);
        Data.nullOf(SortSpec.class);
    }
}
