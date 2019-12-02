package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class PivotGroupRule extends GenericJson
{
    @Key
    private DateTimeRule dateTimeRule;
    @Key
    private HistogramRule histogramRule;
    @Key
    private ManualRule manualRule;
    
    public PivotGroupRule() {
        super();
    }
    
    public DateTimeRule getDateTimeRule() {
        return this.dateTimeRule;
    }
    
    public PivotGroupRule setDateTimeRule(final DateTimeRule dateTimeRule) {
        this.dateTimeRule = dateTimeRule;
        return this;
    }
    
    public HistogramRule getHistogramRule() {
        return this.histogramRule;
    }
    
    public PivotGroupRule setHistogramRule(final HistogramRule histogramRule) {
        this.histogramRule = histogramRule;
        return this;
    }
    
    public ManualRule getManualRule() {
        return this.manualRule;
    }
    
    public PivotGroupRule setManualRule(final ManualRule manualRule) {
        this.manualRule = manualRule;
        return this;
    }
    
    @Override
    public PivotGroupRule set(final String a1, final Object a2) {
        return (PivotGroupRule)super.set(a1, a2);
    }
    
    @Override
    public PivotGroupRule clone() {
        return (PivotGroupRule)super.clone();
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
