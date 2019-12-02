package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TreemapChartSpec extends GenericJson
{
    @Key
    private ChartData colorData;
    @Key
    private TreemapChartColorScale colorScale;
    @Key
    private Color headerColor;
    @Key
    private Boolean hideTooltips;
    @Key
    private Integer hintedLevels;
    @Key
    private ChartData labels;
    @Key
    private Integer levels;
    @Key
    private Double maxValue;
    @Key
    private Double minValue;
    @Key
    private ChartData parentLabels;
    @Key
    private ChartData sizeData;
    @Key
    private TextFormat textFormat;
    
    public TreemapChartSpec() {
        super();
    }
    
    public ChartData getColorData() {
        return this.colorData;
    }
    
    public TreemapChartSpec setColorData(final ChartData colorData) {
        this.colorData = colorData;
        return this;
    }
    
    public TreemapChartColorScale getColorScale() {
        return this.colorScale;
    }
    
    public TreemapChartSpec setColorScale(final TreemapChartColorScale colorScale) {
        this.colorScale = colorScale;
        return this;
    }
    
    public Color getHeaderColor() {
        return this.headerColor;
    }
    
    public TreemapChartSpec setHeaderColor(final Color headerColor) {
        this.headerColor = headerColor;
        return this;
    }
    
    public Boolean getHideTooltips() {
        return this.hideTooltips;
    }
    
    public TreemapChartSpec setHideTooltips(final Boolean hideTooltips) {
        this.hideTooltips = hideTooltips;
        return this;
    }
    
    public Integer getHintedLevels() {
        return this.hintedLevels;
    }
    
    public TreemapChartSpec setHintedLevels(final Integer hintedLevels) {
        this.hintedLevels = hintedLevels;
        return this;
    }
    
    public ChartData getLabels() {
        return this.labels;
    }
    
    public TreemapChartSpec setLabels(final ChartData labels) {
        this.labels = labels;
        return this;
    }
    
    public Integer getLevels() {
        return this.levels;
    }
    
    public TreemapChartSpec setLevels(final Integer levels) {
        this.levels = levels;
        return this;
    }
    
    public Double getMaxValue() {
        return this.maxValue;
    }
    
    public TreemapChartSpec setMaxValue(final Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }
    
    public Double getMinValue() {
        return this.minValue;
    }
    
    public TreemapChartSpec setMinValue(final Double minValue) {
        this.minValue = minValue;
        return this;
    }
    
    public ChartData getParentLabels() {
        return this.parentLabels;
    }
    
    public TreemapChartSpec setParentLabels(final ChartData parentLabels) {
        this.parentLabels = parentLabels;
        return this;
    }
    
    public ChartData getSizeData() {
        return this.sizeData;
    }
    
    public TreemapChartSpec setSizeData(final ChartData sizeData) {
        this.sizeData = sizeData;
        return this;
    }
    
    public TextFormat getTextFormat() {
        return this.textFormat;
    }
    
    public TreemapChartSpec setTextFormat(final TextFormat textFormat) {
        this.textFormat = textFormat;
        return this;
    }
    
    @Override
    public TreemapChartSpec set(final String a1, final Object a2) {
        return (TreemapChartSpec)super.set(a1, a2);
    }
    
    @Override
    public TreemapChartSpec clone() {
        return (TreemapChartSpec)super.clone();
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
