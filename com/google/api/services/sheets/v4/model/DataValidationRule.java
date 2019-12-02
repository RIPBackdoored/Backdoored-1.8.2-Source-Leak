package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DataValidationRule extends GenericJson
{
    @Key
    private BooleanCondition condition;
    @Key
    private String inputMessage;
    @Key
    private Boolean showCustomUi;
    @Key
    private Boolean strict;
    
    public DataValidationRule() {
        super();
    }
    
    public BooleanCondition getCondition() {
        return this.condition;
    }
    
    public DataValidationRule setCondition(final BooleanCondition condition) {
        this.condition = condition;
        return this;
    }
    
    public String getInputMessage() {
        return this.inputMessage;
    }
    
    public DataValidationRule setInputMessage(final String inputMessage) {
        this.inputMessage = inputMessage;
        return this;
    }
    
    public Boolean getShowCustomUi() {
        return this.showCustomUi;
    }
    
    public DataValidationRule setShowCustomUi(final Boolean showCustomUi) {
        this.showCustomUi = showCustomUi;
        return this;
    }
    
    public Boolean getStrict() {
        return this.strict;
    }
    
    public DataValidationRule setStrict(final Boolean strict) {
        this.strict = strict;
        return this;
    }
    
    @Override
    public DataValidationRule set(final String a1, final Object a2) {
        return (DataValidationRule)super.set(a1, a2);
    }
    
    @Override
    public DataValidationRule clone() {
        return (DataValidationRule)super.clone();
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
