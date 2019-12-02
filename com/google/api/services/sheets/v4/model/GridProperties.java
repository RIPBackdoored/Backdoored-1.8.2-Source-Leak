package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class GridProperties extends GenericJson
{
    @Key
    private Integer columnCount;
    @Key
    private Boolean columnGroupControlAfter;
    @Key
    private Integer frozenColumnCount;
    @Key
    private Integer frozenRowCount;
    @Key
    private Boolean hideGridlines;
    @Key
    private Integer rowCount;
    @Key
    private Boolean rowGroupControlAfter;
    
    public GridProperties() {
        super();
    }
    
    public Integer getColumnCount() {
        return this.columnCount;
    }
    
    public GridProperties setColumnCount(final Integer columnCount) {
        this.columnCount = columnCount;
        return this;
    }
    
    public Boolean getColumnGroupControlAfter() {
        return this.columnGroupControlAfter;
    }
    
    public GridProperties setColumnGroupControlAfter(final Boolean columnGroupControlAfter) {
        this.columnGroupControlAfter = columnGroupControlAfter;
        return this;
    }
    
    public Integer getFrozenColumnCount() {
        return this.frozenColumnCount;
    }
    
    public GridProperties setFrozenColumnCount(final Integer frozenColumnCount) {
        this.frozenColumnCount = frozenColumnCount;
        return this;
    }
    
    public Integer getFrozenRowCount() {
        return this.frozenRowCount;
    }
    
    public GridProperties setFrozenRowCount(final Integer frozenRowCount) {
        this.frozenRowCount = frozenRowCount;
        return this;
    }
    
    public Boolean getHideGridlines() {
        return this.hideGridlines;
    }
    
    public GridProperties setHideGridlines(final Boolean hideGridlines) {
        this.hideGridlines = hideGridlines;
        return this;
    }
    
    public Integer getRowCount() {
        return this.rowCount;
    }
    
    public GridProperties setRowCount(final Integer rowCount) {
        this.rowCount = rowCount;
        return this;
    }
    
    public Boolean getRowGroupControlAfter() {
        return this.rowGroupControlAfter;
    }
    
    public GridProperties setRowGroupControlAfter(final Boolean rowGroupControlAfter) {
        this.rowGroupControlAfter = rowGroupControlAfter;
        return this;
    }
    
    @Override
    public GridProperties set(final String a1, final Object a2) {
        return (GridProperties)super.set(a1, a2);
    }
    
    @Override
    public GridProperties clone() {
        return (GridProperties)super.clone();
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
