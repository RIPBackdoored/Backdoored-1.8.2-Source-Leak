package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BasicChartSpec extends GenericJson
{
    @Key
    private List<BasicChartAxis> axis;
    @Key
    private String chartType;
    @Key
    private String compareMode;
    @Key
    private List<BasicChartDomain> domains;
    @Key
    private Integer headerCount;
    @Key
    private Boolean interpolateNulls;
    @Key
    private String legendPosition;
    @Key
    private Boolean lineSmoothing;
    @Key
    private List<BasicChartSeries> series;
    @Key
    private String stackedType;
    @Key
    private Boolean threeDimensional;
    
    public BasicChartSpec() {
        super();
    }
    
    public List<BasicChartAxis> getAxis() {
        return this.axis;
    }
    
    public BasicChartSpec setAxis(final List<BasicChartAxis> axis) {
        this.axis = axis;
        return this;
    }
    
    public String getChartType() {
        return this.chartType;
    }
    
    public BasicChartSpec setChartType(final String chartType) {
        this.chartType = chartType;
        return this;
    }
    
    public String getCompareMode() {
        return this.compareMode;
    }
    
    public BasicChartSpec setCompareMode(final String compareMode) {
        this.compareMode = compareMode;
        return this;
    }
    
    public List<BasicChartDomain> getDomains() {
        return this.domains;
    }
    
    public BasicChartSpec setDomains(final List<BasicChartDomain> domains) {
        this.domains = domains;
        return this;
    }
    
    public Integer getHeaderCount() {
        return this.headerCount;
    }
    
    public BasicChartSpec setHeaderCount(final Integer headerCount) {
        this.headerCount = headerCount;
        return this;
    }
    
    public Boolean getInterpolateNulls() {
        return this.interpolateNulls;
    }
    
    public BasicChartSpec setInterpolateNulls(final Boolean interpolateNulls) {
        this.interpolateNulls = interpolateNulls;
        return this;
    }
    
    public String getLegendPosition() {
        return this.legendPosition;
    }
    
    public BasicChartSpec setLegendPosition(final String legendPosition) {
        this.legendPosition = legendPosition;
        return this;
    }
    
    public Boolean getLineSmoothing() {
        return this.lineSmoothing;
    }
    
    public BasicChartSpec setLineSmoothing(final Boolean lineSmoothing) {
        this.lineSmoothing = lineSmoothing;
        return this;
    }
    
    public List<BasicChartSeries> getSeries() {
        return this.series;
    }
    
    public BasicChartSpec setSeries(final List<BasicChartSeries> series) {
        this.series = series;
        return this;
    }
    
    public String getStackedType() {
        return this.stackedType;
    }
    
    public BasicChartSpec setStackedType(final String stackedType) {
        this.stackedType = stackedType;
        return this;
    }
    
    public Boolean getThreeDimensional() {
        return this.threeDimensional;
    }
    
    public BasicChartSpec setThreeDimensional(final Boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
        return this;
    }
    
    @Override
    public BasicChartSpec set(final String a1, final Object a2) {
        return (BasicChartSpec)super.set(a1, a2);
    }
    
    @Override
    public BasicChartSpec clone() {
        return (BasicChartSpec)super.clone();
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
        Data.nullOf(BasicChartDomain.class);
    }
}
