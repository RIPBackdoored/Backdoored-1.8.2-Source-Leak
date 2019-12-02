package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class BooleanRule extends GenericJson
{
    @Key
    private BooleanCondition condition;
    @Key
    private CellFormat format;
    
    public BooleanRule() {
        super();
    }
    
    public BooleanCondition getCondition() {
        return this.condition;
    }
    
    public BooleanRule setCondition(final BooleanCondition condition) {
        this.condition = condition;
        return this;
    }
    
    public CellFormat getFormat() {
        return this.format;
    }
    
    public BooleanRule setFormat(final CellFormat format) {
        this.format = format;
        return this;
    }
    
    @Override
    public BooleanRule set(final String a1, final Object a2) {
        return (BooleanRule)super.set(a1, a2);
    }
    
    @Override
    public BooleanRule clone() {
        return (BooleanRule)super.clone();
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
