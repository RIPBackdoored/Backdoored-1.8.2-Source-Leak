package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class SetDataValidationRequest extends GenericJson
{
    @Key
    private GridRange range;
    @Key
    private DataValidationRule rule;
    
    public SetDataValidationRequest() {
        super();
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public SetDataValidationRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public DataValidationRule getRule() {
        return this.rule;
    }
    
    public SetDataValidationRequest setRule(final DataValidationRule rule) {
        this.rule = rule;
        return this;
    }
    
    @Override
    public SetDataValidationRequest set(final String a1, final Object a2) {
        return (SetDataValidationRequest)super.set(a1, a2);
    }
    
    @Override
    public SetDataValidationRequest clone() {
        return (SetDataValidationRequest)super.clone();
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
