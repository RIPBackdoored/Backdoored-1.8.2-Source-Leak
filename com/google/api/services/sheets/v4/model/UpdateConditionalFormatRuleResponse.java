package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class UpdateConditionalFormatRuleResponse extends GenericJson
{
    @Key
    private Integer newIndex;
    @Key
    private ConditionalFormatRule newRule;
    @Key
    private Integer oldIndex;
    @Key
    private ConditionalFormatRule oldRule;
    
    public UpdateConditionalFormatRuleResponse() {
        super();
    }
    
    public Integer getNewIndex() {
        return this.newIndex;
    }
    
    public UpdateConditionalFormatRuleResponse setNewIndex(final Integer newIndex) {
        this.newIndex = newIndex;
        return this;
    }
    
    public ConditionalFormatRule getNewRule() {
        return this.newRule;
    }
    
    public UpdateConditionalFormatRuleResponse setNewRule(final ConditionalFormatRule newRule) {
        this.newRule = newRule;
        return this;
    }
    
    public Integer getOldIndex() {
        return this.oldIndex;
    }
    
    public UpdateConditionalFormatRuleResponse setOldIndex(final Integer oldIndex) {
        this.oldIndex = oldIndex;
        return this;
    }
    
    public ConditionalFormatRule getOldRule() {
        return this.oldRule;
    }
    
    public UpdateConditionalFormatRuleResponse setOldRule(final ConditionalFormatRule oldRule) {
        this.oldRule = oldRule;
        return this;
    }
    
    @Override
    public UpdateConditionalFormatRuleResponse set(final String a1, final Object a2) {
        return (UpdateConditionalFormatRuleResponse)super.set(a1, a2);
    }
    
    @Override
    public UpdateConditionalFormatRuleResponse clone() {
        return (UpdateConditionalFormatRuleResponse)super.clone();
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
