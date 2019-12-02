package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class PivotValue extends GenericJson
{
    @Key
    private String calculatedDisplayType;
    @Key
    private String formula;
    @Key
    private String name;
    @Key
    private Integer sourceColumnOffset;
    @Key
    private String summarizeFunction;
    
    public PivotValue() {
        super();
    }
    
    public String getCalculatedDisplayType() {
        return this.calculatedDisplayType;
    }
    
    public PivotValue setCalculatedDisplayType(final String calculatedDisplayType) {
        this.calculatedDisplayType = calculatedDisplayType;
        return this;
    }
    
    public String getFormula() {
        return this.formula;
    }
    
    public PivotValue setFormula(final String formula) {
        this.formula = formula;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public PivotValue setName(final String name) {
        this.name = name;
        return this;
    }
    
    public Integer getSourceColumnOffset() {
        return this.sourceColumnOffset;
    }
    
    public PivotValue setSourceColumnOffset(final Integer sourceColumnOffset) {
        this.sourceColumnOffset = sourceColumnOffset;
        return this;
    }
    
    public String getSummarizeFunction() {
        return this.summarizeFunction;
    }
    
    public PivotValue setSummarizeFunction(final String summarizeFunction) {
        this.summarizeFunction = summarizeFunction;
        return this;
    }
    
    @Override
    public PivotValue set(final String a1, final Object a2) {
        return (PivotValue)super.set(a1, a2);
    }
    
    @Override
    public PivotValue clone() {
        return (PivotValue)super.clone();
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
