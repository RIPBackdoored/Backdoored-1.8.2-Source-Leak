package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class TextFormat extends GenericJson
{
    @Key
    private Boolean bold;
    @Key
    private String fontFamily;
    @Key
    private Integer fontSize;
    @Key
    private Color foregroundColor;
    @Key
    private Boolean italic;
    @Key
    private Boolean strikethrough;
    @Key
    private Boolean underline;
    
    public TextFormat() {
        super();
    }
    
    public Boolean getBold() {
        return this.bold;
    }
    
    public TextFormat setBold(final Boolean bold) {
        this.bold = bold;
        return this;
    }
    
    public String getFontFamily() {
        return this.fontFamily;
    }
    
    public TextFormat setFontFamily(final String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }
    
    public Integer getFontSize() {
        return this.fontSize;
    }
    
    public TextFormat setFontSize(final Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }
    
    public Color getForegroundColor() {
        return this.foregroundColor;
    }
    
    public TextFormat setForegroundColor(final Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        return this;
    }
    
    public Boolean getItalic() {
        return this.italic;
    }
    
    public TextFormat setItalic(final Boolean italic) {
        this.italic = italic;
        return this;
    }
    
    public Boolean getStrikethrough() {
        return this.strikethrough;
    }
    
    public TextFormat setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }
    
    public Boolean getUnderline() {
        return this.underline;
    }
    
    public TextFormat setUnderline(final Boolean underline) {
        this.underline = underline;
        return this;
    }
    
    @Override
    public TextFormat set(final String a1, final Object a2) {
        return (TextFormat)super.set(a1, a2);
    }
    
    @Override
    public TextFormat clone() {
        return (TextFormat)super.clone();
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
