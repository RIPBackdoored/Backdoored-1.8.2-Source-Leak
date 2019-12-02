package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class WaterfallChartSeries extends GenericJson
{
    @Key
    private List<WaterfallChartCustomSubtotal> customSubtotals;
    @Key
    private ChartData data;
    @Key
    private Boolean hideTrailingSubtotal;
    @Key
    private WaterfallChartColumnStyle negativeColumnsStyle;
    @Key
    private WaterfallChartColumnStyle positiveColumnsStyle;
    @Key
    private WaterfallChartColumnStyle subtotalColumnsStyle;
    
    public WaterfallChartSeries() {
        super();
    }
    
    public List<WaterfallChartCustomSubtotal> getCustomSubtotals() {
        return this.customSubtotals;
    }
    
    public WaterfallChartSeries setCustomSubtotals(final List<WaterfallChartCustomSubtotal> customSubtotals) {
        this.customSubtotals = customSubtotals;
        return this;
    }
    
    public ChartData getData() {
        return this.data;
    }
    
    public WaterfallChartSeries setData(final ChartData data) {
        this.data = data;
        return this;
    }
    
    public Boolean getHideTrailingSubtotal() {
        return this.hideTrailingSubtotal;
    }
    
    public WaterfallChartSeries setHideTrailingSubtotal(final Boolean hideTrailingSubtotal) {
        this.hideTrailingSubtotal = hideTrailingSubtotal;
        return this;
    }
    
    public WaterfallChartColumnStyle getNegativeColumnsStyle() {
        return this.negativeColumnsStyle;
    }
    
    public WaterfallChartSeries setNegativeColumnsStyle(final WaterfallChartColumnStyle negativeColumnsStyle) {
        this.negativeColumnsStyle = negativeColumnsStyle;
        return this;
    }
    
    public WaterfallChartColumnStyle getPositiveColumnsStyle() {
        return this.positiveColumnsStyle;
    }
    
    public WaterfallChartSeries setPositiveColumnsStyle(final WaterfallChartColumnStyle positiveColumnsStyle) {
        this.positiveColumnsStyle = positiveColumnsStyle;
        return this;
    }
    
    public WaterfallChartColumnStyle getSubtotalColumnsStyle() {
        return this.subtotalColumnsStyle;
    }
    
    public WaterfallChartSeries setSubtotalColumnsStyle(final WaterfallChartColumnStyle subtotalColumnsStyle) {
        this.subtotalColumnsStyle = subtotalColumnsStyle;
        return this;
    }
    
    @Override
    public WaterfallChartSeries set(final String a1, final Object a2) {
        return (WaterfallChartSeries)super.set(a1, a2);
    }
    
    @Override
    public WaterfallChartSeries clone() {
        return (WaterfallChartSeries)super.clone();
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
