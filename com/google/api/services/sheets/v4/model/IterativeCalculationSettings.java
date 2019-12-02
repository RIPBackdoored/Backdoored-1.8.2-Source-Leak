package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class IterativeCalculationSettings extends GenericJson
{
    @Key
    private Double convergenceThreshold;
    @Key
    private Integer maxIterations;
    
    public IterativeCalculationSettings() {
        super();
    }
    
    public Double getConvergenceThreshold() {
        return this.convergenceThreshold;
    }
    
    public IterativeCalculationSettings setConvergenceThreshold(final Double convergenceThreshold) {
        this.convergenceThreshold = convergenceThreshold;
        return this;
    }
    
    public Integer getMaxIterations() {
        return this.maxIterations;
    }
    
    public IterativeCalculationSettings setMaxIterations(final Integer maxIterations) {
        this.maxIterations = maxIterations;
        return this;
    }
    
    @Override
    public IterativeCalculationSettings set(final String a1, final Object a2) {
        return (IterativeCalculationSettings)super.set(a1, a2);
    }
    
    @Override
    public IterativeCalculationSettings clone() {
        return (IterativeCalculationSettings)super.clone();
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
