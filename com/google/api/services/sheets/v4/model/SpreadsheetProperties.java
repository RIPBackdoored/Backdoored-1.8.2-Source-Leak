package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class SpreadsheetProperties extends GenericJson
{
    @Key
    private String autoRecalc;
    @Key
    private CellFormat defaultFormat;
    @Key
    private IterativeCalculationSettings iterativeCalculationSettings;
    @Key
    private String locale;
    @Key
    private String timeZone;
    @Key
    private String title;
    
    public SpreadsheetProperties() {
        super();
    }
    
    public String getAutoRecalc() {
        return this.autoRecalc;
    }
    
    public SpreadsheetProperties setAutoRecalc(final String autoRecalc) {
        this.autoRecalc = autoRecalc;
        return this;
    }
    
    public CellFormat getDefaultFormat() {
        return this.defaultFormat;
    }
    
    public SpreadsheetProperties setDefaultFormat(final CellFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
        return this;
    }
    
    public IterativeCalculationSettings getIterativeCalculationSettings() {
        return this.iterativeCalculationSettings;
    }
    
    public SpreadsheetProperties setIterativeCalculationSettings(final IterativeCalculationSettings iterativeCalculationSettings) {
        this.iterativeCalculationSettings = iterativeCalculationSettings;
        return this;
    }
    
    public String getLocale() {
        return this.locale;
    }
    
    public SpreadsheetProperties setLocale(final String locale) {
        this.locale = locale;
        return this;
    }
    
    public String getTimeZone() {
        return this.timeZone;
    }
    
    public SpreadsheetProperties setTimeZone(final String timeZone) {
        this.timeZone = timeZone;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public SpreadsheetProperties setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    @Override
    public SpreadsheetProperties set(final String a1, final Object a2) {
        return (SpreadsheetProperties)super.set(a1, a2);
    }
    
    @Override
    public SpreadsheetProperties clone() {
        return (SpreadsheetProperties)super.clone();
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
