package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddChartResponse extends GenericJson
{
    @Key
    private EmbeddedChart chart;
    
    public AddChartResponse() {
        super();
    }
    
    public EmbeddedChart getChart() {
        return this.chart;
    }
    
    public AddChartResponse setChart(final EmbeddedChart chart) {
        this.chart = chart;
        return this;
    }
    
    @Override
    public AddChartResponse set(final String a1, final Object a2) {
        return (AddChartResponse)super.set(a1, a2);
    }
    
    @Override
    public AddChartResponse clone() {
        return (AddChartResponse)super.clone();
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
