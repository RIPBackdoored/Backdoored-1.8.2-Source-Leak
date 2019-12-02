package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddFilterViewResponse extends GenericJson
{
    @Key
    private FilterView filter;
    
    public AddFilterViewResponse() {
        super();
    }
    
    public FilterView getFilter() {
        return this.filter;
    }
    
    public AddFilterViewResponse setFilter(final FilterView filter) {
        this.filter = filter;
        return this;
    }
    
    @Override
    public AddFilterViewResponse set(final String a1, final Object a2) {
        return (AddFilterViewResponse)super.set(a1, a2);
    }
    
    @Override
    public AddFilterViewResponse clone() {
        return (AddFilterViewResponse)super.clone();
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
