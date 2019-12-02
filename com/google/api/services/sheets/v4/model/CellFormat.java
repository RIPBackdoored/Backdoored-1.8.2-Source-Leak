package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class CellFormat extends GenericJson
{
    @Key
    private Color backgroundColor;
    @Key
    private Borders borders;
    @Key
    private String horizontalAlignment;
    @Key
    private String hyperlinkDisplayType;
    @Key
    private NumberFormat numberFormat;
    @Key
    private Padding padding;
    @Key
    private String textDirection;
    @Key
    private TextFormat textFormat;
    @Key
    private TextRotation textRotation;
    @Key
    private String verticalAlignment;
    @Key
    private String wrapStrategy;
    
    public CellFormat() {
        super();
    }
    
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
    
    public CellFormat setBackgroundColor(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public Borders getBorders() {
        return this.borders;
    }
    
    public CellFormat setBorders(final Borders borders) {
        this.borders = borders;
        return this;
    }
    
    public String getHorizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    public CellFormat setHorizontalAlignment(final String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }
    
    public String getHyperlinkDisplayType() {
        return this.hyperlinkDisplayType;
    }
    
    public CellFormat setHyperlinkDisplayType(final String hyperlinkDisplayType) {
        this.hyperlinkDisplayType = hyperlinkDisplayType;
        return this;
    }
    
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }
    
    public CellFormat setNumberFormat(final NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }
    
    public Padding getPadding() {
        return this.padding;
    }
    
    public CellFormat setPadding(final Padding padding) {
        this.padding = padding;
        return this;
    }
    
    public String getTextDirection() {
        return this.textDirection;
    }
    
    public CellFormat setTextDirection(final String textDirection) {
        this.textDirection = textDirection;
        return this;
    }
    
    public TextFormat getTextFormat() {
        return this.textFormat;
    }
    
    public CellFormat setTextFormat(final TextFormat textFormat) {
        this.textFormat = textFormat;
        return this;
    }
    
    public TextRotation getTextRotation() {
        return this.textRotation;
    }
    
    public CellFormat setTextRotation(final TextRotation textRotation) {
        this.textRotation = textRotation;
        return this;
    }
    
    public String getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    public CellFormat setVerticalAlignment(final String verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }
    
    public String getWrapStrategy() {
        return this.wrapStrategy;
    }
    
    public CellFormat setWrapStrategy(final String wrapStrategy) {
        this.wrapStrategy = wrapStrategy;
        return this;
    }
    
    @Override
    public CellFormat set(final String a1, final Object a2) {
        return (CellFormat)super.set(a1, a2);
    }
    
    @Override
    public CellFormat clone() {
        return (CellFormat)super.clone();
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
