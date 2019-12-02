package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class HistogramSeries extends GenericJson
{
    @Key
    private Color barColor;
    @Key
    private ChartData data;
    
    public HistogramSeries() {
        super();
    }
    
    public Color getBarColor() {
        return this.barColor;
    }
    
    public HistogramSeries setBarColor(final Color barColor) {
        this.barColor = barColor;
        return this;
    }
    
    public ChartData getData() {
        return this.data;
    }
    
    public HistogramSeries setData(final ChartData data) {
        this.data = data;
        return this;
    }
    
    @Override
    public HistogramSeries set(final String a1, final Object a2) {
        return (HistogramSeries)super.set(a1, a2);
    }
    
    @Override
    public HistogramSeries clone() {
        return (HistogramSeries)super.clone();
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
