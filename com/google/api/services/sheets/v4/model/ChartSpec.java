package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class ChartSpec extends GenericJson
{
    @Key
    private String altText;
    @Key
    private Color backgroundColor;
    @Key
    private BasicChartSpec basicChart;
    @Key
    private BubbleChartSpec bubbleChart;
    @Key
    private CandlestickChartSpec candlestickChart;
    @Key
    private String fontName;
    @Key
    private String hiddenDimensionStrategy;
    @Key
    private HistogramChartSpec histogramChart;
    @Key
    private Boolean maximized;
    @Key
    private OrgChartSpec orgChart;
    @Key
    private PieChartSpec pieChart;
    @Key
    private String subtitle;
    @Key
    private TextFormat subtitleTextFormat;
    @Key
    private TextPosition subtitleTextPosition;
    @Key
    private String title;
    @Key
    private TextFormat titleTextFormat;
    @Key
    private TextPosition titleTextPosition;
    @Key
    private TreemapChartSpec treemapChart;
    @Key
    private WaterfallChartSpec waterfallChart;
    
    public ChartSpec() {
        super();
    }
    
    public String getAltText() {
        return this.altText;
    }
    
    public ChartSpec setAltText(final String altText) {
        this.altText = altText;
        return this;
    }
    
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
    
    public ChartSpec setBackgroundColor(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public BasicChartSpec getBasicChart() {
        return this.basicChart;
    }
    
    public ChartSpec setBasicChart(final BasicChartSpec basicChart) {
        this.basicChart = basicChart;
        return this;
    }
    
    public BubbleChartSpec getBubbleChart() {
        return this.bubbleChart;
    }
    
    public ChartSpec setBubbleChart(final BubbleChartSpec bubbleChart) {
        this.bubbleChart = bubbleChart;
        return this;
    }
    
    public CandlestickChartSpec getCandlestickChart() {
        return this.candlestickChart;
    }
    
    public ChartSpec setCandlestickChart(final CandlestickChartSpec candlestickChart) {
        this.candlestickChart = candlestickChart;
        return this;
    }
    
    public String getFontName() {
        return this.fontName;
    }
    
    public ChartSpec setFontName(final String fontName) {
        this.fontName = fontName;
        return this;
    }
    
    public String getHiddenDimensionStrategy() {
        return this.hiddenDimensionStrategy;
    }
    
    public ChartSpec setHiddenDimensionStrategy(final String hiddenDimensionStrategy) {
        this.hiddenDimensionStrategy = hiddenDimensionStrategy;
        return this;
    }
    
    public HistogramChartSpec getHistogramChart() {
        return this.histogramChart;
    }
    
    public ChartSpec setHistogramChart(final HistogramChartSpec histogramChart) {
        this.histogramChart = histogramChart;
        return this;
    }
    
    public Boolean getMaximized() {
        return this.maximized;
    }
    
    public ChartSpec setMaximized(final Boolean maximized) {
        this.maximized = maximized;
        return this;
    }
    
    public OrgChartSpec getOrgChart() {
        return this.orgChart;
    }
    
    public ChartSpec setOrgChart(final OrgChartSpec orgChart) {
        this.orgChart = orgChart;
        return this;
    }
    
    public PieChartSpec getPieChart() {
        return this.pieChart;
    }
    
    public ChartSpec setPieChart(final PieChartSpec pieChart) {
        this.pieChart = pieChart;
        return this;
    }
    
    public String getSubtitle() {
        return this.subtitle;
    }
    
    public ChartSpec setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
        return this;
    }
    
    public TextFormat getSubtitleTextFormat() {
        return this.subtitleTextFormat;
    }
    
    public ChartSpec setSubtitleTextFormat(final TextFormat subtitleTextFormat) {
        this.subtitleTextFormat = subtitleTextFormat;
        return this;
    }
    
    public TextPosition getSubtitleTextPosition() {
        return this.subtitleTextPosition;
    }
    
    public ChartSpec setSubtitleTextPosition(final TextPosition subtitleTextPosition) {
        this.subtitleTextPosition = subtitleTextPosition;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public ChartSpec setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    public TextFormat getTitleTextFormat() {
        return this.titleTextFormat;
    }
    
    public ChartSpec setTitleTextFormat(final TextFormat titleTextFormat) {
        this.titleTextFormat = titleTextFormat;
        return this;
    }
    
    public TextPosition getTitleTextPosition() {
        return this.titleTextPosition;
    }
    
    public ChartSpec setTitleTextPosition(final TextPosition titleTextPosition) {
        this.titleTextPosition = titleTextPosition;
        return this;
    }
    
    public TreemapChartSpec getTreemapChart() {
        return this.treemapChart;
    }
    
    public ChartSpec setTreemapChart(final TreemapChartSpec treemapChart) {
        this.treemapChart = treemapChart;
        return this;
    }
    
    public WaterfallChartSpec getWaterfallChart() {
        return this.waterfallChart;
    }
    
    public ChartSpec setWaterfallChart(final WaterfallChartSpec waterfallChart) {
        this.waterfallChart = waterfallChart;
        return this;
    }
    
    @Override
    public ChartSpec set(final String a1, final Object a2) {
        return (ChartSpec)super.set(a1, a2);
    }
    
    @Override
    public ChartSpec clone() {
        return (ChartSpec)super.clone();
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
