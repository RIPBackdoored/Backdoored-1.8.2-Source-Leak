package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class ConditionalFormatRule extends GenericJson
{
    @Key
    private BooleanRule booleanRule;
    @Key
    private GradientRule gradientRule;
    @Key
    private List<GridRange> ranges;
    
    public ConditionalFormatRule() {
        super();
    }
    
    public BooleanRule getBooleanRule() {
        return this.booleanRule;
    }
    
    public ConditionalFormatRule setBooleanRule(final BooleanRule booleanRule) {
        this.booleanRule = booleanRule;
        return this;
    }
    
    public GradientRule getGradientRule() {
        return this.gradientRule;
    }
    
    public ConditionalFormatRule setGradientRule(final GradientRule gradientRule) {
        this.gradientRule = gradientRule;
        return this;
    }
    
    public List<GridRange> getRanges() {
        return this.ranges;
    }
    
    public ConditionalFormatRule setRanges(final List<GridRange> ranges) {
        this.ranges = ranges;
        return this;
    }
    
    @Override
    public ConditionalFormatRule set(final String a1, final Object a2) {
        return (ConditionalFormatRule)super.set(a1, a2);
    }
    
    @Override
    public ConditionalFormatRule clone() {
        return (ConditionalFormatRule)super.clone();
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
