package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class WaterfallChartDomain extends GenericJson
{
    @Key
    private ChartData data;
    @Key
    private Boolean reversed;
    
    public WaterfallChartDomain() {
        super();
    }
    
    public ChartData getData() {
        return this.data;
    }
    
    public WaterfallChartDomain setData(final ChartData data) {
        this.data = data;
        return this;
    }
    
    public Boolean getReversed() {
        return this.reversed;
    }
    
    public WaterfallChartDomain setReversed(final Boolean reversed) {
        this.reversed = reversed;
        return this;
    }
    
    @Override
    public WaterfallChartDomain set(final String a1, final Object a2) {
        return (WaterfallChartDomain)super.set(a1, a2);
    }
    
    @Override
    public WaterfallChartDomain clone() {
        return (WaterfallChartDomain)super.clone();
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
