package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class ManualRuleGroup extends GenericJson
{
    @Key
    private ExtendedValue groupName;
    @Key
    private List<ExtendedValue> items;
    
    public ManualRuleGroup() {
        super();
    }
    
    public ExtendedValue getGroupName() {
        return this.groupName;
    }
    
    public ManualRuleGroup setGroupName(final ExtendedValue groupName) {
        this.groupName = groupName;
        return this;
    }
    
    public List<ExtendedValue> getItems() {
        return this.items;
    }
    
    public ManualRuleGroup setItems(final List<ExtendedValue> items) {
        this.items = items;
        return this;
    }
    
    @Override
    public ManualRuleGroup set(final String a1, final Object a2) {
        return (ManualRuleGroup)super.set(a1, a2);
    }
    
    @Override
    public ManualRuleGroup clone() {
        return (ManualRuleGroup)super.clone();
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
    
    static {
        Data.nullOf(ExtendedValue.class);
    }
}
