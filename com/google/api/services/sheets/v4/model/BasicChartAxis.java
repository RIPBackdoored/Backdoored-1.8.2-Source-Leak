package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class BasicChartAxis extends GenericJson
{
    @Key
    private TextFormat format;
    @Key
    private String position;
    @Key
    private String title;
    @Key
    private TextPosition titleTextPosition;
    
    public BasicChartAxis() {
        super();
    }
    
    public TextFormat getFormat() {
        return this.format;
    }
    
    public BasicChartAxis setFormat(final TextFormat format) {
        this.format = format;
        return this;
    }
    
    public String getPosition() {
        return this.position;
    }
    
    public BasicChartAxis setPosition(final String position) {
        this.position = position;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public BasicChartAxis setTitle(final String title) {
        this.title = title;
        return this;
    }
    
    public TextPosition getTitleTextPosition() {
        return this.titleTextPosition;
    }
    
    public BasicChartAxis setTitleTextPosition(final TextPosition titleTextPosition) {
        this.titleTextPosition = titleTextPosition;
        return this;
    }
    
    @Override
    public BasicChartAxis set(final String a1, final Object a2) {
        return (BasicChartAxis)super.set(a1, a2);
    }
    
    @Override
    public BasicChartAxis clone() {
        return (BasicChartAxis)super.clone();
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
