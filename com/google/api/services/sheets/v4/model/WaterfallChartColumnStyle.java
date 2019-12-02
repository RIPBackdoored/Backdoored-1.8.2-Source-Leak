package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class WaterfallChartColumnStyle extends GenericJson
{
    @Key
    private Color color;
    @Key
    private String label;
    
    public WaterfallChartColumnStyle() {
        super();
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public WaterfallChartColumnStyle setColor(final Color color) {
        this.color = color;
        return this;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public WaterfallChartColumnStyle setLabel(final String label) {
        this.label = label;
        return this;
    }
    
    @Override
    public WaterfallChartColumnStyle set(final String a1, final Object a2) {
        return (WaterfallChartColumnStyle)super.set(a1, a2);
    }
    
    @Override
    public WaterfallChartColumnStyle clone() {
        return (WaterfallChartColumnStyle)super.clone();
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
