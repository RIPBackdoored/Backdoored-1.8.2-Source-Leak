package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class BubbleChartSpec extends GenericJson
{
    @Key
    private Color bubbleBorderColor;
    @Key
    private ChartData bubbleLabels;
    @Key
    private Integer bubbleMaxRadiusSize;
    @Key
    private Integer bubbleMinRadiusSize;
    @Key
    private Float bubbleOpacity;
    @Key
    private ChartData bubbleSizes;
    @Key
    private TextFormat bubbleTextStyle;
    @Key
    private ChartData domain;
    @Key
    private ChartData groupIds;
    @Key
    private String legendPosition;
    @Key
    private ChartData series;
    
    public BubbleChartSpec() {
        super();
    }
    
    public Color getBubbleBorderColor() {
        return this.bubbleBorderColor;
    }
    
    public BubbleChartSpec setBubbleBorderColor(final Color bubbleBorderColor) {
        this.bubbleBorderColor = bubbleBorderColor;
        return this;
    }
    
    public ChartData getBubbleLabels() {
        return this.bubbleLabels;
    }
    
    public BubbleChartSpec setBubbleLabels(final ChartData bubbleLabels) {
        this.bubbleLabels = bubbleLabels;
        return this;
    }
    
    public Integer getBubbleMaxRadiusSize() {
        return this.bubbleMaxRadiusSize;
    }
    
    public BubbleChartSpec setBubbleMaxRadiusSize(final Integer bubbleMaxRadiusSize) {
        this.bubbleMaxRadiusSize = bubbleMaxRadiusSize;
        return this;
    }
    
    public Integer getBubbleMinRadiusSize() {
        return this.bubbleMinRadiusSize;
    }
    
    public BubbleChartSpec setBubbleMinRadiusSize(final Integer bubbleMinRadiusSize) {
        this.bubbleMinRadiusSize = bubbleMinRadiusSize;
        return this;
    }
    
    public Float getBubbleOpacity() {
        return this.bubbleOpacity;
    }
    
    public BubbleChartSpec setBubbleOpacity(final Float bubbleOpacity) {
        this.bubbleOpacity = bubbleOpacity;
        return this;
    }
    
    public ChartData getBubbleSizes() {
        return this.bubbleSizes;
    }
    
    public BubbleChartSpec setBubbleSizes(final ChartData bubbleSizes) {
        this.bubbleSizes = bubbleSizes;
        return this;
    }
    
    public TextFormat getBubbleTextStyle() {
        return this.bubbleTextStyle;
    }
    
    public BubbleChartSpec setBubbleTextStyle(final TextFormat bubbleTextStyle) {
        this.bubbleTextStyle = bubbleTextStyle;
        return this;
    }
    
    public ChartData getDomain() {
        return this.domain;
    }
    
    public BubbleChartSpec setDomain(final ChartData domain) {
        this.domain = domain;
        return this;
    }
    
    public ChartData getGroupIds() {
        return this.groupIds;
    }
    
    public BubbleChartSpec setGroupIds(final ChartData groupIds) {
        this.groupIds = groupIds;
        return this;
    }
    
    public String getLegendPosition() {
        return this.legendPosition;
    }
    
    public BubbleChartSpec setLegendPosition(final String legendPosition) {
        this.legendPosition = legendPosition;
        return this;
    }
    
    public ChartData getSeries() {
        return this.series;
    }
    
    public BubbleChartSpec setSeries(final ChartData series) {
        this.series = series;
        return this;
    }
    
    @Override
    public BubbleChartSpec set(final String a1, final Object a2) {
        return (BubbleChartSpec)super.set(a1, a2);
    }
    
    @Override
    public BubbleChartSpec clone() {
        return (BubbleChartSpec)super.clone();
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
