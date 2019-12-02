package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class BandingProperties extends GenericJson
{
    @Key
    private Color firstBandColor;
    @Key
    private Color footerColor;
    @Key
    private Color headerColor;
    @Key
    private Color secondBandColor;
    
    public BandingProperties() {
        super();
    }
    
    public Color getFirstBandColor() {
        return this.firstBandColor;
    }
    
    public BandingProperties setFirstBandColor(final Color firstBandColor) {
        this.firstBandColor = firstBandColor;
        return this;
    }
    
    public Color getFooterColor() {
        return this.footerColor;
    }
    
    public BandingProperties setFooterColor(final Color footerColor) {
        this.footerColor = footerColor;
        return this;
    }
    
    public Color getHeaderColor() {
        return this.headerColor;
    }
    
    public BandingProperties setHeaderColor(final Color headerColor) {
        this.headerColor = headerColor;
        return this;
    }
    
    public Color getSecondBandColor() {
        return this.secondBandColor;
    }
    
    public BandingProperties setSecondBandColor(final Color secondBandColor) {
        this.secondBandColor = secondBandColor;
        return this;
    }
    
    @Override
    public BandingProperties set(final String a1, final Object a2) {
        return (BandingProperties)super.set(a1, a2);
    }
    
    @Override
    public BandingProperties clone() {
        return (BandingProperties)super.clone();
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
