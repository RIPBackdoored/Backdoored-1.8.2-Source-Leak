package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class HistogramRule extends GenericJson
{
    @Key
    private Double end;
    @Key
    private Double interval;
    @Key
    private Double start;
    
    public HistogramRule() {
        super();
    }
    
    public Double getEnd() {
        return this.end;
    }
    
    public HistogramRule setEnd(final Double end) {
        this.end = end;
        return this;
    }
    
    public Double getInterval() {
        return this.interval;
    }
    
    public HistogramRule setInterval(final Double interval) {
        this.interval = interval;
        return this;
    }
    
    public Double getStart() {
        return this.start;
    }
    
    public HistogramRule setStart(final Double start) {
        this.start = start;
        return this;
    }
    
    @Override
    public HistogramRule set(final String a1, final Object a2) {
        return (HistogramRule)super.set(a1, a2);
    }
    
    @Override
    public HistogramRule clone() {
        return (HistogramRule)super.clone();
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
