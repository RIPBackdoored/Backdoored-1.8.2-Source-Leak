package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class FilterView extends GenericJson
{
    @Key
    private Map<String, FilterCriteria> criteria;
    @Key
    private Integer filterViewId;
    @Key
    private String namedRangeId;
    @Key
    private GridRange range;
    @Key
    private List<SortSpec> sortSpecs;
    @Key
    private String title;
    
    public FilterView() {
        super();
    }
    
    public Map<String, FilterCriteria> getCriteria() {
        return this.criteria;
    }
    
    public FilterView setCriteria(final Map<String, FilterCriteria> criteria) {
        this.criteria = criteria;
        return this;
    }
    
    public Integer getFilterViewId() {
        return this.filterViewId;
    }
    
    public FilterView setFilterViewId(final Integer filterViewId) {
        this.filterViewId = filterViewId;
        return this;
    }
    
    public String getNamedRangeId() {
        return this.namedRangeId;
    }
    
    public FilterView setNamedRangeId(final String namedRangeId) {
        this.namedRangeId = namedRangeId;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public FilterView setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public List<SortSpec> getSortSpecs() {
        return this.sortSpecs;
    }
    
    public FilterView setSortSpecs(final List<SortSpec> sortSpecs) {
        this.sortSpecs = sortSpecs;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public FilterView setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    @Override
    public FilterView set(final String a1, final Object a2) {
        return (FilterView)super.set(a1, a2);
    }
    
    @Override
    public FilterView clone() {
        return (FilterView)super.clone();
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
