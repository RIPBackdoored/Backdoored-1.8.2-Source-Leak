package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateConditionalFormatRuleRequest extends GenericJson
{
    @Key
    private Integer index;
    @Key
    private Integer newIndex;
    @Key
    private ConditionalFormatRule rule;
    @Key
    private Integer sheetId;
    
    public UpdateConditionalFormatRuleRequest() {
        super();
    }
    
    public Integer getIndex() {
        return this.index;
    }
    
    public UpdateConditionalFormatRuleRequest setIndex(final Integer index) {
        this.index = index;
        return this;
    }
    
    public Integer getNewIndex() {
        return this.newIndex;
    }
    
    public UpdateConditionalFormatRuleRequest setNewIndex(final Integer newIndex) {
        this.newIndex = newIndex;
        return this;
    }
    
    public ConditionalFormatRule getRule() {
        return this.rule;
    }
    
    public UpdateConditionalFormatRuleRequest setRule(final ConditionalFormatRule rule) {
        this.rule = rule;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public UpdateConditionalFormatRuleRequest setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public UpdateConditionalFormatRuleRequest set(final String a1, final Object a2) {
        return (UpdateConditionalFormatRuleRequest)super.set(a1, a2);
    }
    
    @Override
    public UpdateConditionalFormatRuleRequest clone() {
        return (UpdateConditionalFormatRuleRequest)super.clone();
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
