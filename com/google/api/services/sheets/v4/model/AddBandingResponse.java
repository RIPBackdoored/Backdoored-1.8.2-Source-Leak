package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddBandingResponse extends GenericJson
{
    @Key
    private BandedRange bandedRange;
    
    public AddBandingResponse() {
        super();
    }
    
    public BandedRange getBandedRange() {
        return this.bandedRange;
    }
    
    public AddBandingResponse setBandedRange(final BandedRange bandedRange) {
        this.bandedRange = bandedRange;
        return this;
    }
    
    @Override
    public AddBandingResponse set(final String a1, final Object a2) {
        return (AddBandingResponse)super.set(a1, a2);
    }
    
    @Override
    public AddBandingResponse clone() {
        return (AddBandingResponse)super.clone();
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
