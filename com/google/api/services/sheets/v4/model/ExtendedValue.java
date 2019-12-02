package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class ExtendedValue extends GenericJson
{
    @Key
    private Boolean boolValue;
    @Key
    private ErrorValue errorValue;
    @Key
    private String formulaValue;
    @Key
    private Double numberValue;
    @Key
    private String stringValue;
    
    public ExtendedValue() {
        super();
    }
    
    public Boolean getBoolValue() {
        return this.boolValue;
    }
    
    public ExtendedValue setBoolValue(final Boolean boolValue) {
        this.boolValue = boolValue;
        return this;
    }
    
    public ErrorValue getErrorValue() {
        return this.errorValue;
    }
    
    public ExtendedValue setErrorValue(final ErrorValue errorValue) {
        this.errorValue = errorValue;
        return this;
    }
    
    public String getFormulaValue() {
        return this.formulaValue;
    }
    
    public ExtendedValue setFormulaValue(final String formulaValue) {
        this.formulaValue = formulaValue;
        return this;
    }
    
    public Double getNumberValue() {
        return this.numberValue;
    }
    
    public ExtendedValue setNumberValue(final Double numberValue) {
        this.numberValue = numberValue;
        return this;
    }
    
    public String getStringValue() {
        return this.stringValue;
    }
    
    public ExtendedValue setStringValue(final String stringValue) {
        this.stringValue = stringValue;
        return this;
    }
    
    @Override
    public ExtendedValue set(final String a1, final Object a2) {
        return (ExtendedValue)super.set(a1, a2);
    }
    
    @Override
    public ExtendedValue clone() {
        return (ExtendedValue)super.clone();
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
