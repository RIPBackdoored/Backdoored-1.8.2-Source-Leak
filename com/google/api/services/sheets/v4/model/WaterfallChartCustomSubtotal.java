package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class WaterfallChartCustomSubtotal extends GenericJson
{
    @Key
    private Boolean dataIsSubtotal;
    @Key
    private String label;
    @Key
    private Integer subtotalIndex;
    
    public WaterfallChartCustomSubtotal() {
        super();
    }
    
    public Boolean getDataIsSubtotal() {
        return this.dataIsSubtotal;
    }
    
    public WaterfallChartCustomSubtotal setDataIsSubtotal(final Boolean dataIsSubtotal) {
        this.dataIsSubtotal = dataIsSubtotal;
        return this;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public WaterfallChartCustomSubtotal setLabel(final String label) {
        this.label = label;
        return this;
    }
    
    public Integer getSubtotalIndex() {
        return this.subtotalIndex;
    }
    
    public WaterfallChartCustomSubtotal setSubtotalIndex(final Integer subtotalIndex) {
        this.subtotalIndex = subtotalIndex;
        return this;
    }
    
    @Override
    public WaterfallChartCustomSubtotal set(final String a1, final Object a2) {
        return (WaterfallChartCustomSubtotal)super.set(a1, a2);
    }
    
    @Override
    public WaterfallChartCustomSubtotal clone() {
        return (WaterfallChartCustomSubtotal)super.clone();
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
