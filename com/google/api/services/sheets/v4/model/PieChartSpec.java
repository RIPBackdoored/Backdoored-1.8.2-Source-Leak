package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class PieChartSpec extends GenericJson
{
    @Key
    private ChartData domain;
    @Key
    private String legendPosition;
    @Key
    private Double pieHole;
    @Key
    private ChartData series;
    @Key
    private Boolean threeDimensional;
    
    public PieChartSpec() {
        super();
    }
    
    public ChartData getDomain() {
        return this.domain;
    }
    
    public PieChartSpec setDomain(final ChartData domain) {
        this.domain = domain;
        return this;
    }
    
    public String getLegendPosition() {
        return this.legendPosition;
    }
    
    public PieChartSpec setLegendPosition(final String legendPosition) {
        this.legendPosition = legendPosition;
        return this;
    }
    
    public Double getPieHole() {
        return this.pieHole;
    }
    
    public PieChartSpec setPieHole(final Double pieHole) {
        this.pieHole = pieHole;
        return this;
    }
    
    public ChartData getSeries() {
        return this.series;
    }
    
    public PieChartSpec setSeries(final ChartData series) {
        this.series = series;
        return this;
    }
    
    public Boolean getThreeDimensional() {
        return this.threeDimensional;
    }
    
    public PieChartSpec setThreeDimensional(final Boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
        return this;
    }
    
    @Override
    public PieChartSpec set(final String a1, final Object a2) {
        return (PieChartSpec)super.set(a1, a2);
    }
    
    @Override
    public PieChartSpec clone() {
        return (PieChartSpec)super.clone();
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
