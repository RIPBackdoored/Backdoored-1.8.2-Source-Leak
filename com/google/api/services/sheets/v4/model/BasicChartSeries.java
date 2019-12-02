package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class BasicChartSeries extends GenericJson
{
    @Key
    private Color color;
    @Key
    private LineStyle lineStyle;
    @Key
    private ChartData series;
    @Key
    private String targetAxis;
    @Key
    private String type;
    
    public BasicChartSeries() {
        super();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public BasicChartSeries setColor(final Color color) {
        this.color = color;
        return this;
    }
    
    public LineStyle getLineStyle() {
        return this.lineStyle;
    }
    
    public BasicChartSeries setLineStyle(final LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }
    
    public ChartData getSeries() {
        return this.series;
    }
    
    public BasicChartSeries setSeries(final ChartData series) {
        this.series = series;
        return this;
    }
    
    public String getTargetAxis() {
        return this.targetAxis;
    }
    
    public BasicChartSeries setTargetAxis(final String targetAxis) {
        this.targetAxis = targetAxis;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public BasicChartSeries setType(final String type) {
        this.type = type;
        return this;
    }
    
    @Override
    public BasicChartSeries set(final String a1, final Object a2) {
        return (BasicChartSeries)super.set(a1, a2);
    }
    
    @Override
    public BasicChartSeries clone() {
        return (BasicChartSeries)super.clone();
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
