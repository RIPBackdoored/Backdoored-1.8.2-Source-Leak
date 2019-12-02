package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class CellData extends GenericJson
{
    @Key
    private DataValidationRule dataValidation;
    @Key
    private CellFormat effectiveFormat;
    @Key
    private ExtendedValue effectiveValue;
    @Key
    private String formattedValue;
    @Key
    private String hyperlink;
    @Key
    private String note;
    @Key
    private PivotTable pivotTable;
    @Key
    private List<TextFormatRun> textFormatRuns;
    @Key
    private CellFormat userEnteredFormat;
    @Key
    private ExtendedValue userEnteredValue;
    
    public CellData() {
        super();
    }
    
    public DataValidationRule getDataValidation() {
        return this.dataValidation;
    }
    
    public CellData setDataValidation(final DataValidationRule dataValidation) {
        this.dataValidation = dataValidation;
        return this;
    }
    
    public CellFormat getEffectiveFormat() {
        return this.effectiveFormat;
    }
    
    public CellData setEffectiveFormat(final CellFormat effectiveFormat) {
        this.effectiveFormat = effectiveFormat;
        return this;
    }
    
    public ExtendedValue getEffectiveValue() {
        return this.effectiveValue;
    }
    
    public CellData setEffectiveValue(final ExtendedValue effectiveValue) {
        this.effectiveValue = effectiveValue;
        return this;
    }
    
    public String getFormattedValue() {
        return this.formattedValue;
    }
    
    public CellData setFormattedValue(final String formattedValue) {
        this.formattedValue = formattedValue;
        return this;
    }
    
    public String getHyperlink() {
        return this.hyperlink;
    }
    
    public CellData setHyperlink(final String hyperlink) {
        this.hyperlink = hyperlink;
        return this;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public CellData setNote(final String note) {
        this.note = note;
        return this;
    }
    
    public PivotTable getPivotTable() {
        return this.pivotTable;
    }
    
    public CellData setPivotTable(final PivotTable pivotTable) {
        this.pivotTable = pivotTable;
        return this;
    }
    
    public List<TextFormatRun> getTextFormatRuns() {
        return this.textFormatRuns;
    }
    
    public CellData setTextFormatRuns(final List<TextFormatRun> textFormatRuns) {
        this.textFormatRuns = textFormatRuns;
        return this;
    }
    
    public CellFormat getUserEnteredFormat() {
        return this.userEnteredFormat;
    }
    
    public CellData setUserEnteredFormat(final CellFormat userEnteredFormat) {
        this.userEnteredFormat = userEnteredFormat;
        return this;
    }
    
    public ExtendedValue getUserEnteredValue() {
        return this.userEnteredValue;
    }
    
    public CellData setUserEnteredValue(final ExtendedValue userEnteredValue) {
        this.userEnteredValue = userEnteredValue;
        return this;
    }
    
    @Override
    public CellData set(final String a1, final Object a2) {
        return (CellData)super.set(a1, a2);
    }
    
    @Override
    public CellData clone() {
        return (CellData)super.clone();
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
        Data.nullOf(TextFormatRun.class);
    }
}
