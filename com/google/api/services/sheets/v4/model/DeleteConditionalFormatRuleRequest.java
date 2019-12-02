package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeleteConditionalFormatRuleRequest extends GenericJson
{
    @Key
    private Integer index;
    @Key
    private Integer sheetId;
    
    public DeleteConditionalFormatRuleRequest() {
        super();
    }
    
    public Integer getIndex() {
        return this.index;
    }
    
    public DeleteConditionalFormatRuleRequest setIndex(final Integer index) {
        this.index = index;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public DeleteConditionalFormatRuleRequest setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public DeleteConditionalFormatRuleRequest set(final String a1, final Object a2) {
        return (DeleteConditionalFormatRuleRequest)super.set(a1, a2);
    }
    
    @Override
    public DeleteConditionalFormatRuleRequest clone() {
        return (DeleteConditionalFormatRuleRequest)super.clone();
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
