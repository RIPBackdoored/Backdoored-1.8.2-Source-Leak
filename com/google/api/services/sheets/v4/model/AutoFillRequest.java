package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AutoFillRequest extends GenericJson
{
    @Key
    private GridRange range;
    @Key
    private SourceAndDestination sourceAndDestination;
    @Key
    private Boolean useAlternateSeries;
    
    public AutoFillRequest() {
        super();
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public AutoFillRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public SourceAndDestination getSourceAndDestination() {
        return this.sourceAndDestination;
    }
    
    public AutoFillRequest setSourceAndDestination(final SourceAndDestination sourceAndDestination) {
        this.sourceAndDestination = sourceAndDestination;
        return this;
    }
    
    public Boolean getUseAlternateSeries() {
        return this.useAlternateSeries;
    }
    
    public AutoFillRequest setUseAlternateSeries(final Boolean useAlternateSeries) {
        this.useAlternateSeries = useAlternateSeries;
        return this;
    }
    
    @Override
    public AutoFillRequest set(final String a1, final Object a2) {
        return (AutoFillRequest)super.set(a1, a2);
    }
    
    @Override
    public AutoFillRequest clone() {
        return (AutoFillRequest)super.clone();
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
