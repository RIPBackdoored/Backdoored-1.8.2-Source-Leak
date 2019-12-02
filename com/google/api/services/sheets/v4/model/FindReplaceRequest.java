package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class FindReplaceRequest extends GenericJson
{
    @Key
    private Boolean allSheets;
    @Key
    private String find;
    @Key
    private Boolean includeFormulas;
    @Key
    private Boolean matchCase;
    @Key
    private Boolean matchEntireCell;
    @Key
    private GridRange range;
    @Key
    private String replacement;
    @Key
    private Boolean searchByRegex;
    @Key
    private Integer sheetId;
    
    public FindReplaceRequest() {
        super();
    }
    
    public Boolean getAllSheets() {
        return this.allSheets;
    }
    
    public FindReplaceRequest setAllSheets(final Boolean allSheets) {
        this.allSheets = allSheets;
        return this;
    }
    
    public String getFind() {
        return this.find;
    }
    
    public FindReplaceRequest setFind(final String find) {
        this.find = find;
        return this;
    }
    
    public Boolean getIncludeFormulas() {
        return this.includeFormulas;
    }
    
    public FindReplaceRequest setIncludeFormulas(final Boolean includeFormulas) {
        this.includeFormulas = includeFormulas;
        return this;
    }
    
    public Boolean getMatchCase() {
        return this.matchCase;
    }
    
    public FindReplaceRequest setMatchCase(final Boolean matchCase) {
        this.matchCase = matchCase;
        return this;
    }
    
    public Boolean getMatchEntireCell() {
        return this.matchEntireCell;
    }
    
    public FindReplaceRequest setMatchEntireCell(final Boolean matchEntireCell) {
        this.matchEntireCell = matchEntireCell;
        return this;
    }
    
    public GridRange getRange() {
        return this.range;
    }
    
    public FindReplaceRequest setRange(final GridRange range) {
        this.range = range;
        return this;
    }
    
    public String getReplacement() {
        return this.replacement;
    }
    
    public FindReplaceRequest setReplacement(final String replacement) {
        this.replacement = replacement;
        return this;
    }
    
    public Boolean getSearchByRegex() {
        return this.searchByRegex;
    }
    
    public FindReplaceRequest setSearchByRegex(final Boolean searchByRegex) {
        this.searchByRegex = searchByRegex;
        return this;
    }
    
    public Integer getSheetId() {
        return this.sheetId;
    }
    
    public FindReplaceRequest setSheetId(final Integer sheetId) {
        this.sheetId = sheetId;
        return this;
    }
    
    @Override
    public FindReplaceRequest set(final String a1, final Object a2) {
        return (FindReplaceRequest)super.set(a1, a2);
    }
    
    @Override
    public FindReplaceRequest clone() {
        return (FindReplaceRequest)super.clone();
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
