package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class HistogramChartSpec extends GenericJson
{
    @Key
    private Double bucketSize;
    @Key
    private String legendPosition;
    @Key
    private Double outlierPercentile;
    @Key
    private List<HistogramSeries> series;
    @Key
    private Boolean showItemDividers;
    
    public HistogramChartSpec() {
        super();
    }
    
    public Double getBucketSize() {
        return this.bucketSize;
    }
    
    public HistogramChartSpec setBucketSize(final Double bucketSize) {
        this.bucketSize = bucketSize;
        return this;
    }
    
    public String getLegendPosition() {
        return this.legendPosition;
    }
    
    public HistogramChartSpec setLegendPosition(final String legendPosition) {
        this.legendPosition = legendPosition;
        return this;
    }
    
    public Double getOutlierPercentile() {
        return this.outlierPercentile;
    }
    
    public HistogramChartSpec setOutlierPercentile(final Double outlierPercentile) {
        this.outlierPercentile = outlierPercentile;
        return this;
    }
    
    public List<HistogramSeries> getSeries() {
        return this.series;
    }
    
    public HistogramChartSpec setSeries(final List<HistogramSeries> series) {
        this.series = series;
        return this;
    }
    
    public Boolean getShowItemDividers() {
        return this.showItemDividers;
    }
    
    public HistogramChartSpec setShowItemDividers(final Boolean showItemDividers) {
        this.showItemDividers = showItemDividers;
        return this;
    }
    
    @Override
    public HistogramChartSpec set(final String a1, final Object a2) {
        return (HistogramChartSpec)super.set(a1, a2);
    }
    
    @Override
    public HistogramChartSpec clone() {
        return (HistogramChartSpec)super.clone();
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
