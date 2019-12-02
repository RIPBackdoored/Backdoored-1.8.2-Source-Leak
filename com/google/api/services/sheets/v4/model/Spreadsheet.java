package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class Spreadsheet extends GenericJson
{
    @Key
    private List<DeveloperMetadata> developerMetadata;
    @Key
    private List<NamedRange> namedRanges;
    @Key
    private SpreadsheetProperties properties;
    @Key
    private List<Sheet> sheets;
    @Key
    private String spreadsheetId;
    @Key
    private String spreadsheetUrl;
    
    public Spreadsheet() {
        super();
    }
    
    public List<DeveloperMetadata> getDeveloperMetadata() {
        return this.developerMetadata;
    }
    
    public Spreadsheet setDeveloperMetadata(final List<DeveloperMetadata> developerMetadata) {
        this.developerMetadata = developerMetadata;
        return this;
    }
    
    public List<NamedRange> getNamedRanges() {
        return this.namedRanges;
    }
    
    public Spreadsheet setNamedRanges(final List<NamedRange> namedRanges) {
        this.namedRanges = namedRanges;
        return this;
    }
    
    public SpreadsheetProperties getProperties() {
        return this.properties;
    }
    
    public Spreadsheet setProperties(final SpreadsheetProperties properties) {
        this.properties = properties;
        return this;
    }
    
    public List<Sheet> getSheets() {
        return this.sheets;
    }
    
    public Spreadsheet setSheets(final List<Sheet> sheets) {
        this.sheets = sheets;
        return this;
    }
    
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }
    
    public Spreadsheet setSpreadsheetId(final String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        return this;
    }
    
    public String getSpreadsheetUrl() {
        return this.spreadsheetUrl;
    }
    
    public Spreadsheet setSpreadsheetUrl(final String spreadsheetUrl) {
        this.spreadsheetUrl = spreadsheetUrl;
        return this;
    }
    
    @Override
    public Spreadsheet set(final String a1, final Object a2) {
        return (Spreadsheet)super.set(a1, a2);
    }
    
    @Override
    public Spreadsheet clone() {
        return (Spreadsheet)super.clone();
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
        Data.nullOf(DeveloperMetadata.class);
        Data.nullOf(Sheet.class);
    }
}
