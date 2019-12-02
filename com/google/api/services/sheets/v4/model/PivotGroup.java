package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class PivotGroup extends GenericJson
{
    @Key
    private PivotGroupRule groupRule;
    @Key
    private String label;
    @Key
    private Boolean repeatHeadings;
    @Key
    private Boolean showTotals;
    @Key
    private String sortOrder;
    @Key
    private Integer sourceColumnOffset;
    @Key
    private PivotGroupSortValueBucket valueBucket;
    @Key
    private List<PivotGroupValueMetadata> valueMetadata;
    
    public PivotGroup() {
        super();
    }
    
    public PivotGroupRule getGroupRule() {
        return this.groupRule;
    }
    
    public PivotGroup setGroupRule(final PivotGroupRule groupRule) {
        this.groupRule = groupRule;
        return this;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public PivotGroup setLabel(final String label) {
        this.label = label;
        return this;
    }
    
    public Boolean getRepeatHeadings() {
        return this.repeatHeadings;
    }
    
    public PivotGroup setRepeatHeadings(final Boolean repeatHeadings) {
        this.repeatHeadings = repeatHeadings;
        return this;
    }
    
    public Boolean getShowTotals() {
        return this.showTotals;
    }
    
    public PivotGroup setShowTotals(final Boolean showTotals) {
        this.showTotals = showTotals;
        return this;
    }
    
    public String getSortOrder() {
        return this.sortOrder;
    }
    
    public PivotGroup setSortOrder(final String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }
    
    public Integer getSourceColumnOffset() {
        return this.sourceColumnOffset;
    }
    
    public PivotGroup setSourceColumnOffset(final Integer sourceColumnOffset) {
        this.sourceColumnOffset = sourceColumnOffset;
        return this;
    }
    
    public PivotGroupSortValueBucket getValueBucket() {
        return this.valueBucket;
    }
    
    public PivotGroup setValueBucket(final PivotGroupSortValueBucket valueBucket) {
        this.valueBucket = valueBucket;
        return this;
    }
    
    public List<PivotGroupValueMetadata> getValueMetadata() {
        return this.valueMetadata;
    }
    
    public PivotGroup setValueMetadata(final List<PivotGroupValueMetadata> valueMetadata) {
        this.valueMetadata = valueMetadata;
        return this;
    }
    
    @Override
    public PivotGroup set(final String a1, final Object a2) {
        return (PivotGroup)super.set(a1, a2);
    }
    
    @Override
    public PivotGroup clone() {
        return (PivotGroup)super.clone();
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
        Data.nullOf(PivotGroupValueMetadata.class);
    }
}
