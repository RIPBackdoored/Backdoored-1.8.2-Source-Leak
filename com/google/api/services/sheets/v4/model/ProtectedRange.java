package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class ProtectedRange extends GenericJson
{
    @Key
    private String description;
    @Key
    private Editors editors;
    @Key
    private String namedRangeId;
    @Key
    private Integer protectedRangeId;
    @Key
    private GridRange range;
    @Key
    private Boolean requestingUserCanEdit;
    @Key
    private List<GridRange> unprotectedRanges;
    @Key
    private Boolean warningOnly;
    
    public ProtectedRange() {
        super();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public ProtectedRange setDescription(final String description) {
        this.description = description;
        return this;
    }
    
    public Editors getEditors() {
        return this.editors;
    }
    
    public ProtectedRange setEditors(final Editors editors) {
        this.editors = editors;
        return this;
    }
    
    public String getNamedRangeId() {
        return this.namedRangeId;
    }
    
    public ProtectedRange setNamedRangeId(final String namedRangeId) {
        this.namedRangeId = namedRangeId;
        return this;
    }
    
    public Integer getProtectedRangeId() {
        return this.protectedRangeId;
    }
    
    public ProtectedRange setProtectedRangeId(final Integer protectedRangeId) {
        this.protectedRangeId = protectedRangeId;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public ProtectedRange setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public Boolean getRequestingUserCanEdit() {
        return this.requestingUserCanEdit;
    }
    
    public ProtectedRange setRequestingUserCanEdit(final Boolean requestingUserCanEdit) {
        this.requestingUserCanEdit = requestingUserCanEdit;
        return this;
    }
    
    public List<GridRange> getUnprotectedRanges() {
        return this.unprotectedRanges;
    }
    
    public ProtectedRange setUnprotectedRanges(final List<GridRange> unprotectedRanges) {
        this.unprotectedRanges = unprotectedRanges;
        return this;
    }
    
    public Boolean getWarningOnly() {
        return this.warningOnly;
    }
    
    public ProtectedRange setWarningOnly(final Boolean warningOnly) {
        this.warningOnly = warningOnly;
        return this;
    }
    
    @Override
    public ProtectedRange set(final String a1, final Object a2) {
        return (ProtectedRange)super.set(a1, a2);
    }
    
    @Override
    public ProtectedRange clone() {
        return (ProtectedRange)super.clone();
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
        Data.nullOf(GridRange.class);
    }
}
