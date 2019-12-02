package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class EmbeddedChart extends GenericJson
{
    @Key
    private Integer chartId;
    @Key
    private EmbeddedObjectPosition position;
    @Key
    private ChartSpec spec;
    
    public EmbeddedChart() {
        super();
    }
    
    public Integer getChartId() {
        return this.chartId;
    }
    
    public EmbeddedChart setChartId(final Integer chartId) {
        this.chartId = chartId;
        return this;
    }
    
    public EmbeddedObjectPosition getPosition() {
        return this.position;
    }
    
    public EmbeddedChart setPosition(final EmbeddedObjectPosition position) {
        this.position = position;
        return this;
    }
    
    public ChartSpec getSpec() {
        return this.spec;
    }
    
    public EmbeddedChart setSpec(final ChartSpec spec) {
        this.spec = spec;
        return this;
    }
    
    @Override
    public EmbeddedChart set(final String a1, final Object a2) {
        return (EmbeddedChart)super.set(a1, a2);
    }
    
    @Override
    public EmbeddedChart clone() {
        return (EmbeddedChart)super.clone();
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
