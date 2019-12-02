package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class BooleanCondition extends GenericJson
{
    @Key
    private String type;
    @Key
    private List<ConditionValue> values;
    
    public BooleanCondition() {
        super();
    }
    
    public String getType() {
        return this.type;
    }
    
    public BooleanCondition setType(final String type) {
        this.type = type;
        return this;
    }
    
    public List<ConditionValue> getValues() {
        return this.values;
    }
    
    public BooleanCondition setValues(final List<ConditionValue> values) {
        this.values = values;
        return this;
    }
    
    @Override
    public BooleanCondition set(final String a1, final Object a2) {
        return (BooleanCondition)super.set(a1, a2);
    }
    
    @Override
    public BooleanCondition clone() {
        return (BooleanCondition)super.clone();
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
