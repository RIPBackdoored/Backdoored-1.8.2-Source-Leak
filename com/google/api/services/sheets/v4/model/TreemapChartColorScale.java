package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TreemapChartColorScale extends GenericJson
{
    @Key
    private Color maxValueColor;
    @Key
    private Color midValueColor;
    @Key
    private Color minValueColor;
    @Key
    private Color noDataColor;
    
    public TreemapChartColorScale() {
        super();
    }
    
    public Color getMaxValueColor() {
        return this.maxValueColor;
    }
    
    public TreemapChartColorScale setMaxValueColor(final Color maxValueColor) {
        this.maxValueColor = maxValueColor;
        return this;
    }
    
    public Color getMidValueColor() {
        return this.midValueColor;
    }
    
    public TreemapChartColorScale setMidValueColor(final Color midValueColor) {
        this.midValueColor = midValueColor;
        return this;
    }
    
    public Color getMinValueColor() {
        return this.minValueColor;
    }
    
    public TreemapChartColorScale setMinValueColor(final Color minValueColor) {
        this.minValueColor = minValueColor;
        return this;
    }
    
    public Color getNoDataColor() {
        return this.noDataColor;
    }
    
    public TreemapChartColorScale setNoDataColor(final Color noDataColor) {
        this.noDataColor = noDataColor;
        return this;
    }
    
    @Override
    public TreemapChartColorScale set(final String a1, final Object a2) {
        return (TreemapChartColorScale)super.set(a1, a2);
    }
    
    @Override
    public TreemapChartColorScale clone() {
        return (TreemapChartColorScale)super.clone();
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
